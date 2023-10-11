package com.bed.model;

import java.math.BigInteger;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import dto.View;
import dto.View.CustomerView;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Customer {
	
	
	@Id
	@GeneratedValue
	private int customerId;
	
	@NotEmpty(message = "Name shoud not empty")
	@JsonView({CustomerView.Post.class, CustomerView.PUT.class})
	private String customerName;
	
	
	@JsonView({CustomerView.Post.class, CustomerView.PUT.class})
	private long customerPhn;
	


	public int getCustomerId() {
		return customerId;
	}




	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}




	public String getCustomerName() {
		return customerName;
	}




	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}




	public long getCustomerPhn() {
		return customerPhn;
	}




	public void setCustomerPhn(long customerPhn) {
		this.customerPhn = customerPhn;
	}




	public Set<Account> getAccounts() {
		return accounts;
	}




	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	
	
	
	
	public Customer() {
	
	}


	public Customer(int customerId, @NotEmpty(message = "Name shoud not empty") String customerName, long customerPhn
			) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPhn = customerPhn;
	}


	public Customer(int customerId, @NotEmpty(message = "Name shoud not empty") String customerName, long customerPhn,
			Set<Account> accounts) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPhn = customerPhn;
		this.accounts = accounts;
	}




	public Customer(@NotEmpty(message = "Name shoud not empty") String customerName, long customerPhn) {
		// TODO Auto-generated constructor stub
		this.customerName = customerName;
		this.customerPhn = customerPhn;
	
	}




	@JsonView({CustomerView.Post.class})
	@OneToMany(targetEntity = Account.class, cascade = CascadeType.ALL)
	@JoinColumn(name="customerId_fk", referencedColumnName = "customerId")
	Set<Account> accounts;

}










