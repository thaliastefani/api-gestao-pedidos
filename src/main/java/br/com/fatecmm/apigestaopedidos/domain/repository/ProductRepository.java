package br.com.fatecmm.apigestaopedidos.domain.repository;


import br.com.fatecmm.apigestaopedidos.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
