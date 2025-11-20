package com.example.altmate_operations.model;

public enum UserRole {
    ADMIN,              // Main account - full access
    COORDINATOR,        // Manages clients, assigns tasks
    CONTENT_CREATOR,    // Creates and schedules content
    DESIGNER,           // Receives design tasks
    MEDIA_SPECIALIST,   // Handles photo/video shoots
    MODERATOR,          // Manages inbox and comments
    MEDIA_BUYER,        // Manages ad campaigns
    ACCOUNTANT,         // Financial management
    CHATBOT_MANAGER     // Configures chatbots
}
