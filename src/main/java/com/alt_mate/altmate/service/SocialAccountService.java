package com.example.altmate_operations.service;

import com.example.altmate_operations.model.SocialAccount;
import com.example.altmate_operations.model.SocialPlatform;
import com.example.altmate_operations.repository.SocialAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialAccountService {
    
    private final SocialAccountRepository socialAccountRepository;
    
    @Transactional
    public SocialAccount createSocialAccount(SocialAccount socialAccount) {
        socialAccount.setConnectedAt(LocalDateTime.now());
        return socialAccountRepository.save(socialAccount);
    }
    
    @Transactional
    public SocialAccount updateSocialAccount(Long accountId, SocialAccount accountDetails) {
        SocialAccount account = getSocialAccountById(accountId);
        account.setAccountName(accountDetails.getAccountName());
        account.setAccessToken(accountDetails.getAccessToken());
        account.setRefreshToken(accountDetails.getRefreshToken());
        account.setTokenExpiryDate(accountDetails.getTokenExpiryDate());
        account.setIsActive(accountDetails.getIsActive());
        return socialAccountRepository.save(account);
    }
    
    @Transactional
    public SocialAccount updateAccessToken(Long accountId, String accessToken, String refreshToken, LocalDateTime expiryDate) {
        SocialAccount account = getSocialAccountById(accountId);
        account.setAccessToken(accessToken);
        account.setRefreshToken(refreshToken);
        account.setTokenExpiryDate(expiryDate);
        return socialAccountRepository.save(account);
    }
    
    @Transactional
    public SocialAccount deactivateSocialAccount(Long accountId) {
        SocialAccount account = getSocialAccountById(accountId);
        account.setIsActive(false);
        return socialAccountRepository.save(account);
    }
    
    @Transactional
    public SocialAccount activateSocialAccount(Long accountId) {
        SocialAccount account = getSocialAccountById(accountId);
        account.setIsActive(true);
        return socialAccountRepository.save(account);
    }
    
    @Transactional
    public void deleteSocialAccount(Long accountId) {
        SocialAccount account = getSocialAccountById(accountId);
        socialAccountRepository.delete(account);
    }
    
    public SocialAccount getSocialAccountById(Long accountId) {
        return socialAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Social account not found with id: " + accountId));
    }
    
    public Optional<SocialAccount> getSocialAccountByPlatformId(String platformAccountId) {
        return socialAccountRepository.findByPlatformAccountId(platformAccountId);
    }
    
    public List<SocialAccount> getAllSocialAccounts() {
        return socialAccountRepository.findAll();
    }
    
    public List<SocialAccount> getSocialAccountsByClient(Long clientId) {
        return socialAccountRepository.findByClientId(clientId);
    }
    
    public List<SocialAccount> getAccountsByClient(Long clientId) {
        return getSocialAccountsByClient(clientId);
    }
    
    public List<SocialAccount> getAccountsByPlatform(SocialPlatform platform) {
        return socialAccountRepository.findByPlatform(platform);
    }
    
    public List<SocialAccount> getActiveAccounts() {
        return socialAccountRepository.findByIsActive(true);
    }
    
    @Transactional
    public SocialAccount activateAccount(Long accountId) {
        return activateSocialAccount(accountId);
    }
    
    @Transactional
    public SocialAccount deactivateAccount(Long accountId) {
        return deactivateSocialAccount(accountId);
    }
    
    public List<SocialAccount> getSocialAccountsByPlatform(SocialPlatform platform) {
        return socialAccountRepository.findByPlatform(platform);
    }
    
    public List<SocialAccount> getActiveSocialAccounts() {
        return socialAccountRepository.findByIsActive(true);
    }
    
    public List<SocialAccount> getSocialAccountsByClientAndPlatform(Long clientId, SocialPlatform platform) {
        return socialAccountRepository.findByClientIdAndPlatform(clientId, platform);
    }
    
    public List<SocialAccount> getActiveSocialAccountsByClient(Long clientId) {
        return socialAccountRepository.findByClientIdAndIsActive(clientId, true);
    }
    
    public Optional<SocialAccount> getActiveAccountByClientAndPlatform(Long clientId, SocialPlatform platform) {
        return socialAccountRepository.findActiveAccountByClientAndPlatform(clientId, platform);
    }
    
    public Long countActiveAccountsByClient(Long clientId) {
        return socialAccountRepository.countActiveAccountsByClient(clientId);
    }
}
