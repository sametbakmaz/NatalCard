# 🌟 NatalCard API Reference

Complete REST API documentation for the NatalCard astrology calculation service.

**Version:** 1.0.0  
**Base URL:** `http://localhost:8080` (development)  
**Content-Type:** `application/json`

---

## 📚 Table of Contents

- [Natal Chart API](#natal-chart-api)
- [Location API](#location-api)
- [Utility API](#utility-api)
- [Error Handling](#error-handling)
- [Rate Limiting](#rate-limiting)

---

## 🔮 Natal Chart API

### Calculate Natal Chart

Calculate a complete natal chart with planets, houses, and aspects.

**Endpoint:** `POST /api/astro/natal-chart`

#### Request Body

```json
{
  "birthDateTimeLocal": "1996-04-23T14:35:00",
  "timeZoneId": "Europe/Istanbul",
  "latitude": 40.983,
  "longitude": 29.029,
  "zodiac": "TROPICAL",
  "houseSystem": "PLACIDUS",
  "includeAspects": true,
  "language": "en"
}
```

#### Parameters

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `birthDateTimeLocal` | string | ✅ | Birth date and time in ISO-8601 format (YYYY-MM-DDTHH:mm:ss) |
| `timeZoneId` | string | ✅ | IANA timezone ID (e.g., "Europe/Istanbul", "America/New_York") |
| `latitude` | number | ✅ | Birth location latitude (-90 to 90) |
| `longitude` | number | ✅ | Birth location longitude (-180 to 180) |
| `zodiac` | string | ❌ | Zodiac system: "TROPICAL" (default) or "SIDEREAL" |
| `houseSystem` | string | ❌ | House system: "PLACIDUS" (default), "WHOLE_SIGN", "EQUAL", "KOCH" |
| `includeAspects` | boolean | ❌ | Calculate aspects (default: true) |
| `language` | string | ❌ | Response language: "en" (default) or "tr" |

#### Response Example

```json
{
  "meta": {
    "requestedHouseSystem": "PLACIDUS",
    "effectiveHouseSystem": "PLACIDUS",
    "warnings": [],
    "requestedHouseSystemLocalized": "Placidus",
    "effectiveHouseSystemLocalized": "Placidus",
    "warningsLocalized": []
  },
  "angles": {
    "ascendantLongitude": 34.739,
    "midHeavenLongitude": 52.208,
    "ascendantSign": "TAURUS",
    "midHeavenSign": "TAURUS",
    "ascendantSignLocalized": "Boğa",
    "midHeavenSignLocalized": "Boğa"
  },
  "houses": [
    {
      "number": 1,
      "cuspLongitude": 34.739,
      "sign": "TAURUS",
      "signLocalized": "Boğa"
    }
    // ... houses 2-12
  ],
  "points": {
    "SUN": {
      "name": "SUN",
      "longitude": 33.595,
      "sign": "TAURUS",
      "house": 12,
      "signDegree": 3.595,
      "nameLocalized": "Güneş",
      "signLocalized": "Boğa"
    }
    // ... MOON, MERCURY, VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO
  },
  "aspects": [
    {
      "planet1": "SUN",
      "planet2": "MOON",
      "aspectType": "SEXTILE",
      "angle": 60.0,
      "orb": 3.948,
      "isApplying": false,
      "planet1Localized": "Güneş",
      "planet2Localized": "Ay",
      "aspectTypeLocalized": "Sekstil"
    }
    // ... more aspects
  ]
}
```

#### Status Codes

- `200 OK` - Success
- `400 Bad Request` - Invalid parameters
- `422 Unprocessable Entity` - Calculation error (with warnings in response)
- `500 Internal Server Error` - Server error

---

## 📍 Location API

### Get Countries

List all available countries.

**Endpoint:** `GET /api/location/countries`

#### Response

```json
[
  {
    "code": "TR",
    "name": "Turkey",
    "flag": "🇹🇷"
  }
  // ... more countries
]
```

---

### Get Regions (States/Provinces)

Get regions for a specific country.

**Endpoint:** `GET /api/location/countries/{countryCode}/regions`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `countryCode` | string | ✅ | 2-letter ISO country code (e.g., "TR", "US") |

#### Example

```
GET /api/location/countries/TR/regions
```

#### Response

```json
[
  {
    "code": "34",
    "name": "Istanbul",
    "countryCode": "TR"
  }
  // ... more regions
]
```

---

### Get Places (Cities/Districts)

Get cities or districts for a specific region.

**Endpoint:** `GET /api/location/countries/{countryCode}/regions/{regionCode}/places`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `countryCode` | string | ✅ | 2-letter ISO country code |
| `regionCode` | string | ✅ | Region code |
| `level` | string | ❌ | "CITY" or "DISTRICT" (default: "CITY") |

#### Example

```
GET /api/location/countries/TR/regions/34/places?level=DISTRICT
```

#### Response

```json
[
  {
    "id": "GEODB:745042",
    "name": "Kadıköy",
    "countryCode": "TR",
    "regionCode": "34",
    "type": "DISTRICT",
    "latitude": 40.983,
    "longitude": 29.029,
    "timezoneId": "Europe/Istanbul"
  }
  // ... more places
]
```

---

### Geocode Search

Search for locations by name.

**Endpoint:** `GET /api/location/geocode`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `q` | string | ✅ | Search query (min 2 characters) |
| `countryCode` | string | ❌ | Filter by country code |
| `limit` | number | ❌ | Max results (default: 10, max: 50) |

#### Example

```
GET /api/location/geocode?q=Istanbul&countryCode=TR&limit=5
```

---

### Reverse Geocode

Get location details from coordinates.

**Endpoint:** `GET /api/location/reverse`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `lat` | number | ✅ | Latitude |
| `lon` | number | ✅ | Longitude |

#### Example

```
GET /api/location/reverse?lat=40.983&lon=29.029
```

---

## 🛠️ Utility API

### Get All Picker Options (Recommended!)

Get all picker data in a single call - **best for mobile apps**.

**Endpoint:** `GET /api/util/picker-options`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | string | ❌ | "en" (default) or "tr" |

#### Response

```json
{
  "zodiacSystems": [...],
  "houseSystems": [...],
  "aspectTypes": [...],
  "language": "en"
}
```

**💡 Tip:** Cache this response for 7 days!

---

### Get Popular Timezones

Get 12 most commonly used timezones.

**Endpoint:** `GET /api/util/timezones/popular`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | string | ❌ | "en" (default) or "tr" |

#### Response

```json
{
  "timezones": [
    {
      "id": "Europe/Istanbul",
      "displayName": "Istanbul (+03:00)",
      "displayNameLocalized": "İstanbul (+03:00)",
      "offset": "+03:00",
      "offsetMinutes": 180
    }
    // ... 11 more
  ],
  "totalCount": 12
}
```

---

### Get All Timezones

Get complete list of ~400 timezones.

**Endpoint:** `GET /api/util/timezones`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | string | ❌ | "en" (default) or "tr" |

---

### Get House Systems

Get available house systems.

**Endpoint:** `GET /api/util/house-systems`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | string | ❌ | "en" (default) or "tr" |

#### Response

```json
[
  {
    "code": "PLACIDUS",
    "name": "Placidus",
    "nameLocalized": "Placidus",
    "description": "Most popular house system, quadrant-based",
    "descriptionLocalized": "En popüler ev sistemi, kadran tabanlı",
    "recommended": true,
    "bestFor": "Most latitudes (automatic fallback at poles)",
    "bestForLocalized": "Çoğu enlem (kutuplarda otomatik yedek)"
  }
  // ... WHOLE_SIGN, EQUAL, KOCH
]
```

---

### Get Zodiac Systems

Get available zodiac systems.

**Endpoint:** `GET /api/util/zodiac-systems`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | string | ❌ | "en" (default) or "tr" |

#### Response

```json
[
  {
    "code": "TROPICAL",
    "name": "Tropical",
    "nameLocalized": "Tropikal",
    "description": "Based on seasons and equinoxes (most common in Western astrology)",
    "descriptionLocalized": "Mevsim ve ekinokslara dayalı (Batı astrolojisinde en yaygın)",
    "supported": true
  },
  {
    "code": "SIDEREAL",
    "supported": false
  }
]
```

---

### Get Aspect Types

Get aspect types with colors and symbols.

**Endpoint:** `GET /api/util/aspect-types`

#### Parameters

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | string | ❌ | "en" (default) or "tr" |

#### Response

```json
[
  {
    "code": "CONJUNCTION",
    "name": "Conjunction",
    "nameLocalized": "Kavuşum",
    "angle": 0.0,
    "symbol": "☌",
    "meaning": "Unity, blending",
    "meaningLocalized": "Birlik, kaynaşma",
    "color": "#9C27B0"
  }
  // ... SEXTILE, SQUARE, TRINE, OPPOSITION
]
```

**🎨 Colors:**
- Conjunction: `#9C27B0` (Purple)
- Sextile: `#4CAF50` (Green)
- Square: `#F44336` (Red)
- Trine: `#2196F3` (Blue)
- Opposition: `#FF9800` (Orange)

---

### Health Check

Check if Utility API is running.

**Endpoint:** `GET /api/util/health`

#### Response

```
Utility API is running
```

---

## ⚠️ Error Handling

### Error Response Format

```json
{
  "timestamp": "2024-04-23T14:35:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Birth date time is required",
  "path": "/api/astro/natal-chart"
}
```

### Common Error Codes

| Status | Description | Example |
|--------|-------------|---------|
| `400` | Bad Request | Invalid date format, missing required field |
| `404` | Not Found | Invalid endpoint |
| `422` | Unprocessable Entity | Location not found, calculation warning |
| `429` | Too Many Requests | Rate limit exceeded |
| `500` | Internal Server Error | Unexpected server error |

### Validation Errors

```json
{
  "timestamp": "2024-04-23T14:35:00Z",
  "status": 400,
  "error": "Validation Failed",
  "errors": [
    {
      "field": "latitude",
      "message": "must be between -90 and 90"
    },
    {
      "field": "timeZoneId",
      "message": "must be a valid IANA timezone ID"
    }
  ]
}
```

---

## 🚦 Rate Limiting

### Default Limits

- **Natal Chart API:** 60 requests per minute per IP
- **Location API:** 120 requests per minute per IP
- **Utility API:** Unlimited (cached data)

### Rate Limit Headers

```
X-RateLimit-Limit: 60
X-RateLimit-Remaining: 59
X-RateLimit-Reset: 1619024400
```

### Rate Limit Exceeded Response

```json
{
  "status": 429,
  "error": "Too Many Requests",
  "message": "Rate limit exceeded. Try again in 45 seconds.",
  "retryAfter": 45
}
```

---

## 📱 Mobile Integration Examples

### Flutter/Dart

```dart
// Get picker options on app start
final response = await http.get(
  Uri.parse('$baseUrl/api/util/picker-options?language=tr')
);
final options = PickerOptions.fromJson(jsonDecode(response.body));

// Cache for 7 days
await cache.save('pickerOptions', options, duration: Duration(days: 7));

// Calculate natal chart
final request = NatalChartRequest(
  birthDateTimeLocal: '1996-04-23T14:35:00',
  timeZoneId: 'Europe/Istanbul',
  latitude: 40.983,
  longitude: 29.029,
  houseSystem: 'PLACIDUS',
  language: 'tr',
);

final response = await http.post(
  Uri.parse('$baseUrl/api/astro/natal-chart'),
  headers: {'Content-Type': 'application/json'},
  body: jsonEncode(request.toJson()),
);

final chart = NatalChart.fromJson(jsonDecode(response.body));
```

### Swift/iOS

```swift
// Get picker options
let url = URL(string: "\(baseURL)/api/util/picker-options?language=en")!
let (data, _) = try await URLSession.shared.data(from: url)
let options = try JSONDecoder().decode(PickerOptions.self, from: data)

// Calculate natal chart
let request = NatalChartRequest(
    birthDateTimeLocal: "1996-04-23T14:35:00",
    timeZoneId: "Europe/Istanbul",
    latitude: 40.983,
    longitude: 29.029,
    houseSystem: "PLACIDUS",
    language: "en"
)

let url = URL(string: "\(baseURL)/api/astro/natal-chart")!
var urlRequest = URLRequest(url: url)
urlRequest.httpMethod = "POST"
urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
urlRequest.httpBody = try JSONEncoder().encode(request)

let (data, _) = try await URLSession.shared.data(for: urlRequest)
let chart = try JSONDecoder().decode(NatalChart.self, from: data)
```

### Kotlin/Android

```kotlin
// Get picker options
val response = client.get("$baseUrl/api/util/picker-options?language=tr")
val options = response.body<PickerOptions>()

// Calculate natal chart
val request = NatalChartRequest(
    birthDateTimeLocal = "1996-04-23T14:35:00",
    timeZoneId = "Europe/Istanbul",
    latitude = 40.983,
    longitude = 29.029,
    houseSystem = "PLACIDUS",
    language = "tr"
)

val response = client.post("$baseUrl/api/astro/natal-chart") {
    contentType(ContentType.Application.Json)
    setBody(request)
}
val chart = response.body<NatalChart>()
```

---

## 🔐 Security

### CORS

API supports CORS for browser-based clients:

```
Access-Control-Allow-Origin: *
Access-Control-Allow-Methods: GET, POST, OPTIONS
Access-Control-Allow-Headers: Content-Type, Accept, Accept-Language
```

### HTTPS

**Production:** Always use HTTPS endpoints  
**Development:** HTTP is acceptable for localhost only

---

## 📊 Performance Tips

### Best Practices

1. **Cache Picker Options** - Save for 7 days, update weekly
2. **Cache Timezones** - Popular timezones rarely change
3. **Cache Countries/Regions** - Update monthly
4. **Debounce Search** - Wait 300ms after user stops typing
5. **Use Single Picker Call** - `/api/util/picker-options` instead of 3 separate calls
6. **Batch Calculations** - Calculate multiple charts in sequence, not parallel

### Caching Strategy

| Data Type | TTL | Update Frequency | Provider |
|-----------|-----|------------------|----------|
| Picker Options | 7 days | Weekly | Local |
| Timezones | 7 days | Monthly | Local |
| Countries | 7 days | Monthly | REST Countries API (free) |
| Regions | 1 day | Daily | GeoNames (requires account) |
| Places | 1 hour | Hourly | GeoNames (requires account) |
| Natal Charts | Never | Never cache (personal data) | - |

### Location Data Providers

| Provider | Data | Cost | Configuration |
|----------|------|------|---------------|
| **REST Countries** | Countries only | ✅ FREE | None required |
| **GeoNames** | Regions, Cities, Districts | ✅ FREE | Username required |
| **Nominatim** | Geocoding fallback | ✅ FREE | None (1 req/sec limit) |

**Setup GeoNames (Free):**
1. Register: [https://www.geonames.org/login](https://www.geonames.org/login)
2. Enable web services in account settings
3. Set `GEONAMES_USERNAME` environment variable

---

## 📝 Changelog

See [CHANGELOG.md](CHANGELOG.md) for version history.

---

## 🤝 Support

- **Documentation:** [README.md](README.md)
- **Mobile Guide:** [MOBILE_API_GUIDE.md](MOBILE_API_GUIDE.md)
- **Issues:** Create an issue on GitHub

---

**Built with ❤️ using Spring Boot 4.0 & Astronomy Engine**
