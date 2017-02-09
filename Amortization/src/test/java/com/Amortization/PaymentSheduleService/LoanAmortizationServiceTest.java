package com.Amortization.PaymentSheduleService;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.Amortization.AmortizationApplicationTests;
import com.Amortization.BO.Payment;
import com.Amortization.BO.PaymentCalculationInput;
import com.Amortization.PaymentSheduleService.LoanAmortizationService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanAmortizationServiceTest {
  
  @Autowired
  private LoanAmortizationService loanAmortizationService;

  @Test
  public void test() {
    PaymentCalculationInput inputData = new PaymentCalculationInput();
    inputData.setLoanAmount(1950.33F);
    inputData.setRate(37.06F);
    inputData.setPaymentsPerYear(12);
    inputData.setTerm(24);
    LocalDate contractDate = LocalDateTime.of(2016, 10, 9, 12, 0).toLocalDate();
    LocalDate accuralDate = LocalDateTime.of(2016, 10, 9, 12, 0).toLocalDate();
    LocalDate firstPayDate = LocalDateTime.of(2017, 02, 22, 12, 0).toLocalDate();
    inputData.setContractDate(contractDate);
    inputData.setAccuralStartDate(accuralDate);
    inputData.setFirstPayDate(firstPayDate);
    System.out.println(inputData.toString());

    float emi = loanAmortizationService.calculatePayment(inputData.getRate(), inputData.getLoanAmount(), inputData.getTerm(), inputData.getPaymentsPerYear());
    //float emi = 125.84F;
    System.out.println(inputData.toString() + " , " + emi);
    float payment = emi;
    Set<Payment> shedule = loanAmortizationService.getPaymentSheduleMain(inputData, payment);
    System.out.println("Shedule Length : " + shedule.size());
    for (Payment payment2 : shedule) {
      System.out.println(payment2.toString());
    }
    fail("Not yet implemented");
  }

}
