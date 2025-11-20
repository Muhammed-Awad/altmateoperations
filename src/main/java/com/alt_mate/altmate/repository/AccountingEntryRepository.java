package com.example.altmate_operations.repository;

import com.example.altmate_operations.model.AccountingEntry;
import com.example.altmate_operations.model.EntryType;
import com.example.altmate_operations.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountingEntryRepository extends JpaRepository<AccountingEntry, Long> {
    
    List<AccountingEntry> findByClientId(Long clientId);
    
    List<AccountingEntry> findByType(EntryType type);
    
    List<AccountingEntry> findByPaymentStatus(PaymentStatus paymentStatus);
    
    List<AccountingEntry> findByClientIdAndType(Long clientId, EntryType type);
    
    List<AccountingEntry> findByClientIdAndPaymentStatus(Long clientId, PaymentStatus paymentStatus);
    
    @Query("SELECT ae FROM AccountingEntry ae WHERE ae.date BETWEEN :startDate AND :endDate")
    List<AccountingEntry> findByDateBetween(@Param("startDate") LocalDateTime startDate, 
                                           @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT ae FROM AccountingEntry ae WHERE ae.client.id = :clientId AND ae.date BETWEEN :startDate AND :endDate")
    List<AccountingEntry> findByClientIdAndDateBetween(@Param("clientId") Long clientId, 
                                                       @Param("startDate") LocalDateTime startDate, 
                                                       @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT SUM(ae.amount) FROM AccountingEntry ae WHERE ae.client.id = :clientId AND ae.type = :type")
    BigDecimal getTotalAmountByClientAndType(@Param("clientId") Long clientId, 
                                             @Param("type") EntryType type);
    
    @Query("SELECT SUM(ae.amount) FROM AccountingEntry ae WHERE ae.client.id = :clientId AND ae.type = 'INCOME'")
    BigDecimal getTotalIncomeByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT SUM(ae.amount) FROM AccountingEntry ae WHERE ae.client.id = :clientId AND ae.type = 'EXPENSE'")
    BigDecimal getTotalExpenseByClient(@Param("clientId") Long clientId);
    
    @Query("SELECT SUM(ae.amount) FROM AccountingEntry ae WHERE ae.paymentStatus = 'PENDING'")
    BigDecimal getTotalPendingPayments();
}
