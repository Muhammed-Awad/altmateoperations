# Altmate API - Complete Postman Testing Guide

**Base URL:** `http://localhost:8081`

---

## 🔐 STEP 1: Authentication

### 1.1 Register Admin User
**POST** `http://localhost:8081/api/auth/register`

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "fullname": "Admin User",
  "email": "admin@altmate.com",
  "password": "Admin123!",
  "role": "ADMIN"
}
```

**Save the `accessToken` from response!**

---

### 1.2 Login (Get New Token)
**POST** `http://localhost:8081/api/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "email": "admin@altmate.com",
  "password": "Admin123!"
}
```

**Copy the `accessToken` - You'll need it for all requests below!**

---

## 📝 STEP 2: Users Management

### 2.1 Create Coordinator
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Sarah Coordinator",
  "email": "sarah@altmate.com",
  "password": "Sarah123!",
  "role": "COORDINATOR"
}
```

---

### 2.2 Create Designer
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Emma Designer",
  "email": "emma@altmate.com",
  "password": "Designer123!",
  "role": "DESIGNER"
}
```

---

### 2.3 Create Content Team Member
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "John Writer",
  "email": "john@altmate.com",
  "password": "Writer123!",
  "role": "CONTENT_TEAM"
}
```

---

### 2.4 Create Media Team Member
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Alex Photographer",
  "email": "alex@altmate.com",
  "password": "Photo123!",
  "role": "MEDIA_TEAM"
}
```

---

### 2.5 Create Media Buyer
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Tom Buyer",
  "email": "tom@altmate.com",
  "password": "Buyer123!",
  "role": "MEDIA_BUYER"
}
```

---

### 2.6 Create Moderator
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Lisa Moderator",
  "email": "lisa@altmate.com",
  "password": "Mod123!",
  "role": "MODERATOR"
}
```

---

### 2.7 Create Accountant
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Mark Accountant",
  "email": "mark@altmate.com",
  "password": "Account123!",
  "role": "ACCOUNTANT"
}
```

---

### 2.8 Create Chatbot Manager
**POST** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Chris Chatbot",
  "email": "chris@altmate.com",
  "password": "Chat123!",
  "role": "CHATBOT_MANAGER"
}
```

---

### 2.9 Get All Users
**GET** `http://localhost:8081/api/users`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 2.10 Get User by ID
**GET** `http://localhost:8081/api/users/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 2.11 Update User
**PUT** `http://localhost:8081/api/users/2`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "fullname": "Sarah Coordinator Updated",
  "email": "sarah.new@altmate.com",
  "role": "COORDINATOR"
}
```

---

### 2.12 Deactivate User
**PUT** `http://localhost:8081/api/users/2/deactivate`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 2.13 Activate User
**PUT** `http://localhost:8081/api/users/2/activate`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 2.14 Delete User
**DELETE** `http://localhost:8081/api/users/5`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 👥 STEP 3: Clients Management

