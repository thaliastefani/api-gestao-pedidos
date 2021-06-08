package br.com.fatecmm.apigestaopedidos.api.model.input;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInput {

    private String description;

    @Size(max = 25)
    @NotBlank
    @Column(nullable = false)
    private String sku;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double weight;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double height;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double width;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double depth;

    @Size(min = 1, max = 25)
    @NotBlank
    @Column(nullable = false)
    private String manufacturer;

    @DecimalMin("1")
    @NotNull
    @Column(nullable = false)
    private BigDecimal price;
}
