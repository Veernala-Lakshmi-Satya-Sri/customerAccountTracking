package com.bed.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.bed.Exceptions.NotBelongsException;
import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.repositary.AccountRepo;
import com.bed.repositary.CustomerRepo;

@Service
public class AccountService implements AccountInterface{
	
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Lazy
	@Autowired
	private CustomerInterface customerInterface;
	
	@Override
	public List<Account> getAccounts() {
		// TODO Auto-generated method stub
		List<Account> accounts= new ArrayList<Account>();
		accountRepo.findAll().forEach(a->accounts.add(a));
		return accounts;
	}

	@Override
	public Set<Account> getAccountsOfCustomer(int cid) throws ResourseNotFoundException {
		// TODO Auto-generated method stub
		
		
		Set<Account> accounts2= new HashSet<Account>();
		Customer c=customerInterface.getCustomerById(cid);
		c.getAccounts().forEach(a2->accounts2.add(a2));
		return accounts2;
		
	}

	@Override
	public Account getAccountOfCustomer(int cid, int aid) throws ResourseNotFoundException, NotBelongsException {
		// TODO Auto-generated method stub
		
		
		Set<Account> a =getAccountsOfCustomer(cid);
		
		
		if(a.contains(getAccountById(aid)) != true) {
			throw new NotBelongsException("Account with Id :" +aid + "Not Belongs to the Customer with id: "+cid);
		}
		return getAccountById(aid);
	}
	
	@Override
	public Account getAccountById(int id) throws ResourseNotFoundException {
		// TODO Auto-generated method stub
		if(accountRepo.findById(id).isEmpty()) {
			throw new ResourseNotFoundException("Account with given id "+id+" is not present");
		}
		return accountRepo.findById(id).get();
	}

	@Override
	public Set<Account> getAccountsWithHigherBalance(int cid, float balance) throws ResourseNotFoundException {
		// TODO Auto-generated method stub
		Customer c= customerInterface.getCustomerById(cid);
		if(accountRepo.findByBalanceGreaterThanEqual(balance).isEmpty()) {
			throw new ResourseNotFoundException("Accounts with minimum balance "+balance+" is not present");
		}
		Set<Account> customerAccounts= new HashSet<Account>();
		Set<Account> accounts= accountRepo.findByBalanceGreaterThanEqual(balance);
		for(Account a: accounts) {
			if(c.getAccounts().contains(a)) {
				customerAccounts.add(a);
			}
		}
		return customerAccounts;
	}

	@Override
	public List<Account> getAccountsByType(String type) throws ResourseNotFoundException {
		// TODO Auto-generated method stub
		if(accountRepo.findAllByAccountTypeIgnoreCase(type).isEmpty()) {
			throw new ResourseNotFoundException("The given account type is not present");
		}
		return accountRepo.findAllByAccountTypeIgnoreCase(type);
	}
	
	
	

}