### 3.1 Create Client
**POST** `http://localhost:8081/api/clients`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Starbucks Coffee",
  "industry": "RESTAURANT",
  "description": "Premium coffee chain, need social media management",
  "logo": "https://example.com/logos/starbucks.png",
  "isActive": true,
  "assignedUserIds": [1, 2]
}
```

---

### 3.2 Create Another Client
**POST** `http://localhost:8081/api/clients`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Derma Care Clinic",
  "industry": "MEDICAL",
  "description": "Dermatology clinic focusing on aesthetic treatments",
  "logo": "https://example.com/logos/dermacare.png",
  "isActive": true,
  "assignedUserIds": [1, 3]
}
```

---

### 3.3 Get All Clients
**GET** `http://localhost:8081/api/clients`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 3.4 Get Client by ID
**GET** `http://localhost:8081/api/clients/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 3.5 Update Client
**PUT** `http://localhost:8081/api/clients/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Starbucks Coffee Updated",
  "industry": "RESTAURANT",
  "description": "Premium coffee chain - Updated description",
  "logo": "https://example.com/logos/starbucks-new.png",
  "isActive": true,
  "assignedUserIds": [1, 2, 3]
}
```

---

### 3.6 Deactivate Client
**PUT** `http://localhost:8081/api/clients/2/deactivate`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 3.7 Activate Client
**PUT** `http://localhost:8081/api/clients/2/activate`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 3.8 Delete Client
**DELETE** `http://localhost:8081/api/clients/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 📱 STEP 4: Social Accounts

### 4.1 Create Facebook Account
**POST** `http://localhost:8081/api/social-accounts`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "client": { "id": 1 },
  "platform": "FACEBOOK",
  "accountUsername": "starbucks_official",
  "accountId": "fb_123456789",
  "accountName": "Starbucks Coffee Official",
  "accessToken": "sample_access_token_fb",
  "refreshToken": "sample_refresh_token_fb",
  "tokenExpiresAt": "2026-12-20T18:00:00",
  "isActive": true,
  "followerCount": 15000
}
```

---

### 4.2 Create Instagram Account
**POST** `http://localhost:8081/api/social-accounts`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "client": { "id": 1 },
  "platform": "INSTAGRAM",
  "accountUsername": "starbucks_ig",
  "accountId": "ig_987654321",
  "accountName": "Starbucks IG",
  "accessToken": "sample_access_token_ig",
  "refreshToken": "sample_refresh_token_ig",
  "tokenExpiresAt": "2026-12-20T18:00:00",
  "isActive": true,
  "followerCount": 25000
}
```

---

### 4.3 Get All Social Accounts
**GET** `http://localhost:8081/api/social-accounts`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 4.4 Get Accounts by Client
**GET** `http://localhost:8081/api/social-accounts/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 4.5 Update Social Account
**PUT** `http://localhost:8081/api/social-accounts/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "client": { "id": 1 },
  "platform": "FACEBOOK",
  "accountUsername": "starbucks_official_updated",
  "accountId": "fb_123456789",
  "accountName": "Starbucks Coffee Official Page",
  "accessToken": "new_access_token_fb",
  "refreshToken": "new_refresh_token_fb",
  "tokenExpiresAt": "2027-01-20T18:00:00",
  "isActive": true,
  "followerCount": 20000
}
```

---

### 4.6 Update Follower Count
**PUT** `http://localhost:8081/api/social-accounts/1/followers?count=25000`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 4.7 Delete Social Account
**DELETE** `http://localhost:8081/api/social-accounts/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 📝 STEP 5: Posts

### 5.1 Create Draft Post
**POST** `http://localhost:8081/api/posts`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "content": "☕ NEW WINTER MENU IS HERE! ❄️\n\nWarm up with our new Peppermint Mocha and Caramel Brûlée Latte. Available for a limited time only!\n\n#WinterDrinks #StarbucksSeason #CoffeeLovers",
  "hook": "Cozy winter vibes in every sip",
  "slogan": "Taste the season",
  "mediaUrls": ["https://example.com/images/winter-drinks.jpg"],
  "status": "DRAFT",
  "createdById": 1
}
```

---

### 5.2 Create Scheduled Post
**POST** `http://localhost:8081/api/posts`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 2,
  "content": "✨ Happy Holidays from Starbucks! 🎄\n\nCelebrate the season with our festive drinks and cozy atmosphere.\n\n#HolidayVibes #Starbucks",
  "hook": "Holiday magic in every cup",
  "slogan": "Make it merry",
  "mediaUrls": ["https://example.com/images/holiday.jpg"],
  "status": "SCHEDULED",
  "scheduledAt": "2025-12-25T09:00:00",
  "createdById": 3
}
```

---

### 5.3 Get All Posts
**GET** `http://localhost:8081/api/posts`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.4 Get Posts by Client
**GET** `http://localhost:8081/api/posts/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.5 Get Posts by Status
**GET** `http://localhost:8081/api/posts/status/DRAFT`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.6 Update Post
**PUT** `http://localhost:8081/api/posts/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "content": "☕ UPDATED: NEW WINTER MENU! ❄️\n\nUpdated content here...",
  "hook": "Updated hook",
  "slogan": "Updated slogan",
  "mediaUrls": ["https://example.com/images/winter-drinks-v2.jpg"],
  "status": "DRAFT",
  "createdById": 1
}
```

