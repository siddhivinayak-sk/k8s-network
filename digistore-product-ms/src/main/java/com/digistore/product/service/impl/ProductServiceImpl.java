package com.digistore.product.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digistore.product.entities.Product;
import com.digistore.product.model.ProductDTO;
import com.digistore.product.model.ResponseDTO;
import com.digistore.product.repositories.ProductRepository;
import com.digistore.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public ResponseDTO<ProductDTO> create(ProductDTO product) {
		if(null == product || null == product.getName()) {
			return new ResponseDTO<>(null, "Invalid Request", "400");
		}
		if(productRepository.existByName(product.getName())) {
			return new ResponseDTO<>(null, "Product Already Exists", "400");
		}
		else {
			Product productEntity = new Product();
			BeanUtils.copyProperties(product, productEntity);
			productEntity = productRepository.save(productEntity);
			BeanUtils.copyProperties(productEntity, product);
			return new ResponseDTO<>(product, "Save Successfully", "200");
		}
	}

	@Override
	public ResponseDTO<ProductDTO> update(ProductDTO product) {
		if(null != product && productRepository.existsById(product.getId())) {
			Optional<Product> productEntityOptional = productRepository.findById(product.getId());
			productEntityOptional.ifPresent(prd -> {
				BeanUtils.copyProperties(product, prd);
				prd = productRepository.save(prd);
				BeanUtils.copyProperties(prd, product);
			});
			return new ResponseDTO<>(product, "Updated Successfully", "200");
		}
		else {
			return new ResponseDTO<>(null, "Invalid Request", "400");
		}
	}

	@Override
	public ResponseDTO<ProductDTO> delete(Long productId) {
		if(null != productId && productRepository.existsById(productId)) {
			Optional<Product> productEntityOptional = productRepository.findById(productId);
			ProductDTO product = new ProductDTO();
			productEntityOptional.ifPresent(prd -> {
				BeanUtils.copyProperties(prd, product);
				productRepository.delete(prd);
			});
			return new ResponseDTO<>(product, "Delete Successfully", "200");
		}
		else {
			return new ResponseDTO<>(null, "Invalid Request", "400");
		}
	}

	@Override
	public ResponseDTO<ProductDTO> getByName(String productName) {
		if(null != productName && productRepository.existByName(productName)) {
			Product productEntity = productRepository.findByName(productName);
			ProductDTO product = new ProductDTO();
			BeanUtils.copyProperties(productEntity, product);
			return new ResponseDTO<>(product, "Found Successfully", "200");
		}
		else {
			return new ResponseDTO<>(null, "Invalid Request & Requested Resource not available", "400");
		}
	}

	@Override
	public ResponseDTO<ProductDTO> getById(Long productId) {
		if(null != productId && productRepository.existsById(productId)) {
			Optional<Product> productEntity = productRepository.findById(productId);
			ProductDTO product = new ProductDTO();
			productEntity.ifPresent(prd -> 
				BeanUtils.copyProperties(prd, product)
			);
			return new ResponseDTO<>(product, "Found Successfully", "200");
		}
		else {
			return new ResponseDTO<>(null, "Invalid Request & Requested Resource not available", "400");
		}
	}

	@Override
	public ResponseDTO<List<ProductDTO>> getPorducts() {
		if(productRepository.count() > 0) {
			List<Product> productEntities = productRepository.findAll();
			List<ProductDTO> products = productEntities.stream().map(prd -> {
				ProductDTO product = new ProductDTO();
				BeanUtils.copyProperties(prd, product);
				return product;
			}).collect(Collectors.toList()); 
			return new ResponseDTO<>(products, "Found Successfully", "200");
		}
		else {
			return new ResponseDTO<>(null, "No Products available", "200");
		}
	}
}
