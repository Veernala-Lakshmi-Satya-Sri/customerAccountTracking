package com.bed.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.core.properties.SwaggerUiConfigProperties.Csrf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.Exceptions.SameAccountException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.service.AccountInterface;
import com.bed.service.CustomerInterface;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.boolex.Matcher;
import dto.Amount;


@RunWith(SpringRunner.class)
@WebMvcTest(BankController.class)
class BankControllerTest {

	@Autowired
	MockMvc mockmvc;
	
	@MockBean
	CustomerInterface customerInterface;
	
	@MockBean
	AccountInterface accountInterface;
		
	@Autowired
	ObjectMapper objectMapper;
	
//done	
	@Test
	void addNewCustomerTest() throws Exception {
		
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(12,"Savinfs", 1000000);
		a_list.add(a1);
		Customer c= new Customer(1,"name1", 1234567890, a_list);
	
		when(customerInterface.addCustomer(any())).thenReturn(c);
		
		RequestBuilder request=MockMvcRequestBuilders.post("/customer")
				.accept(MediaType.APPLICATION_JSON)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(c));
		
		MvcResult response =mockmvc.perform(request).andExpect(status().isCreated()).andReturn();
	
		
		String result=response.getResponse().getContentAsString();
	
		Customer addedCustomer= objectMapper.readValue(result,Customer.class);
		
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c));
		
		assertThat(Objects.equals(a_list, addedCustomer.getAccounts()));

		assertEquals("name1", addedCustomer.getCustomerName());
		assertEquals(1234567890, addedCustomer.getCustomerPhn());
		
		//assertEquals(c, addedCustomer);
		
	}
	
	
	//done
	@Test
	void getAllCustomersTest() throws Exception {
		
		List<Customer>c_list= new ArrayList<Customer>();
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(12,"Savings", 1000000);
		a_list.add(a1);
		Set<Account> a_list2= new HashSet<Account>();
		Account a2= new Account(12,"Savings", 1000000);
		a_list.add(a2);
		Customer c= new Customer(1,"name1", 1000000000, a_list);
		Customer c2=new Customer(2,"name2", 1111111111, a_list);
		Customer c3=new Customer(3,"name3", 1222222222, a_list2);
		c_list.add(c);
		c_list.add(c2);
		c_list.add(c3);
	
		when(customerInterface.getCustomers()).thenReturn(c_list);
		
		RequestBuilder request=MockMvcRequestBuilders.get("/customer");
				//.contentType("application/json");
		
		MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		
		
		
		List<Customer> returned= objectMapper.readValue(result, new TypeReference<List<Customer>>(){});
		
		
		assertEquals("name1", returned.get(0).getCustomerName());
		assertEquals("name2", returned.get(1).getCustomerName());
		assertEquals(1222222222, returned.get(2).getCustomerPhn());
		
		
		assertTrue(returned.get(2).getAccounts().equals(a_list2));
		assertEquals(3, returned.size());

		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c_list));
		
		
	}
	
	//done
	@Test
	void getCustomerByIdTest() throws Exception {
		
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(12,"Savinfs", 1000000);
		a_list.add(a1);
		Customer c= new Customer(1,"name1", 1234567890, a_list);
	
		when(customerInterface.getCustomerById(anyInt())).thenReturn(c);
		RequestBuilder request=MockMvcRequestBuilders.get("/customer/{cid}",1);
				//.param("customerId", "1")
				//.accept(MediaType.APPLICATION_JSON)
				//.contentType("application/json");
				
	
		MvcResult response=	mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
	
		Customer returned= objectMapper.readValue(result, Customer.class);
		
		assertEquals("name1", returned.getCustomerName());
		assertEquals(1, returned.getCustomerId());
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c));

	}
	
	//done-but not sure
	@Test
	void addAccountToCustomerTest() throws Exception  {
		
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(11,"Savings", 50000);
		a_list.add(a1);
		Customer c= new Customer(1,"name1", 1234567890, a_list);
		
		Account a2= new Account(12,"Savings", 1000000);
		
		when(customerInterface.addAccount(anyInt(),any())).thenReturn(c);
		RequestBuilder request=MockMvcRequestBuilders.put("/customer/addaccount/{cid}",1)
				//.param("customerId", "1")
				//.accept(MediaType.APPLICATION_JSON)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(a2));
		
		MvcResult response =mockmvc.perform(request).andExpect(status().isCreated()).andReturn();
	
		
		String result=response.getResponse().getContentAsString();
		
		Customer addedCustomer= objectMapper.readValue(result,Customer.class);
		
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c));
		
	}
	
	//done
		@Test
		void editCustomerPersonalDetailsTest() throws Exception {
			
			
			Customer c= new Customer(1,"name1", 1234567890);
			Customer c1= new Customer(1,"name2",1222222222);
			
			when(customerInterface.editCustomerPersonalDetails(anyInt(),any())).thenReturn(c1);
			RequestBuilder request=MockMvcRequestBuilders.put("/customer/edit-personal-details/{cid}", 1)
					//.param("customerId", "1")
					//.accept(MediaType.APPLICATION_JSON)
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(c));
			
			MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
			
			String result=response.getResponse().getContentAsString();
			
			Customer editedCustomer= objectMapper.readValue(result,Customer.class);
			
			
			assertEquals("name2", editedCustomer.getCustomerName());
			assertEquals(1222222222, editedCustomer.getCustomerPhn());
			assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c1));
			
			
		}
	
	
