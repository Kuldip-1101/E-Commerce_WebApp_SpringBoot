package com.Enterprise.Final_Project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Enterprise.Final_Project.dto.ProductDTO;
import com.Enterprise.Final_Project.model.Category;
import com.Enterprise.Final_Project.model.Product;
import com.Enterprise.Final_Project.service.CategoryService;
import com.Enterprise.Final_Project.service.ProductService;


@Controller
public class AdminController {

	public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;

	//******************************************Category Section*****************************************

	//-------------Method For admin home page---------------
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}

	//---------------method to display all category--------------
	@GetMapping("/admin/categories")
	public String getCategories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}

	//-------------------method for Redirect add category form-----------------
	@GetMapping("/admin/categories/add")
	public String addCategories(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}


	//------------------Method to add category into database------------------
	@PostMapping("/admin/categories/add")
	public String postaddCategories(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}


	//---------------------Method to Delete the Category-----------------
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id)
	{
		categoryService.removeCategoryByID(id);
		return "redirect:/admin/categories";
	}

	//------------------Method to Update the Category-----------------
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model)
	{
		Optional<Category> category = categoryService.getCategoryByID(id);
		if(category.isPresent()) {
			model.addAttribute("category",  category.get());
			return "categoriesAdd";
		}
		else
			return "404";
	}


	//**********************************Product Section**********************************

	//------------Method to get all the products----------------
	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}

	//-------------------method for Redirect add product form-----------------
	@GetMapping("/admin/products/add")
	public String addProducts(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}

	//------------------Method to add product into database------------------
	@PostMapping("/admin/products/add")
	public String postaddProducts(@ModelAttribute("productDTO") ProductDTO productDTO,
									@RequestParam("productImage")MultipartFile file,
									@RequestParam("imgName")String imgName) throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryByID(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!file.isEmpty())
		{
			imageUUID = file.getOriginalFilename();
			Path fileNameandPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameandPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);

		return "redirect:/admin/products";
	}


	//---------------------Method to Delete the Product-----------------
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id)
	{
		productService.removeProductByID(id);
		return "redirect:/admin/products";
	}

	//------------------Method to Update the Product-----------------
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model)
	{
		Product product = productService.getProductByID(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());

		model.addAttribute("productDTO", productDTO);
		model.addAttribute("categories", categoryService.getAllCategory());

		return "productsAdd";

	}

}
