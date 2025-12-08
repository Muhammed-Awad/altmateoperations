package com.alt_mate.altmate.model;

public enum UserRole {
    ADMIN,              // Main account - full access
    COORDINATOR,        // Manages clients, assigns tasks xxx
    CONTENT_TEAM,    // Creates and schedules content, assigns tasks
    DESIGNER,           // Receives design tasks
    MEDIA_TEAM,   // Handles photo/video shoots
    MODERATOR,          // Manages inbox and comments
    MEDIA_BUYER,        // Manages ad campaigns
    ACCOUNTANT,         // Financial management
    CHATBOT_MANAGER     // Configures chatbots
}
