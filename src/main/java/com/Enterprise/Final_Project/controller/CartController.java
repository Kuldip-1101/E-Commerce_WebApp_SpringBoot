package com.Enterprise.Final_Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Enterprise.Final_Project.global.GlobalData;
import com.Enterprise.Final_Project.model.Product;
import com.Enterprise.Final_Project.service.ProductService;

@Controller
public class CartController {

	@Autowired
	ProductService productService;
	
	//---------------Method to add product in cart-----------------
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable int id) {
		GlobalData.cart.add(productService.getProductByID(id).get());
		return "redirect:/shop";
	}
	
	
	//------------Method to see all the product in cart--------------
	@GetMapping("/cart")
	public String cartGet(Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart",GlobalData.cart);
		return "cart";
	}
	
	
	//-----------------Method to remove the item from the cart-------------
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index)
	{
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}
	
	//----------------Method to do check out from the cart----------------
	@GetMapping("/checkout")
	public String checkout(Model model)
	{
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}
}
