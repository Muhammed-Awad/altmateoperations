package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.InboxMessage;
import com.alt_mate.altmate.model.MessageStatus;
import com.alt_mate.altmate.repository.InboxMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InboxMessageService {
    
    private final InboxMessageRepository inboxMessageRepository;
    
    @Transactional
    public InboxMessage createInboxMessage(InboxMessage message) {
        message.setReceivedAt(LocalDateTime.now());
        return inboxMessageRepository.save(message);
    }
    
    @Transactional
    public InboxMessage createMessage(InboxMessage message) {
        message.setReceivedAt(LocalDateTime.now());
        return inboxMessageRepository.save(message);
    }
    
    public InboxMessage getMessageById(Long messageId) {
        return getInboxMessageById(messageId);
    }
    
    public List<InboxMessage> getAllMessages() {
        return getAllInboxMessages();
    }
    
    public List<InboxMessage> getUnreadMessages() {
        return inboxMessageRepository.findByStatus(MessageStatus.UNREAD);
    }
    
    @Transactional
    public InboxMessage updateMessage(Long messageId, InboxMessage messageDetails) {
        InboxMessage message = getInboxMessageById(messageId);
        message.setMessageText(messageDetails.getMessageText());
        message.setStatus(messageDetails.getStatus());
        return inboxMessageRepository.save(message);
    }
    
    @Transactional
    public InboxMessage respondToMessage(Long messageId, String responseText) {
        InboxMessage message = getInboxMessageById(messageId);
        message.setStatus(MessageStatus.RESPONDED);
        message.setResponseText(responseText);
        message.setRespondedAt(LocalDateTime.now());
        return inboxMessageRepository.save(message);
    }
    
    @Transactional
    public void deleteMessage(Long messageId) {
        deleteInboxMessage(messageId);
    }
    
    public Long countUnreadMessages() {
        return (long) inboxMessageRepository.findByStatus(MessageStatus.UNREAD).size();
    }
    
    @Transactional
    public InboxMessage updateMessageStatus(Long messageId, MessageStatus status) {
        InboxMessage message = getInboxMessageById(messageId);
        message.setStatus(status);
        
        if (status == MessageStatus.RESPONDED) {
            message.setRespondedAt(LocalDateTime.now());
        }
        
        return inboxMessageRepository.save(message);
    }
    
    @Transactional
    public InboxMessage respondToMessage(Long messageId, Long userId, String responseText) {
        InboxMessage message = getInboxMessageById(messageId);
        message.setStatus(MessageStatus.RESPONDED);
        message.setResponseText(responseText);
        message.setRespondedAt(LocalDateTime.now());
        // Set respondedBy using UserService
        return inboxMessageRepository.save(message);
    }
    
    @Transactional
    public InboxMessage markAsRead(Long messageId) {
        InboxMessage message = getInboxMessageById(messageId);
        message.setStatus(MessageStatus.READ);
        return inboxMessageRepository.save(message);
    }
    
    @Transactional
    public void deleteInboxMessage(Long messageId) {
        InboxMessage message = getInboxMessageById(messageId);
        inboxMessageRepository.delete(message);
    }
    
    public InboxMessage getInboxMessageById(Long messageId) {
        return inboxMessageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Inbox message not found with id: " + messageId));
    }
    
    public List<InboxMessage> getAllInboxMessages() {
        return inboxMessageRepository.findAll();
    }
    
    public List<InboxMessage> getMessagesBySocialAccount(Long socialAccountId) {
        return inboxMessageRepository.findBySocialAccountId(socialAccountId);
    }
    
    public List<InboxMessage> getMessagesByStatus(MessageStatus status) {
        return inboxMessageRepository.findByStatus(status);
    }
    
    public List<InboxMessage> getMessagesByResponder(Long userId) {
        return inboxMessageRepository.findByRespondedById(userId);
    }
    
    public List<InboxMessage> getMessagesByAccountAndStatus(Long socialAccountId, MessageStatus status) {
        return inboxMessageRepository.findBySocialAccountIdAndStatus(socialAccountId, status);
    }
    
    public List<InboxMessage> getMessagesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return inboxMessageRepository.findByReceivedAtBetween(startDate, endDate);
    }
    
    public List<InboxMessage> getMessagesByClient(Long clientId) {
        return inboxMessageRepository.findByClientId(clientId);
    }
    
    public List<InboxMessage> getMessagesByClientAndStatus(Long clientId, MessageStatus status) {
        return inboxMessageRepository.findByClientIdAndStatus(clientId, status);
    }
    
    public Long countPendingMessages() {
        return inboxMessageRepository.countPendingMessages();
    }
    
    public Long countPendingMessagesByAccount(Long accountId) {
        return inboxMessageRepository.countPendingMessagesByAccount(accountId);
    }
}
