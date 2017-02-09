package com.Amortization.PaymentSheduleService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Amortization.BO.Payment;
import com.Amortization.BO.PaymentCalculationInput;

@Service
public class SheduleCreationSevice {

  @Autowired
  private SimpleCalculationsAndOperations simpleCalculate;

  private int COUNT = 0;;
  
  private Set<Payment> OLD_SHEDULE = null;
  
  
  
  public Set<Payment> getPaymentShedule(PaymentCalculationInput inputData, float payment) {
    float loanAmount = inputData.getLoanAmount();
    Payment pay = fillPaymentBO(inputData, payment, loanAmount);
    int numOfInstallments = simpleCalculate.getNumberOfInstallments(inputData.getTerm(), inputData.getPaymentsPerYear());
    Set<Payment> shedule = createFullPaymentShedule(pay, numOfInstallments, inputData.getFirstPayDate());
    if(OLD_SHEDULE == null) {
      OLD_SHEDULE = shedule;
    } else {
      float oldRemain = simpleCalculate.getPrincipleRemain(OLD_SHEDULE, loanAmount);
      float newRemain = simpleCalculate.getPrincipleRemain(shedule, loanAmount);
      if(oldRemain > newRemain){
        return shedule;
      }
    }
    OLD_SHEDULE = shedule;
    //adjustPayment(payment, shedule, inputData.getLoanAmount(), numOfInstallments, inputData);
    return OLD_SHEDULE;
  }
  
  private Set<Payment> createFullPaymentShedule(Payment firstPayment, int numOfInstallments, LocalDate firstPayDate) {
    Set<Payment> shedule = new LinkedHashSet<Payment>();
    firstPayment = getCalculatedSinglePayment(firstPayment, 0);
    
    // TODO :: For moving the payment date for some specific interval, We can add Logic in here 
    // By Setting the Remaining amount other than principle as excess amount in the first payment will do the movement of paydate.
    //firstPayment.setExcessAmount(firstPayment.getExcessAmount() + 154.60F);
    shedule.add(firstPayment);
    Payment previousPayment = firstPayment;
    int i = 0;
    for (i = 1; i < numOfInstallments; i++){
      Payment currentPayment = new Payment();
      currentPayment = getNextPayment(previousPayment, firstPayDate);
      currentPayment = getCalculatedSinglePayment(currentPayment, previousPayment.getExcessAmount());
      if(currentPayment.getPerDayInterest() < 0)
        break;
      shedule.add(currentPayment);
      previousPayment = currentPayment;
    }
    /*for (Payment payment : shedule) {
      System.out.println(payment.toString());
    }*/
    COUNT ++;
    return shedule;
    
  }
  
  private Payment fillPaymentBO(PaymentCalculationInput inputData, float payment,
      float loanAmount) {
    Payment pay = new Payment();
    payment = simpleCalculate.getHalfRounded2DecimalValue(payment);
    pay.setPayment(payment);
    pay.setInitialPrinciple(simpleCalculate.getHalfRounded2DecimalValue(loanAmount));
    pay.setRate(inputData.getRate());
    float perDayInterest = simpleCalculate.calculatePerDayIntrest(loanAmount, inputData.getRate()); 
    //perDayInterest = simpleCalculate.getHalfRounded2DecimalValue(perDayInterest);
    pay.setPerDayInterest(perDayInterest);
    pay.setStartDate(inputData.getAccuralStartDate());
    pay.setEndDate(inputData.getFirstPayDate());
    pay.setPaymentsPerYear(inputData.getPaymentsPerYear());
    int duration = simpleCalculate.calcDuration(pay.getStartDate(), pay.getEndDate());
    pay.setDuration(duration);
    return pay;
  }
  
  private Payment getCalculatedSinglePayment(Payment pay, float excessAmount) {
    float intrestDue = (pay.getDuration() * pay.getPerDayInterest()) + excessAmount;
    intrestDue = simpleCalculate.getHalfRounded2DecimalValue(intrestDue);
    pay.setCalculatedIntrest(intrestDue);
    float payment = simpleCalculate.getHalfRounded2DecimalValue(pay.getPayment());
    if(intrestDue > payment){
      pay.setExcessAmount(simpleCalculate.getHalfRounded2DecimalValue(intrestDue - payment));
      pay.setIntrestDue(simpleCalculate.getHalfRounded2DecimalValue(payment));
      pay.setPrincipleDue(0);
    } else {
      pay.setExcessAmount(0);
      pay.setIntrestDue(simpleCalculate.getHalfRounded2DecimalValue(intrestDue));
      payment = simpleCalculate.getHalfRounded2DecimalValue(payment);
      intrestDue = simpleCalculate.getHalfRounded2DecimalValue(intrestDue);
      pay.setPrincipleDue(simpleCalculate.getHalfRounded2DecimalValue(payment - intrestDue));
    }
    pay.setRemainingPrinciple(simpleCalculate.getHalfRounded2DecimalValue(pay.getInitialPrinciple() - pay.getPrincipleDue()));
    return pay;
  }
  
  private Payment getNextPayment(Payment previousPayment, LocalDate firstPayDate) {
    Payment currentpayment = new Payment();
    float remPrinciple = previousPayment.getRemainingPrinciple();
    //remPrinciple = new BigDecimal(previousPayment.getRemainingPrinciple()).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
    currentpayment.setInitialPrinciple(remPrinciple);
    currentpayment.setRate(previousPayment.getRate());
    currentpayment.setPayment(new BigDecimal(previousPayment.getPayment()).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue());
    float perDayInterest = simpleCalculate.calculatePerDayIntrest(currentpayment.getInitialPrinciple(), currentpayment.getRate());
    //perDayInterest = new BigDecimal(perDayInterest).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
    currentpayment.setPerDayInterest(perDayInterest);
    currentpayment.setStartDate(previousPayment.getEndDate());
    currentpayment.setPaymentsPerYear(previousPayment.getPaymentsPerYear());
    
    LocalDate endDate = simpleCalculate.calculateEndDate(currentpayment.getStartDate(), currentpayment.getPaymentsPerYear(), firstPayDate);
    currentpayment.setEndDate(endDate);
    int duration = simpleCalculate.calcDuration(currentpayment.getStartDate(), currentpayment.getEndDate());;
    currentpayment.setDuration(duration);
    return currentpayment;
  }

}
