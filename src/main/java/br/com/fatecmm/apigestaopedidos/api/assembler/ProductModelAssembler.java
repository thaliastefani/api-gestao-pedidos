package br.com.fatecmm.apigestaopedidos.api.assembler;

import br.com.fatecmm.apigestaopedidos.domain.model.Product;
import br.com.fatecmm.apigestaopedidos.api.model.ProductModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProductModelAssembler {
    @Autowired
    private ModelMapper modelMapper;
    public ProductModel toModel(Product product) {
        return modelMapper.map(product, ProductModel.class);
    }
    public List<ProductModel> toCollectionModel(List<Product> products) {
        return products.stream()
                .map(product -> toModel(product))
                .collect(Collectors.toList());
    }
}
