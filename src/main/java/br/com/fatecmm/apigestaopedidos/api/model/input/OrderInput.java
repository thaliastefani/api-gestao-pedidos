package br.com.fatecmm.apigestaopedidos.api.model.input;

import lombok.*;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInput {
    @Size(max = 50)
    @NotBlank
    @Column(nullable = false)
    private String customerName;

    @Size(max = 25)
    @NotBlank
    @Column(nullable = false)
    private String phone;

    @PositiveOrZero
    @NotNull
    @Column(nullable = false)
    private BigDecimal discountAmount;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ProductIdInput> products;
}
