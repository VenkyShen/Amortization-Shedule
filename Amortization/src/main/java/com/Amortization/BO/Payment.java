package com.Amortization.BO;

import java.time.LocalDate;
import java.util.Date;

public class Payment {
  
  private float rate;
  
  private float initialPrinciple;
  
  private float payment;
  
  private float intrestDue;
  
  private float principleDue;
  
  private float remainingPrinciple;
  
  private float excessAmount;
  
  private float calculatedIntrest;
  
  private float perDayInterest;
  
  private int duration;
  
  private LocalDate startDate;
  
  private LocalDate endDate;
  
  private int paymentsPerYear;

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public float getInitialPrinciple() {
    return initialPrinciple;
  }

  public void setInitialPrinciple(float initialPrinciple) {
    this.initialPrinciple = initialPrinciple;
  }

  public float getPayment() {
    return payment;
  }

  public void setPayment(float payment) {
    this.payment = payment;
  }

  public float getIntrestDue() {
    return intrestDue;
  }

  public void setIntrestDue(float intrestDue) {
    this.intrestDue = intrestDue;
  }

  public float getPrincipleDue() {
    return principleDue;
  }

  public void setPrincipleDue(float principleDue) {
    this.principleDue = principleDue;
  }

  public float getRemainingPrinciple() {
    return remainingPrinciple;
  }

  public void setRemainingPrinciple(float remainingPrinciple) {
    this.remainingPrinciple = remainingPrinciple;
  }

  public float getExcessAmount() {
    return excessAmount;
  }

  public void setExcessAmount(float excessAmount) {
    this.excessAmount = excessAmount;
  }

  public float getCalculatedIntrest() {
    return calculatedIntrest;
  }

  public void setCalculatedIntrest(float calculatedIntrest) {
    this.calculatedIntrest = calculatedIntrest;
  }

  public float getPerDayInterest() {
    return perDayInterest;
  }

  public void setPerDayInterest(float perDayInterest) {
    this.perDayInterest = perDayInterest;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public int getPaymentsPerYear() {
    return paymentsPerYear;
  }

  public void setPaymentsPerYear(int numberOfInstallments) {
    this.paymentsPerYear = numberOfInstallments;
  }

  @Override
  public String toString() {
    return "Payment [rate=" + rate + ", initialPrinciple=" + initialPrinciple + ", payment="
        + payment + ", intrestDue=" + intrestDue + ", principleDue=" + principleDue
        + ", remainingPrinciple=" + remainingPrinciple + ", excessAmount=" + excessAmount
        + ", calculatedIntrest=" + calculatedIntrest + ", perDayInterest=" + perDayInterest
        + ", duration=" + duration + ", startDate=" + startDate + ", endDate=" + endDate
        + ", paymentsPerYear=" + paymentsPerYear + "]";
  }
  
}
