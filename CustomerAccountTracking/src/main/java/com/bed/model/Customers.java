package com.bed.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Customers implements Serializable{
	
	private List<Customer> customerList;
	
//	public Customers() {
//		customerList= new ArrayList<>();
//	}

	public List<Customer> getCustomerList() {
		if(customerList==null) {
			customerList= new ArrayList<Customer>();
		}
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

}
