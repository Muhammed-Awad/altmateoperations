# 🚀 Social Media Integration Guide

## Complete Multi-Platform Integration
Control **Facebook, Instagram, Threads, Twitter/X, LinkedIn, and Snapchat** from your application.

---

## 📋 Setup Instructions

### **1. Get API Credentials**

#### **Meta Platforms (Facebook, Instagram, Threads)**
1. Visit [Facebook Developers](https://developers.facebook.com/)
2. Create new app or use existing
3. Add **Facebook Login** product
4. Get **App ID** and **App Secret**
5. Configure OAuth redirect: `http://localhost:8081/api/social/callback/facebook`
6. Add permissions:
   - Facebook: `pages_show_list`, `pages_manage_posts`, `pages_read_engagement`
   - Instagram: `instagram_basic`, `instagram_content_publish`, `instagram_manage_insights`
   - Threads: `threads_basic`, `threads_content_publish`

#### **Twitter/X**
1. Visit [Twitter Developer Portal](https://developer.twitter.com/)
2. Create new app
3. Enable **OAuth 2.0**
4. Get **API Key** and **API Secret**
5. Add callback URL: `http://localhost:8081/api/social/callback/twitter`
6. Required scopes: `tweet.read`, `tweet.write`, `users.read`, `offline.access`

#### **LinkedIn**
1. Visit [LinkedIn Developers](https://www.linkedin.com/developers/)
2. Create new app
3. Get **Client ID** and **Client Secret**
4. Add redirect URL: `http://localhost:8081/api/social/callback/linkedin`
5. Required products: **Sign In with LinkedIn**, **Share on LinkedIn**

#### **Snapchat**
1. Visit [Snapchat Business](https://business.snapchat.com/)
2. Create Business account
3. Get **Client ID** and **Client Secret**
4. Add redirect URL: `http://localhost:8081/api/social/callback/snapchat`

---

### **2. Configure application.properties**

Update these values in `src/main/resources/application.properties`:

```properties
# Meta (Facebook, Instagram, Threads)
social.meta.app-id=123456789012345
social.meta.app-secret=your_facebook_app_secret_here

# Twitter/X
social.twitter.api-key=your_twitter_api_key_here
social.twitter.api-secret=your_twitter_api_secret_here

# LinkedIn
social.linkedin.client-id=your_linkedin_client_id
social.linkedin.client-secret=your_linkedin_client_secret

# Snapchat
social.snapchat.client-id=your_snapchat_client_id
social.snapchat.client-secret=your_snapchat_client_secret
```

---

## 🎯 API Usage Guide

### **Get Authorization URL**

```http
GET http://localhost:8081/api/social/auth-url/{platform}?clientId=1
Authorization: Bearer {your_token}
```

**Platforms:** `facebook`, `instagram`, `threads`, `twitter`, `linkedin`, `snapchat`

**Example:**
```javascript
// Frontend code
const response = await fetch('http://localhost:8081/api/social/auth-url/facebook?clientId=1', {
  headers: { 'Authorization': 'Bearer ' + token }
});
const data = await response.json();

// Redirect user to authorization page
window.location.href = data.data;
```

---

### **Connect Account (After OAuth)**

After user authorizes, they're redirected to:
```
http://localhost:8081/api/social/callback/{platform}?code=xxx&state=clientId
```

Backend automatically:
1. Exchanges code for access token
2. Fetches account info
3. Saves to database
4. Shows success message

---

### **Publish Post to Any Platform**

```http
POST http://localhost:8081/api/social/publish
Authorization: Bearer {your_token}
Content-Type: application/json

{
  "socialAccountId": 1,
  "message": "Check out our amazing new product! 🚀",
  "mediaUrl": "https://example.com/image.jpg"
}
```

**Platform-Specific Notes:**
- **Facebook**: Message + optional media URL
- **Instagram**: Requires mediaUrl (image/video)
- **Threads**: Message + optional media
- **Twitter/X**: 280 character limit
- **LinkedIn**: Professional tone recommended
- **Snapchat**: Ads API only (organic posting not supported)

---

### **Get Account Insights**

```http
GET http://localhost:8081/api/social/insights/1
Authorization: Bearer {your_token}
```

Returns followers, engagement metrics, etc.

---

## 📱 Frontend Integration Example

### **1. Connect Button**

```javascript
const connectSocialMedia = async (platform, clientId) => {
  try {
    const response = await fetch(
      `http://localhost:8081/api/social/auth-url/${platform}?clientId=${clientId}`,
      {
        headers: { 'Authorization': 'Bearer ' + token }
      }
    );
    
    const data = await response.json();
    
    // Open popup or redirect
    window.open(data.data, '_blank', 'width=600,height=700');
    
    // Or full redirect
    // window.location.href = data.data;
  } catch (error) {
    console.error('Connection failed:', error);
  }
};

// Usage
<button onClick={() => connectSocialMedia('facebook', clientId)}>
  Connect Facebook
</button>
<button onClick={() => connectSocialMedia('instagram', clientId)}>
  Connect Instagram
</button>
<button onClick={() => connectSocialMedia('twitter', clientId)}>
  Connect Twitter/X
</button>
```

---

### **2. Publish Post**

```javascript
const publishPost = async (socialAccountId, message, mediaUrl) => {
  const response = await fetch('http://localhost:8081/api/social/publish', {
    method: 'POST',
    headers: {
      'Authorization': 'Bearer ' + token,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      socialAccountId,
      message,
      mediaUrl
    })
  });
  
  const result = await response.json();
  console.log('Published to:', result.data.platform);
  console.log('Post ID:', result.data.postId);
};

