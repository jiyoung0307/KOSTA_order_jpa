package com.example.order_jpa.service;

import com.example.order_jpa.dto.ProductUpdateDTO;
import com.example.order_jpa.entity.Product;
import com.example.order_jpa.repository.JPAProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final JPAProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId);
        productRepository.delete(product);
    }

    public Product getProductInfo(long productId) {
        return productRepository.findById(productId);
    }

    /** 별도의 비즈니스 로직은 Service나 Entity의 메소드로 처리할 것*/
    public void updateProduct(ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.findById(productUpdateDTO.getProductId());
        product.setName(productUpdateDTO.getName());
        product.setPrice(productUpdateDTO.getPrice());
//        product.setQuantity(productUpdateDto.getQuantity());  /** 재고 수량은 변경할 수 없음 */
        productRepository.save(product);
    }
}