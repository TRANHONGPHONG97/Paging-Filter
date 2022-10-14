package com.cg.demo_paging_filter.repository;


import com.cg.demo_paging_filter.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> findByPrice(BigDecimal price, Pageable pageable);

  Page<Product> findByAction(boolean action, Pageable pageable);

  Page<Product> findByTitleContaining(String title, Pageable pageable);
}
