package com.Amortization.PaymentSheduleController;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Amortization.BO.Payment;
import com.Amortization.BO.PaymentCalculationControllerData;
import com.Amortization.BO.PaymentCalculationInput;
import com.Amortization.PaymentSheduleHelper.ControllerHelper;
import com.Amortization.PaymentSheduleService.LoanAmortizationService;

@RestController
public class PaymentSheduleController {

  @Autowired
  private LoanAmortizationService loanAmortizationService;
   
  @Autowired
  private ControllerHelper controllerHelper;
  
  @RequestMapping(value = "/", method = RequestMethod.POST, produces =  { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<String> getPaymentShedule(@RequestBody String request) throws Exception {
    PaymentCalculationControllerData controllerData = controllerHelper.bindJsonToObj(request, PaymentCalculationControllerData.class);
    System.out.println(controllerData.toString());
    PaymentCalculationInput inputData = controllerHelper.getPaymentCalculationInput(controllerData);
    System.out.println(inputData.toString());
    Set<Payment> paymenShedule = loanAmortizationService.createPaymentShedule(inputData);
    String response = controllerHelper.generateJsonFromObj(paymenShedule);
    return ResponseEntity.ok(response);
    
  }
  
}
