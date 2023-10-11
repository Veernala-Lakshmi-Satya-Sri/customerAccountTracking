package com.bed.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bed.Exceptions.InsufficientBalance;
import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.NotBelongsException;
import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.Exceptions.SameAccountException;
import com.bed.Exceptions.SingleAccountException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.repositary.AccountRepo;
import com.bed.repositary.CustomerRepo;

import jakarta.validation.ConstraintViolationException;

@ExtendWith(MockitoExtension.class)
class CustomerInterfaceTest {
	
	
	@Mock
	CustomerRepo customerRepo;
	
	@Mock
	AccountRepo accountRepo;
	
	@Mock
	AccountInterface accountService;
	
//	@InjectMocks
//	CustomerInterface customerService= new CustomerService();
	
	@InjectMocks
	CustomerService customerService;
	
	
	
	
	
	@Test
	void addCustomerTest() throws ConstraintViolationException, InvalidCredintialsException, SameAccountException  {
		
		Set<Account> a_list= new HashSet<Account>();
		
		Account	a1= new Account(12,"Savinfs", 1000000);
		a_list.add(a1);
		Customer c= new Customer(1,"name", 1234567890, a_list);
		
		when(customerRepo.save(c)).thenReturn(c);
		
		Customer added= customerService.addCustomer(c);
		
		assertEquals("name",added.getCustomerName());
		 
		assertEquals(c, added);
		
		
	}
	
	

	

