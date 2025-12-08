package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.SocialAccount;
import com.alt_mate.altmate.model.SocialPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    
    List<SocialAccount> findByClientId(Long clientId);
    
    List<SocialAccount> findByPlatform(SocialPlatform platform);
    
    List<SocialAccount> findByIsActive(Boolean isActive);
    
    List<SocialAccount> findByClientIdAndPlatform(Long clientId, SocialPlatform platform);
    
    List<SocialAccount> findByClientIdAndIsActive(Long clientId, Boolean isActive);
    
    Optional<SocialAccount> findByAccountId(String accountId);
    
    Optional<SocialAccount> findByPlatformAndAccountId(SocialPlatform platform, String accountId);
    
    @Query("SELECT sa FROM SocialAccount sa WHERE sa.client.id = :clientId AND sa.platform = :platform AND sa.isActive = true")
    Optional<SocialAccount> findActiveAccountByClientAndPlatform(@Param("clientId") Long clientId, 
                                                                  @Param("platform") SocialPlatform platform);
    
    @Query("SELECT COUNT(sa) FROM SocialAccount sa WHERE sa.client.id = :clientId AND sa.isActive = true")
    Long countActiveAccountsByClient(@Param("clientId") Long clientId);
}
