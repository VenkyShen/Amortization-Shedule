package com.Amortization.PaymentSheduleService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SimpleCalculationsAndOperations {
  
  public int getNumberOfInstallments(int term, int paymentsPerYear) {
    int numOfInstallments = (term/12)*paymentsPerYear;
    return numOfInstallments;
  }
  
  public float calculatePerDayIntrest(float loanAmount, float rate) {
    float perDayIntrest = (loanAmount * rate)/ (100 * 365); 
    return perDayIntrest;
  }
  
  public int calcDuration(LocalDate startDate, LocalDate endDate) {
    int duration = (int) ChronoUnit.DAYS.between(startDate, endDate);
    if(endDate.isLeapYear()){
      /*if(endDate.getMonth() == Month.MARCH && startDate.getMonthValue() < 3){
        duration = duration - 1;
      }*/
      LocalDate last = endDate.withMonth(2).withDayOfMonth(29); 
      if((startDate.getMonth() == Month.FEBRUARY && startDate.getDayOfMonth() == 29)
          || (last.isAfter(startDate) && last.isBefore(endDate))){
        duration = duration - 1;
      }
    }
    return duration;
  }
  
  public float getPrincipleRemain(Set<com.Amortization.BO.Payment> shedule, float loanAmount) {
    float principleAcheived = getPrincipleAcheived(shedule);
    if(principleAcheived > loanAmount){
      return principleAcheived - loanAmount;
    } 
    return loanAmount- principleAcheived;
  }
  
  public float getPrincipleAcheived(Set<com.Amortization.BO.Payment> currentShedule) {
    float principleAcheived = 0;
    for (com.Amortization.BO.Payment shedule : currentShedule) {
      principleAcheived = principleAcheived + shedule.getPrincipleDue();
    }
    return principleAcheived;
  }
  
  public LocalDate calculateEndDate(LocalDate startDate, int paymentsPerYear, LocalDate firstPayDate) {
    LocalDate endDate = null;
    //LocalDate localDate =  new java.sql.Date(startDate.getTime()).toLocalDate();
    
    // TODO :: date bug
    
    if(paymentsPerYear == 12){
      endDate = startDate.plusMonths(1);
      if(firstPayDate.getDayOfMonth() == 31) {
        endDate = endDate.withDayOfMonth(endDate.getMonth().maxLength());
        if(startDate.getMonth() == Month.JANUARY){
          endDate = startDate.withDayOfMonth(endDate.getMonth().maxLength()-1);
        }
      } else if(firstPayDate.getDayOfMonth() == 1) {
        endDate = endDate.withDayOfMonth(1);
      } else {
        endDate = startDate.plusMonths(1);
      }
    } else if(paymentsPerYear == 26){
      endDate = startDate.plusDays(14);
      /*if(endDate.isLeapYear()){
        LocalDate last = startDate.withMonth(2).withDayOfMonth(29);
        if((endDate.getMonth() == Month.FEBRUARY && endDate.getDayOfMonth() == 29)
            || (last.isAfter(startDate) && last.isBefore(endDate))){
          endDate = endDate.minusDays(1);
        }
      }*/
      
    } else if(paymentsPerYear == 24){
      endDate = startDate.plusDays(15);
      /*if(endDate.isLeapYear()){
        LocalDate last = startDate.withMonth(2).withDayOfMonth(29);
        if((endDate.getMonth() == Month.FEBRUARY && endDate.getDayOfMonth() == 29)
            || (last.isAfter(startDate) && last.isBefore(endDate))){
          endDate = endDate.minusDays(1);
        }
      }*/
    } 
    //Date endDate1 = java.sql.Date.valueOf(endDate);
    return endDate;
  }
  
  public float adjustPaymentDecreaseLevelOne(float payment, float principleDiff, int numOfPayments) {
    float diff = getHalfRounded2DecimalValue(principleDiff/numOfPayments);
    payment = payment - diff;
    return payment;
  }
  
  public float adjustPaymentDecreaseLevelTwo(float temp, float payment) {
    float diff = getPaymentDiffLevelTwo(temp, payment);
    payment = temp - diff;
    payment = getHalfRounded2DecimalValue(payment);
    return payment;
  }
  
  public float getPaymentDiffLevelTwo(float temp, float payment) {
    float diff = 0F; 
    temp = getHalfRounded2DecimalValue(temp);
    payment = getHalfRounded2DecimalValue(payment);
    if(temp > payment){
      diff = (temp - payment)/2;
    } else if(payment > temp){
      diff = (payment - temp)/2;
    }
    diff = getHalfRounded2DecimalValue(diff);
    return diff;
  }
  
  public float adjustPaymentIncreaseLevelTwo(float temp, float payment) {
    float diff = getPaymentDiffLevelTwo(temp, payment);
    payment = payment + diff;
    payment = getHalfRounded2DecimalValue(payment);
    return payment;
  }
  
  public float adjustPaymentIncreaseLevelOne(float payment, float principleDiff, int numOfPayments) {
    System.out.println("Principal Diff : " + principleDiff);
    payment = payment + (principleDiff/numOfPayments);
    
    return payment;
  }
  
  public float getHalfRounded2DecimalValue(float amount) {
    amount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
    return amount;
  }
  
}
