package fr.fullstack.shopapp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.fullstack.shopapp.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query(value="SELECT * FROM Products WHERE shop_id = ?1", nativeQuery = true)
	List<Product> findByShop(Long shopId, Pageable pageable);

	@Query(value="SELECT * FROM Products p WHERE p.shop_id = ?1 AND p.id IN (SELECT pc.product_id FROM products_categories pc WHERE pc.category_id = ?2)", nativeQuery = true)
	List<Product> findByShopAndCategory(Long shopId, Long categoryId, Pageable pageable);
}