package com.bed.Exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class MyControllerAdvice {
	
	String message;
	
	@ExceptionHandler(ResourseNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
//	@ResponseBody 
	MyExceptionResponseFormat handleCustomerNotFoundException(ResourseNotFoundException exception,HttpServletRequest req)
	{
		
		MyExceptionResponseFormat response=new MyExceptionResponseFormat();
		response.setError(exception.getMessage());
		response.setUrl(req.getRequestURI());
				
		
		return response;
	}
	
	@ExceptionHandler(InvalidCredintialsException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	MyExceptionResponseFormat handleInvalidCredintialsException(InvalidCredintialsException exception,HttpServletRequest req)
	{
		
		MyExceptionResponseFormat response=new MyExceptionResponseFormat();
		response.setError(exception.getMessage());
		response.setUrl(req.getRequestURI());
				
		
		return response;
	}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	MyExceptionResponseFormat handleConstraintViolationException(ConstraintViolationException exception,HttpServletRequest req)
	{	
		//List<String> s=new ArrayList<String>();
		
		exception.getConstraintViolations().forEach(v -> message=v.getMessage());
		MyExceptionResponseFormat response=new MyExceptionResponseFormat();
		
		response.setError(message);
		//response.setError(s.toString());
		response.setUrl(req.getRequestURI());
				
		return response;
	}
	
	@ExceptionHandler(InsufficientBalance.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	MyExceptionResponseFormat handleInsufficientBalance(InsufficientBalance exception,HttpServletRequest req)
	{
		
		MyExceptionResponseFormat response=new MyExceptionResponseFormat();
		response.setError(exception.getMessage());
		response.setUrl(req.getRequestURI());
				
		
		return response;
	}
	
	@ExceptionHandler(NotBelongsException .class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	MyExceptionResponseFormat handleNotBelongsException(NotBelongsException  exception,HttpServletRequest req)
	{
		
		MyExceptionResponseFormat response=new MyExceptionResponseFormat();
		response.setError(exception.getMessage());
		response.setUrl(req.getRequestURI());
				
		
		return response;
	}
	
	 
	 
		@ExceptionHandler(SingleAccountException.class)
		@ResponseStatus(value=HttpStatus.BAD_REQUEST)
		MyExceptionResponseFormat handleSingleAccountException(SingleAccountException exception,HttpServletRequest req)
		{
			
			MyExceptionResponseFormat response=new MyExceptionResponseFormat();
			response.setError(exception.getMessage());
			response.setUrl(req.getRequestURI());
					
			
			return response;
		}
		
		
		@ExceptionHandler(SameAccountException.class)
		@ResponseStatus(value=HttpStatus.BAD_REQUEST)
		MyExceptionResponseFormat handleSameAccountExceptions(SameAccountException exception,HttpServletRequest req)
		{
			
			MyExceptionResponseFormat response=new MyExceptionResponseFormat();
			response.setError(exception.getMessage());
			response.setUrl(req.getRequestURI());
					
			
			return response;
		}
		

}



