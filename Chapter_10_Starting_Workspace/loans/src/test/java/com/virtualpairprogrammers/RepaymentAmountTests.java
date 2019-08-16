package com.virtualpairprogrammers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
public class RepaymentAmountTests {
	
   @Spy
   LoanApplication loanApplication;
   LoanCalculatorController loanCalculatorController;
	
   @Before
   public void setUp() {
	   loanCalculatorController = new LoanCalculatorController();
	   LoanRepository data = mock(LoanRepository.class);
	   JavaMailSender mailSender = mock(JavaMailSender.class);
	   RestTemplate restTemplate = mock(RestTemplate.class);
	   loanCalculatorController.setData(data);
	   loanCalculatorController.setMailSender(mailSender);
	   loanCalculatorController.setRestTemplate(restTemplate);
	   loanApplication = spy(new LoanApplication());	
   }
   @Test
   public void test1YearLoanWholePounds() {   
	   loanApplication.setPrincipal(1200);
	   loanApplication.setTermInMonths(12);
	   doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
	   
	   loanCalculatorController.processNewLoanApplication(loanApplication);
	   assertEquals(new BigDecimal(110), loanApplication.getRepayment());   
   }
   
   @Test
   public void test2YearLoanWholePounds() {	   
	   loanApplication.setPrincipal(1200);
	   loanApplication.setTermInMonths(24);
	   doReturn(new BigDecimal(10)).when(loanApplication).getInterestRate();
	   
	   loanCalculatorController.processNewLoanApplication(loanApplication);
	   assertEquals(new BigDecimal(60), loanApplication.getRepayment());
   }
   @Test
   public void test3YearLoanWithRounding() {
	   loanApplication.setPrincipal(5000);
	   loanApplication.setTermInMonths(60);
	   doReturn(new BigDecimal(6.5)).when(loanApplication).getInterestRate();
	   
	   loanCalculatorController.processNewLoanApplication(loanApplication);
	   assertEquals(new BigDecimal(111), loanApplication.getRepayment());
   }

}
