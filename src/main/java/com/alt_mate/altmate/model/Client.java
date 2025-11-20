package com.alt_mate.altmate.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clients")

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ClientIndustry industry;

    @Column(length = 1000)
    private String description;

    private String logo; // URL or file path

    @Column(nullable = false)
    private Boolean isActive = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Relationships
    @ManyToMany
    @JoinTable(
            name = "client_users",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers = new HashSet<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<SocialAccount> socialAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<AdCampaign> adCampaigns = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<Task> tasks = new ArrayList<>();

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private ChatbotConfig chatbotConfig;

    @OneToMany(mappedBy = "client")
    private List<AccountingEntry> accountingEntries = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<ClientComplaint> complaints = new ArrayList<>();
}
