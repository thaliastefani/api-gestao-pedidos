package br.com.fatecmm.apigestaopedidos.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderModel {
    private Long id;

    private String customerName;

    private String phone;

    private BigDecimal valueProducts;

    private BigDecimal discountAmount;

    private BigDecimal amount;

    private List<ProductModel> products = new ArrayList<>();
}
