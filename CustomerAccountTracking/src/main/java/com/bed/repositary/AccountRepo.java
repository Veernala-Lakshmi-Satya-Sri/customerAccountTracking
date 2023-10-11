package com.bed.repositary;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.bed.model.Account;

public interface AccountRepo extends CrudRepository<Account, Integer> {
	
	Set<Account> findByBalanceGreaterThanEqual(float balance);

	List<Account> findAllByAccountTypeIgnoreCase(String type);

}
