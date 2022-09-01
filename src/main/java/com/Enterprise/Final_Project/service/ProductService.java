package com.Enterprise.Final_Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Enterprise.Final_Project.model.Product;
import com.Enterprise.Final_Project.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	//-----------------Method to get all products--------------
	public List<Product> getAllProduct(){
		return productRepository.findAll();
	}

	//--------------Method to get Product by Specific id----------------
	public Optional<Product> getProductByID(long id) {
		return productRepository.findById(id);
	}

	//--------------------Method to add Product---------------
	public void addProduct(Product product)
	{
		productRepository.save(product);
	}

	//-------------------Method to remove the Category by id----------
	public void removeProductByID(long id)
	{
		productRepository.deleteById(id);
	}

	//-----------------Method to get all product by category id-----------
	public List<Product> getAllProductsByCategoryId(int id){
		return productRepository.findAllByCategory_Id(id);
	}
}