//done -not sure set and list confusion
	@Test
	void getAllAccountsOfCustomer() throws Exception  {
		
		Set<Account> a_list= new HashSet<Account>();
		a_list.add(new Account(11,"Savings1", 90000));
		a_list.add(new Account(12,"Savings2", 20000));
		a_list.add(new Account(13,"Savings3", 50000));
		
		Customer c= new Customer(1,"name1", 1234567890, a_list);
	
		when(accountInterface.getAccountsOfCustomer(anyInt())).thenReturn(a_list);
		RequestBuilder request=MockMvcRequestBuilders.get("/customer/accounts/{cid}",1);
			//	.param("customerId", "1")
			//	.accept(MediaType.APPLICATION_JSON)
			//	.contentType("application/json");
				
	
		MvcResult response=	mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
	
		System.out.println(result);
		List<Account> returned= objectMapper.readValue(result, new TypeReference<List<Account>>(){});
		
		
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(a_list));

	}
	
	
	//done
	@Test
	void getAccountByIdTest() throws Exception {
		
		Account a1= new Account(12,"Savings", 30000);
	
	
		when(accountInterface.getAccountById(anyInt())).thenReturn(a1);
		RequestBuilder request=MockMvcRequestBuilders.get("/accounts/byid/{aid}",12);
				//.param("customerId", "1")
				//.accept(MediaType.APPLICATION_JSON)
				//.contentType("application/json");
				
	
		MvcResult response=	mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
	
		Account returned= objectMapper.readValue(result, Account.class);
		
		assertEquals("Savings", returned.getAccountType());
		assertEquals(30000, returned.getBalance());
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(a1));

	}
	
	

