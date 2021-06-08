package br.com.fatecmm.apigestaopedidos.api.model;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {
    private Long id;

    private String description;

    private String sku;

    private Double weight;

    private Double height;

    private Double width;

    private Double depth;

    private String manufacturer;

    private BigDecimal price;
}
