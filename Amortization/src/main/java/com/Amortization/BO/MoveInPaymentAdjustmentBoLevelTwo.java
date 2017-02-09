package com.Amortization.BO;

import java.util.LinkedHashSet;
import java.util.Set;

public class MoveInPaymentAdjustmentBoLevelTwo {
  float temp;
  float pmnt;
  int numOfPayments;
  boolean match;
  Set<Payment> shedule = new LinkedHashSet<Payment>();
    
  public float getTemp() {
    return temp;
  }
  public void setTemp(float temp) {
    this.temp = temp;
  }
  public float getPmnt() {
    return pmnt;
  }
  public void setPmnt(float pmnt) {
    this.pmnt = pmnt;
  }
  public int getNumOfPayments() {
    return numOfPayments;
  }
  public void setNumOfPayments(int numOfPayments) {
    this.numOfPayments = numOfPayments;
  }
  public boolean isMatch() {
    return match;
  }
  public void setMatch(boolean match) {
    this.match = match;
  }
  public Set<Payment> getShedule() {
    return shedule;
  }
  public void setShedule(Set<Payment> shedule) {
    this.shedule = shedule;
  }
  @Override
  public String toString() {
    return "PairPayment [temp=" + temp + ", pmnt=" + pmnt + ", numOfPayments=" + numOfPayments
        + ", match=" + match + "]";
  }
}
