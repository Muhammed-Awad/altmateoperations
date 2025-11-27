# Altmate API Documentation

**Base URL:** `http://localhost:8081`  
**Authentication:** JWT Bearer Token (except for `/api/auth/**` endpoints)

---

## Table of Contents
1. [Authentication](#authentication)
2. [Users](#users)
3. [Clients](#clients)
4. [Social Accounts](#social-accounts)
5. [Posts](#posts)
6. [Comments](#comments)
7. [Tasks](#tasks)
8. [Task Revisions](#task-revisions)
9. [Ad Campaigns](#ad-campaigns)
10. [Accounting Entries](#accounting-entries)
11. [Inbox Messages](#inbox-messages)
12. [Client Complaints](#client-complaints)
13. [Content Generation Requests](#content-generation-requests)
14. [Competitor Analysis](#competitor-analysis)
15. [File Metadata](#file-metadata)
16. [Permissions](#permissions)
17. [Audit Logs](#audit-logs)
18. [Chatbot Config](#chatbot-config)
19. [Dashboard Snapshots](#dashboard-snapshots)

---

## Authentication

### Register User
- **POST** `/api/auth/register`
- **Access:** Public
- **Request Body:**
```json
{
  "fullname": "John Doe",
  "email": "john@example.com",
  "password": "Password123!",
  "role": "ADMIN"
}
```
- **Roles:** `ADMIN`, `COORDINATOR`, `CONTENT_CREATOR`, `DESIGNER`, `MEDIA_SPECIALIST`, `MODERATOR`, `MEDIA_BUYER`, `ACCOUNTANT`, `CHATBOT_MANAGER`
- **Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "accessToken": "jwt_token_here",
    "refreshToken": "refresh_token_here",
    "tokenType": "Bearer",
    "expiresIn": 86400000,
    "user": { "id": 1, "fullname": "John Doe", "email": "john@example.com", "role": "ADMIN" }
  }
}
```

### Login
- **POST** `/api/auth/login`
- **Access:** Public
- **Request Body:**
```json
{
  "email": "john@example.com",
  "password": "Password123!"
}
```
- **Response:** Same as Register

### Refresh Token
- **POST** `/api/auth/refresh`
- **Access:** Public
- **Request Body:**
```json
{
  "refreshToken": "refresh_token_here"
}
```

### Logout
- **POST** `/api/auth/logout`
- **Access:** Authenticated
- **Headers:** `Authorization: Bearer {token}`

---

## Users

### Create User
- **POST** `/api/users`
- **Access:** Admin only
- **Request Body:**
```json
{
  "fullname": "Sarah Designer",
  "email": "sarah@example.com",
  "password": "Designer123!",
  "role": "DESIGNER"
}
```

### Get User by ID
- **GET** `/api/users/{id}`
- **Access:** Admin, Coordinator

### Get All Users
- **GET** `/api/users`
- **Access:** Admin, Coordinator

### Get Users by Role
- **GET** `/api/users/role/{role}`
- **Access:** Admin, Coordinator
- **Roles:** `ADMIN`, `COORDINATOR`, `CONTENT_CREATOR`, `DESIGNER`, `MEDIA_SPECIALIST`, `MODERATOR`, `MEDIA_BUYER`, `ACCOUNTANT`, `CHATBOT_MANAGER`

### Get Active Users
- **GET** `/api/users/active`
- **Access:** Admin, Coordinator

### Update User
- **PUT** `/api/users/{id}`
- **Access:** Admin only
- **Request Body:**
```json
{
  "fullname": "Sarah Designer Updated",
  "email": "sarah.new@example.com",
  "role": "DESIGNER"
}
```

### Deactivate User
- **PUT** `/api/users/{id}/deactivate`
- **Access:** Admin only

### Activate User
- **PUT** `/api/users/{id}/activate`
- **Access:** Admin only

### Change Password
- **PUT** `/api/users/{id}/change-password`
- **Access:** Authenticated
- **Request Body:**
```json
{
  "oldPassword": "OldPass123!",
  "newPassword": "NewPass123!"
}
```

### Delete User
- **DELETE** `/api/users/{id}`
- **Access:** Admin only

### Search Users
- **GET** `/api/users/search?keyword={keyword}`
- **Access:** Admin, Coordinator

---

## Clients

### Create Client
- **POST** `/api/clients`
- **Access:** Authenticated
- **Request Body:**
```json
{
  "name": "Starbucks Coffee",
  "industry": "RESTAURANT",
  "description": "Premium coffee chain",
  "logo": "https://example.com/logo.png",
  "isActive": true,
  "assignedUserIds": [1, 2]
}
```
- **Industries:** `RESTAURANT`, `MEDICAL`, `COSMETICS`, `FASHION`, `RETAIL`, `SERVICES`, `OTHER`

### Get Client by ID
- **GET** `/api/clients/{id}`

### Get All Clients
- **GET** `/api/clients`

### Get Clients by Industry
- **GET** `/api/clients/industry/{industry}`

### Get Active Clients
- **GET** `/api/clients/active`

### Update Client
- **PUT** `/api/clients/{id}`
- **Request Body:** Same as Create

### Deactivate Client
- **PUT** `/api/clients/{id}/deactivate`

### Activate Client
- **PUT** `/api/clients/{id}/activate`

### Delete Client
- **DELETE** `/api/clients/{id}`

### Assign User to Client
- **POST** `/api/clients/{clientId}/assign-user/{userId}`

### Remove User from Client
- **DELETE** `/api/clients/{clientId}/remove-user/{userId}`

### Search Clients
- **GET** `/api/clients/search?keyword={keyword}`

---

## Social Accounts

### Create Social Account
- **POST** `/api/social-accounts`
- **Request Body:**
```json
{
  "client": { "id": 1 },
  "platform": "FACEBOOK",
  "accountUsername": "starbucks_official",
  "accountId": "fb_123456789",
  "accountName": "Starbucks Coffee Official",
  "accessToken": "token_here",
  "refreshToken": "refresh_token_here",
  "tokenExpiresAt": "2025-12-20T18:00:00",
  "isActive": true,
  "followerCount": 15000
}
```
- **Platforms:** `FACEBOOK`, `INSTAGRAM`, `TIKTOK`, `LINKEDIN`, `SNAPCHAT`, `X_TWITTER`

### Get Social Account by ID
- **GET** `/api/social-accounts/{id}`

### Get All Social Accounts
- **GET** `/api/social-accounts`

### Get Accounts by Client
- **GET** `/api/social-accounts/client/{clientId}`

### Get Accounts by Platform
- **GET** `/api/social-accounts/platform/{platform}`

### Get Active Accounts
- **GET** `/api/social-accounts/active`

### Update Social Account
- **PUT** `/api/social-accounts/{id}`

### Update Follower Count
- **PUT** `/api/social-accounts/{id}/followers?count={count}`

### Refresh Access Token
- **PUT** `/api/social-accounts/{id}/refresh-token`
- **Request Body:**
```json
{
  "accessToken": "new_token",
  "refreshToken": "new_refresh_token",
  "expiresAt": "2026-01-20T18:00:00"
}
```

### Deactivate Account
- **PUT** `/api/social-accounts/{id}/deactivate`

### Activate Account
- **PUT** `/api/social-accounts/{id}/activate`

### Delete Social Account
- **DELETE** `/api/social-accounts/{id}`

---

## Posts

### Create Post
- **POST** `/api/posts`
- **Request Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "content": "Post content here with hashtags #example",
  "hook": "Attention-grabbing hook",
  "slogan": "Brand slogan",
  "mediaUrls": ["https://example.com/image1.jpg", "https://example.com/image2.jpg"],
  "status": "DRAFT",
  "scheduledAt": "2025-11-21T09:00:00",
  "createdById": 1
}
```
- **Statuses:** `DRAFT`, `SCHEDULED`, `PUBLISHED`, `FAILED`

### Get Post by ID
- **GET** `/api/posts/{id}`

### Get All Posts
- **GET** `/api/posts`

### Get Posts by Client
- **GET** `/api/posts/client/{clientId}`

### Get Posts by Status
- **GET** `/api/posts/status/{status}`

### Get Posts by Social Account
- **GET** `/api/posts/account/{accountId}`

### Get Scheduled Posts Due
- **GET** `/api/posts/scheduled-due`

### Get Top Performing Posts
- **GET** `/api/posts/top-performing/client/{clientId}`

### Update Post
- **PUT** `/api/posts/{id}`
- **Request Body:** Same as Create

### Update Post Status
- **PUT** `/api/posts/{id}/status?status={status}`

### Schedule Post
- **PUT** `/api/posts/{id}/schedule?scheduledAt={datetime}`

### Publish Post
- **PUT** `/api/posts/{id}/publish`

### Update Post Metrics
- **PUT** `/api/posts/{id}/metrics?reach={reach}&impressions={impressions}&likes={likes}&comments={comments}&shares={shares}`

### Delete Post
- **DELETE** `/api/posts/{id}`

---

## Comments

### Create Comment
- **POST** `/api/comments`
- **Request Body:**
```json
{
  "socialAccountId": 1,
  "postId": 1,
  "commenterName": "Sarah (@coffee_addict_sarah)",
  "commenterProfileUrl": "https://instagram.com/coffee_addict_sarah",
  "commentText": "Love this! 😍"
}
```

### Get Comment by ID
- **GET** `/api/comments/{id}`

### Get All Comments
- **GET** `/api/comments`

### Get Comments by Post
- **GET** `/api/comments/post/{postId}`

### Get Comments by Status
- **GET** `/api/comments/status/{status}`
- **Statuses:** `PENDING`, `REPLIED`, `HIDDEN`

### Get Pending Comments
- **GET** `/api/comments/pending`

### Update Comment
- **PUT** `/api/comments/{id}`

### Update Comment Status
- **PUT** `/api/comments/{id}/status?status={status}`

### Approve Comment
- **PUT** `/api/comments/{id}/approve`

### Hide Comment
- **PUT** `/api/comments/{id}/hide`

### Reply to Comment
- **PUT** `/api/comments/{id}/reply?userId={userId}`
- **Request Body:**
```json
{
  "replyText": "Thank you for your interest! Available tomorrow."
}
```

### Delete Comment
- **DELETE** `/api/comments/{id}`

---

## Tasks

### Create Task
- **POST** `/api/tasks`
- **Request Body:**
```json
{
  "clientId": 1,
  "type": "DESIGN",
  "title": "Winter Holiday Instagram Post Design",
  "description": "Create festive Instagram post for winter drinks menu",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-11-25T17:00:00",
  "assignedToId": 2,
  "assignedById": 1,
  "designBrief": "Use warm colors, holiday theme",
  "referenceImages": ["https://example.com/ref1.jpg"],
  "moodBoard": "https://example.com/moodboard.pdf",
  "shootDate": "2025-11-24T10:00:00"
}
```
- **Types:** `DESIGN`, `MEDIA_SHOOT`, `CONTENT_WRITING`, `VIDEO_EDITING`
- **Statuses:** `PENDING`, `IN_PROGRESS`, `SUBMITTED`, `REVISION_REQUESTED`, `APPROVED`, `COMPLETED`, `CANCELLED`
- **Priorities:** `LOW`, `MEDIUM`, `HIGH`, `URGENT`

### Get Task by ID
- **GET** `/api/tasks/{id}`

### Get All Tasks
- **GET** `/api/tasks`

### Get Tasks by Client
- **GET** `/api/tasks/client/{clientId}`

### Get Tasks by Status
- **GET** `/api/tasks/status/{status}`

### Get Tasks by Type
- **GET** `/api/tasks/type/{type}`

### Get Tasks Assigned to User
- **GET** `/api/tasks/assigned/{userId}`

### Get Overdue Tasks
- **GET** `/api/tasks/overdue`

### Update Task
- **PUT** `/api/tasks/{id}`

### Update Task Status
- **PUT** `/api/tasks/{id}/status?status={status}`

### Submit Task
- **PUT** `/api/tasks/{id}/submit`

### Approve Task
- **PUT** `/api/tasks/{id}/approve?userId={userId}`

### Request Revision
- **PUT** `/api/tasks/{id}/request-revision?userId={userId}`
- **Request Body:**
```json
{
  "revisionNotes": "Please adjust colors and add more text"
}
```

### Complete Task
- **PUT** `/api/tasks/{id}/complete`

### Add Deliverable
- **PUT** `/api/tasks/{id}/deliverable?deliverableUrl={url}`

### Delete Task
- **DELETE** `/api/tasks/{id}`

### Count Tasks by Status
- **GET** `/api/tasks/count/status/{status}`

---

## Task Revisions

### Create Task Revision
- **POST** `/api/task-revisions`
- **Request Body:**
```json
{
  "taskId": 1,
  "revisionNotes": "Please change background color to blue",
  "requestedById": 1
}
```

### Get Revision by ID
- **GET** `/api/task-revisions/{id}`

### Get All Revisions
- **GET** `/api/task-revisions`

### Get Revisions by Task
- **GET** `/api/task-revisions/task/{taskId}`

### Update Revision
- **PUT** `/api/task-revisions/{id}`

### Delete Revision
- **DELETE** `/api/task-revisions/{id}`

### Count Revisions for Task
- **GET** `/api/task-revisions/task/{taskId}/count`

---

## Ad Campaigns

### Create Campaign
- **POST** `/api/campaigns`
- **Request Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "campaignName": "Winter Menu Launch 2025",
  "objective": "CONVERSIONS",
  "totalBudget": 5000.00,
  "autoPauseEnabled": true,
  "status": "ACTIVE",
  "startDate": "2025-11-21T00:00:00",
  "endDate": "2025-12-31T23:59:59",
  "managedById": 1
}
```
- **Objectives:** `AWARENESS`, `TRAFFIC`, `ENGAGEMENT`, `LEADS`, `CONVERSIONS`
- **Statuses:** `ACTIVE`, `PAUSED`, `COMPLETED`, `CANCELLED`

### Get Campaign by ID
- **GET** `/api/campaigns/{id}`

### Get All Campaigns
- **GET** `/api/campaigns`

### Get Campaigns by Client
- **GET** `/api/campaigns/client/{clientId}`

### Get Campaigns by Status
- **GET** `/api/campaigns/status/{status}`

### Update Campaign
- **PUT** `/api/campaigns/{id}`

### Update Campaign Status
- **PUT** `/api/campaigns/{id}/status?status={status}`

### Update Spent Amount
- **PUT** `/api/campaigns/{id}/spend?amount={amount}`

### Update Campaign Metrics
- **PUT** `/api/campaigns/{id}/metrics?impressions={num}&clicks={num}&conversions={num}`

### Delete Campaign
- **DELETE** `/api/campaigns/{id}`

### Get Total Spent by Client
- **GET** `/api/campaigns/client/{clientId}/total-spent`

### Auto-Pause Budget Exhausted Campaigns
- **POST** `/api/campaigns/auto-pause`

---

## Accounting Entries

### Create Entry
- **POST** `/api/accounting`
- **Request Body:**
```json
{
  "clientId": 1,
  "type": "COLLECTION_PACKAGE",
  "amount": 3500.00,
  "paymentStatus": "PAID",
  "dueDate": "2025-11-20T00:00:00",
  "paidDate": "2025-11-19T14:30:00",
  "description": "Monthly social media management - November 2025",
  "invoiceNumber": "INV-2025-001"
}
```
- **Types:** `COLLECTION_AD`, `COLLECTION_PACKAGE`, `COLLECTION_DEBT`, `EXPENSE`
- **Payment Statuses:** `PENDING`, `PAID`, `OVERDUE`, `CANCELLED`

### Get Entry by ID
- **GET** `/api/accounting/{id}`

### Get All Entries
- **GET** `/api/accounting`

### Get Entries by Client
- **GET** `/api/accounting/client/{clientId}`

### Get Entries by Type
- **GET** `/api/accounting/type/{type}`

### Get Entries by Payment Status
- **GET** `/api/accounting/status/{status}`

### Update Entry
- **PUT** `/api/accounting/{id}`

### Update Payment Status
- **PUT** `/api/accounting/{id}/payment-status?status={status}`

### Delete Entry
- **DELETE** `/api/accounting/{id}`

### Get Total Income by Client
- **GET** `/api/accounting/client/{clientId}/income`

### Get Total Expense by Client
- **GET** `/api/accounting/client/{clientId}/expense`

### Get Net Profit by Client
- **GET** `/api/accounting/client/{clientId}/profit`

### Get Total Pending Payments
- **GET** `/api/accounting/pending-payments/total`

---

## Inbox Messages

### Create Message
- **POST** `/api/inbox-messages`
- **Request Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "senderName": "John Customer",
  "senderProfileUrl": "https://facebook.com/john.customer",
  "messageText": "Do you have vegan options?"
}
```

### Get Message by ID
- **GET** `/api/inbox-messages/{id}`

### Get All Messages
- **GET** `/api/inbox-messages`

### Get Messages by Client
- **GET** `/api/inbox-messages/client/{clientId}`

### Get Messages by Social Account
- **GET** `/api/inbox-messages/account/{accountId}`

### Get Unread Messages
- **GET** `/api/inbox-messages/unread`

### Get Unanswered Messages
- **GET** `/api/inbox-messages/unanswered`

### Update Message
- **PUT** `/api/inbox-messages/{id}`

### Mark as Read
- **PUT** `/api/inbox-messages/{id}/mark-read`

### Mark as Unread
- **PUT** `/api/inbox-messages/{id}/mark-unread`

### Reply to Message
- **PUT** `/api/inbox-messages/{id}/reply?userId={userId}`
- **Request Body:**
```json
{
  "replyText": "Yes, we offer oat, almond, and soy milk options!"
}
```

### Delete Message
- **DELETE** `/api/inbox-messages/{id}`

### Count Unread Messages
- **GET** `/api/inbox-messages/unread/count`

---

## Client Complaints

### Create Complaint
- **POST** `/api/client-complaints`
- **Request Body:**
```json
{
  "clientId": 1,
  "subject": "Post Published at Wrong Time",
  "description": "The winter menu post went live at 6 AM instead of 9 AM"
}
```

### Get Complaint by ID
- **GET** `/api/client-complaints/{id}`

### Get All Complaints
- **GET** `/api/client-complaints`

### Get Complaints by Client
- **GET** `/api/client-complaints/client/{clientId}`

### Get Complaints by Severity
- **GET** `/api/client-complaints/severity/{severity}`
- **Severities:** `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

### Get Complaints by Status
- **GET** `/api/client-complaints/status/{status}`
- **Statuses:** `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`

### Get Open Complaints
- **GET** `/api/client-complaints/open`

### Update Complaint
- **PUT** `/api/client-complaints/{id}`

### Update Complaint Status
- **PUT** `/api/client-complaints/{id}/status?status={status}`

### Assign Complaint
- **PUT** `/api/client-complaints/{id}/assign/{userId}`

### Resolve Complaint
- **PUT** `/api/client-complaints/{id}/resolve?userId={userId}`
- **Request Body:**
```json
{
  "resolution": "Issue fixed, post rescheduled correctly"
}
```

### Close Complaint
- **PUT** `/api/client-complaints/{id}/close`

### Delete Complaint
- **DELETE** `/api/client-complaints/{id}`

---

## Standard API Response Format

All endpoints return responses in this format:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* Response data here */ },
  "timestamp": "2025-11-27T10:30:00"
}
```

### Error Response:
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2025-11-27T10:30:00"
}
```

### Validation Error Response:
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "fieldName": "Error message for this field"
  },
  "timestamp": "2025-11-27T10:30:00"
}
```

---

## Authentication

### Using JWT Token

All protected endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

### Public Endpoints (No Authentication Required):
- `/api/auth/register`
- `/api/auth/login`
- `/api/auth/refresh`
- `/api/public/**`

### Token Expiration:
- **Access Token:** 24 hours (86400000 ms)
- **Refresh Token:** 7 days (604800000 ms)

---

## Notes for Frontend Developer

1. **Base URL:** Update this when deploying to production
2. **CORS:** Already configured for `localhost:3000`, `localhost:4200`, `localhost:5173`, `localhost:8081`
3. **Date Format:** Use ISO 8601 format: `YYYY-MM-DDTHH:mm:ss`
4. **File Uploads:** All media URLs are stored as strings (integrate with cloud storage separately)
5. **Pagination:** Not implemented yet - returns all records
6. **Filtering:** Use query parameters where available
7. **Validation:** Check `data` object in error responses for field-specific errors

---

## Quick Start Example (JavaScript/React)

```javascript
// Login
const login = async (email, password) => {
  const response = await fetch('http://localhost:8081/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });
  const data = await response.json();
  
  if (data.success) {
    localStorage.setItem('accessToken', data.data.accessToken);
    localStorage.setItem('refreshToken', data.data.refreshToken);
  }
  return data;
};

// Authenticated Request
const getClients = async () => {
  const token = localStorage.getItem('accessToken');
  const response = await fetch('http://localhost:8081/api/clients', {
    headers: { 
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    }
  });
  return await response.json();
};

// Create Post
const createPost = async (postData) => {
  const token = localStorage.getItem('accessToken');
  const response = await fetch('http://localhost:8081/api/posts', {
    method: 'POST',
    headers: { 
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(postData)
  });
  return await response.json();
};
```

---

**Generated:** November 27, 2025  
**Backend Version:** 0.0.1-SNAPSHOT  
**Spring Boot:** 3.5.8  
**Java:** 17
