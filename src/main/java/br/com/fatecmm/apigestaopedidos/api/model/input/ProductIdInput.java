package br.com.fatecmm.apigestaopedidos.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Getter
@Setter
public class ProductIdInput {

    @NotNull
    private Long id;
}
