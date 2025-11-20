package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    
    List<ProductInfo> findByChatbotConfigId(Long chatbotConfigId);
    
    @Query("SELECT p FROM ProductInfo p WHERE p.chatbotConfig.id = :configId AND p.name LIKE %:name%")
    List<ProductInfo> searchByName(@Param("configId") Long configId, 
                                   @Param("name") String name);
    
    @Query("SELECT p FROM ProductInfo p WHERE p.chatbotConfig.id = :configId AND p.price BETWEEN :minPrice AND :maxPrice")
    List<ProductInfo> findByPriceRange(@Param("configId") Long configId, 
                                       @Param("minPrice") BigDecimal minPrice, 
                                       @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT COUNT(p) FROM ProductInfo p WHERE p.chatbotConfig.id = :configId")
    Long countByConfigId(@Param("configId") Long configId);
}
