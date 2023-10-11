package com.bed.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.Exceptions.SingleAccountException;
import com.bed.Exceptions.SameAccountException;
import com.bed.Exceptions.InsufficientBalance;
import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.NotBelongsException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import dto.Amount;
import jakarta.validation.ConstraintViolationException;

@Service
public interface CustomerInterface {

	List<Customer> getCustomers() throws ResourseNotFoundException;

	Customer getCustomerById(int id) throws ResourseNotFoundException;

	Customer addCustomer(Customer customer) throws ConstraintViolationException, InvalidCredintialsException,SameAccountException;

	Customer addAccount(int id, Account account) throws ResourseNotFoundException, SameAccountException;

	String deleteCustomer(int id) throws ResourseNotFoundException;

	Customer deleteAccountFromCustomer(int cid, int aid) throws ResourseNotFoundException, NotBelongsException, SingleAccountException;

	Customer editCustomerPersonalDetails(int id, Customer customer) throws ResourseNotFoundException, InvalidCredintialsException;

	
	Customer selfAccountFundsTransfer(float amountToTranfer, int cid, int fromac, int toac) throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException;

	List<Customer> tranferFundsToOther(float amountToTranfer, int cid, int cid2, int fromac, int toac) throws ResourseNotFoundException, InsufficientBalance, NotBelongsException, InvalidCredintialsException;

	List<Customer> getCustomersByName(String cname) throws ResourseNotFoundException;

}
