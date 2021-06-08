package br.com.fatecmm.apigestaopedidos.api.assembler;

import br.com.fatecmm.apigestaopedidos.domain.model.Order;
import br.com.fatecmm.apigestaopedidos.api.model.input.OrderInput;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderInputAssembler {
    @Autowired
    private ModelMapper modelMapper;
    public Order toDomainObject(OrderInput orderInput) {
        return modelMapper.map(orderInput, Order.class);
    }
    public void copyToDomainObject(OrderInput orderInput, Order order) {
        modelMapper.map(orderInput, order);
    }
}
