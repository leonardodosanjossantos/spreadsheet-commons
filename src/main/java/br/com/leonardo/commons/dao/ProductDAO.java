package br.com.leonardo.commons.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.leonardo.commons.model.Product;
import br.com.leonardo.commons.repository.ProductRepository;

@Component
public class ProductDAO {
	
	@Autowired
	private ProductRepository productRepository;

	public List<Product> getProducts() {
		return (List<Product>)productRepository.findAll();
	}
	
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElse(null);
	}
	
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product updateProduct(Long id, Product product) {
		Product productUpdate = getProduct(id);
		if (productUpdate != null) {
			productUpdate.setName(product.getName());
			productUpdate.setDescription(product.getDescription());
			productUpdate.setFreeShipping(product.isFreeShipping());
			productUpdate.setLm(product.getLm());
			productUpdate.setPrice(product.getPrice());
			productUpdate = saveProduct(productUpdate);
		}
		return productUpdate;
	}
	
	public boolean deleteProduct(Long id) {
		try {
			productRepository.deleteById(id);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
