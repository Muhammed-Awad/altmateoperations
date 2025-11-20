package com.example.altmate_operations.DTO;

import com.example.altmate_operations.model.EntryType;
import com.example.altmate_operations.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountingEntryDTO {
    private Long id;
    private Long clientId;
    private String clientName;
    private EntryType type;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime dueDate;
    private LocalDateTime paidDate;
    private String description;
    private String invoiceNumber;
    private LocalDateTime date;
}
