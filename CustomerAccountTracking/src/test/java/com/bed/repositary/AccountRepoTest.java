package com.bed.repositary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.bed.model.Account;



@ExtendWith(MockitoExtension.class)
@DataJpaTest
class AccountRepoTest {

	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	TestEntityManager entitymanager;
	
	
	@Test
	void findByBalanceGreaterThanEqualTest() {
		
		Account a1=new Account("Savings", 2000);
		Account a2=new Account("Savings", 2500);
		Account a3=new Account("Savings", 9000);
		
		
		entitymanager.persist(a1);
		entitymanager.persist(a2);
		entitymanager.persist(a3);
		
		Set<Account> ac= accountRepo.findByBalanceGreaterThanEqual(2500);
		assertEquals(2, ac.size());
		
	}
	
	@Test
	void findByAccountType() {
		Account a1=new Account("Savings", 2000);
		Account a2=new Account("SAVINGS", 2500);
		Account a3=new Account("Joint", 9000);
		
		entitymanager.persist(a1);
		entitymanager.persist(a2);
		entitymanager.persist(a3);
		List<Account> ac= accountRepo.findAllByAccountTypeIgnoreCase("savings");
		assertEquals(2, ac.size());
		
	}
}





