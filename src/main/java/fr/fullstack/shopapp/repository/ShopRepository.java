package fr.fullstack.shopapp.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.fullstack.shopapp.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    // FILTERS
    Page<Shop> findByInVacations(boolean inVacations, Pageable pageable);

    Page<Shop> findByCreatedAtLessThan(LocalDate date, Pageable pageable);

    Page<Shop> findByCreatedAtGreaterThan(LocalDate date, Pageable pageable);

    Page<Shop> findByCreatedAtBetween(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    Page<Shop> findByInVacationsAndCreatedAtLessThan(boolean inVacations, LocalDate date, Pageable pageable);

    Page<Shop> findByInVacationsAndCreatedAtGreaterThan(boolean inVacations, LocalDate date, Pageable pageable);

    Page<Shop> findByInVacationsAndCreatedAtLessThanAndCreatedAtGreaterThan(boolean inVacations, LocalDate dateStart,
            LocalDate dateEnd, Pageable pageable);

    // SORT
    Page<Shop> findByOrderByNameAsc(Pageable pageable);

    Page<Shop> findByOrderByCreatedAtAsc(Pageable pageable);

    @Query(
        value="SELECT *,"
            + "(SELECT COUNT(*) FROM products p WHERE p.shop_id = s.id) as nbProducts, "
            + "(SELECT COUNT(DISTINCT pc.category_id) FROM products_categories pc WHERE pc.product_id IN (SELECT p.id FROM products p WHERE p.shop_id = s.id)) as nbCategories "
            + "FROM shops s "
            + "ORDER BY (SELECT COUNT(*) FROM products p WHERE p.shop_id = s.id) DESC",
        countQuery =  "SELECT * "
                    + "FROM shops s "
                    + "ORDER BY (SELECT COUNT(*) FROM products p WHERE p.shop_id = s.id) DESC",
        nativeQuery = true
    )
    Page<Shop> findByOrderByNbProductsAsc(Pageable pageable);
}