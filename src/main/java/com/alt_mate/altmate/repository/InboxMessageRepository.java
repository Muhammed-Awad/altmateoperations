package com.alt_mate.altmate.repository;

import com.alt_mate.altmate.model.InboxMessage;
import com.alt_mate.altmate.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InboxMessageRepository extends JpaRepository<InboxMessage, Long> {
    
    List<InboxMessage> findBySocialAccountId(Long socialAccountId);
    
    List<InboxMessage> findByStatus(MessageStatus status);
    
    List<InboxMessage> findByRepliedById(Long userId);
    
    List<InboxMessage> findBySocialAccountIdAndStatus(Long socialAccountId, MessageStatus status);
    
    @Query("SELECT im FROM InboxMessage im WHERE im.receivedAt BETWEEN :startDate AND :endDate")
    List<InboxMessage> findByReceivedAtBetween(@Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT im FROM InboxMessage im WHERE im.socialAccount.client.id = :clientId")
    List<InboxMessage> findByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT im FROM InboxMessage im WHERE im.socialAccount.client.id = :clientId AND im.status = :status")
    List<InboxMessage> findByClientIdAndStatus(@Param("clientId") Long clientId, 
                                               @Param("status") MessageStatus status);
    
    @Query("SELECT COUNT(im) FROM InboxMessage im WHERE im.status = 'PENDING'")
    Long countPendingMessages();
    
    @Query("SELECT COUNT(im) FROM InboxMessage im WHERE im.socialAccount.id = :accountId AND im.status = 'PENDING'")
    Long countPendingMessagesByAccount(@Param("accountId") Long accountId);
}
