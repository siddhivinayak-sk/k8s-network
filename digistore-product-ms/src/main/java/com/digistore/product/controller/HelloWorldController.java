package com.digistore.product.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@Api(tags = {"Hello API"})
@SwaggerDefinition(tags = {@Tag(name = "Hello API", description = "It used to get user logged-in information")})
public class HelloWorldController {

	@ApiOperation(httpMethod = "GET", value = "Get User Detail", produces = MediaType.TEXT_PLAIN_VALUE, consumes = "NA")
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "User details provided successfully", response = String.class),
			@ApiResponse(code = 400, message = "Bad Request", response = String.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = String.class)
			} )
	@RequestMapping({ "/hello" })
	public String hello(@RequestAttribute("user") UserDetails user) {
		
		return user.getUsername();
	}

}
