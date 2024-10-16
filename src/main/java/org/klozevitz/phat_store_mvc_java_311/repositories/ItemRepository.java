package org.klozevitz.phat_store_mvc_java_311.repositories;

import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT * FROM item_t WHERE category_id = ?1 AND price BETWEEN ?3 AND ?2;", nativeQuery = true)
    List<Item> filterByCategoryIdAndPriceBetween(int categoryId, double minPrice, double maxPrice);


    @Query(value = "SELECT * FROM item_t WHERE brand_id =:brandId AND price<:minPrice AND price>:maxPrice", nativeQuery = true)
    List<Item> filterByBrandIdAndPriceBetween(@Param("brandId") int brandId, @Param("minPrice") double minPrice,@Param("maxPrice")  double maxPrice);

    @Query(value = "SELECT * FROM item_t WHERE brand_id =:brandId AND category_id =:categoryId AND price<:minPrice  AND price>:maxPrice", nativeQuery = true)
    List<Item> filterByCategoryIdAndBrandIdAndPriceBetween(@Param("categoryId") int categoryId, @Param("brandId") int brandId, @Param("minPrice") double minPrice, @Param("maxPrice")  double maxPrice);

    @Query(value = "SELECT * FROM item_t WHERE category_id = :categoryId",nativeQuery = true)
    List<Item> queryCategoryId(@Param("categoryId") int categoryId);
    @Query(value = "SELECT * FROM item_t WHERE brand_id = :brandId",nativeQuery = true)
    List<Item> queryBrandId(@Param("brandId") int brandId);



    @Query(value = "SELECT MAX(price) FROM item_t", nativeQuery = true)
    Double maxPrice();
    @Query(value = "SELECT MIN(price) FROM item_t", nativeQuery = true)
    Double minPrice();
}