package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.EntryType;
import com.example.altmate_operations.model.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountingEntryCreateRequest {
    
    @NotNull(message = "Client ID is required")
    private Long clientId;
    
    @NotNull(message = "Entry type is required")
    private EntryType type;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
    
    private LocalDateTime dueDate;
    
    private LocalDateTime paidDate;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @Size(max = 500, message = "Invoice number must not exceed 500 characters")
    private String invoiceNumber;
}
