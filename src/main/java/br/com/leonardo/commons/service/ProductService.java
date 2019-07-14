package br.com.leonardo.commons.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leonardo.commons.model.Product;
import br.com.leonardo.commons.model.ProductDTO;
import br.com.leonardo.commons.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public void saveList(List<ProductDTO> listProductDTO) {
		final ModelMapper modelMapper = new ModelMapper();
		
		List<Product> listProduct = new ArrayList<>();
		listProductDTO.stream().forEach(itemDTO -> {
			listProduct.add(modelMapper.map(itemDTO, Product.class));
		});
		productRepository.saveAll(listProduct);
	}
	
}
