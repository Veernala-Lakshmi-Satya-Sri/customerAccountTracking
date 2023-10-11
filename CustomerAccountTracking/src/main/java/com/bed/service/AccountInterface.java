package com.bed.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bed.Exceptions.NotBelongsException;
import com.bed.Exceptions.ResourseNotFoundException;
import com.bed.model.Account;

@Service
public interface AccountInterface {

	List<Account> getAccounts();

	Set<Account> getAccountsOfCustomer(int cid) throws ResourseNotFoundException;

	Account getAccountOfCustomer(int cid, int aid) throws ResourseNotFoundException, NotBelongsException;
	
	Account getAccountById(int id) throws ResourseNotFoundException;


	Set<Account> getAccountsWithHigherBalance(int cid,float balance) throws ResourseNotFoundException;
	
	List<Account> getAccountsByType(String type) throws ResourseNotFoundException;


}
