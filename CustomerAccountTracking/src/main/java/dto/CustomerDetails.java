package dto;

import com.bed.model.Customer;

public class CustomerDetails {
	
	private Customer customer;


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public CustomerDetails() {
		super();
	}

	public CustomerDetails(Customer customer) {
		super();
		this.customer = customer;
	}


	

}
