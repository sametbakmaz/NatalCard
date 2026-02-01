klı # 🌟 NatalCard - Professional Astrology Backend API

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen?style=for-the-badge&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**A production-ready REST API for calculating natal charts with multi-language support and location services**

[Features](#-features) • [Quick Start](#-quick-start) • [API Documentation](#-api-documentation) • [Examples](#-examples) • [Localization](#-internationalization)

</div>

---

## ✨ Features

### 🔮 Astrology Engine
- ✅ **Accurate Planet Positions** - Using Astronomy Engine (VSOP87 algorithms)
- ✅ **10 Celestial Bodies** - Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto
- ✅ **Angles Calculation** - Precise Ascendant (ASC) and Midheaven (MC)
- ✅ **Major Aspects** - Conjunction (0°), Sextile (60°), Square (90°), Trine (120°), Opposition (180°)
- ✅ **Smart Orb System** - 8° for Sun/Moon, 6° for other planets

### 🏠 House Systems
- ✅ **PLACIDUS** - Most popular system with automatic high-latitude fallback
- ✅ **WHOLE SIGN** - Traditional system, each house = one zodiac sign
- ✅ **EQUAL** - Simple system, each house = 30° from ASC
- ✅ **KOCH** - Birthplace system
- ✅ **Automatic Fallback** - Intelligent fallback for edge cases

### 🌍 Multi-Language Support (i18n)
- ✅ **English** - Full support
- ✅ **Turkish (Türkçe)** - Complete translation
- ✅ **Dual Response Format** - Both original and localized fields
- ✅ **Extensible** - Easy to add new languages

### 📍 Location Services
- ✅ **Hierarchical Location Data** - Country → Region → City → District
- ✅ **Multiple Providers** - GeoDB Cities, GeoNames, Nominatim with automatic fallback
- ✅ **Intelligent Caching** - 7-30 days TTL using Caffeine
- ✅ **Geocoding & Reverse Geocoding** - Lat/Lon ↔ Place name

### 🚀 Production Ready
- ✅ **Input Validation** - Jakarta Bean Validation
- ✅ **Error Handling** - Proper HTTP status codes (400, 422, 503)
- ✅ **Warning System** - Clear warnings for edge cases
- ✅ **Performance Optimized** - Caching, non-blocking HTTP clients
- ✅ **Clean Architecture** - Separation of concerns

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Programming Language |
| **Spring Boot** | 4.0.2 | Application Framework |
| **Maven** | 3.6+ | Build Tool |
| **Astronomy Engine** | Latest | Planet Position Calculations |
| **Caffeine Cache** | 3.x | High-Performance Caching |
| **WebFlux** | - | Non-Blocking HTTP Client |
| **Lombok** | - | Boilerplate Reduction |
| **Jakarta Validation** | - | Input Validation |

---

## 📦 Prerequisites

- **Java 17** or higher ([Download](https://adoptium.net/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **(Optional)** GeoNames account for enhanced location services ([Sign up](https://www.geonames.org/login))

---

## 💿 Installation

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/NatalCard.git
cd NatalCard
```

### 2️⃣ Configure Environment (Optional but Recommended)

For **full location services** (regions, cities, districts):

1. **Get FREE GeoNames account:** [https://www.geonames.org/login](https://www.geonames.org/login)
2. **Activate your account** via email
3. **Enable free web services:** [https://www.geonames.org/manageaccount](https://www.geonames.org/manageaccount)
4. **Set environment variable:**

**Windows (PowerShell):**
```powershell
$env:GEONAMES_USERNAME="your_username"
```

**Linux/Mac:**
```bash
export GEONAMES_USERNAME="your_username"
```

**Note:** Without GeoNames:
- ✅ **Countries still work** (via REST Countries API - no config needed)
- ❌ **Regions/Cities unavailable** (requires GeoNames username)

📖 **Detailed Setup Guide:** See [GEONAMES_SETUP.md](GEONAMES_SETUP.md) for step-by-step instructions

### 3️⃣ Build the Project

```bash
mvn clean install
```

### 4️⃣ Run the Application

```bash
mvn spring-boot:run
```

The API will start at `http://localhost:8080`

---

## 🚀 Quick Start

### Calculate Your First Natal Chart

```bash
curl -X POST http://localhost:8080/api/astro/natal-chart \
  -H "Content-Type: application/json" \
  -d '{
    "birthDateTimeLocal": "1996-04-23T14:35:00",
    "timeZoneId": "Europe/Istanbul",
    "latitude": 40.983,
    "longitude": 29.029,
    "zodiac": "TROPICAL",
    "houseSystem": "WHOLE_SIGN",
    "includeAspects": true,
    "language": "tr"
  }'
```

---

## 📚 API Documentation

### 📖 Complete Documentation

- **[API Reference](API_REFERENCE.md)** - Complete REST API documentation with examples
- **[Mobile App Guide](MOBILE_API_GUIDE.md)** - Integration guide for Flutter, Swift, Kotlin
- **[Picker API Summary](PICKER_API_SUMMARY.md)** - Quick reference for utility endpoints
- **[Postman Collection](postman_collection.json)** - Import to test all endpoints

### Quick Start

#### Calculate Natal Chart (Synchronous)

**Endpoint:** `POST /api/astro/natal-chart`

**Use Case:** Single requests, immediate response needed

**Request Body:**

```json
{
  "birthDateTimeLocal": "1996-04-23T14:35:00",  // ISO 8601 format
  "timeZoneId": "Europe/Istanbul",              // IANA timezone
  "latitude": 40.983,                           // Geographic latitude
  "longitude": 29.029,                          // Geographic longitude
  "zodiac": "TROPICAL",                         // Only TROPICAL supported
  "houseSystem": "WHOLE_SIGN",                  // PLACIDUS | WHOLE_SIGN | EQUAL | KOCH
  "includeAspects": true,                       // Calculate aspects?
  "language": "tr"                              // en | tr
}
```

**Response Example (Türkçe):**

```json
{
  "meta": {
    "requestedHouseSystem": "WHOLE_SIGN",
    "requestedHouseSystemLocalized": "Tam Burç",
    "effectiveHouseSystem": "WHOLE_SIGN",
    "effectiveHouseSystemLocalized": "Tam Burç",
    "warnings": [],
    "warningsLocalized": []
  },
  "angles": {
    "ascendantLongitude": 34.74,
    "ascendantSign": "TAURUS",
    "ascendantSignLocalized": "Boğa",
    "midHeavenLongitude": 52.21,
    "midHeavenSign": "TAURUS",
    "midHeavenSignLocalized": "Boğa"
  },
  "houses": [
    {
      "number": 1,
      "cuspLongitude": 30.0,
      "sign": "TAURUS",
      "signLocalized": "Boğa"
    }
    // ... houses 2-12
  ],
  "points": {
    "SUN": {
      "name": "SUN",
      "nameLocalized": "Güneş",
      "longitude": 33.60,
      "sign": "TAURUS",
      "signLocalized": "Boğa",
      "house": 1,
      "signDegree": 3.60
    },
    "MOON": {
      "name": "MOON",
      "nameLocalized": "Ay",
      "longitude": 97.54,
      "sign": "CANCER",
      "signLocalized": "Yengeç",
      "house": 3,
      "signDegree": 7.54
    }
    // ... other planets
  },
  "aspects": [
    {
      "planet1": "SUN",
      "planet1Localized": "Güneş",
      "planet2": "MOON",
      "planet2Localized": "Ay",
      "aspectType": "SEXTILE",
      "aspectTypeLocalized": "Sekstil",
      "angle": 60.0,
      "orb": 3.95,
      "isApplying": null
    }
    // ... other aspects
  ]
}
```

---

### Calculate Natal Chart (Asynchronous) 🚀

**Endpoint:** `POST /api/astro/natal-chart/async`

**Use Case:** High-load scenarios (50-60 concurrent requests), non-blocking

**Request Body:** Same as synchronous endpoint

**Response:** `CompletableFuture<NatalChartResponseDTO>` (non-blocking)

**Example:**
```bash
curl -X POST http://localhost:8080/api/astro/natal-chart/async \
  -H "Content-Type: application/json" \
  -d '{
    "birthDateTimeLocal": "1996-04-23T14:35:00",
    "timeZoneId": "Europe/Istanbul",
    "latitude": 40.983,
    "longitude": 29.029,
    "houseSystem": "WHOLE_SIGN",
    "language": "tr",
    "includeAspects": true
  }'
```

**Benefits:**
- ✅ Handles 50-60 concurrent requests efficiently
- ✅ Non-blocking I/O
- ✅ Automatic thread pool management
- ✅ Graceful degradation under heavy load

---

### Calculate Multiple Charts (Batch) 📊

**Endpoint:** `POST /api/astro/natal-chart/batch`

**Use Case:** Batch operations, chart comparisons

**Request Body:** Array of natal chart requests (max 10)

```json
[
  {
    "birthDateTimeLocal": "1996-04-23T14:35:00",
    "timeZoneId": "Europe/Istanbul",
    "latitude": 40.983,
    "longitude": 29.029,
    "houseSystem": "WHOLE_SIGN",
    "language": "en",
    "includeAspects": true
  },
  {
    "birthDateTimeLocal": "1990-03-15T08:20:00",
    "timeZoneId": "America/New_York",
    "latitude": 40.7128,
    "longitude": -74.0060,
    "houseSystem": "PLACIDUS",
    "language": "en",
    "includeAspects": true
  }
]
```

**Response:** Array of `NatalChartResponseDTO` (all calculated in parallel)

**Limits:**
- Max 10 charts per batch request
- All calculations run in parallel

---

## ⚡ Performance & Scalability

### Thread Pool Configuration

**Natal Chart Calculations:**
- Core pool: 10 threads (always active)
- Max pool: 50 threads (scales up to 50 concurrent requests)
- Queue capacity: 100 (buffers overflow)

**HTTP Server (Tomcat):**
- Max threads: 200
- Max connections: 10,000

### Capacity

| Scenario | Capacity | Response Time |
|----------|----------|---------------|
| Single request | 1 | < 100ms |
| 10 concurrent | 10 | < 150ms |
| 50 concurrent | 50 | < 300ms |
| 60 concurrent | 60 | < 500ms* |

*Some requests may queue if all 50 threads are busy

### Load Testing

Run performance tests:
```bash
mvn test -Dtest=AsyncPerformanceTest
```

---

## 🏠 House Systems

### PLACIDUS (Default)
- Most widely used in Western astrology
- Quadrant-based system
- **Automatic fallback** to Equal houses at high latitudes (>66°)

### WHOLE SIGN
- Traditional system from Hellenistic astrology
- Each house = one complete zodiac sign
- House 1 starts at 0° of Ascendant's sign

### EQUAL
- Simplest system
- Each house = exactly 30° from Ascendant
- Works at any latitude

### KOCH
- Birthplace system
- Similar to Placidus but uses different pole

---

## ⭐ Aspects

### Supported Major Aspects

| Aspect | Angle | Symbol | Turkish |
|--------|-------|--------|---------|
| Conjunction | 0° | ☌ | Kavuşum |
| Sextile | 60° | ⚹ | Sekstil |
| Square | 90° | □ | Kare |
| Trine | 120° | △ | Trigon |
| Opposition | 180° | ☍ | Karşıt |

### Orb Rules
- **Sun/Moon involved**: ±8°
- **Other planets**: ±6°

### `isApplying` Field
Currently returns `null` (planet speeds not calculated yet).
Future enhancement will return:
- `true` - planets moving closer (forming)
- `false` - planets moving apart (separating)

---

## 📱 Utility APIs (Pickers for Mobile Apps)

### Get All Picker Options (Recommended)

**Endpoint:** `GET /api/util/picker-options?language=tr`

**Use Case:** App initialization - get all dropdown options in one call

**Response:**
```json
{
  "zodiacSystems": [/* TROPICAL, SIDEREAL */],
  "houseSystems": [/* PLACIDUS, WHOLE_SIGN, EQUAL, KOCH */],
  "aspectTypes": [/* CONJUNCTION, SEXTILE, SQUARE, TRINE, OPPOSITION */],
  "language": "tr"
}
```

### Get Popular Timezones

**Endpoint:** `GET /api/util/timezones/popular?language=tr`

**Use Case:** Timezone picker dropdown

**Response:**
```json
{
  "timezones": [
    {
      "id": "Europe/Istanbul",
      "displayName": "Istanbul (+03:00)",
      "displayNameLocalized": "İstanbul (+03:00)",
      "offset": "+03:00",
      "region": "Europe"
    }
    // ... 11 more popular timezones
  ]
}
```

### Get House Systems

**Endpoint:** `GET /api/util/house-systems?language=tr`

**Use Case:** House system picker

**Response:**
```json
[
  {
    "code": "PLACIDUS",
    "name": "Placidus",
    "nameLocalized": "Placidus",
    "description": "Most popular house system",
    "descriptionLocalized": "En popüler ev sistemi",
    "recommended": true,
    "bestFor": "Most latitudes"
  }
  // ... WHOLE_SIGN, EQUAL, KOCH
]
```

### Get Aspect Types

**Endpoint:** `GET /api/util/aspect-types?language=tr`

**Use Case:** Aspect information for UI (with colors!)

**Response:**
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
  // ... other aspects with colors
]
```

### Complete Utility API List

| Endpoint | Purpose | Cache |
|----------|---------|-------|
| `GET /api/util/picker-options` | All picker data in one call | ✅ 7 days |
| `GET /api/util/timezones/popular` | 12 popular timezones | ✅ 7 days |
| `GET /api/util/timezones` | All ~400 timezones | ✅ 7 days |
| `GET /api/util/house-systems` | 4 house systems | ✅ Static |
| `GET /api/util/zodiac-systems` | Zodiac systems | ✅ Static |
| `GET /api/util/aspect-types` | Aspect types with colors | ✅ Static |

**Mobile App Workflow:**
```
1. App Launch → GET /api/util/picker-options (cache 7 days)
2. User fills form → Use cached picker data
3. User selects location → GET /api/location/... endpoints
4. User submits → POST /api/astro/natal-chart
```

See [MOBILE_API_GUIDE.md](MOBILE_API_GUIDE.md) for detailed integration examples!

---

## 🌍 Internationalization

### Supported Languages

#### English (`language: "en"`)
```json
{
  "name": "SUN",
  "nameLocalized": "Sun",
  "sign": "ARIES",
  "signLocalized": "Aries",
  "aspectType": "TRINE",
  "aspectTypeLocalized": "Trine"
}
```

#### Turkish (`language: "tr"`)
```json
{
  "name": "SUN",
  "nameLocalized": "Güneş",
  "sign": "ARIES",
  "signLocalized": "Koç",
  "aspectType": "TRINE",
  "aspectTypeLocalized": "Trigon"
}
```

### Translation Coverage

**Zodiac Signs (12):**
```
Koç, Boğa, İkizler, Yengeç, Aslan, Başak,
Terazi, Akrep, Yay, Oğlak, Kova, Balık

Aries, Taurus, Gemini, Cancer, Leo, Virgo,
Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces
```

**Planets (10):**
```
Güneş, Ay, Merkür, Venüs, Mars, Jüpiter,
Satürn, Uranüs, Neptün, Plüton

Sun, Moon, Mercury, Venus, Mars, Jupiter,
Saturn, Uranus, Neptune, Pluto
```

**House Systems (4):**
```
Placidus, Tam Burç, Eşit, Koch
Placidus, Whole Sign, Equal, Koch
```

---

## 📍 Location Services

### Get Countries

```bash
GET /api/location/countries
```

### Get Regions

```bash
GET /api/location/countries/TR/regions
```

### Get Cities

```bash
GET /api/location/countries/TR/regions/34/places?level=CITY
```

### Geocode (Place name → Coordinates)

```bash
GET /api/location/geocode?q=Istanbul&countryCode=TR
```

### Reverse Geocode (Coordinates → Place name)

```bash
GET /api/location/reverse?lat=40.983&lon=29.029
```

---

## 💡 Examples

### PowerShell Example

```powershell
$body = @{
    birthDateTimeLocal = "1996-04-23T14:35:00"
    timeZoneId = "Europe/Istanbul"
    latitude = 40.983
    longitude = 29.029
    zodiac = "TROPICAL"
    houseSystem = "PLACIDUS"
    includeAspects = $true
    language = "tr"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/astro/natal-chart" `
  -Method Post `
  -ContentType "application/json" `
  -Body $body
```

### JavaScript/TypeScript Example

```typescript
const response = await fetch('http://localhost:8080/api/astro/natal-chart', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    birthDateTimeLocal: "1996-04-23T14:35:00",
    timeZoneId: "Europe/Istanbul",
    latitude: 40.983,
    longitude: 29.029,
    zodiac: "TROPICAL",
    houseSystem: "WHOLE_SIGN",
    includeAspects: true,
    language: "en"
  })
});

