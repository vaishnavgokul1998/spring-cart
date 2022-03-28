package com.example.springsecurity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;

@ControllerAdvice
public class ControllerException  {


    @ExceptionHandler(value= {InvalidInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response processValidationError(InvalidInputException ex) {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		resCode.setCode(ex.getErrorCode());
		resCode.setStatus("Failure");
		resCode.setMessage(ex.getErrorMessage());
		res.setResponseCode(resCode);
        return res;
    }
}