---

### 5.7 Update Post Status
**PUT** `http://localhost:8081/api/posts/1/status?status=SCHEDULED`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.8 Schedule Post
**PUT** `http://localhost:8081/api/posts/1/schedule?scheduledAt=2025-12-01T10:00:00`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.9 Publish Post
**PUT** `http://localhost:8081/api/posts/1/publish`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.10 Update Post Metrics
**PUT** `http://localhost:8081/api/posts/1/metrics?reach=5000&impressions=8000&likes=350&comments=45&shares=20`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 5.11 Delete Post
**DELETE** `http://localhost:8081/api/posts/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 💬 STEP 6: Comments

### 6.1 Create Comment
**POST** `http://localhost:8081/api/comments`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "socialAccountId": 1,
  "postId": 1,
  "commenterName": "Sarah (@coffee_addict_sarah)",
  "commenterProfileUrl": "https://instagram.com/coffee_addict_sarah",
  "commentText": "Love this! When will the peppermint mocha be available? 😍"
}
```

---

### 6.2 Get All Comments
**GET** `http://localhost:8081/api/comments`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 6.3 Get Comments by Post
**GET** `http://localhost:8081/api/comments/post/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 6.4 Get Pending Comments
**GET** `http://localhost:8081/api/comments/pending`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 6.5 Reply to Comment
**PUT** `http://localhost:8081/api/comments/1/reply?userId=1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "replyText": "Hi Sarah! It's available starting tomorrow. Thanks for your interest! ☕"
}
```

---

### 6.6 Approve Comment
**PUT** `http://localhost:8081/api/comments/1/approve`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 6.7 Hide Comment
**PUT** `http://localhost:8081/api/comments/1/hide`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 6.8 Delete Comment
**DELETE** `http://localhost:8081/api/comments/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 📋 STEP 7: Tasks

### 7.1 Create Design Task
**POST** `http://localhost:8081/api/tasks`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "DESIGN",
  "title": "Winter Holiday Instagram Post Design",
  "description": "Create festive Instagram post for new winter drinks menu. Use warm colors and holiday theme.",
  "status": "PENDING",
  "priority": "HIGH",
  "dueDate": "2025-12-05T17:00:00",
  "assignedToId": 3,
  "assignedById": 1,
  "designBrief": "Warm colors, festive theme, include product images"
}
```

---

### 7.2 Create Content Writing Task
**POST** `http://localhost:8081/api/tasks`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "CONTENT_WRITING",
  "title": "Write Facebook Post Captions - Winter Campaign",
  "description": "Write 5 engaging captions for winter drink promotions. Include CTAs and hashtags.",
  "status": "PENDING",
  "priority": "MEDIUM",
  "dueDate": "2025-12-03T15:00:00",
  "assignedToId": 4,
  "assignedById": 2
}
```

---

### 7.3 Get All Tasks
**GET** `http://localhost:8081/api/tasks`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 7.4 Get Tasks by Client
**GET** `http://localhost:8081/api/tasks/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 7.5 Get Tasks by Status
**GET** `http://localhost:8081/api/tasks/status/PENDING`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 7.6 Update Task
**PUT** `http://localhost:8081/api/tasks/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "DESIGN",
  "title": "Winter Holiday Instagram Post Design - UPDATED",
  "description": "Updated description with new requirements",
  "status": "IN_PROGRESS",
  "priority": "URGENT",
  "dueDate": "2025-12-04T17:00:00",
  "assignedToId": 3,
  "assignedById": 1
}
```

---

### 7.7 Update Task Status
**PUT** `http://localhost:8081/api/tasks/1/status?status=IN_PROGRESS`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 7.8 Submit Task
**PUT** `http://localhost:8081/api/tasks/1/submit`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 7.9 Approve Task
**PUT** `http://localhost:8081/api/tasks/1/approve?userId=1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 7.10 Request Revision
**PUT** `http://localhost:8081/api/tasks/1/request-revision?userId=1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "revisionNotes": "Please adjust colors to match brand guidelines and add more festive elements"
}
```

---

### 7.11 Delete Task
**DELETE** `http://localhost:8081/api/tasks/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 📢 STEP 8: Ad Campaigns

