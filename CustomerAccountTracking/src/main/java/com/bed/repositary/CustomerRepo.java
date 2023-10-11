package com.bed.repositary;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bed.model.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Integer>{

	List<Customer> findAllByCustomerNameIgnoreCase(String cname);

}
