package com.bed.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bed.Exceptions.NotBelongsException;
import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.repositary.AccountRepo;
import com.bed.repositary.CustomerRepo;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
	
	

	@Mock
	CustomerRepo customerRepo;
	
	@Mock
	AccountRepo accountRepo;
	
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	AccountService accountService;
	
//	@InjectMocks
//	AccountService accountService= new AccountService();
	
	
	@Test
	void getAccountsTest() {
		List<Account> a_list= new ArrayList<Account>();
		a_list.add(new Account(11,"Savings", 100000));
		a_list.add(new Account(12,"Savings2", 10000));
		a_list.add(new Account(13,"Savings3", 10000));;
		
		when(accountRepo.findAll()).thenReturn(a_list);
		List<Account> acs= accountService.getAccounts();
		
		
		assertTrue(Objects.equals(a_list, acs));
		assertEquals(a_list, acs);
		
	}

	
	@Test
	void getAllAccountsOfCustomerTest() throws ResourseNotFoundException {
		
		
		Set<Account> a_list= new HashSet<Account>();
		a_list.add(new Account(11,"Savings", 1000000));
		a_list.add(new Account(12,"Savings", 1000000));
		a_list.add(new Account(13,"Savings", 1000000));
	
		Customer c= new Customer(1,"raj", 1234567890, a_list);
		
		when(customerService.getCustomerById(1)).thenReturn(c);
		
		Set<Account> acs= accountService.getAccountsOfCustomer(1);
		
		assertEquals(a_list, acs);
		assertEquals(3, acs.size());
		
	}
	
	@Test
	void getAccountOfCustomerTest() throws ResourseNotFoundException, NotBelongsException  {
		
		Set<Account> a_list= new HashSet<Account>();
		

		Account a= new Account(11,"Savings", 1000000);
		Account a2= new Account(12,"Savings", 1000000);
		a_list.add(a);
		a_list.add(a2);
		Customer c= new Customer(1,"raj", 1234567890, a_list);
		
		
		when(customerService.getCustomerById(1)).thenReturn(c);
		when(accountRepo.findById(11)).thenReturn(Optional.of(a));
		Account returnedAccount= accountService.getAccountOfCustomer(1,11);
		
		assertEquals(a, returnedAccount);
		
		
	}
	
	
	@Test
	void getAccountById() throws ResourseNotFoundException {
		


		Account a= new Account(11,"Savings", 10000);
	
		when(accountRepo.findById(11)).thenReturn(Optional.of(a));
		Account returnedAccount= accountService.getAccountById(11);
		
		
		assertEquals(10000, returnedAccount.getBalance());
		assertEquals("Savings", returnedAccount.getAccountType());
		assertEquals(a, returnedAccount);
		
		
	}
	
	@Test
	void getAccountsWithHigherBalanceTest() throws ResourseNotFoundException {
		 
		
		Set<Account> a_list= new HashSet<Account>();
		
		Account a= new Account(11,"Savings", 25000);
		Account a2= new Account(12,"Savings", 50000);
		a_list.add(a);
		a_list.add(a2);
		Customer c= new Customer(1,"raj", 1234567890, a_list);
		when(customerService.getCustomerById(1)).thenReturn(c);
		when(accountRepo.findByBalanceGreaterThanEqual(25000)).thenReturn(c.getAccounts());
		
		Set<Account> ac= accountService.getAccountsWithHigherBalance(1,25000);
		
		assertEquals(2, ac.size());
	}
	
	
	
	@Test
	void getAccountsByType() throws ResourseNotFoundException {
		List<Account> a_list= new ArrayList<Account>();
		a_list.add(new Account(11,"Joint",50000));
		
		
		when(accountRepo.findAllByAccountTypeIgnoreCase("Joint")).thenReturn(a_list);
		
		List<Account> ac= accountService.getAccountsByType("Joint");
		
		assertEquals(1, ac.size());
	}
}
























