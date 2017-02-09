package com.Amortization.PaymentSheduleService;

import java.math.BigDecimal;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Amortization.BO.MoveInPaymentAdjustmentBoLevelTwo;
import com.Amortization.BO.MoveOutPaymentAdjustmentBoLevelOne;
import com.Amortization.BO.Payment;
import com.Amortization.BO.PaymentCalculationInput;

@Service
public class PaymentAdjustmentService {

  @Autowired
  private SimpleCalculationsAndOperations simpleCalculate;
  
  @Autowired
  private SheduleCreationSevice sheduleCreationSevice;
  
  public Set<Payment> adjustPayment(float payment, Set<Payment> currentShedule,
      float principle, int numOfPayments, PaymentCalculationInput inputData) {
    float principleAcheived = simpleCalculate.getPrincipleAcheived(currentShedule);
    float principleDiff = principleAcheived - principle;
    //System.out.println("Principle : " + principleDiff);
    if (principleDiff > 0) {
      // Do the decreasing

      System.out.println("Decreasing Data : ");

      float temp = payment;
      float pay = simpleCalculate.adjustPaymentDecreaseLevelOne(temp, principleDiff, numOfPayments);
      MoveOutPaymentAdjustmentBoLevelOne moveOutPaymentBO = doLevelOneCalculation(principle, inputData, pay);
      float newPrincipleDiff = moveOutPaymentBO.getDiff();
      while (newPrincipleDiff > 0 && moveOutPaymentBO.getNumOfCycles() < numOfPayments) {
        // System.out.println("While one -- 1");
        temp = pay;
        pay = simpleCalculate.adjustPaymentDecreaseLevelOne(temp, newPrincipleDiff, numOfPayments);
        moveOutPaymentBO = doLevelOneCalculation(principle, inputData, pay);
      }

      // Level Two comparison starts here

      System.out.println(" Temp : " + temp + " Pay : " + pay);
      MoveInPaymentAdjustmentBoLevelTwo moveInPayAdjustBo = doLevelTwoComparision(inputData, temp, pay);
      while (!moveInPayAdjustBo.isMatch() || moveInPayAdjustBo.getNumOfPayments() < numOfPayments) {
        System.out.println("While one -- 2 -- NumOfPmnts : " + moveInPayAdjustBo.getNumOfPayments() + " -- " +  numOfPayments);
        moveInPayAdjustBo =
            doLevelTwoComparision(inputData, moveInPayAdjustBo.getTemp(), moveInPayAdjustBo.getPmnt());
      }
      /*float averagePayment = (pairPayment.getTemp()+pairPayment.getPmnt())/2;
      averagePayment = new BigDecimal(averagePayment).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
      shedulePair.setShedule(getPaymentShedule(inputData, averagePayment));*/
      return moveInPayAdjustBo.getShedule();
       

    } else {
      // Do the increasing

      System.out.println("Increasing data");
      System.out.println("PAymenttttt : " + payment);
      float pay = payment;
      float temp = simpleCalculate.adjustPaymentIncreaseLevelOne(pay, -principleDiff, numOfPayments);
      System.out.println("Pay : " + pay + " Temp : " + temp);
      MoveOutPaymentAdjustmentBoLevelOne diffAndNumOfCycles = doLevelOneCalculation(principle, inputData, temp);
      float newPrincipleDiff = diffAndNumOfCycles.getDiff();
      while (newPrincipleDiff < 0) {
        // System.out.println("While one -- 11");
        temp = pay;
        pay = simpleCalculate.adjustPaymentIncreaseLevelOne(temp, newPrincipleDiff, numOfPayments);
        diffAndNumOfCycles = doLevelOneCalculation(principle, inputData, pay);
        newPrincipleDiff = diffAndNumOfCycles.getDiff();
      }

      // Level Two comparison starts here

      MoveInPaymentAdjustmentBoLevelTwo moveInPaymentBO = doLevelTwoComparision(inputData, temp, pay);
      while (!moveInPaymentBO.isMatch()) {
        // System.out.println("While one -- 12");
        moveInPaymentBO =
            doLevelTwoComparision(inputData, moveInPaymentBO.getTemp(), moveInPaymentBO.getPmnt());
      }
      /*float averagePayment = (pairPayment.getTemp()+pairPayment.getPmnt())/2;
      averagePayment = new BigDecimal(averagePayment).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
      shedulePair.setShedule(getPaymentShedule(inputData, averagePayment));*/
      return moveInPaymentBO.getShedule();
    }
  }
  