### 8.1 Create Campaign
**POST** `http://localhost:8081/api/campaigns`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "campaignName": "Winter Menu Launch 2025",
  "objective": "CONVERSIONS",
  "totalBudget": 5000.00,
  "autoPauseEnabled": true,
  "status": "ACTIVE",
  "startDate": "2025-12-01T00:00:00",
  "endDate": "2025-12-31T23:59:59",
  "managedById": 1
}
```

---

### 8.2 Get All Campaigns
**GET** `http://localhost:8081/api/campaigns`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 8.3 Get Campaigns by Client
**GET** `http://localhost:8081/api/campaigns/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 8.4 Update Campaign
**PUT** `http://localhost:8081/api/campaigns/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "campaignName": "Winter Menu Launch 2025 - Extended",
  "objective": "CONVERSIONS",
  "totalBudget": 7000.00,
  "autoPauseEnabled": true,
  "status": "ACTIVE",
  "startDate": "2025-12-01T00:00:00",
  "endDate": "2026-01-15T23:59:59",
  "managedById": 1
}
```

---

### 8.5 Update Campaign Status
**PUT** `http://localhost:8081/api/campaigns/1/status?status=PAUSED`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 8.6 Update Spent Amount
**PUT** `http://localhost:8081/api/campaigns/1/spend?amount=1500.50`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 8.7 Update Campaign Metrics
**PUT** `http://localhost:8081/api/campaigns/1/metrics?impressions=50000&clicks=1200&conversions=85`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 8.8 Get Total Spent by Client
**GET** `http://localhost:8081/api/campaigns/client/1/total-spent`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 8.9 Delete Campaign
**DELETE** `http://localhost:8081/api/campaigns/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 💰 STEP 9: Accounting Entries

### 9.1 Create Income Entry (Package Collection)
**POST** `http://localhost:8081/api/accounting`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "COLLECTION_PACKAGE",
  "amount": 3500.00,
  "paymentStatus": "PAID",
  "dueDate": "2025-11-30T00:00:00",
  "paidDate": "2025-11-29T14:30:00",
  "description": "Monthly social media management - November 2025",
  "invoiceNumber": "INV-2025-001"
}
```

---

### 9.2 Create Expense Entry
**POST** `http://localhost:8081/api/accounting`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "EXPENSE",
  "amount": 500.00,
  "paymentStatus": "PENDING",
  "dueDate": "2025-12-05T00:00:00",
  "description": "Facebook Ads - Winter Campaign",
  "invoiceNumber": "EXP-2025-001"
}
```

---

### 9.3 Create Ad Collection Entry
**POST** `http://localhost:8081/api/accounting`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "COLLECTION_AD",
  "amount": 1000.00,
  "paymentStatus": "PAID",
  "dueDate": "2025-11-25T00:00:00",
  "paidDate": "2025-11-25T10:00:00",
  "description": "Ad budget collection for Winter campaign",
  "invoiceNumber": "AD-2025-001"
}
```

---

### 9.4 Get All Entries
**GET** `http://localhost:8081/api/accounting`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.5 Get Entries by Client
**GET** `http://localhost:8081/api/accounting/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.6 Get Entries by Type
**GET** `http://localhost:8081/api/accounting/type/EXPENSE`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.7 Update Entry
**PUT** `http://localhost:8081/api/accounting/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "type": "COLLECTION_PACKAGE",
  "amount": 4000.00,
  "paymentStatus": "PAID",
  "dueDate": "2025-11-30T00:00:00",
  "paidDate": "2025-11-29T14:30:00",
  "description": "Monthly social media management - November 2025 (Updated)",
  "invoiceNumber": "INV-2025-001-REV"
}
```

---

