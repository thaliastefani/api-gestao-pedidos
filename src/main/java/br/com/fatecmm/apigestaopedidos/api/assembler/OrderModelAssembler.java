package br.com.fatecmm.apigestaopedidos.api.assembler;

import br.com.fatecmm.apigestaopedidos.domain.model.Order;
import br.com.fatecmm.apigestaopedidos.api.model.OrderModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderModelAssembler {
    @Autowired
    private ModelMapper modelMapper;
    public OrderModel toModel(Order order) {
        return modelMapper.map(order, OrderModel.class);
    }
    public List<OrderModel> toCollectionModel(List<Order> orders) {
        return orders.stream()
                .map(order -> toModel(order))
                .collect(Collectors.toList());
    }
}
