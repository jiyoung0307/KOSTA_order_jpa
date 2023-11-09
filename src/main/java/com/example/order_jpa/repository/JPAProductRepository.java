package com.example.order_jpa.repository;

import com.example.order_jpa.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JPAProductRepository {
//    JPA 사용 / 바로 구현체로 만듦
    @PersistenceContext     // 영속성 컨텍스트를 Bean 등록
    private final EntityManager em;

    /** EntityManager 사용 */
    /** JPA가 가지고 있는 메소드와 이름을 같게 하면 Service에서 따로 작업 안해도 됨*/
    public void save(Product product) {
        em.persist(product);
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Product findById(Long productId) {
        return em.find(Product.class, productId);
    }

    public void remove(Product product) {
        em.remove(product);
    }
}
