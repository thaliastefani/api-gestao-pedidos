package br.com.fatecmm.apigestaopedidos.domain.repository;

import br.com.fatecmm.apigestaopedidos.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
