package com.example.ecommerce.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.example.ecommerce.dto.ErrorDTO;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorDTO> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
		
		String message = "";
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			message = message + error.getDefaultMessage() + ", ";
		}
	
		message = message.substring(0, message.lastIndexOf(","));
		
		return new ResponseEntity<ErrorDTO>(new ErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), message), HttpStatus.BAD_REQUEST);
	}
}