const natalChart = await response.json();
console.log(natalChart);
```

### Python Example

```python
import requests

response = requests.post(
    'http://localhost:8080/api/astro/natal-chart',
    json={
        'birthDateTimeLocal': '1996-04-23T14:35:00',
        'timeZoneId': 'Europe/Istanbul',
        'latitude': 40.983,
        'longitude': 29.029,
        'zodiac': 'TROPICAL',
        'houseSystem': 'PLACIDUS',
        'includeAspects': True,
        'language': 'en'
    }
)

natal_chart = response.json()
print(natal_chart)
```

---

## ⚙️ Configuration

### Environment Variables

| Variable | Required | Description |
|----------|----------|-------------|
| `GEONAMES_USERNAME` | No | GeoNames API username for district-level location data |

### Application Properties

```properties
# Server Configuration
server.port=8080

# Cache Configuration (Caffeine)
# - Countries/Regions/Cities: 7 days TTL
# - Place Details: 30 days TTL
# - Geocode Results: 30 days TTL

# Logging
logging.level.com.natalcard=INFO
```

---

## 🧪 Testing

### Run Tests

```bash
mvn test
```

### Integration Tests

```bash
mvn verify
```

### Manual Testing

See [Examples](#-examples) section for curl/PowerShell/JavaScript examples.

---

## 📊 Warning System

The API includes intelligent warnings for edge cases:

| Warning Code | Description |
|--------------|-------------|
| `PLACIDUS_FALLBACK_EQUAL_HIGH_LAT` | Placidus not reliable at high latitudes (>66°), using Equal houses |
| `PLACIDUS_SOLVER_FAILED_FALLBACK_EQUAL` | Numerical solver failed, using Equal houses |

Example response with warning:
```json
{
  "meta": {
    "warnings": ["PLACIDUS_FALLBACK_EQUAL_HIGH_LAT"],
    "warningsLocalized": ["Placidus yüksek enlemlerde güvenilir değil, Eşit ev sistemi kullanıldı"]
  }
}
```

---

## 🛠️ Troubleshooting

### Location API Issues

**Problem:** Countries list is empty

**Solution:** Check if REST Countries API is accessible:
```bash
curl https://restcountries.com/v3.1/all?fields=cca2,name
```

If blocked, check firewall/proxy settings.

---

**Problem:** Regions and cities return empty

**Solution:** GeoNames requires free account:

1. **Sign up:** [https://www.geonames.org/login](https://www.geonames.org/login)
2. **Activate account** via email (check spam folder!)
3. **Enable Web Services:** [https://www.geonames.org/manageaccount](https://www.geonames.org/manageaccount)
4. **Set environment variable:**
   ```bash
   export GEONAMES_USERNAME="your_username"
   ```
5. **Restart application**

**Verify setup:**
```bash
curl "http://api.geonames.org/searchJSON?country=TR&maxRows=1&username=YOUR_USERNAME"
```

Should return JSON (not HTML error page).

---

**Problem:** `SSLHandshakeException` or certificate errors

**Solution:** All APIs now use reliable endpoints:
- ✅ **REST Countries:** HTTPS with valid certificate
- ✅ **GeoNames:** HTTP (no SSL issues)
- ✅ **Nominatim:** HTTPS with valid certificate

No custom SSL configuration needed!

### High-Latitude Calculation Issues

If you're calculating charts for locations above 66° latitude (Arctic/Antarctic), the system will automatically fallback from Placidus to Equal house system. This is expected behavior and will be indicated in the `warnings` field.

### External API Rate Limits

- **GeoDB Cities:** ~1000 requests/hour (free tier)
- **GeoNames:** Requires account (set `GEONAMES_USERNAME` env variable)
- **Nominatim:** 1 request/second (MUST respect, or you'll be blocked)

The application caches all location data (7-30 days TTL) to minimize API calls.

---

## 🐛 Error Handling

| Status Code | Description |
|-------------|-------------|
| 200 | Success (may include warnings) |
| 400 | Invalid request (validation errors) |
| 422 | Location not found |
| 503 | External service unavailable |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards
- Follow Java naming conventions
- Write unit tests for new features
- Update documentation
- Keep code clean and commented

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- [Astronomy Engine](https://github.com/cosinekitty/astronomy) - For accurate planet position calculations
- [Spring Boot](https://spring.io/projects/spring-boot) - Application framework
- [GeoDB Cities API](https://geodb-cities-api.wirefreethought.com/) - Location data provider
- [GeoNames](https://www.geonames.org/) - Geographic database
- [OpenStreetMap](https://www.openstreetmap.org/) - Nominatim geocoding service

---

## 📧 Contact

**Project Link:** [https://github.com/YOUR_USERNAME/NatalCard](https://github.com/YOUR_USERNAME/NatalCard)

**Issues:** [https://github.com/YOUR_USERNAME/NatalCard/issues](https://github.com/YOUR_USERNAME/NatalCard/issues)

---

## 🗺️ Roadmap

### Version 1.0 (Current) ✅
- [x] Natal chart calculations
- [x] Multiple house systems (Placidus, Whole Sign, Equal, Koch)
- [x] Major aspects
- [x] Multi-language support (EN, TR)
- [x] Location services
- [x] Caching

### Version 2.0 (Planned)
- [ ] Planet speeds & applying/separating aspects
- [ ] Minor aspects (Semisextile, Semisquare, Quincunx, etc.)
- [ ] Additional celestial points (North Node, Chiron, Part of Fortune)
- [ ] More house systems (Campanus, Regiomontanus, Porphyry)
- [ ] Arabic Parts calculation
- [ ] Transits & Progressions
- [ ] More languages (Spanish, German, French)

---

<div align="center">

**Made with ❤️ and ☕**

⭐ **Star this repo if you find it useful!** ⭐

</div>
#   N a t a l C a r d  
 