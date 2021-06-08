package br.com.fatecmm.apigestaopedidos.api.assembler;

import br.com.fatecmm.apigestaopedidos.domain.model.Product;
import br.com.fatecmm.apigestaopedidos.api.model.input.ProductInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInputAssembler {
    @Autowired
    private ModelMapper modelMapper;
    public Product toDomainObject(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }
    public void copyToDomainObject(ProductInput productInput, Product product) {
        modelMapper.map(productInput, product);
    }
}
