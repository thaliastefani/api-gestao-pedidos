package br.com.fatecmm.apigestaopedidos.domain.service;


import br.com.fatecmm.apigestaopedidos.domain.exception.EntityInUseException;
import br.com.fatecmm.apigestaopedidos.domain.exception.ProductNotFoundException;
import br.com.fatecmm.apigestaopedidos.domain.model.Product;
import br.com.fatecmm.apigestaopedidos.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public static final String  MESSAGE_PRODUCT_NOT_FOUND = "There is no product registered with the code %d";
    public static final String  MESSAGE_PRODUCT_CANNOT_BE_REMOVED = "Product with code %d cannot be removed because" +
            " it is in use";

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format(MESSAGE_PRODUCT_NOT_FOUND, id)));
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public void remove(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MESSAGE_PRODUCT_CANNOT_BE_REMOVED, id));
        } catch (EmptyResultDataAccessException ex) {
            throw new ProductNotFoundException(String.format(MESSAGE_PRODUCT_NOT_FOUND, id));
        }
    }
}
