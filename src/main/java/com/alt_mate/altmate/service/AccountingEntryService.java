package com.example.altmate_operations.service;

import com.example.altmate_operations.model.AccountingEntry;
import com.example.altmate_operations.model.EntryType;
import com.example.altmate_operations.model.PaymentStatus;
import com.example.altmate_operations.repository.AccountingEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountingEntryService {
    
    private final AccountingEntryRepository accountingEntryRepository;
    
    @Transactional
    public AccountingEntry createAccountingEntry(AccountingEntry entry) {
        entry.setDate(LocalDateTime.now());
        return accountingEntryRepository.save(entry);
    }
    
    @Transactional
    public AccountingEntry updateAccountingEntry(Long entryId, AccountingEntry entryDetails) {
        AccountingEntry entry = getAccountingEntryById(entryId);
        entry.setDescription(entryDetails.getDescription());
        entry.setAmount(entryDetails.getAmount());
        entry.setType(entryDetails.getType());
        entry.setPaymentStatus(entryDetails.getPaymentStatus());
        entry.setInvoiceNumber(entryDetails.getInvoiceNumber());
        entry.setNotes(entryDetails.getNotes());
        return accountingEntryRepository.save(entry);
    }
    
    @Transactional
    public AccountingEntry updatePaymentStatus(Long entryId, PaymentStatus status) {
        AccountingEntry entry = getAccountingEntryById(entryId);
        entry.setPaymentStatus(status);
        return accountingEntryRepository.save(entry);
    }
    
    @Transactional
    public void deleteAccountingEntry(Long entryId) {
        AccountingEntry entry = getAccountingEntryById(entryId);
        accountingEntryRepository.delete(entry);
    }
    
    public AccountingEntry getAccountingEntryById(Long entryId) {
        return accountingEntryRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Accounting entry not found with id: " + entryId));
    }
    
    public List<AccountingEntry> getAllAccountingEntries() {
        return accountingEntryRepository.findAll();
    }
    
    public List<AccountingEntry> getEntriesByClient(Long clientId) {
        return accountingEntryRepository.findByClientId(clientId);
    }
    
    public List<AccountingEntry> getEntriesByType(EntryType type) {
        return accountingEntryRepository.findByType(type);
    }
    
    public List<AccountingEntry> getEntriesByPaymentStatus(PaymentStatus status) {
        return accountingEntryRepository.findByPaymentStatus(status);
    }
    
    public List<AccountingEntry> getEntriesByClientAndType(Long clientId, EntryType type) {
        return accountingEntryRepository.findByClientIdAndType(clientId, type);
    }
    
    public List<AccountingEntry> getEntriesByClientAndPaymentStatus(Long clientId, PaymentStatus status) {
        return accountingEntryRepository.findByClientIdAndPaymentStatus(clientId, status);
    }
    
    public List<AccountingEntry> getEntriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return accountingEntryRepository.findByDateBetween(startDate, endDate);
    }
    
    public List<AccountingEntry> getEntriesByClientAndDateRange(Long clientId, LocalDateTime startDate, LocalDateTime endDate) {
        return accountingEntryRepository.findByClientIdAndDateBetween(clientId, startDate, endDate);
    }
    
    public BigDecimal getTotalAmountByClientAndType(Long clientId, EntryType type) {
        BigDecimal total = accountingEntryRepository.getTotalAmountByClientAndType(clientId, type);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalIncomeByClient(Long clientId) {
        BigDecimal total = accountingEntryRepository.getTotalIncomeByClient(clientId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalExpenseByClient(Long clientId) {
        BigDecimal total = accountingEntryRepository.getTotalExpenseByClient(clientId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getNetProfitByClient(Long clientId) {
        BigDecimal income = getTotalIncomeByClient(clientId);
        BigDecimal expense = getTotalExpenseByClient(clientId);
        return income.subtract(expense);
    }
    
    public BigDecimal getTotalPendingPayments() {
        BigDecimal total = accountingEntryRepository.getTotalPendingPayments();
        return total != null ? total : BigDecimal.ZERO;
    }
}
