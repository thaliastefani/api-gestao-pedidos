package br.com.fatecmm.apigestaopedidos.api.controller;

import br.com.fatecmm.apigestaopedidos.api.assembler.OrderInputAssembler;
import br.com.fatecmm.apigestaopedidos.api.assembler.OrderModelAssembler;
import br.com.fatecmm.apigestaopedidos.domain.model.Order;
import br.com.fatecmm.apigestaopedidos.domain.repository.OrderRepository;
import br.com.fatecmm.apigestaopedidos.domain.service.OrderService;
import br.com.fatecmm.apigestaopedidos.api.model.OrderModel;
import br.com.fatecmm.apigestaopedidos.api.model.input.OrderInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderInputAssembler orderInputAssembler;
    @Autowired
    private OrderModelAssembler orderModelAssembler;
    @GetMapping
    public List<OrderModel> list() {
        return orderModelAssembler.toCollectionModel(orderService.findAll());
    }
    @GetMapping("/{id}")
    public OrderModel find(@PathVariable Long id) {
        return orderModelAssembler.toModel(orderService.findById(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel add(@RequestBody @Valid OrderInput orderInput) {
        Order order = orderInputAssembler.toDomainObject(orderInput);
        return orderModelAssembler.toModel(orderService.purchaseOrder(order));
    }
    @PutMapping("/{id}")
    public OrderModel update(@RequestBody @Valid OrderInput orderInput, @PathVariable Long id) {
        Order order = orderService.findById(id);
        orderInputAssembler.copyToDomainObject(orderInput, order);
        return orderModelAssembler.toModel(orderService.purchaseOrder(order));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        orderService.remove(id);
    }
}
