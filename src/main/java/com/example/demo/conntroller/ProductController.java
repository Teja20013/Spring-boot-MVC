package com.example.demo.conntroller;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.ProductEntity;
import com.example.demo.service.ProductService;
import com.examplu.demo.model.ProductModel;

import jakarta.validation.Valid;


@Controller
public class ProductController {
	@Autowired
	ProductService productService;
	
//	@GetMapping("/productform")
//	public String getProductForm()
//	{
//		return "add-product";
//	}
//	@PostMapping("/saveProduct")
//	public String saveProduct(ProductModel productModel) {
//		productService.saveProductDetails(productModel);
//		return "succcess";
//	}
	//getting the details of all products from data base
	@GetMapping("/getAllProducts")
	public String getAllProducts(Model model){
		List<ProductEntity> products=productService.getAllProducts();
		 model.addAttribute("products",products);
		return "product-list";
	}
	//searching the data from database
	@GetMapping("/searchform")
	public String searchfrom()
	{
		return "search-product";
	}
	
	@PostMapping("/searchById")
	public String searchById(@RequestParam Long id, Model model)
	{
		ProductEntity product = productService.searchById(id);
		model.addAttribute("product",product);
		return "search-product";
	}
	
	
	//delete the data by giving the id value
	@GetMapping("/delete/{id}")
	public String deleteProductById(@PathVariable("id") Long id)
	{
		productService.deleteProductById(id);
		return "redirect:/getAllProducts";
	}
	
	//edit the data by giving the id value
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable("id") Long id, Model model) {
	    ProductModel product = productService.getProductById(id);

	    if (product == null) {
	        model.addAttribute("errorMessage", "Product not found for ID: " + id);
	        return "error"; // Ensure an `error.html` page exists in `src/main/resources/templates`
	    }

	    model.addAttribute("product", product);
	    return "edit-product";
	}

	@PostMapping("/editproductsave/{id}")
	public String updateProduct(@PathVariable("id") Long id, ProductModel productModel) {
	    productService.updateProduct(id, productModel);
	    return "redirect:/getAllProducts"; // Redirects to the list of products after the update
	}
	@GetMapping("/productform")
	public String getProductForm(Model model)
	{
		ProductModel productModel=new ProductModel();
		productModel.setMadeIn("India");
		productModel.setQuantity(2);
		productModel.setDiscountrate(10.5);
		model.addAttribute("productModel",productModel);
		return "add-product";
	}
	@PostMapping("/save")
	public String saveProductDetails(@Valid ProductModel productModel, BindingResult bindingresult, Model model) {
		HashMap<String, String> validationErrors=new HashMap<String, String>();
		if(bindingresult.hasErrors())
		{
			for(FieldError fieldError:bindingresult.getFieldErrors())
			{
				validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("validationErrors",validationErrors);
			return "add-product";
		}
		
		productService.saveProductDetails(productModel);
		return "redirect:/getAllProducts";
	}
}
