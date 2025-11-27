package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.*;
import com.alt_mate.altmate.model.AccountingEntry;
import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.EntryType;
import com.alt_mate.altmate.model.PaymentStatus;
import com.alt_mate.altmate.service.AccountingEntryService;
import com.alt_mate.altmate.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounting")
@RequiredArgsConstructor
public class AccountingEntryController {
    
    private final AccountingEntryService accountingEntryService;
    private final ClientService clientService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<AccountingEntryDTO>> createEntry(@Valid @RequestBody AccountingEntryCreateRequest request) {
        AccountingEntry entry = mapToEntity(request);
        AccountingEntry createdEntry = accountingEntryService.createAccountingEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Accounting entry created successfully", mapToDTO(createdEntry)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountingEntryDTO>> getEntryById(@PathVariable Long id) {
        AccountingEntry entry = accountingEntryService.getAccountingEntryById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(entry)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountingEntryDTO>>> getAllEntries() {
        List<AccountingEntry> entries = accountingEntryService.getAllAccountingEntries();
        List<AccountingEntryDTO> entryDTOs = entries.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(entryDTOs));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<AccountingEntryDTO>>> getEntriesByClient(@PathVariable Long clientId) {
        List<AccountingEntry> entries = accountingEntryService.getEntriesByClient(clientId);
        List<AccountingEntryDTO> entryDTOs = entries.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(entryDTOs));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<AccountingEntryDTO>>> getEntriesByType(@PathVariable EntryType type) {
        List<AccountingEntry> entries = accountingEntryService.getEntriesByType(type);
        List<AccountingEntryDTO> entryDTOs = entries.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(entryDTOs));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AccountingEntryDTO>>> getEntriesByStatus(@PathVariable PaymentStatus status) {
        List<AccountingEntry> entries = accountingEntryService.getEntriesByPaymentStatus(status);
        List<AccountingEntryDTO> entryDTOs = entries.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(entryDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountingEntryDTO>> updateEntry(
            @PathVariable Long id,
            @Valid @RequestBody AccountingEntryCreateRequest request) {
        AccountingEntry entryDetails = mapToEntity(request);
        AccountingEntry updatedEntry = accountingEntryService.updateAccountingEntry(id, entryDetails);
        return ResponseEntity.ok(ApiResponse.success("Accounting entry updated successfully", mapToDTO(updatedEntry)));
    }
    
    @PutMapping("/{id}/payment-status")
    public ResponseEntity<ApiResponse<AccountingEntryDTO>> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        AccountingEntry entry = accountingEntryService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Payment status updated successfully", mapToDTO(entry)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEntry(@PathVariable Long id) {
        accountingEntryService.deleteAccountingEntry(id);
        return ResponseEntity.ok(ApiResponse.success("Accounting entry deleted successfully", null));
    }
    
    @GetMapping("/client/{clientId}/income")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalIncomeByClient(@PathVariable Long clientId) {
        BigDecimal totalIncome = accountingEntryService.getTotalIncomeByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(totalIncome));
    }
    
    @GetMapping("/client/{clientId}/expense")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalExpenseByClient(@PathVariable Long clientId) {
        BigDecimal totalExpense = accountingEntryService.getTotalExpenseByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(totalExpense));
    }
    
    @GetMapping("/client/{clientId}/profit")
    public ResponseEntity<ApiResponse<BigDecimal>> getNetProfitByClient(@PathVariable Long clientId) {
        BigDecimal netProfit = accountingEntryService.getNetProfitByClient(clientId);
        return ResponseEntity.ok(ApiResponse.success(netProfit));
    }
    
    @GetMapping("/pending-payments/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalPendingPayments() {
        BigDecimal totalPending = accountingEntryService.getTotalPendingPayments();
        return ResponseEntity.ok(ApiResponse.success(totalPending));
    }
    
    private AccountingEntryDTO mapToDTO(AccountingEntry entry) {
        AccountingEntryDTO dto = new AccountingEntryDTO();
        dto.setId(entry.getId());
        dto.setType(entry.getType());
        dto.setAmount(entry.getAmount());
        dto.setPaymentStatus(entry.getPaymentStatus());
        dto.setDueDate(entry.getDueDate());
        dto.setPaidDate(entry.getPaidDate());
        dto.setDescription(entry.getDescription());
        dto.setInvoiceNumber(entry.getInvoiceNumber());
        dto.setDate(entry.getDate());
        
        if (entry.getClient() != null) {
            dto.setClientId(entry.getClient().getId());
            dto.setClientName(entry.getClient().getName());
        }
        
        return dto;
    }
    
    private AccountingEntry mapToEntity(AccountingEntryCreateRequest request) {
        AccountingEntry entry = new AccountingEntry();
        entry.setType(request.getType());
        entry.setAmount(request.getAmount());
        entry.setPaymentStatus(request.getPaymentStatus());
        entry.setDueDate(request.getDueDate());
        entry.setPaidDate(request.getPaidDate());
        entry.setDescription(request.getDescription());
        entry.setInvoiceNumber(request.getInvoiceNumber());
        
        // Set Client relationship
        if (request.getClientId() != null) {
            Client client = clientService.getClientById(request.getClientId());
            entry.setClient(client);
        }
        
        return entry;
    }
}
