package com.Amortization.PaymentSheduleHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import com.Amortization.BO.Payment;
import com.Amortization.BO.PaymentCalculationControllerData;
import com.Amortization.BO.PaymentCalculationInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@Component
public class ControllerHelper {

  private final Logger LOG = LoggerFactory.getLogger(ControllerHelper.class);
  
  @Autowired
  private ObjectMapper objectMapper;
  
  public <T> T bindJsonToObj(String request, Class<T> clazz) {
    LOG.info("Request Body --> " + request);
    try {
      T t = objectMapper.readValue(request, clazz);
      return t;
    } catch (IOException e) {
      throw new HttpMessageNotReadableException("IOException", e);
    }
  }
  
  public <T> List<T> bindJsonArrayToList(String request, Class<T> clazz) {
    LOG.info("Request Body --> " + request);
    try {
      List<T> t = objectMapper.readValue(request,
          TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz));
      return t;
    } catch (IOException e) {
      throw new HttpMessageNotReadableException("IOException", e);
    }
  }

  public String generateJsonFromObj(Object obj) throws Exception {
    if(String.class.isInstance(obj)) {
      LOG.info("Response Body --> ");
      LOG.info("" + obj);
      return (String) obj;
    }
    String response;
    try {
      response = objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      LOG.info("Error while createing response, ", e);
      throw new Exception(e);
    }
    LOG.info("Response Body --> " + response);
    return response;
  }

  public PaymentCalculationInput getPaymentCalculationInput(
      PaymentCalculationControllerData controllerInput) {
    PaymentCalculationInput inputData = new PaymentCalculationInput();
    inputData.setLoanAmount(controllerInput.getLoanAmount());
    inputData.setRate(controllerInput.getRate());
    inputData.setPaymentsPerYear(controllerInput.getPaymentsPerYear());
    inputData.setTerm(controllerInput.getTerm());
    LocalDate contractDate = LocalDate.parse(controllerInput.getContractDate());
    LocalDate accuralDate = LocalDate.parse(controllerInput.getAccuralStartDate());
    LocalDate firstPayDate = LocalDate.parse(controllerInput.getFirstPayDate());
    inputData.setContractDate(contractDate);
    inputData.setAccuralStartDate(accuralDate);
    inputData.setFirstPayDate(firstPayDate);
    return inputData;
  }

    
}
