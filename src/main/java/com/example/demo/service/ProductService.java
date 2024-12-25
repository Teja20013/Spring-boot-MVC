package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.examplu.demo.model.ProductModel;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	public List<ProductEntity> getAllProducts() {
		// TODO Auto-generated method stub
		List<ProductEntity> products=productRepository.findAll();
		return products;
	}
	public ProductEntity searchById(Long id) {
		// TODO Auto-generated method stub
		Optional<ProductEntity>optionalData=productRepository.findById(id);
		if(optionalData.isPresent())
		{
			ProductEntity product=optionalData.get();
			return product;
		}
		else {
			return null;
		}
	}
    public void deleteProductById(Long id) {
		
		// TODO Auto-generated method stub
    	productRepository.deleteById(id);
		
	}
   
    //get product get by id
    public ProductModel getProductById(Long id) {
        return productRepository.findById(id)
                .map(productEntity -> {
                    ProductModel productModel = new ProductModel();
                    productModel.setProductName(productEntity.getProductName());
                    productModel.setBrand(productEntity.getBrand());
                    productModel.setMadeIn(productEntity.getMadeIn());
                    productModel.setQuantity(productEntity.getQuantity());
                    productModel.setPrice(productEntity.getPrice());
                    productModel.setDiscountrate(productEntity.getDiscountrate());
                    return productModel;
                })
                .orElse(null); // Return null if no product is found
    }
    public void updateProduct(Long id, ProductModel productModel) {
        ProductEntity Product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for ID: " + id));

        // Update fields with the data from ProductModel
        Product.setProductName(productModel.getProductName());
        Product.setBrand(productModel.getBrand());
        Product.setMadeIn(productModel.getMadeIn());
        Product.setQuantity(productModel.getQuantity());
        Product.setPrice(productModel.getPrice());
        Product.setDiscountrate(productModel.getDiscountrate());
        // Save the updated product
        productRepository.save(Product);
    }
    
    //save the details 
    	public void saveProductDetails(ProductModel productModel) {
		// TODO Auto-generated method
		double stockvalue;
		stockvalue = productModel.getPrice()*productModel.getQuantity();
		
		double discountprice;
		discountprice = productModel.getPrice()*productModel.getDiscountrate()/100;
		
		double offerprice;
		offerprice=productModel.getPrice()-discountprice;
		
		double taxprice;
		taxprice=productModel.getPrice()*0.18;
		
		double finalprice;
		finalprice=offerprice+taxprice;
		
		ProductEntity productEntity=new ProductEntity();
		productEntity.setProductName(productModel.getProductName());
		productEntity.setBrand(productModel.getBrand());
		productEntity.setMadeIn(productModel.getMadeIn());
		productEntity.setPrice(productModel.getPrice());
		productEntity.setQuantity(productModel.getQuantity());
		productEntity.setDiscountrate(productModel.getDiscountrate());
		productEntity.setTaxprice(taxprice);
		productEntity.setStockvalue(stockvalue);
		productEntity.setDiscountprice(discountprice);
		productEntity.setOfferprice(offerprice);
		productEntity.setFinalprice(finalprice);
		
		productRepository.save(productEntity);
	}
		
}
