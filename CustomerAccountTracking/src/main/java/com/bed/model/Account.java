package com.bed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table
public class Account {
	
	@Id
	@GeneratedValue
	private int acId;
	
	
	private String accountType;
	
	@Min(message = "minimum balance should be 0", value = 0)
	private float balance;



	public int getAcId() {
		return acId;
	}

	public void setAcId(int acId) {
		this.acId = acId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Account() {
		
	}
	
	public Account(int acId, String accountType,
			@Min(message = "minimum balance should be 0", value = 0) float balance) {
		super();
		this.acId = acId;
		this.accountType = accountType;
		this.balance = balance;
	}

	public Account(String accountType,@Min(message = "minimum balance should be 0", value = 0) float balance) {
		// TODO Auto-generated constructor stub
		this.accountType = accountType;
		this.balance = balance;
	}

	
	
	
	

}
