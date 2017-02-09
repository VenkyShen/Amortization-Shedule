package com.Amortization.BO;

public class PaymentCalculationControllerData {
  
  private float loanAmount;
  
  private int term;
  
  private float rate;
  
  private int paymentsPerYear;
  
  private String contractDate;
  
  private String firstPayDate;
  
  private String accuralStartDate;

  public float getLoanAmount() {
    return loanAmount;
  }

  public void setLoanAmount(float loanAmount) {
    this.loanAmount = loanAmount;
  }

  public int getTerm() {
    return term;
  }

  public void setTerm(int terms) {
    term = terms;
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

  
 
  public String getContractDate() {
    return contractDate;
  }

  public void setContractDate(String contractDate) {
    this.contractDate = contractDate;
  }

  public String getFirstPayDate() {
    return firstPayDate;
  }

  public void setFirstPayDate(String firstPayDate) {
    this.firstPayDate = firstPayDate;
  }

  public String getAccuralStartDate() {
    return accuralStartDate;
  }

  public void setAccuralStartDate(String accuralStartDate) {
    this.accuralStartDate = accuralStartDate;
  }

  @Override
  public String toString() {
    return "Payment [loanAmount=" + loanAmount + ", Term=" + term + ", rate=" + rate
        + ", paymentsPerYear=" + paymentsPerYear + ", contractDate=" + contractDate
        + ", firstPayDate=" + firstPayDate + ", accuralStartDate=" + accuralStartDate + "]";
  }
 
  

}
