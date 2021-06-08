package br.com.fatecmm.apigestaopedidos.domain.exception;

public class DiscountExceedsLimitValue extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DiscountExceedsLimitValue(String message) {
        super(message);
    }
}
