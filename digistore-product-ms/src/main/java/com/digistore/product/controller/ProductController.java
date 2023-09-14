package com.digistore.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digistore.product.model.ProductDTO;
import com.digistore.product.model.ResponseDTO;
import com.digistore.product.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * 
 * @author kumar-sand
 *
 */
@RestController
@RequestMapping("/v1/products")
//@CrossOrigin
@Api(tags = {"Product API"})
@SwaggerDefinition(tags = {@Tag(name = "Product API", description = "This API provides endpoints for product management")})
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@ApiOperation(httpMethod = "POST", value = "Create product", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Product created successfully", response = ResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = ResponseDTO.class)
			} )
	@PostMapping
	public ResponseDTO<ProductDTO> create(ProductDTO product) {
		return productService.create(product);
	}
	
	@ApiOperation(httpMethod = "PUT", value = "Update product", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Product updated successfully", response = ResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = ResponseDTO.class)
			} )
	@PutMapping
	public ResponseDTO<ProductDTO> update(ProductDTO product) {
		return productService.update(product);
	}

	@ApiOperation(httpMethod = "DELETE", value = "Delete product", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Product deleted successfully", response = ResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = ResponseDTO.class)
			} )
	@DeleteMapping
	public ResponseDTO<ProductDTO> delete(@RequestParam Long productId) {
		return productService.delete(productId);
	}
	
	@ApiOperation(httpMethod = "GET", value = "Get Products by Name", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Product retrieved successfully", response = ResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = ResponseDTO.class)
			} )
	@GetMapping("/byname")
	public ResponseDTO<ProductDTO> getByName(@RequestParam String productName) {
		return productService.getByName(productName);
	}

	@ApiOperation(httpMethod = "GET", value = "Get Products by id", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Product retrieved successfully", response = ResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = ResponseDTO.class)
			} )
	@GetMapping("/byid")
	public ResponseDTO<ProductDTO> getById(@RequestParam Long productId) {
		return productService.getById(productId);
	}
	
	@ApiOperation(httpMethod = "GET", value = "Get all Products", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses( value = {
			@ApiResponse(code = 200, message = "Product retrieved successfully", response = ResponseDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ResponseDTO.class),
			@ApiResponse(code = 401, message = "Unauthorized access", response = ResponseDTO.class)
			} )
	@GetMapping
	public ResponseDTO<List<ProductDTO>> getPorducts() {
		return productService.getPorducts();
	}
}
