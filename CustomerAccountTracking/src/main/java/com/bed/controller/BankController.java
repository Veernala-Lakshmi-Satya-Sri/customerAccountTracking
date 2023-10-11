package com.bed.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.Exceptions.SingleAccountException;
import com.bed.Exceptions.SameAccountException;
import com.bed.Exceptions.InsufficientBalance;
import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.NotBelongsException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.service.AccountInterface;
import com.bed.service.CustomerInterface;
import com.fasterxml.jackson.annotation.JsonView;

import dto.Amount;
import dto.CustomerDetails;
import dto.View.CustomerView;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
public class BankController {
	
	
	
	@Autowired
	private CustomerInterface customerInterface;
	
	@Autowired
	private AccountInterface accountInterface;
	
//done 
	@PostMapping("/customer")
	public ResponseEntity<Customer> addNewCustomer(@Valid @RequestBody CustomerDetails customerDetails) throws ConstraintViolationException, InvalidCredintialsException, SameAccountException {
		
			return new ResponseEntity<Customer>(customerInterface.addCustomer(customerDetails.getCustomer()), HttpStatus.CREATED);

	}
	
//DONE 
	@GetMapping("/customer")
	public ResponseEntity<List<Customer>> getAllCustomers() throws ResourseNotFoundException{
		
		return new ResponseEntity<List<Customer>>(customerInterface.getCustomers(), HttpStatus.OK);
	}
//DONE 
	@GetMapping("/customer/{cid}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int cid) throws ResourseNotFoundException{
		
		return new ResponseEntity<Customer>(customerInterface.getCustomerById(cid), HttpStatus.OK);
	}
//DONE 	
	@PutMapping("/customer/addaccount/{cid}")
	public ResponseEntity<Customer> addAccountToCustomer(@PathVariable int cid, @RequestBody Account account) throws ResourseNotFoundException, SameAccountException{
		
		return new ResponseEntity<Customer>(customerInterface.addAccount(cid, account),HttpStatus.CREATED);
		
	}
//DONE
	@PutMapping("/customer/edit-personal-details/{cid}")
	public ResponseEntity<Customer> editCustomerPersonalDetails(@PathVariable int cid, @RequestBody @JsonView(value=CustomerView.PUT.class) Customer  customer) throws ResourseNotFoundException, InvalidCredintialsException {
		
		return new ResponseEntity<Customer>(customerInterface.editCustomerPersonalDetails(cid,customer), HttpStatus.OK);
	}
	
	
//DONE
	@GetMapping("/customer/accounts/{cid}")
	public ResponseEntity<Set<Account>> getAllAccountsOfCustomer(@PathVariable int cid) throws ResourseNotFoundException {
		//return accountInterface.getAccountsOfCustomer(cid);
		return new ResponseEntity<Set<Account>>(accountInterface.getAccountsOfCustomer(cid), HttpStatus.OK);
	}
	
//done		
	@GetMapping("/accounts/byid/{aid}")
	public Account getAccountById(@PathVariable int aid) throws ResourseNotFoundException {
			return accountInterface.getAccountById(aid);
	}
	
//done	
	@GetMapping("customer/account/{cid}/{aid}")
	public ResponseEntity<Account> getAccountOfCustomerByAccountId(@PathVariable int cid, @PathVariable int aid) throws ResourseNotFoundException, NotBelongsException {
		
		return new ResponseEntity<Account>(accountInterface.getAccountOfCustomer(cid,aid), HttpStatus.OK);
	}
//DONE--done but not sure	
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		return new ResponseEntity<List<Account>>(accountInterface.getAccounts(), HttpStatus.OK);
	}
	
//DONE	--done
	@DeleteMapping("/customer/{cid}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int cid) throws ResourseNotFoundException {
		return new ResponseEntity<String>(customerInterface.deleteCustomer(cid), HttpStatus.OK);
		
	}
//done
	@DeleteMapping("/customer/{cid}/{aid}")
	public ResponseEntity<Customer> deleteAccountFromCoustomer(@PathVariable int cid, @PathVariable int aid) throws ResourseNotFoundException, NotBelongsException, SingleAccountException {
	
		return new ResponseEntity<Customer>(customerInterface.deleteAccountFromCustomer(cid,aid), HttpStatus.OK);
	}
	
//done
	
	@PutMapping("/customer/self-transfer/{cid}/{fromac}/{toac}")
	public ResponseEntity<Customer> tranferFundsToSelfAccount(@RequestBody Amount amount ,@PathVariable int cid, @PathVariable int fromac, @PathVariable int toac) throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException {
		return new ResponseEntity<Customer>(customerInterface.selfAccountFundsTransfer(amount.getAmountToTranfer(),cid,fromac,toac),HttpStatus.OK);
//done		
	}
	@PutMapping("/customer/funds-transfer/{cid}/{cid2}/{fromac}/{toac}")
	public ResponseEntity<List<Customer>> tranferFundsToOthers(@RequestBody Amount amount ,@PathVariable int cid,@PathVariable int cid2, @PathVariable int fromac, @PathVariable int toac) throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException {
		return new ResponseEntity<List<Customer>>(customerInterface.tranferFundsToOther(amount.getAmountToTranfer(),cid,cid2,fromac,toac),HttpStatus.OK);

	}
//done	
	@GetMapping("/customers/byname/{cname}")
	public ResponseEntity<List<Customer>> getCustomersByName(@PathVariable String cname) throws ResourseNotFoundException{
		return new ResponseEntity<List<Customer>>(customerInterface.getCustomersByName(cname), HttpStatus.OK);
	}
	
//done
	@GetMapping("accounts/bybalance/{cid}/{minbalance}")
	public ResponseEntity<Set<Account>> getAccountsWithHigherBalance(@PathVariable int cid, @PathVariable float minbalance) throws ResourseNotFoundException{
		return new ResponseEntity<Set<Account>>(accountInterface.getAccountsWithHigherBalance(cid,minbalance), HttpStatus.OK);
	}
	
//done	
@GetMapping("/accounts/bytype/{type}")
		public List<Account> getAccountsByName(@PathVariable String type) throws ResourseNotFoundException {
				return accountInterface.getAccountsByType(type);
		}
	
	
}











