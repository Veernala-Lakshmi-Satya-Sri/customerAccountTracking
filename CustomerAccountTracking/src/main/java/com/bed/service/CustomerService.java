package com.bed.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.Exceptions.SingleAccountException;
import com.bed.controller.BankController;
import com.bed.Exceptions.SameAccountException;
import com.bed.Exceptions.InsufficientBalance;
import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.NotBelongsException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.repositary.AccountRepo;
import com.bed.repositary.CustomerRepo;

import jakarta.validation.ConstraintViolationException;

@Service
public class CustomerService implements CustomerInterface{
	
	private static final Logger Log= LoggerFactory.getLogger(CustomerService.class);
	
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private AccountInterface accountInterface;
	
	@Autowired
	private AccountService accountService;


		@Override
		public Customer addCustomer(Customer customer) throws ConstraintViolationException, InvalidCredintialsException, SameAccountException {
	
		Set<Account> acs= customer.getAccounts();
		List<String> types= new ArrayList<String>();
		
		for(Account a: acs) {
			types.add(a.getAccountType().toUpperCase());
		}
		
		for(String t: types) {
			
				if(Collections.frequency(types, t.toUpperCase())>1) {
					
					Log.error("SameAccountException raised");
					throw new SameAccountException("You cannot have mutiple accounts of same type, Please Check.");
				}
		}
		
		String phn_str	=String.valueOf(customer.getCustomerPhn());
		if(phn_str.length()<10 || phn_str.length()>10) {
			
			Log.error("InvalidCredintialsException raised");
			throw new InvalidCredintialsException("Phn number shoud not be Null and lenght Should be 10");
		}
		
		
		
			Log.info("Successfully added customer");
			return customerRepo.save(customer);
		}
		
		
		
		@Override
		public List<Customer> getCustomers() throws ResourseNotFoundException {
			// TODO Auto-generated method stub
			List<Customer> customers= new ArrayList<Customer>();
			
		 customerRepo.findAll().forEach(c-> customers.add(c));
		 
		 
		 if(customers.size()==0) {
			 Log.error("ResourseNotFoundException raised");
			 throw new ResourseNotFoundException("No customers present");
		 }
		 Log.info("Successfully returning customers");
		 return customers;
		}
		
		
		@Override
		public Customer getCustomerById(int id) throws ResourseNotFoundException{
			// TODO Auto-generated method stub
			
		
			if(customerRepo.findById(id).isEmpty()) {
				Log.error("ResourseNotFoundException raised");
				throw new ResourseNotFoundException("Customer with given ID : " +id+" is not present");
			}
			
			Log.info("Successfully returning customer");
			return customerRepo.findById(id).get();
		}
		
		@Override
		public Customer addAccount(int id, Account account) throws ResourseNotFoundException, SameAccountException {
			// TODO Auto-generated method stub
		
				Customer c= getCustomerById(id);
				
				Set<Account> currentAccounts= c.getAccounts();
				
				for(Account a: currentAccounts) {
					
					if(a.getAccountType().equalsIgnoreCase(account.getAccountType())) {
						
						Log.error("SameAccountException raised");

						throw new SameAccountException(account.getAccountType()+" account is already present");

					}
				}
				
				c.getAccounts().add(account);
				
				accountRepo.save(account);
				
				Log.info("Successfully added account to customer");
				
				return customerRepo.save(c);

		}

		@Override
		public Customer editCustomerPersonalDetails(int id, Customer customer) throws ResourseNotFoundException, InvalidCredintialsException {
			// TODO Auto-generated method stub
			
			Customer updatedCustomer= getCustomerById(id);
			
			String phn_str	=String.valueOf(customer.getCustomerPhn());
		
			if(phn_str.length()<10 || phn_str.length()>10 ) {
				
				Log.error("InvalidCredintialsException raised");

				throw new InvalidCredintialsException("Phn number shoud not be Null and lenght Should be 10");
			}
			updatedCustomer.setCustomerPhn(customer.getCustomerPhn());
			updatedCustomer.setCustomerName(customer.getCustomerName());
			
		
			customerRepo.save(updatedCustomer);
			
			Log.info("Successfully edited customer details");
			return updatedCustomer;
		}

