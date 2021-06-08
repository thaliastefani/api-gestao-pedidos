package br.com.fatecmm.apigestaopedidos.domain.model;

import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "gp_product")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(columnDefinition = "serial")
    private Long id;

    private String description;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double width;

    @Column(nullable = false)
    private Double depth;

    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = false)
    private BigDecimal price;
}
