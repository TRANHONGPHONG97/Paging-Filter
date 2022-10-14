package com.cg.demo_paging_filter.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.demo_paging_filter.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.demo_paging_filter.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {

  @Autowired
  ProductRepository productRepository;

  @GetMapping("/products")
  public ResponseEntity<Map<String, Object>> getAllProducts(
          @RequestParam(required = false) String title,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size
  ) {

    try {
      List<Product> products = new ArrayList<Product>();
      Pageable paging = PageRequest.of(page, size);

      Page<Product> pageTuts;
      if (title == null)
        pageTuts = productRepository.findAll(paging);
      else
        pageTuts = productRepository.findByTitleContaining(title, paging);

      products = pageTuts.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("products", products);
      response.put("currentPage", pageTuts.getNumber());
      response.put("totalItems", pageTuts.getTotalElements());
      response.put("totalPages", pageTuts.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/products/price")
  public ResponseEntity<Map<String, Object>> findByPrice(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size
  ) {
    try {
      List<Product> products = new ArrayList<Product>();
      Pageable paging = PageRequest.of(page, size);

      Page<Product> pageTuts = productRepository.findByPrice(BigDecimal.valueOf(2000), paging);
      products = pageTuts.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("products", products);
      response.put("currentPage", pageTuts.getNumber());
      response.put("totalItems", pageTuts.getTotalElements());
      response.put("totalPages", pageTuts.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/products/action")
  public ResponseEntity<Map<String, Object>> findByAction(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size
  ) {
    try {
      List<Product> products = new ArrayList<Product>();
      Pageable paging = PageRequest.of(page, size);

      Page<Product> pageTuts = productRepository.findByAction(true, paging);
      products = pageTuts.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("products", products);
      response.put("currentPage", pageTuts.getNumber());
      response.put("totalItems", pageTuts.getTotalElements());
      response.put("totalPages", pageTuts.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
