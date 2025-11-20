package com.example.altmate_operations.controller;

import com.example.altmate_operations.DTO.ApiResponse;
import com.example.altmate_operations.DTO.SocialAccountDTO;
import com.example.altmate_operations.model.SocialAccount;
import com.example.altmate_operations.model.SocialPlatform;
import com.example.altmate_operations.service.SocialAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/social-accounts")
@RequiredArgsConstructor
public class SocialAccountController {
    
    private final SocialAccountService socialAccountService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<SocialAccountDTO>> createSocialAccount(@RequestBody SocialAccount account) {
        SocialAccount createdAccount = socialAccountService.createSocialAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Social account created successfully", mapToDTO(createdAccount)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SocialAccountDTO>> getSocialAccountById(@PathVariable Long id) {
        SocialAccount account = socialAccountService.getSocialAccountById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(account)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<SocialAccountDTO>>> getAllSocialAccounts() {
        List<SocialAccount> accounts = socialAccountService.getAllSocialAccounts();
        List<SocialAccountDTO> accountDTOs = accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(accountDTOs));
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<ApiResponse<List<SocialAccountDTO>>> getAccountsByClient(@PathVariable Long clientId) {
        List<SocialAccount> accounts = socialAccountService.getAccountsByClient(clientId);
        List<SocialAccountDTO> accountDTOs = accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(accountDTOs));
    }
    
    @GetMapping("/platform/{platform}")
    public ResponseEntity<ApiResponse<List<SocialAccountDTO>>> getAccountsByPlatform(@PathVariable SocialPlatform platform) {
        List<SocialAccount> accounts = socialAccountService.getAccountsByPlatform(platform);
        List<SocialAccountDTO> accountDTOs = accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(accountDTOs));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<SocialAccountDTO>>> getActiveAccounts() {
        List<SocialAccount> accounts = socialAccountService.getActiveAccounts();
        List<SocialAccountDTO> accountDTOs = accounts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(accountDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SocialAccountDTO>> updateSocialAccount(
            @PathVariable Long id,
            @RequestBody SocialAccount accountDetails) {
        SocialAccount updatedAccount = socialAccountService.updateSocialAccount(id, accountDetails);
        return ResponseEntity.ok(ApiResponse.success("Social account updated successfully", mapToDTO(updatedAccount)));
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<SocialAccountDTO>> activateAccount(@PathVariable Long id) {
        SocialAccount account = socialAccountService.activateAccount(id);
        return ResponseEntity.ok(ApiResponse.success("Account activated successfully", mapToDTO(account)));
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<SocialAccountDTO>> deactivateAccount(@PathVariable Long id) {
        SocialAccount account = socialAccountService.deactivateAccount(id);
        return ResponseEntity.ok(ApiResponse.success("Account deactivated successfully", mapToDTO(account)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSocialAccount(@PathVariable Long id) {
        socialAccountService.deleteSocialAccount(id);
        return ResponseEntity.ok(ApiResponse.success("Social account deleted successfully", null));
    }
    
    private SocialAccountDTO mapToDTO(SocialAccount account) {
        SocialAccountDTO dto = new SocialAccountDTO();
        dto.setId(account.getId());
        dto.setPlatform(account.getPlatform());
        dto.setAccountUsername(account.getAccountUsername());
        dto.setAccessToken(account.getAccessToken());
        dto.setRefreshToken(account.getRefreshToken());
        dto.setTokenExpiry(account.getTokenExpiresAt());
        dto.setIsActive(account.getIsActive());
        dto.setIsConnected(account.getIsConnected());
        dto.setConnectedAt(account.getConnectedAt());
        dto.setCreatedAt(account.getConnectedAt());
        dto.setUpdatedAt(account.getConnectedAt());
        
        if (account.getClient() != null) {
            dto.setClientId(account.getClient().getId());
            dto.setClientName(account.getClient().getName());
        }
        
        return dto;
    }
}
