package com.bed.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.tomcat.util.json.JSONParser;
import org.h2.util.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.bed.CustomerAccountTrackingApplication;
import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.MyExceptionResponseFormat;
import com.bed.Exceptions.SameAccountException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.model.Customers;
import com.bed.repositary.AccountRepo;
import com.bed.repositary.CustomerRepo;
import com.bed.service.CustomerInterface;
import com.bed.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.CustomerDetails;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;

/*
@SpringBootTest(classes = CustomerAccountTrackingApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class IntegrationTesting {

	@LocalServerPort
	int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	


	@Test
	@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	public void testAddCustomer() throws Exception {

		Set<Account> a_list= new HashSet<Account>();
		a_list.add(new Account("Savings",1200));
		Customer c= new Customer(2,"Satya", 1234567890,a_list);
		CustomerDetails dto= new CustomerDetails();
		dto.setCustomer(c);
		
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		
//
//		HttpEntity<CustomerDetails> requestEntity = new HttpEntity<>(dto,headers);
//		    
//		ResponseEntity<Customer> returned = restTemplate.exchange("http://localhost:" +port+"/customer",HttpMethod.POST, requestEntity, Customer.class);
//		System.out.println(returned.getBody().getCustomerName());
		
		
		
		ResponseEntity<Customer> returned = this.restTemplate
				.postForEntity("http://localhost:" + port + "/customer", dto, Customer.class);
		assertEquals(201, returned.getStatusCodeValue());
		assertEquals(2, returned.getBody().getCustomerId());
		assertEquals("Satya", returned.getBody().getCustomerName());
		Set<Account> aa= returned.getBody().getAccounts();

		assertEquals(1, returned.getBody().getAccounts().size());
		


	}
	
	
	@Test
	public void testGetCustomers() throws Exception{

//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		//HttpEntity<Customers> requestEntity = new HttpEntity<Customers>(null,headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" +port+"/customer",HttpMethod.GET,null,
		String.class);
		List<Customer> returned= objectMapper.readValue(response.getBody(), new TypeReference<List<Customer>>(){});
		
		assertEquals("SatyaTestData" ,returned.get(0).getCustomerName());
		assertEquals(1 ,returned.size());
//		
//		Customers responseEntity = this.restTemplate
//		.getForObject("http://localhost:" + port + "/customer",Customers.class);
		
		//assertEquals(1 ,responseEntity.getCustomerList());
		

//		Customer[] responseEntity = this.restTemplate
//				.getForObject("http://localhost:" + port + "/customer",Customer[].class);
//		assertEquals(1 ,responseEntity[0].getCustomerName());
//		

//		ResponseEntity<String> responseEntity = this.restTemplate
//				.getForEntity("http://localhost:" + port + "/customer",String.class);
//		MyExceptionResponseFormat customeError= objectMapper.readValue(responseEntity.getBody(),MyExceptionResponseFormat.class);
//		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//		assertTrue(customeError.getError().equalsIgnoreCase("No customers present"));
		
		

	}
	////getting edited customer details
	@Test
	public void testGetCustomersById() throws Exception{
		
		HttpHeaders headers = new HttpHeaders();
		//  headers.setContentType(MediaType.APPLICATION_JSON);
		 // headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	//	String url= "http://localhost:" +port+"/customer";

//		HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);
//		
//		ResponseEntity<Customer[]> responseEntity = restTemplate.exchange("http://localhost:" +port+"/customer",HttpMethod.GET,requestEntity,
//		Customer[].class);
//		
//		ResponseEntity<CustomerDetails> responseEntity = this.restTemplate
//				.getForEntity("http://localhost:" + port + "/customer",CustomerDetails.class);
		
		ResponseEntity<Customer> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/customer/{id}",Customer.class,1);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("SatyaTestData", responseEntity.getBody().getCustomerName());
		

	}
	
//	@Test
//	public void testAddAccountToCustomer() throws Exception {
//		
//		
//		Account account= new Account(13,"Savings3",1200);
//		
////DETACHED ENTITY PASSED 		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//		HttpEntity<Account> requestEntity = new HttpEntity<Account>(account,headers);
//		
//		ResponseEntity<Customer> returned = restTemplate.exchange("http://localhost:" +port+"/customer/addaccount/{cid}",
//				HttpMethod.PUT 
//				,requestEntity,Customer.class,1);
//		
//
////	 this.restTemplate.put("http://localhost:" + port + "/customer/addaccount/{id}", account,1);
////
////		assertEquals(201, returned.getStatusCodeValue());
////		
////		assertEquals(1, returned.getBody().getCustomerId());
////		assertEquals("Satya", returned.getBody().getCustomerName());
////		assertEquals(2, returned.getBody().getAccounts().size());
//
//
//	}
	@Test
	@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	public void testEditCustomerPersonalDetails() throws Exception {
	
		
		Customer customer= new Customer(1,"New Satya",1234567899);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Customer> requestEntity = new HttpEntity<Customer>(customer,headers);
		
		ResponseEntity<Customer> returned = restTemplate.exchange("http://localhost:" +port+"/customer/edit-personal-details/{cid}",
				HttpMethod.PUT 
				,requestEntity,Customer.class,1);

	// this.restTemplate.put("http://localhost:" + port + "/customer/edit-personal-details/{cid}", customer,1);

		assertEquals(200, returned.getStatusCodeValue());
		
		assertEquals(1, returned.getBody().getCustomerId());
		assertEquals("New Satya", returned.getBody().getCustomerName());
		assertEquals(2, returned.getBody().getAccounts().size());


	}
	
	
	@Test
	public void testGetAllAccountsOfCustomer() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(null,headers);
		
		ResponseEntity<Account[]> response = restTemplate.exchange("http://localhost:" +port+"/customer/accounts/{cid}",
				HttpMethod.GET,requestEntity,Account[].class,1);
		
	
		
		assertEquals(2 ,response.getBody().length);
		
	}
	
//Generating new IDs each time	
//	@Test
//	public void testGetAccountById() throws Exception {
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<Account> requestEntity = new HttpEntity<Account>(null,headers);
//		
//		ResponseEntity<Account> response = restTemplate.exchange("http://localhost:" +port+"/accounts/byid/{aid}",
//				HttpMethod.GET,requestEntity,Account.class,11);
//		System.out.println(response);
////		List<Account> returned= objectMapper.readValue(response.getBody(), new TypeReference<List<Account>>(){});
////		System.out.println(returned);
//		
//		
//		assertEquals(1200 ,response.getBody().getBalance());
//		assertEquals("Joint" ,response.getBody().getAccountType());
//		//assertEquals(2 ,response.getBody());
//		
//	}
//	
	// adding one more extra account because of addcustomertest
	@Test
	public void testGetAllAccounts() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(null,headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" +port+"/accounts",
				HttpMethod.GET,requestEntity,String.class);
		
		
		System.out.println(response);

		List<Account> returned= objectMapper.readValue(response.getBody(), new TypeReference<List<Account>>(){});
		
		assertEquals("Savings" ,returned.get(0).getAcId());
		assertEquals("Savings" ,returned.get(1).getAcId());
		assertEquals(2 ,returned.size());
		
		
	}
	
	
	
	@Test
	@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	public void testDeleteCustomer() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(null,headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" +port+"/customer/{cid}",
				HttpMethod.DELETE,requestEntity,String.class,1);
		System.out.println(response);

		assertEquals("Customer with id 1 is deleted" ,response.getBody());

		
	}
	
	@Test
	public void testDeleteAccountFromCustomer() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<String>(null,headers);
		
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" +port+"/customer/{cid}/{aid}",
				HttpMethod.DELETE,null,String.class,1,1);
		System.out.println("delete---------"+response.getBody());

		//assertEquals(1 ,response.getBody().getCustomerName());

		
	}
	

	

}
*/