//DONE	
	@Test
	void getAccountOfCustomerByAccountIdTest() throws Exception {
		
		Account a1= new Account(12,"Savings", 20000);
	
		when(accountInterface.getAccountOfCustomer(anyInt(), anyInt())).thenReturn(a1);
		RequestBuilder request=MockMvcRequestBuilders.get("/customer/account/{cid}/{aid}",1,12);
				//.param("cid", "1")
			//.param("aid", "12");
				
				
	
		MvcResult response=	mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
	
		Account returned= objectMapper.readValue(result, Account.class);
		
		assertEquals("Savings", returned.getAccountType());
		assertEquals(20000, returned.getBalance());
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(a1));
	}
	

	//done
	@Test
	void getAllAccountTest() throws Exception {
		
		List<Account> a_list= new ArrayList<Account>();
		a_list.add(new Account(11,"Joint", 11000));
		a_list.add(new Account(12,"Savings", 2000));
		a_list.add(new Account(13,"Salary", 10000));
	
		when(accountInterface.getAccounts()).thenReturn(a_list);
		
		RequestBuilder request=MockMvcRequestBuilders.get("/accounts");
				//.contentType("application/json");
		
		
		MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		
		
		List<Account> returned= objectMapper.readValue(result, new TypeReference<List<Account>>(){});
		
		assertEquals("Joint", returned.get(0).getAccountType());
		assertEquals(12, returned.get(1).getAcId());
		assertEquals(10000, returned.get(2).getBalance());
		
		assertFalse(returned.isEmpty());
		assertEquals(3, returned.size());
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(a_list));
		
		
	}
	
//DONE	
	@Test
	void  deleteCustomerByIdTest() throws Exception {
	
		
		when(customerInterface.deleteCustomer(1)).thenReturn("deleted");
		RequestBuilder request = MockMvcRequestBuilders.delete("/customer/{cid}",1);
								
		MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		assertEquals("deleted", result);
								
				
	}
//done	
	
	@Test
	void  deleteAccountFromCoustomerTest() throws Exception {
		
		
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(12,"Savings", 70000);
		a_list.add(a1);
		Customer c= new Customer(1,"name1", 1234567890, a_list);
		
		when(customerInterface.deleteAccountFromCustomer(1,1)).thenReturn(c);
		RequestBuilder request = MockMvcRequestBuilders.delete("/customer/{cid}/{aid}",1,1);
				
								
		MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		
		
		Customer returned= objectMapper.readValue(result, Customer.class);
		
		assertEquals("name1", returned.getCustomerName());
		assertEquals(1234567890, returned.getCustomerPhn());
		
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c));
								
				
	}
	//DONE
	@Test
	void tranferFundsToSelfAccountTest() throws Exception{
		Amount amount= new Amount(1000);
//		
//		List<Account> a_list= new ArrayList<Account>();
//		
//		a_list.add(new Account(11,"Savings1", 40000));
//		a_list.add(new Account(12,"Savings2", 70000));
		
		Set<Account> a_list= new HashSet<Account>();
		
		a_list.add(new Account(11,"Savings", 40000));
		a_list.add(new Account(12,"Savings", 70000));
		
		Customer c= new Customer(1,"name1", 1227236451, a_list);

		when(customerInterface.selfAccountFundsTransfer(anyFloat(),anyInt(),anyInt(),anyInt())).thenReturn(c);
		RequestBuilder request=MockMvcRequestBuilders.put("/customer/self-transfer/{cid}/{fromac}/{toac}", 1,1,2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(amount));
		
		
		MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		
		
		//List<Account> returned= objectMapper.readValue(result, new TypeReference<List<Account>>(){});
		
		Customer returned= objectMapper.readValue(result,Customer.class);

		assertEquals("name1", returned.getCustomerName());
		assertThat(Objects.equals(a_list, returned.getAccounts()));
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c));
		
		
	}
	
//done		
	@Test
	void tranferFundsToOthersTest() throws Exception{
		Amount amount= new Amount(1000);
		
		Set<Account> a_list1= new HashSet<Account>();
		Set<Account> a_list2= new HashSet<Account>();
		
		a_list1.add(new Account(11,"Savings", 40000));
		a_list2.add(new Account(12,"Savings", 70000));
		
		Customer c= new Customer(1,"Satya",1234567899, a_list1);
		Customer c2= new Customer(1,"Satya2",1234567890, a_list2);

		List<Customer> c_list= new ArrayList<Customer>();
		c_list.add(c);
		c_list.add(c2);
		when(customerInterface.tranferFundsToOther(anyFloat(),anyInt(),anyInt(),anyInt(),anyInt())).thenReturn(c_list);
		RequestBuilder request=MockMvcRequestBuilders.put("/customer/funds-transfer/{cid}/{cid2}/{fromac}/{toac}", 1,2,1,2)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(amount));
		
		
		MvcResult response =mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		

		List<Customer> returned= objectMapper.readValue(result, new TypeReference<List<Customer>>(){});
		

