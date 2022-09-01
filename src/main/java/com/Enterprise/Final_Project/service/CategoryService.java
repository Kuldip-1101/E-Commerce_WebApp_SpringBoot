package com.Enterprise.Final_Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Enterprise.Final_Project.model.Category;
import com.Enterprise.Final_Project.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	//-----------------Method to get all category--------------
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}

	//--------------Method to get Category by Specific id----------------
	public Optional<Category> getCategoryByID(int id) {
		return categoryRepository.findById(id);
	}

	//--------------------Method to add Category---------------
	public void addCategory(Category category)
	{
		categoryRepository.save(category);
	}

	//-------------------Method to remove the Category by id----------
	public void removeCategoryByID(int id)
	{
		categoryRepository.deleteById(id);
	}


}