	@Test
	void getCustomersTest() throws ResourseNotFoundException  {
		
		List<Customer>c_list= new ArrayList<Customer>();
		
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(12,"Savinfs", 1000000);
		a_list.add(a1);
		
		Customer c1= new Customer(1,"name1", 1234567899, a_list);
		Customer c2=new Customer(2,"name2", 1234567890, a_list);
		Customer c3=new Customer(3,"name3", 1234567891, a_list);
		c_list.add(c1);
		c_list.add(c2);
		c_list.add(c3);
		
		
		 when(customerRepo.findAll()).thenReturn(c_list);
		
		 List<Customer> returnedList= customerService.getCustomers();
		 
		 assertEquals("name1",c_list.get(0).getCustomerName());
		 assertEquals(1234567891,c_list.get(2).getCustomerPhn());
		 assertEquals(a_list, c_list.get(0).getAccounts());
		 assertEquals(3,returnedList.size());
		
		
		
	}

	
	@Test
	void getCustomerByIdTest() throws ResourseNotFoundException {
		
	List<Customer>c_list= new ArrayList<Customer>();
		Set<Account> a_set= new HashSet<Account>();
		Account a1= new Account(12,"Savinfs", 1000000);
		a_set.add(a1);
		Customer c= new Customer(1,"name1", 1234567890, a_set);
		Customer c2=new Customer(2,"name2", 1234567890, a_set);
		Customer c3=new Customer(3,"name3", 1234567890, a_set);
		c_list.add(c3);
		c_list.add(c2);
		c_list.add(c);
		
		 //Optional<Customer> c4= new Optional<Customer>(c);
		
		 when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(c));
		//when(customerRepo.findById(1).get()).thenReturn(c);
		 Customer returnedCustomer= customerService.getCustomerById(1);
		 
		 
		 assertEquals("name1", returnedCustomer.getCustomerName());
		 assertThat(returnedCustomer).isEqualTo(c);
		 assertEquals(1, returnedCustomer.getAccounts().size());

		
	}
	
	@Test
	void addAccountTest() throws ResourseNotFoundException, SameAccountException {
		
		Set<Account> a_list= new HashSet<Account>();
		Account	a1= new Account(12,"Savings", 1000000);
		
		Account	a2= new Account(1,"Savings2", 1000000);
		a_list.add(a1);
		Customer c= new Customer(1,"name", 1234567890, a_list);
		
		
		when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(c));
		when(accountRepo.save(a2)).thenReturn(a2);
		when(customerRepo.save(c)).thenReturn(c);
		Customer returnedCustomer= customerService.addAccount(1, a2);
		
		
		assertEquals(c, returnedCustomer);
		assertEquals(2, returnedCustomer.getAccounts().size());
		
		
		
	}
	
	@Test
	void editCustomerPersonalDetailsTest() throws ResourseNotFoundException, InvalidCredintialsException{
		
		Set<Account> a_list= new HashSet<Account>();
		Account	a1= new Account(12,"Savings", 1000000);
		Account	a2= new Account(1,"Savings2", 1000000);
		a_list.add(a1);
		
		Customer updateDetails= new Customer(1,"old", 1234567899, a_list);
		
		Customer newDetails= new Customer(1,"new", 1234567890);
		
		
		when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(updateDetails));
		when(customerRepo.save(updateDetails)).thenReturn(updateDetails);
		
		Customer returnedCustomer= customerService.editCustomerPersonalDetails(1, newDetails);
		
		
		assertEquals(updateDetails, returnedCustomer);
		assertEquals("new", returnedCustomer.getCustomerName());
		assertEquals(1, returnedCustomer.getCustomerId());
		assertEquals(1234567890, returnedCustomer.getCustomerPhn());
		
	}
	
	@Test
	void selfAccountFundsTransferTest() throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException {
		
		Set<Account> a_list= new HashSet<Account>();
		Account	a1= new Account(1,"Savings", 10000);
		Account	a2= new Account(2,"Savings2",10000);
		a_list.add(a1);
		a_list.add(a2);
		Customer c= new Customer(1,"name", 1234567890, a_list);
		
		
		when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(c));
		when(accountService.getAccountOfCustomer(1, 1)).thenReturn(a1);
		when(accountService.getAccountOfCustomer(1, 2)).thenReturn(a2);
		when(accountRepo.save(a1)).thenReturn(a1);
		when(accountRepo.save(a2)).thenReturn(a2);
		//when(customerRepo.save(c)).thenReturn(c);
		Customer returned= customerService.selfAccountFundsTransfer(1000,1,1,2);
		
		
		assertEquals(9000, a1.getBalance());
		assertEquals(11000, a2.getBalance());
	}
	
	@Test
	void transferFundsToOthers() throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException  {
		
		Set<Account> a_list1= new HashSet<Account>();
		Account	a1= new Account(11,"Savings", 100);
		a_list1.add(a1);
		
		Set<Account> a_list2= new HashSet<Account>();
		Account	a2= new Account(22,"Savings2", 100);
		a_list2.add(a2);
		
		Customer c1= new Customer(1,"name1", 1234567890, a_list1);
		Customer c2= new Customer(2,"name", 1234567890, a_list2);
		
		when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(c1));
		when(customerRepo.findById(2)).thenReturn(Optional.ofNullable(c2));
		when(accountService.getAccountOfCustomer(1, 11)).thenReturn(a1);
		when(accountService.getAccountOfCustomer(2, 12)).thenReturn(a2);
		when(accountRepo.save(a1)).thenReturn(a1);
		when(accountRepo.save(a2)).thenReturn(a2);
		//when(customerRepo.save(c1)).thenReturn(c1);
		List<Customer> returnedAccount= customerService.tranferFundsToOther(10,1,2,11,12);
		
		
		assertEquals(90, a1.getBalance());
		assertEquals(110, a2.getBalance());
	}
	
	
	@Test
	void deleteCustomerTest() throws ResourseNotFoundException {
		Set<Account> a_list= new HashSet<Account>();
		Account	a1= new Account(22,"Savings2", 10000);
		a_list.add(a1);
		Customer c= new Customer(1,"name", 1234567890, a_list);
		
		
		when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(c));
		
		customerService.deleteCustomer(1);
		
		verify(customerRepo, times(1)).deleteById(1);
		
		
	}
	
	@Test
	void deleteAccountFromCustomerTest() throws ResourseNotFoundException, NotBelongsException, SingleAccountException {
		Set<Account> a_list= new HashSet<Account>();
		Account	a1= new Account(11,"Savings", 10000);
		Account	a2= new Account(12,"Savings", 10000);
		a_list.add(a1);
		a_list.add(a2);
		Customer c= new Customer(1,"name", 1234567890, a_list);
		
		
		assertEquals(2, c.getAccounts().size());///before deleting
		
		when(accountService.getAccountById(anyInt())).thenReturn(a2);
		when(accountService.getAccountOfCustomer(1, 11)).thenReturn(a1);
		when(customerRepo.findById(1)).thenReturn(Optional.ofNullable(c));	
		when(customerRepo.save(c)).thenReturn(c);
		
		Customer returnedCustomer= customerService.deleteAccountFromCustomer(1, 11);
		
		
		assertEquals(c, returnedCustomer);
		assertEquals(1, c.getAccounts().size());
		
	}
	
	@Test
	void getCustomerByNameTest() throws ResourseNotFoundException {
		List<Customer>c_list= new ArrayList<Customer>();
		Set<Account> a_list= new HashSet<Account>();
		Account a1= new Account(12,"Savinfs", 1000000);
		a_list.add(a1);
		Customer c= new Customer(1,"raj", 1234567890, a_list);
		Customer c2=new Customer(2,"raj", 1234567890, a_list);
		Customer c3=new Customer(3,"raj", 1234567890, a_list);
		c_list.add(c3);
		c_list.add(c2);
		c_list.add(c);

		
		when(customerRepo.findAllByCustomerNameIgnoreCase("Raj")).thenReturn(c_list);
		List<Customer> returnedCustomers= customerService.getCustomersByName("Raj");
		
		assertEquals(c_list, returnedCustomers);
		
	
		
	}

}