//		assertEquals("Savings", returned.get(1).getAccountType());
//		assertEquals(40000, returned.get(0).getBalance());
		
		assertEquals("Satya", returned.get(0).getCustomerName());
		assertEquals(1234567890, returned.get(1).getCustomerPhn());
		
		assertThat(Objects.equals(a_list1, returned.get(0).getAccounts()));

		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c_list));
		
		
	}
	
	//done
	@Test
	void getCustomersByNameTest() throws Exception {
		List<Customer>c_list= new ArrayList<Customer>();

		Set<Account> a_list= new HashSet<Account>();
		a_list.add(new Account(12,"Savings", 1000000));
		Customer c= new Customer(1,"Satya", 1234567890, a_list);
		c_list.add(c);
		
		//String name="Satya";
		when(customerInterface.getCustomersByName(anyString())).thenReturn(c_list);
		RequestBuilder request= MockMvcRequestBuilders.get("/customers/byname/{cname}", "satya");

		MvcResult response= mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		String result=response.getResponse().getContentAsString();
		
		
		List<Customer> returned= objectMapper.readValue(result, new TypeReference<List<Customer>>(){});
		
		assertEquals(1, returned.size());
		assertEquals("Satya", returned.get(0).getCustomerName());
		
		
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(c_list));

		
				
	}
	
//done	
	@Test
	void getAccountswithHigherBalanceTest() throws Exception {

		Set<Account> a_list= new HashSet<Account>();
		a_list.add(new Account(11,"Joint", 11000));
		a_list.add(new Account(12,"Savings", 2000));
		a_list.add(new Account(13,"Salary", 10000));
		
		
		when(accountInterface.getAccountsWithHigherBalance(anyInt(),anyFloat())).thenReturn(a_list);
		RequestBuilder request= MockMvcRequestBuilders.get("/accounts/bybalance/{cid}/{minbalance}", 1,2000);

		MvcResult response= mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		
		
		
		Set<Account> returned= objectMapper.readValue(result, new TypeReference<Set<Account>>(){});
	
		
		assertFalse(returned.isEmpty());
		assertEquals(3, returned.size());
		
		assertThat(Objects.equals(a_list, returned));

		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(a_list));

		
				
	}
	
	@Test
	void getAccountsByType() throws Exception {

		List<Account> a_list= new ArrayList<Account>();
		a_list.add(new Account(11,"Joint", 11000));
		a_list.add(new Account(12,"Salary", 2000));
		a_list.add(new Account(13,"Salary", 10000));
		
		
		when(accountInterface.getAccountsByType(anyString())).thenReturn(a_list);
		RequestBuilder request= MockMvcRequestBuilders.get("/accounts/bytype/{type}", "Salary");

		MvcResult response= mockmvc.perform(request).andExpect(status().isOk()).andReturn();
		
		String result=response.getResponse().getContentAsString();
		
		
		
		List<Account> returned= objectMapper.readValue(result, new TypeReference<List<Account>>(){});
		
		assertEquals("Joint", returned.get(0).getAccountType());
		assertEquals(12, returned.get(1).getAcId());
		assertEquals(10000, returned.get(2).getBalance());
		
		assertFalse(returned.isEmpty());
		assertEquals(3, returned.size());
		
		assertThat(result).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(a_list));

		
				
	}
	
}







//assertEquals(c,assertThat(objectMapper.readValue(response, Customer.class)));
//
//System.out.println(result);
//	.andExpect(jsonPath("$.customerName").value("name1"));jsonPath("$.customerName").value(c.getCustomerName())

//






