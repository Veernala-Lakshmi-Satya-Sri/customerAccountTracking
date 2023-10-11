package com.bed.Exceptions;

public class SameAccountException extends Exception{

	public SameAccountException () {
		
	}
	public SameAccountException(String msg) {
		super(msg);
	}
}
