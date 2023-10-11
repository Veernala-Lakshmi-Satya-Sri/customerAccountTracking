package com.bed.Exceptions;

public class InsufficientBalance extends Exception{

	public InsufficientBalance() {
		
	}
	
	public InsufficientBalance(String msg) {
		super(msg);
	}
}
