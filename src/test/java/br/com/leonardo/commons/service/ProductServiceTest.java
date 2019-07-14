package br.com.leonardo.commons.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.leonardo.commons.model.Product;
import br.com.leonardo.commons.model.ProductDTO;
import br.com.leonardo.commons.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	@Test
	public void saveAllTest() throws IOException {
		
		List<ProductDTO> listOfProducts = Arrays.asList(new ProductDTO(1l),new ProductDTO(2l));
		
		List<Product> expectedListProduct = Arrays.asList(new Product(1l),new Product(2l)); 
		
		productService.saveList(listOfProducts);
		
		verify(productRepository,times(1)).saveAll(expectedListProduct);
		
	}
	


}
