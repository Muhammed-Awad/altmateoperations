package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.ClientIndustry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Optional<Client> findByName(String name);
    
    List<Client> findByIsActive(Boolean isActive);
    
    List<Client> findByIndustry(ClientIndustry industry);
    
    List<Client> findByIndustryAndIsActive(ClientIndustry industry, Boolean isActive);
    
    @Query("SELECT c FROM Client c WHERE c.name LIKE %:name%")
    List<Client> searchByName(@Param("name") String name);
    
    @Query("SELECT c FROM Client c JOIN c.assignedUsers u WHERE u.id = :userId")
    List<Client> findByAssignedUserId(@Param("userId") Long userId);
    
    @Query("SELECT c FROM Client c JOIN c.assignedUsers u WHERE u.id = :userId AND c.isActive = :isActive")
    List<Client> findByAssignedUserIdAndIsActive(@Param("userId") Long userId, @Param("isActive") Boolean isActive);
    
    @Query("SELECT COUNT(c) FROM Client c WHERE c.isActive = true")
    Long countActiveClients();
}
