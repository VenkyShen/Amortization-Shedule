package com.Amortization.PaymentSheduleService;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Amortization.BO.Payment;
import com.Amortization.BO.PaymentCalculationInput;

@Service
public class LoanAmortizationService {
  
  @Autowired 
  private SheduleCreationSevice sheduleCreationSevice;
  
  @Autowired 
  private PaymentAdjustmentService paymentAdjustmentService;
  
  @Autowired
  private SimpleCalculationsAndOperations simpleCalculationsAndOperations;
  
  
  public Set<Payment> createPaymentShedule(PaymentCalculationInput inputData) {
    float emi = calculatePayment(inputData.getRate(), inputData.getLoanAmount(), inputData.getTerm(), inputData.getPaymentsPerYear());
    Set<Payment> paymentShedule = getPaymentSheduleMain(inputData, emi);
    return paymentShedule;
  }
    
  public Set<Payment> getPaymentSheduleMain(PaymentCalculationInput inputData, float payment) {
    System.out.println("Inside Get Payment Shedule  ---------- >");
    int numOfInstallments = simpleCalculationsAndOperations.getNumberOfInstallments(inputData.getTerm(), inputData.getPaymentsPerYear());
    payment = simpleCalculationsAndOperations.getHalfRounded2DecimalValue(payment);
    Set<Payment> schedule = sheduleCreationSevice.getPaymentShedule(inputData, payment);
    schedule = paymentAdjustmentService.adjustPayment(payment, schedule, inputData.getLoanAmount(), numOfInstallments, inputData);
    
    System.out.println("Level Two ----------------");
    if(schedule.size() < numOfInstallments) {
      payment = schedule.iterator().next().getPayment();
      schedule = paymentAdjustmentService.adjustPayment(payment, schedule, inputData.getLoanAmount(), numOfInstallments, inputData);
    }
    /*payment = new BigDecimal(shedule.iterator().next().getPayment()).setScale(2, BigDecimal.ROUND_HALF_EVEN).floatValue();
     * System.out.println("Level Three ----------------");
    schedule = paymentAdjustmentService.adjustPayment(payment, schedule, inputData.getLoanAmount(), numOfInstallments, inputData);*/
    return schedule;
  }
  
  public float calculatePayment(float apr, float loanAmount, int terms, int paymentsPerYear) {
    terms = (terms/12)*paymentsPerYear;
    System.out.println("Total Number of Payments : " + terms);
    float rate = (apr/100)/paymentsPerYear;
    float payment = (float) ((loanAmount * rate) / (1 - Math.pow((1 + rate), -terms)));
    return payment;
  }
    
}
