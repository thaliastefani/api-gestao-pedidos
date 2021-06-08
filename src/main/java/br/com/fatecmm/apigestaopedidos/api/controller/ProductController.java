package br.com.fatecmm.apigestaopedidos.api.controller;

import br.com.fatecmm.apigestaopedidos.api.assembler.ProductInputAssembler;
import br.com.fatecmm.apigestaopedidos.api.assembler.ProductModelAssembler;
import br.com.fatecmm.apigestaopedidos.domain.model.Product;
import br.com.fatecmm.apigestaopedidos.domain.service.ProductService;
import br.com.fatecmm.apigestaopedidos.api.model.ProductModel;
import br.com.fatecmm.apigestaopedidos.api.model.input.ProductInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductInputAssembler productInputAssembler;
    @Autowired
    private ProductModelAssembler productModelAssembler;
    @GetMapping
    public List<ProductModel> list() {
        return productModelAssembler.toCollectionModel(productService.findAll());
    }
    @GetMapping("/{id}")
    public ProductModel find(@PathVariable Long id) {
        return productModelAssembler.toModel(productService.findById(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel add(@RequestBody @Valid ProductInput productInput) {
        Product product = productInputAssembler.toDomainObject(productInput);
        product = productService.add(product);
        return productModelAssembler.toModel(product);
    }
    @PutMapping("/{id}")
    public ProductModel update(@RequestBody @Valid ProductInput productInput, @PathVariable Long id) {
        Product product = productService.findById(id);
        productInputAssembler.copyToDomainObject(productInput, product);
        return productModelAssembler.toModel(productService.add(product));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        productService.remove(id);
    }
}
