package com.alt_mate.altmate.model;

public enum TaskStatus {
    PENDING,            // Created, not started
    IN_PROGRESS,        // Being worked on
    SUBMITTED,          // Submitted for review
    REVISION_REQUESTED, // Needs changes
    APPROVED,           // Approved by content
    COMPLETED,          // Final state
    CANCELLED           // Task cancelled
}