		@Override
		public Customer selfAccountFundsTransfer(float amount,int cid,int fromac,int toac) throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException {
			
			if(fromac== toac) {
				Log.error("InvalidCredintialsException raised");
				throw new InvalidCredintialsException("from account and to account are same");
			}
			Account from_ac=accountInterface.getAccountOfCustomer(cid, fromac);
			
			Account to_ac=accountInterface.getAccountOfCustomer(cid, toac);
			
			transeFunds(amount,from_ac,to_ac);
			
			Log.info("Successfully done self account transfer");
			
			 
			return customerRepo.findById(cid).get();
		}

		@Override
		public List<Customer> tranferFundsToOther(float amount, int cid, int cid2, int fromac, int toac) throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException {
			// TODO Auto-generated method stub
			if(cid== cid2) {
				
				Log.error("InvalidCredintialsException raised for Same Customer IDs");
				throw new InvalidCredintialsException("from customer and to Customer are same");
			}
			
			else if(fromac== toac) {
				
				Log.error("InvalidCredintialsException raised for Same account IDs");
				throw new InvalidCredintialsException("from account and to account are same");
			}
			
			Account from_ac=accountInterface.getAccountOfCustomer(cid, fromac);
			
			Account to_ac=accountInterface.getAccountOfCustomer(cid2, toac);
			
			transeFunds(amount,from_ac,to_ac);
			
			List<Customer> c= new ArrayList<Customer>();
			
			c.add(customerRepo.findById(cid).get());
			c.add(customerRepo.findById(cid2).get());
			 
			
			Log.info("Successfully transferring funds");
			return c;
		}
		
		
		public void transeFunds(float amount, Account fromAccount, Account toAccount) throws InsufficientBalance {
		
			
			
			float total;
			float availableBalance=fromAccount.getBalance();
			
			if(availableBalance>=amount) {
				
				total=	toAccount.getBalance()+amount;
				availableBalance= fromAccount.getBalance()-amount;
				toAccount.setBalance(total);
				fromAccount.setBalance(availableBalance);
				accountRepo.save(toAccount);
			accountRepo.save(fromAccount);
			
			
				
			}
			else {
			
			
				throw new InsufficientBalance("Dear Customer you don't have enough funds to process this request");
			}

		}
		
		
		@Override
		public String deleteCustomer(int id) throws ResourseNotFoundException {
			getCustomerById(id);
			customerRepo.deleteById(id);
			if(customerRepo.findById(id).isEmpty())
			{
				 
				Log.info("Successfully deleted customer");
				return "Customer with id "+id+" is deleted";
			}
		
			return "Not deleted";
		}

		@Override
		public Customer deleteAccountFromCustomer(int cid, int aid) throws ResourseNotFoundException, NotBelongsException, SingleAccountException  {
			// TODO Auto-generated method stub
			
			Account a=accountInterface.getAccountOfCustomer(cid, aid);
			Customer c= getCustomerById(cid);
			if(c.getAccounts().size()==1) {
				
				Log.error("SingleAccountException raised");
				throw new SingleAccountException("Customer Should have atleast one account");
			}
			c.getAccounts().remove(a);
			
			//accountRepo.deleteById(aid);
			
			customerRepo.save(c);
			Account accountToBeDeleted= accountInterface.getAccountById(aid);
			accountRepo.delete(accountToBeDeleted);
			Log.info("Successfully deleting customer account");
			return c;
			
		}

		@Override
		public List<Customer> getCustomersByName(String cname) throws ResourseNotFoundException {
			// TODO Auto-generated method stub
			
			if(customerRepo.findAllByCustomerNameIgnoreCase(cname).isEmpty())
			{
				Log.error("ResourseNotFoundException raised");
				throw new ResourseNotFoundException("Customer with given name is not found");
			}
			
			Log.info("Successfully retuning customers having name", cname);
			return customerRepo.findAllByCustomerNameIgnoreCase(cname);
		}


		
}












