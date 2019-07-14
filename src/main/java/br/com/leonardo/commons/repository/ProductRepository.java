package br.com.leonardo.commons.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leonardo.commons.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

}