### 9.8 Update Payment Status
**PUT** `http://localhost:8081/api/accounting/2/payment-status?status=PAID`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.9 Get Total Income by Client
**GET** `http://localhost:8081/api/accounting/client/1/income`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.10 Get Total Expense by Client
**GET** `http://localhost:8081/api/accounting/client/1/expense`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.11 Get Net Profit by Client
**GET** `http://localhost:8081/api/accounting/client/1/profit`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 9.12 Delete Entry
**DELETE** `http://localhost:8081/api/accounting/3`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 📨 STEP 10: Inbox Messages

### 10.1 Create Message
**POST** `http://localhost:8081/api/inbox-messages`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "socialAccountId": 1,
  "senderName": "John Customer",
  "senderProfileUrl": "https://facebook.com/john.customer",
  "messageText": "Hi! Do you offer dairy-free options for the winter drinks?"
}
```

---

### 10.2 Get All Messages
**GET** `http://localhost:8081/api/inbox-messages`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 10.3 Get Messages by Client
**GET** `http://localhost:8081/api/inbox-messages/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 10.4 Get Unread Messages
**GET** `http://localhost:8081/api/inbox-messages/unread`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 10.5 Mark as Read
**PUT** `http://localhost:8081/api/inbox-messages/1/mark-read`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 10.6 Reply to Message
**PUT** `http://localhost:8081/api/inbox-messages/1/reply?userId=1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "replyText": "Yes! All our winter drinks can be made with oat, almond, or soy milk. 🌱"
}
```

---

### 10.7 Delete Message
**DELETE** `http://localhost:8081/api/inbox-messages/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 🚨 STEP 11: Client Complaints

### 11.1 Create Complaint
**POST** `http://localhost:8081/api/client-complaints`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "subject": "Post Published at Wrong Time",
  "description": "The winter menu post was scheduled for 9 AM but went live at 6 AM. This affected morning engagement."
}
```

---

### 11.2 Get All Complaints
**GET** `http://localhost:8081/api/client-complaints`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 11.3 Get Complaints by Client
**GET** `http://localhost:8081/api/client-complaints/client/1`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 11.4 Get Open Complaints
**GET** `http://localhost:8081/api/client-complaints/open`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 11.5 Update Complaint
**PUT** `http://localhost:8081/api/client-complaints/1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "clientId": 1,
  "subject": "Post Published at Wrong Time - UPDATED",
  "description": "Updated description with more details about the issue"
}
```

---

### 11.6 Assign Complaint
**PUT** `http://localhost:8081/api/client-complaints/1/assign/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 11.7 Resolve Complaint
**PUT** `http://localhost:8081/api/client-complaints/1/resolve?userId=1`

**Headers:**
```
Authorization: Bearer {your_token}
Content-Type: application/json
```

**Body:**
```json
{
  "resolution": "Issue fixed. Post rescheduled correctly for 9 AM. Implemented additional scheduling checks."
}
```

---

### 11.8 Close Complaint
**PUT** `http://localhost:8081/api/client-complaints/1/close`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

### 11.9 Delete Complaint
**DELETE** `http://localhost:8081/api/client-complaints/2`

**Headers:**
```
Authorization: Bearer {your_token}
```

---

## 📊 Summary of Testing Flow

1. **Authentication** → Register/Login → Get Token
2. **Users** → Create team members (Coordinator, Designer, Content Creator, etc.)
3. **Clients** → Create clients and assign team members
4. **Social Accounts** → Create Facebook/Instagram accounts for clients
5. **Posts** → Create, schedule, publish posts
6. **Comments** → Handle customer comments and replies
7. **Tasks** → Assign design/content tasks to team members
8. **Ad Campaigns** → Create and track advertising campaigns
9. **Accounting** → Manage income, expenses, and profits
10. **Inbox Messages** → Handle customer messages
11. **Client Complaints** → Track and resolve client issues

---

## 🎯 Testing Tips

1. **Always include the Authorization header** for protected endpoints
2. **Use the correct Content-Type** (`application/json`) for POST/PUT requests
3. **Copy IDs from responses** to use in subsequent requests
4. **Test in order** - Create dependencies first (users, clients) before creating related entities
5. **Save your token** - It expires after 24 hours, login again if needed
6. **Check response messages** - They provide helpful feedback

---

**Generated:** November 30, 2025  
**Ready for Postman Testing!** 🚀
