package br.com.fatecmm.apigestaopedidos.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    public ProductNotFoundException(String message) {
        super(message);
    }
}