// Publish to multiple platforms
const socialAccounts = [1, 2, 3]; // Facebook, Instagram, Twitter IDs
for (const accountId of socialAccounts) {
  await publishPost(accountId, 'Same message to all!', imageUrl);
}
```

---

### **3. View Analytics**

```javascript
const getAnalytics = async (socialAccountId) => {
  const response = await fetch(
    `http://localhost:8081/api/social/insights/${socialAccountId}`,
    {
      headers: { 'Authorization': 'Bearer ' + token }
    }
  );
  
  const insights = await response.json();
  console.log('Followers:', insights.data.followers_count);
  console.log('Engagement:', insights.data);
};
```

---

## 🔄 Complete Workflow

1. **User clicks "Connect Instagram"**
2. **Frontend calls** `/api/social/auth-url/instagram?clientId=1`
3. **Backend returns** Instagram OAuth URL
4. **User redirects** to Instagram authorization
5. **User approves** access
6. **Instagram redirects** to `/api/social/callback/instagram?code=xxx`
7. **Backend saves** account with access token
8. **User can now publish** posts via `/api/social/publish`

---

## ✅ Supported Features by Platform

| Feature | Facebook | Instagram | Threads | Twitter | LinkedIn | Snapchat |
|---------|----------|-----------|---------|---------|----------|----------|
| Connect Account | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Publish Text | ✅ | ⚠️ | ✅ | ✅ | ✅ | ❌ |
| Publish Media | ✅ | ✅ | ✅ | ✅ | ✅ | ⚠️ |
| Get Insights | ✅ | ✅ | ⚠️ | ✅ | ⚠️ | ❌ |
| Schedule Posts | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |

✅ = Fully Supported  
⚠️ = Partial Support  
❌ = Not Available  

---

## 🎯 Postman Examples

### **Connect Facebook**
```
GET http://localhost:8081/api/social/auth-url/facebook?clientId=1
Authorization: Bearer {token}
```

### **Publish to Instagram**
```
POST http://localhost:8081/api/social/publish
Authorization: Bearer {token}

{
  "socialAccountId": 2,
  "message": "Beautiful sunset! 🌅 #nature #photography",
  "mediaUrl": "https://example.com/sunset.jpg"
}
```

### **Get Twitter Analytics**
```
GET http://localhost:8081/api/social/insights/3
Authorization: Bearer {token}
```

---

## 🔐 Security Notes

1. **Never commit** API secrets to Git
2. **Use environment variables** in production
3. **Rotate tokens** regularly
4. **Validate** all user inputs
5. **Rate limit** API calls
6. **Store tokens** encrypted

---

## 🚀 Ready to Use!

All endpoints are ready. Just add your API credentials and start connecting social media accounts!

**Questions? Check the controller and service files for detailed implementation.**
