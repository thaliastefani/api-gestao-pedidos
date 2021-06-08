package br.com.fatecmm.apigestaopedidos.domain.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Builder
@Table(name = "gp_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private BigDecimal valueProducts;

    @Column(nullable = false)
    private BigDecimal discountAmount;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToMany
    @JoinTable(name = "gp_product_order", joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") })
    private List<Product> products = new ArrayList<>();

    public void estimateTotalValue() {
        this.valueProducts = this.getProducts().stream()
                .map(product -> product.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.amount = this.valueProducts.subtract(this.discountAmount);
    }
}
