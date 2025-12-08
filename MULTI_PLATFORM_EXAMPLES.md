# 🎯 Multi-Platform Social Account Examples

## How One Client Can Have Multiple Platform Accounts

Each client can connect to **any number** of social media platforms. Here's how:

---

## 📱 **Example: Starbucks Coffee (Client ID: 1)**

### **Connected Accounts:**

```json
{
  "clientId": 1,
  "clientName": "Starbucks Coffee",
  "socialAccounts": [
    {
      "id": 1,
      "platform": "FACEBOOK",
      "accountName": "Starbucks Official",
      "accountUsername": "starbucks",
      "followerCount": 37000000,
      "isActive": true
    },
    {
      "id": 2,
      "platform": "INSTAGRAM",
      "accountName": "Starbucks",
      "accountUsername": "@starbucks",
      "followerCount": 19000000,
      "isActive": true
    },
    {
      "id": 3,
      "platform": "TWITTER",
      "accountName": "Starbucks Coffee",
      "accountUsername": "@Starbucks",
      "followerCount": 11000000,
      "isActive": true
    },
    {
      "id": 4,
      "platform": "THREADS",
      "accountName": "Starbucks",
      "accountUsername": "@starbucks",
      "followerCount": 500000,
      "isActive": true
    },
    {
      "id": 5,
      "platform": "LINKEDIN",
      "accountName": "Starbucks",
      "followerCount": 2500000,
      "isActive": true
    }
  ]
}
```

---

## 🎬 **Example: Nike (Client ID: 2)**

### **Connected Accounts:**

```json
{
  "clientId": 2,
  "clientName": "Nike",
  "socialAccounts": [
    {
      "id": 6,
      "platform": "INSTAGRAM",
      "accountName": "Nike",
      "accountUsername": "@nike",
      "followerCount": 306000000,
      "isActive": true
    },
    {
      "id": 7,
      "platform": "TWITTER",
      "accountName": "Nike",
      "accountUsername": "@Nike",
      "followerCount": 10000000,
      "isActive": true
    },
    {
      "id": 8,
      "platform": "TIKTOK",
      "accountName": "Nike",
      "accountUsername": "@nike",
      "followerCount": 8500000,
      "isActive": true
    }
  ]
}
```

---

## 🏥 **Example: Derma Care Clinic (Client ID: 3)**

### **Connected Accounts:**

```json
{
  "clientId": 3,
  "clientName": "Derma Care Clinic",
  "socialAccounts": [
    {
      "id": 9,
      "platform": "FACEBOOK",
      "accountName": "Derma Care Clinic",
      "accountUsername": "dermacareclinic",
      "followerCount": 15000,
      "isActive": true
    },
    {
      "id": 10,
      "platform": "INSTAGRAM",
      "accountName": "Derma Care",
      "accountUsername": "@dermacare_clinic",
      "followerCount": 25000,
      "isActive": true
    },
    {
      "id": 11,
      "platform": "LINKEDIN",
      "accountName": "Derma Care Clinic",
      "followerCount": 3500,
      "isActive": true
    }
  ]
}
```

---

## 🔧 **How to Connect Multiple Platforms for One Client**

### **Step 1: Connect Facebook**

```http
GET http://localhost:8081/api/social/auth-url/facebook?clientId=1
Authorization: Bearer {token}
```

User authorizes → Facebook account saved for Client 1

---

### **Step 2: Connect Instagram (Same Client)**

```http
GET http://localhost:8081/api/social/auth-url/instagram?clientId=1
Authorization: Bearer {token}
```

User authorizes → Instagram account saved for Client 1

---

### **Step 3: Connect Twitter (Same Client)**

```http
GET http://localhost:8081/api/social/auth-url/twitter?clientId=1
Authorization: Bearer {token}
```

User authorizes → Twitter account saved for Client 1

---

### **Step 4: Get All Client's Social Accounts**

```http
GET http://localhost:8081/api/social-accounts/client/1
Authorization: Bearer {token}
```

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "platform": "FACEBOOK",
      "accountName": "Starbucks Official",
      "accountUsername": "starbucks",
      "isActive": true
    },
    {
      "id": 2,
      "platform": "INSTAGRAM",
      "accountName": "Starbucks",
      "accountUsername": "@starbucks",
      "isActive": true
    },
    {
      "id": 3,
      "platform": "TWITTER",
      "accountName": "Starbucks Coffee",
      "accountUsername": "@Starbucks",
      "isActive": true
    }
  ]
}
```

---

## 📤 **Publish to Multiple Platforms at Once**

```javascript
// Publish same post to all platforms
const clientSocialAccounts = [1, 2, 3]; // FB, IG, Twitter IDs

const message = "New winter menu available now! ☕❄️";
const imageUrl = "https://example.com/winter-menu.jpg";

for (const accountId of clientSocialAccounts) {
  await fetch('http://localhost:8081/api/social/publish', {
    method: 'POST',
    headers: {
      'Authorization': 'Bearer ' + token,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      socialAccountId: accountId,
      message: message,
      mediaUrl: imageUrl
    })
  });
}

// Result: Same post published to Facebook, Instagram, and Twitter!
```

---

## 📊 **Database Structure**

```sql
-- social_accounts table
id | client_id | platform  | account_name       | account_username | is_active
---|-----------|-----------|-------------------|------------------|----------
1  | 1         | FACEBOOK  | Starbucks Official| starbucks        | true
2  | 1         | INSTAGRAM | Starbucks         | @starbucks       | true
3  | 1         | TWITTER   | Starbucks Coffee  | @Starbucks       | true
4  | 1         | THREADS   | Starbucks         | @starbucks       | true
5  | 2         | INSTAGRAM | Nike              | @nike            | true
6  | 2         | TWITTER   | Nike              | @Nike            | true
7  | 3         | FACEBOOK  | Derma Care        | dermacare        | true
```

**Each row = One social account**  
**Same client_id = Multiple platforms for one client** ✅

---

## ✅ **Key Features**

1. ✅ **One client → Multiple platforms** (unlimited)
2. ✅ **Each platform has its own credentials** (access tokens)
3. ✅ **Independent management** (can deactivate one platform)
4. ✅ **Cross-platform publishing** (same message to all)
5. ✅ **Platform-specific analytics** (separate insights)
6. ✅ **Easy switching** (select platform dropdown)

---

## 🎯 **Frontend UI Example**

```javascript
// Display connected platforms for a client
const ConnectedPlatforms = ({ clientId }) => {
  const [accounts, setAccounts] = useState([]);
  
  useEffect(() => {
    fetch(`http://localhost:8081/api/social-accounts/client/${clientId}`, {
      headers: { 'Authorization': 'Bearer ' + token }
    })
    .then(res => res.json())
    .then(data => setAccounts(data.data));
  }, [clientId]);
  
  return (
    <div>
      <h3>Connected Platforms</h3>
      {accounts.map(account => (
        <div key={account.id}>
          <img src={`/icons/${account.platform.toLowerCase()}.png`} />
          <span>{account.platform}</span>
          <span>{account.accountUsername}</span>
          <span>{account.isActive ? '✅ Active' : '❌ Inactive'}</span>
        </div>
      ))}
      
      <button onClick={() => connectPlatform('FACEBOOK', clientId)}>
        + Add Facebook
      </button>
      <button onClick={() => connectPlatform('INSTAGRAM', clientId)}>
        + Add Instagram
      </button>
    </div>
  );
};
```

---

**Your system already supports this! Just connect multiple platforms for each client.** 🚀
