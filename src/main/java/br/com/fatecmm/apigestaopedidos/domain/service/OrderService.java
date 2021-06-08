package br.com.fatecmm.apigestaopedidos.domain.service;


import br.com.fatecmm.apigestaopedidos.domain.exception.DiscountExceedsLimitValue;
import br.com.fatecmm.apigestaopedidos.domain.exception.EntityInUseException;
import br.com.fatecmm.apigestaopedidos.domain.exception.OrderNotFoundException;
import br.com.fatecmm.apigestaopedidos.domain.model.Order;
import br.com.fatecmm.apigestaopedidos.domain.model.Product;
import br.com.fatecmm.apigestaopedidos.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    public static final String  MESSAGE_ORDER_NOT_FOUND = "There is no order registered with the code %d";
    public static final String  MESSAGE_ORDER_CANNOT_BE_REMOVED = "Order with code %d cannot be removed %d, " +
            "because it is in use";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format(MESSAGE_ORDER_NOT_FOUND, id)));
    }

    public Order purchaseOrder(Order order) {
        List<Product> products = new ArrayList<>();

        order.getProducts().forEach(item -> {
            products.add(productService.findById(item.getId()));
        });

        order.setProducts(products);
        order.estimateTotalValue();

        if (order.getAmount().doubleValue() <= order.getDiscountAmount().doubleValue()) {
            throw new DiscountExceedsLimitValue(String.format("Discount exceeds limit value"));
        }

        return orderRepository.save(order);
    }

    public void remove(Long id) {
        try {
            orderRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MESSAGE_ORDER_CANNOT_BE_REMOVED, id));
        } catch (EmptyResultDataAccessException ex) {
            throw new OrderNotFoundException(String.format(MESSAGE_ORDER_NOT_FOUND, id));
        }
    }
}