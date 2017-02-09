package com.Amortization.BO;

import java.util.LinkedHashSet;
import java.util.Set;

public class MoveOutPaymentAdjustmentBoLevelOne {
  
  float payment;
  float diff;
  int numOfCycles;
  Set<Payment> shedule = new LinkedHashSet<Payment>();
  
  
  public float getPayment() {
    return payment;
  }
  public void setPayment(float payment) {
    this.payment = payment;
  }
  public float getDiff() {
    return diff;
  }
  public void setDiff(float diff) {
    this.diff = diff;
  }
  public int getNumOfCycles() {
    return numOfCycles;
  }
  public void setNumOfCycles(int numOfCycles) {
    this.numOfCycles = numOfCycles;
  }
  public Set<Payment> getShedule() {
    return shedule;
  }
  public void setShedule(Set<Payment> shedule) {
    this.shedule = shedule;
  }
  @Override
  public String toString() {
    return "MoveOutPaymentAdjustmentBoLevelOne [payment=" + payment + ", diff=" + diff
        + ", numOfCycles=" + numOfCycles + ", shedule=" + shedule + "]";
  }
  

}
