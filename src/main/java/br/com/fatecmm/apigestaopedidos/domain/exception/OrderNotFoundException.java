package br.com.fatecmm.apigestaopedidos.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;
    public OrderNotFoundException(String message) {
        super(message);
    }
}
