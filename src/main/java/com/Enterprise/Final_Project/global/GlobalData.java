package com.Enterprise.Final_Project.global;

import java.util.ArrayList;
import java.util.List;

import com.Enterprise.Final_Project.model.Product;

public class GlobalData {

	public static List<Product> cart;
	static {
		cart = new ArrayList<Product>();
	}
}
