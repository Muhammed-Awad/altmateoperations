package com.alt_mate.altmate.service;

import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.ClientIndustry;
import com.alt_mate.altmate.model.User;
import com.alt_mate.altmate.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    
    private final ClientRepository clientRepository;
    
    @Transactional
    public Client createClient(Client client) {
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
    
    @Transactional
    public Client updateClient(Long clientId, Client clientDetails) {
        Client client = getClientById(clientId);
        client.setName(clientDetails.getName());
        client.setIndustry(clientDetails.getIndustry());
        client.setDescription(clientDetails.getDescription());
        client.setLogo(clientDetails.getLogo());
        client.setIsActive(clientDetails.getIsActive());
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
    
    @Transactional
    public void deleteClient(Long clientId) {
        Client client = getClientById(clientId);
        clientRepository.delete(client);
    }
    
    @Transactional
    public Client deactivateClient(Long clientId) {
        Client client = getClientById(clientId);
        client.setIsActive(false);
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
    
    @Transactional
    public Client activateClient(Long clientId) {
        Client client = getClientById(clientId);
        client.setIsActive(true);
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
    
    @Transactional
    public Client assignUserToClient(Long clientId, User user) {
        Client client = getClientById(clientId);
        client.getAssignedUsers().add(user);
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
    
    @Transactional
    public Client removeUserFromClient(Long clientId, User user) {
        Client client = getClientById(clientId);
        client.getAssignedUsers().remove(user);
        client.setUpdatedAt(LocalDateTime.now());
        return clientRepository.save(client);
    }
    
    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
    }
    
    public Client getClientByName(String name) {
        return clientRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Client not found with name: " + name));
    }
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public List<Client> getActiveClients() {
        return clientRepository.findByIsActive(true);
    }
    
    public List<Client> getClientsByIndustry(ClientIndustry industry) {
        return clientRepository.findByIndustry(industry);
    }
    
    public List<Client> getClientsByIndustryAndStatus(ClientIndustry industry, Boolean isActive) {
        return clientRepository.findByIndustryAndIsActive(industry, isActive);
    }
    
    public List<Client> searchClientsByName(String name) {
        return clientRepository.searchByName(name);
    }
    
    public List<Client> getClientsByAssignedUser(Long userId) {
        return clientRepository.findByAssignedUserId(userId);
    }
    
    public List<Client> getActiveClientsByAssignedUser(Long userId) {
        return clientRepository.findByAssignedUserIdAndIsActive(userId, true);
    }
    
    public Long countActiveClients() {
        return clientRepository.countActiveClients();
    }
}
