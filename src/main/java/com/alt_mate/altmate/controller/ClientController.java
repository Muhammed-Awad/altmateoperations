package com.alt_mate.altmate.controller;

import com.alt_mate.altmate.DTO.*;
import com.alt_mate.altmate.model.Client;
import com.alt_mate.altmate.model.ClientIndustry;
import com.alt_mate.altmate.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    
    private final ClientService clientService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ClientDTO>> createClient(@Valid @RequestBody ClientCreateRequest request) {
        Client client = mapToEntity(request);
        Client createdClient = clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Client created successfully", mapToDTO(createdClient)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> getClientById(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(ApiResponse.success(mapToDTO(client)));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        List<ClientDTO> clientDTOs = clients.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(clientDTOs));
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getActiveClients() {
        List<Client> clients = clientService.getActiveClients();
        List<ClientDTO> clientDTOs = clients.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(clientDTOs));
    }
    
    @GetMapping("/industry/{industry}")
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getClientsByIndustry(@PathVariable ClientIndustry industry) {
        List<Client> clients = clientService.getClientsByIndustry(industry);
        List<ClientDTO> clientDTOs = clients.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(clientDTOs));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ClientDTO>>> getClientsByUser(@PathVariable Long userId) {
        List<Client> clients = clientService.getClientsByAssignedUser(userId);
        List<ClientDTO> clientDTOs = clients.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(clientDTOs));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientDTO>> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientCreateRequest request) {
        Client clientDetails = mapToEntity(request);
        Client updatedClient = clientService.updateClient(id, clientDetails);
        return ResponseEntity.ok(ApiResponse.success("Client updated successfully", mapToDTO(updatedClient)));
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<ClientDTO>> deactivateClient(@PathVariable Long id) {
        Client client = clientService.deactivateClient(id);
        return ResponseEntity.ok(ApiResponse.success("Client deactivated successfully", mapToDTO(client)));
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<ClientDTO>> activateClient(@PathVariable Long id) {
        Client client = clientService.activateClient(id);
        return ResponseEntity.ok(ApiResponse.success("Client activated successfully", mapToDTO(client)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(ApiResponse.success("Client deleted successfully", null));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ClientDTO>>> searchClients(@RequestParam String name) {
        List<Client> clients = clientService.searchClientsByName(name);
        List<ClientDTO> clientDTOs = clients.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(clientDTOs));
    }
    
    @GetMapping("/count/active")
    public ResponseEntity<ApiResponse<Long>> countActiveClients() {
        Long count = clientService.countActiveClients();
        return ResponseEntity.ok(ApiResponse.success(count));
    }
    
    private ClientDTO mapToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setIndustry(client.getIndustry());
        dto.setDescription(client.getDescription());
        dto.setLogo(client.getLogo());
        dto.setIsActive(client.getIsActive());
        dto.setCreatedAt(client.getCreatedAt());
        dto.setUpdatedAt(client.getUpdatedAt());
        return dto;
    }
    
    private Client mapToEntity(ClientCreateRequest request) {
        Client client = new Client();
        client.setName(request.getName());
        client.setIndustry(request.getIndustry());
        client.setDescription(request.getDescription());
        client.setLogo(request.getLogo());
        client.setIsActive(request.getIsActive());
        return client;
    }
}
