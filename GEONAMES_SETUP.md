# 🌍 GeoNames Setup Guide

GeoNames is a FREE geographical database that provides:
- ✅ States/Provinces (regions)
- ✅ Cities
- ✅ Districts
- ✅ Coordinates & timezones

**Note:** Countries work without GeoNames (using REST Countries API).

---

## 🚀 Quick Setup (5 minutes)

### Step 1: Create Free Account

1. Go to: [https://www.geonames.org/login](https://www.geonames.org/login)
2. Click **"create a new user account"**
3. Fill the form:
   - Username (remember this!)
   - Email
   - Password
4. Click **"Create Account"**

---

### Step 2: Activate Account

1. **Check your email** (including spam folder!)
2. Click the activation link
3. You should see: *"Your email address has been confirmed"*

---

### Step 3: Enable Web Services

1. Login: [https://www.geonames.org/login](https://www.geonames.org/login)
2. Go to: [https://www.geonames.org/manageaccount](https://www.geonames.org/manageaccount)
3. Scroll down to **"Free Web Services"**
4. Click **"Click here to enable"**
5. You should see: *"account enabled to use the free web services"*

---

### Step 4: Set Environment Variable

**Windows (PowerShell):**
```powershell
# Temporary (current session only)
$env:GEONAMES_USERNAME="your_username"

# Permanent (system-wide)
[System.Environment]::SetEnvironmentVariable("GEONAMES_USERNAME", "your_username", "User")
```

**Linux/Mac (bash/zsh):**
```bash
# Temporary (current session only)
export GEONAMES_USERNAME="your_username"

# Permanent (add to ~/.bashrc or ~/.zshrc)
echo 'export GEONAMES_USERNAME="your_username"' >> ~/.bashrc
source ~/.bashrc
```

**Docker:**
```yaml
environment:
  - GEONAMES_USERNAME=your_username
```

---

### Step 5: Verify Setup

**Test API directly:**
```bash
curl "http://api.geonames.org/searchJSON?country=TR&maxRows=1&username=YOUR_USERNAME"
```

**Expected response:** JSON with Turkish location data

**If you get HTML error page:**
- ❌ Web services not enabled → Go back to Step 3
- ❌ Account not activated → Check email (Step 2)
- ❌ Wrong username → Double-check spelling

---

### Step 6: Restart Application

```bash
mvn spring-boot:run
```

Check logs for:
```
✅ Fetched 81 regions for TR from GeoNames
✅ Fetched 100 cities for TR/34 from GeoNames
```

---

## ✅ What Works Without GeoNames

| Feature | Works? | Provider |
|---------|--------|----------|
| Get Countries | ✅ YES | REST Countries API |
| Get Regions | ❌ NO | Requires GeoNames |
| Get Cities | ❌ NO | Requires GeoNames |
| Geocode Search | ⚠️ LIMITED | Nominatim (basic) |

---

## 🚨 Common Issues

### "GEONAMES_USERNAME not set"

**Symptom:** 
```
WARN: GEONAMES_USERNAME not set - regions unavailable
```

**Solution:** Environment variable not set or app not restarted. Restart app after setting variable.

---

### "Web services not enabled"

**Symptom:**
```xml
<status message="account not enabled to use the free web service..."/>
```

**Solution:** 
1. Login to GeoNames
2. Go to [https://www.geonames.org/manageaccount](https://www.geonames.org/manageaccount)
3. Enable free web services

---

### Rate Limits

GeoNames free tier limits:
- ✅ **20,000 credits/day** (1 request = 1 credit)
- ✅ **1 hour credit renewal**
- ✅ **2000 credits/hour max**

**The app caches aggressively to stay within limits:**
- Countries: 7 days
- Regions: 1 day
- Cities: 1 hour

---

## 🎯 Production Deployment

### Environment Variables

**Heroku:**
```bash
heroku config:set GEONAMES_USERNAME=your_username
```

**AWS Elastic Beanstalk:**
```bash
eb setenv GEONAMES_USERNAME=your_username
```

**Kubernetes:**
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: geonames-secret
type: Opaque
stringData:
  username: your_username
```

**Docker Compose:**
```yaml
services:
  natalcard:
    image: natalcard:latest
    environment:
      - GEONAMES_USERNAME=${GEONAMES_USERNAME}
```

---

## 📚 Additional Resources

- **GeoNames API Docs:** [https://www.geonames.org/export/web-services.html](https://www.geonames.org/export/web-services.html)
- **REST Countries (no auth):** [https://restcountries.com](https://restcountries.com)
- **Nominatim:** [https://nominatim.org](https://nominatim.org)

---

## 💡 Tips

1. **Test immediately after signup** - Don't wait days, links expire!
2. **Check spam folder** - Activation email often goes there
3. **Use real email** - Fake emails won't work
4. **Enable web services** - Most common mistake is skipping this
5. **Restart app** - Environment changes need app restart

---

## ❓ Still Having Issues?

Check application logs:
```bash
mvn spring-boot:run | grep -i geonames
```

Test endpoint manually:
```bash
curl "http://localhost:8080/api/location/countries/TR/regions"
```

If regions return empty but countries work, GeoNames is the issue.

---

**🎉 Once setup is complete, you'll have access to worldwide location data for free!**
