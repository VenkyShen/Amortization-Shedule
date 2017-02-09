package com.Amortization.BO;

import java.time.LocalDate;

public class PaymentCalculationInput {
  
  private float loanAmount;
  
  private int Term;
  
  private float rate;
  
  private int paymentsPerYear;
  
  private LocalDate contractDate;
  
  private LocalDate firstPayDate;
  
  private LocalDate accuralStartDate;

  public float getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(float loanAmount) {
    this.loanAmount = loanAmount;
  }

  public int getTerm() {
    return Term;
  }

  public void setTerm(int term) {
    Term = term;
  }

  public float getRate() {
    return rate;
  }

  public void setRate(float rate) {
    this.rate = rate;
  }

  public int getPaymentsPerYear() {
    return paymentsPerYear;
  }

  public void setPaymentsPerYear(int paymentsPerYear) {
    this.paymentsPerYear = paymentsPerYear;
  }

  public LocalDate getContractDate() {
    return contractDate;
  }

  public void setContractDate(LocalDate contractDate) {
    this.contractDate = contractDate;
  }

  public LocalDate getFirstPayDate() {
    return firstPayDate;
  }

  public void setFirstPayDate(LocalDate firstPayDate) {
    this.firstPayDate = firstPayDate;
  }

  public LocalDate getAccuralStartDate() {
    return accuralStartDate;
  }

  public void setAccuralStartDate(LocalDate accuralStartDate) {
    this.accuralStartDate = accuralStartDate;
  }
 
  @Override
  public String toString() {
    return "Payment [loanAmount=" + loanAmount + ", Term=" + Term + ", rate=" + rate
        + ", paymentsPerYear=" + paymentsPerYear + ", contractDate=" + contractDate
        + ", firstPayDate=" + firstPayDate + ", accuralStartDate=" + accuralStartDate + "]";
  }
 
  

}