  private MoveOutPaymentAdjustmentBoLevelOne doLevelOneCalculation(float principle, PaymentCalculationInput inputData,
      float pay) {
    MoveOutPaymentAdjustmentBoLevelOne moveOutPaymentBo = new MoveOutPaymentAdjustmentBoLevelOne();
    System.out.println("Payment --- > " + pay);
    Set<Payment> newPaymentShedule = sheduleCreationSevice.getPaymentShedule(inputData, pay);
    //System.out.println("Shedule" + newPaymentShedule.size());
    float newPrincipleAcheived = simpleCalculate.getPrincipleAcheived(newPaymentShedule);
    //System.out.println("New Diff : " + newPrincipleAcheived + " ---- " + principle);
    float newPrincipleDiff = newPrincipleAcheived - principle;
    moveOutPaymentBo.setNumOfCycles(newPaymentShedule.size());
    moveOutPaymentBo.setDiff(newPrincipleDiff);
    moveOutPaymentBo.setPayment(pay);
    return moveOutPaymentBo;
  }
  
  private MoveInPaymentAdjustmentBoLevelTwo doLevelTwoComparision(PaymentCalculationInput inputData, float temp, float pay) {
    boolean match = false;
    MoveInPaymentAdjustmentBoLevelTwo moveInPaymentBO = adjustPaySheduleLevelTwo(inputData, temp, pay);
    BigDecimal tempDec = new BigDecimal(moveInPaymentBO.getTemp()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    BigDecimal pmntDec = new BigDecimal(moveInPaymentBO.getPmnt()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    System.out.println("TempDec : " + tempDec + " PmntDec: " + pmntDec);
    BigDecimal averageDec = new BigDecimal((float)(tempDec.floatValue() + pmntDec.floatValue())/2).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    //System.out.println("Average : " + averageDec);
    if(tempDec.floatValue() == pmntDec.floatValue() || averageDec.floatValue() == tempDec.floatValue() || averageDec.floatValue() == pmntDec.floatValue()) {
      match = true;
      System.out.println("Match : " + match);
    }
    moveInPaymentBO.setMatch(match);
    //System.out.println(pairPayment.toString());
    return moveInPaymentBO;
  }
  
  private MoveInPaymentAdjustmentBoLevelTwo adjustPaySheduleLevelTwo(PaymentCalculationInput inputData, float temp,
      float pay) {
    MoveInPaymentAdjustmentBoLevelTwo moveInPaymentBO = new MoveInPaymentAdjustmentBoLevelTwo();
    moveInPaymentBO.setTemp(temp);
    moveInPaymentBO.setPmnt(pay);
    float pmnt = 0F;
    if(temp > pay){
      pmnt = simpleCalculate.adjustPaymentDecreaseLevelTwo(temp, pay);
      pmnt = new BigDecimal(pmnt).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
      Set<Payment> newPmntShedule = sheduleCreationSevice.getPaymentShedule(inputData, pmnt);
      moveInPaymentBO.setShedule(newPmntShedule);
      moveInPaymentBO.setNumOfPayments(newPmntShedule.size());
      float newPrncplAcheived = simpleCalculate.getPrincipleAcheived(newPmntShedule);
      if(newPrncplAcheived > 0) {
        if(newPrncplAcheived < inputData.getLoanAmount()) {
          moveInPaymentBO.setPmnt(pmnt);
        } else {
          moveInPaymentBO.setTemp(pmnt);
        }
      } else {
        moveInPaymentBO.setPmnt(pmnt);
      }
    } else {
      pmnt = simpleCalculate.adjustPaymentIncreaseLevelTwo(pay, temp);
      new BigDecimal(pmnt).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
      Set<Payment> newPmntShedule = sheduleCreationSevice.getPaymentShedule(inputData, pmnt);
      moveInPaymentBO.setShedule(newPmntShedule);
      moveInPaymentBO.setNumOfPayments(newPmntShedule.size());
      float newPrncplAcheived = simpleCalculate.getPrincipleAcheived(newPmntShedule);
      if(newPrncplAcheived < 0) {
        moveInPaymentBO.setPmnt(pmnt);
      } else {
        if(newPrncplAcheived > inputData.getLoanAmount()) {
          moveInPaymentBO.setTemp(pmnt);
        } else {
          moveInPaymentBO.setTemp(pmnt);
        }
        
        moveInPaymentBO.setPmnt(pmnt);
      }
    }
    return moveInPaymentBO;
  }
  
}
