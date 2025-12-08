# WhatsApp Business & Messenger Integration Guide

## Overview
This guide covers integrating WhatsApp Business API and Facebook Messenger into your social media management application.

## Table of Contents
- [WhatsApp Business Setup](#whatsapp-business-setup)
- [Messenger Setup](#messenger-setup)
- [API Credentials Configuration](#api-credentials-configuration)
- [Usage Examples](#usage-examples)
- [Important Notes](#important-notes)

---

## WhatsApp Business Setup

### Prerequisites
1. A Facebook Business Account
2. A WhatsApp Business Account
3. A verified phone number for WhatsApp Business

### Step 1: Create WhatsApp Business App
1. Go to [Meta for Developers](https://developers.facebook.com/)
2. Navigate to **My Apps** → **Create App**
3. Select **Business** as app type
4. Enable **WhatsApp** product

### Step 2: Configure WhatsApp Business
1. In your app dashboard, go to **WhatsApp** → **Getting Started**
2. Click **Add phone number** or use the test number
3. Select your **Business Account**
4. Copy the following credentials:
   - **Phone Number ID** (from WhatsApp → API Setup)
   - **WhatsApp Business Account ID** (from WhatsApp → API Setup)
   - **Access Token** (from WhatsApp → API Setup → Temporary Token)

### Step 3: Set Up Webhook (Optional for receiving messages)
1. Go to **WhatsApp** → **Configuration**
2. Set **Callback URL**: `https://yourdomain.com/api/social/webhook/whatsapp`
3. Set **Verify Token**: Create a random string (e.g., `whatsapp_verify_token_12345`)
4. Subscribe to webhook fields: `messages`

### Step 4: Get Permanent Access Token
The temporary token expires in 24 hours. For production:
1. Use **System User** access tokens from Business Manager
2. Or implement token refresh flow using your App ID and Secret

---

## Messenger Setup

### Prerequisites
1. A Facebook Page (you must be an admin)
2. A Facebook App with Messenger product

### Step 1: Add Messenger to Your App
1. In [Meta for Developers](https://developers.facebook.com/)
2. Select your app → **Add Product** → **Messenger**
3. Click **Set Up** on Messenger

### Step 2: Generate Page Access Token
1. Go to **Messenger** → **Settings**
2. Under **Access Tokens**, click **Add or Remove Pages**
3. Select your Facebook Page and grant permissions
4. Copy the **Page Access Token**

### Step 3: Set Up Webhook
1. Go to **Messenger** → **Settings** → **Webhooks**
2. Click **Add Callback URL**
   - **Callback URL**: `https://yourdomain.com/api/social/webhook/messenger`
   - **Verify Token**: Create a random string (e.g., `messenger_verify_token_67890`)
3. Subscribe to webhook fields: `messages`, `messaging_postbacks`, `message_reads`

### Step 4: Enable Messenger Features
1. Go to **Messenger** → **Settings** → **Built-In NLP**
2. Enable features you need (optional)
3. Request advanced permissions if needed:
   - `pages_messaging`
   - `pages_manage_metadata`
   - `pages_manage_engagement`

---

## API Credentials Configuration

### Update application.properties

Add the following to your `src/main/resources/application.properties`:

```properties
# WhatsApp Business API
social.whatsapp.phone-number-id=YOUR_WHATSAPP_PHONE_NUMBER_ID
social.whatsapp.business-account-id=YOUR_WHATSAPP_BUSINESS_ACCOUNT_ID
social.whatsapp.access-token=YOUR_WHATSAPP_ACCESS_TOKEN

# Facebook Messenger
social.messenger.page-access-token=YOUR_MESSENGER_PAGE_ACCESS_TOKEN
social.messenger.verify-token=YOUR_MESSENGER_VERIFY_TOKEN
social.messenger.app-secret=YOUR_META_APP_SECRET
```

### Example with actual values:
```properties
# WhatsApp Business API
social.whatsapp.phone-number-id=123456789012345
social.whatsapp.business-account-id=987654321098765
social.whatsapp.access-token=EAAB1234567890abcdefghijklmnop...

# Facebook Messenger
social.messenger.page-access-token=EAAG1234567890abcdefghijklmnop...
social.messenger.verify-token=my_secret_verify_token_12345
social.messenger.app-secret=abcd1234efgh5678ijkl90mnop12
```

---

## Usage Examples

### 1. Connect WhatsApp Business Account

**Request:**
```http
GET /api/social/auth-url/whatsapp?clientId=1
Authorization: Bearer YOUR_JWT_TOKEN
```

**Response:**
```json
{
  "success": true,
  "data": "https://www.facebook.com/v18.0/dialog/oauth?client_id=..."
}
```

### 2. Send WhatsApp Message

**Request:**
```http
POST /api/social/publish
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "socialAccountId": 1,
  "message": "Hello! This is a test message from our system.",
  "mediaUrl": null
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "messageId": "wamid.ABC123...",
    "platform": "WhatsApp"
  }
}
```

### 3. Send WhatsApp Image with Caption

**Request:**
```http
POST /api/social/publish
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "socialAccountId": 1,
  "message": "Check out our new product!",
  "mediaUrl": "https://example.com/image.jpg"
}
```

### 4. Connect Messenger Page

**Request:**
```http
GET /api/social/auth-url/messenger?clientId=1
Authorization: Bearer YOUR_JWT_TOKEN
```

### 5. Send Messenger Message

**Request:**
```http
POST /api/social/publish
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "socialAccountId": 2,
  "message": "Thank you for contacting us! We'll get back to you soon.",
  "mediaUrl": null
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "messageId": "mid.ABC123...",
    "platform": "Messenger"
  }
}
```

---

## Important Notes

### WhatsApp Business

1. **Message Templates**
   - For customer-initiated conversations: You can send free-form messages within 24 hours
   - For business-initiated conversations: You must use approved message templates
   - Apply for message template approval in Meta Business Manager

2. **Phone Number Requirements**
   - Must be verified with WhatsApp Business
   - Cannot be used on regular WhatsApp
   - Must be registered to your business

3. **Pricing**
   - First 1,000 conversations per month are free
   - After that, pricing varies by country
   - See [WhatsApp Pricing](https://developers.facebook.com/docs/whatsapp/pricing)

4. **Rate Limits**
   - Messaging tier-based limits (Tier 1: 1,000 conversations/day)
   - Quality rating affects your tier
   - Monitor quality in Meta Business Manager

### Messenger

1. **Page Requirement**
   - Must have a Facebook Page
   - Page must be published (not draft)
   - You must be Page admin

2. **User Consent**
   - Users must initiate conversation first
   - Or you need explicit opt-in for promotional messages
   - Follow Facebook's messaging policies

3. **Message Types**
   - **Response**: Reply within 24 hours of user message (free)
   - **Subscription**: User opted in to recurring content
   - **Message Tags**: For specific use cases outside 24-hour window

4. **Permissions**
   - Standard access: Up to 250 users
   - Advanced access: Unlimited (requires app review)
   - Apply for advanced access in App Review

### Testing

1. **WhatsApp Test Mode**
   - Use test phone numbers provided by Meta
   - Test numbers: +1 555-025-3483 (US), +44 XXXX XXXXXX (UK)
   - No actual SMS sent in test mode

2. **Messenger Test Mode**
   - Test with Page roles (Admin, Developer, Tester)
   - Send test messages to yourself
   - Use Messenger API Tester tool

### Production Checklist

- [ ] WhatsApp phone number verified
- [ ] Message templates approved (for WhatsApp)
- [ ] Permanent access token configured (not temporary)
- [ ] Webhook configured and verified
- [ ] Privacy Policy and Terms of Service published
- [ ] App Review completed for advanced permissions
- [ ] Business verification completed
- [ ] Monitoring and logging configured
- [ ] Error handling implemented
- [ ] Rate limiting handled

---

## API Endpoints Summary

| Endpoint | Method | Platform | Description |
|----------|--------|----------|-------------|
| `/api/social/auth-url/whatsapp` | GET | WhatsApp | Get OAuth URL |
| `/api/social/auth-url/messenger` | GET | Messenger | Get OAuth URL |
| `/api/social/callback/whatsapp` | GET | WhatsApp | OAuth callback |
| `/api/social/callback/messenger` | GET | Messenger | OAuth callback |
| `/api/social/publish` | POST | Both | Send message |
| `/api/social/webhook/whatsapp` | POST | WhatsApp | Receive messages |
| `/api/social/webhook/messenger` | POST | Messenger | Receive messages |

---

## Troubleshooting

### WhatsApp

**Problem**: "Phone number not found"
- **Solution**: Verify phone number ID is correct and phone is registered

**Problem**: "Access token expired"
- **Solution**: Use system user token or implement token refresh

**Problem**: "Template not approved"
- **Solution**: Submit template for approval in Business Manager

### Messenger

**Problem**: "Page not found"
- **Solution**: Ensure page access token is correct and page is published

**Problem**: "Permission denied"
- **Solution**: Grant all required permissions to the app for the page

**Problem**: "Webhook verification failed"
- **Solution**: Check verify token matches and endpoint is accessible

---

## Resources

- [WhatsApp Business Platform Documentation](https://developers.facebook.com/docs/whatsapp)
- [Messenger Platform Documentation](https://developers.facebook.com/docs/messenger-platform)
- [Meta Business Suite](https://business.facebook.com/)
- [Meta for Developers](https://developers.facebook.com/)

---

## Support

For issues with:
- **WhatsApp Business API**: Contact Meta Business Support
- **Messenger Platform**: Use Developer Community Forum
- **This Integration**: Check application logs or contact your development team
