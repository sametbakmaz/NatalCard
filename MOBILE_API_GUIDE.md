# 📱 Mobile App Integration Guide

## API Endpoints for Pickers & UI Components

Bu rehber, mobil uygulama için gerekli tüm picker/selector endpoint'lerini içerir.

---

## 🌍 Timezone Picker API

### Popüler Timezone'ları Getir (Önerilen)

```
GET /api/util/timezones/popular?language=tr
```

**Response:**
```json
{
  "timezones": [
    {
      "id": "Europe/Istanbul",
      "displayName": "Istanbul (+03:00)",
      "displayNameLocalized": "İstanbul (+03:00)",
      "offset": "+03:00",
      "region": "Europe",
      "country": "Istanbul",
      "countryLocalized": "İstanbul"
    },
    {
      "id": "Europe/London",
      "displayName": "London (+00:00)",
      "displayNameLocalized": "Londra (+00:00)",
      "offset": "+00:00",
      "region": "Europe",
      "country": "London",
      "countryLocalized": "Londra"
    }
    // ... 10 more popular timezones
  ],
  "totalCount": 12,
  "language": "tr"
}
```

### Tüm Timezone'ları Getir

```
GET /api/util/timezones?language=tr
```

**Response:** ~400 timezone (aynı format)

---

## 🏠 House System Picker API

```
GET /api/util/house-systems?language=tr
```

**Response:**
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
  },
  {
    "code": "WHOLE_SIGN",
    "name": "Whole Sign",
    "nameLocalized": "Tam Burç",
    "description": "Traditional Hellenistic system, each house = one sign",
    "descriptionLocalized": "Geleneksel Helenistik sistem, her ev = bir burç",
    "recommended": false,
    "bestFor": "All latitudes, historical accuracy",
    "bestForLocalized": "Tüm enlemler, tarihsel doğruluk"
  },
  {
    "code": "EQUAL",
    "name": "Equal",
    "nameLocalized": "Eşit",
    "description": "Simplest system, each house exactly 30 degrees",
    "descriptionLocalized": "En basit sistem, her ev tam 30 derece",
    "recommended": false,
    "bestFor": "All latitudes, simplicity",
    "bestForLocalized": "Tüm enlemler, basitlik"
  },
  {
    "code": "KOCH",
    "name": "Koch",
    "nameLocalized": "Koch",
    "description": "Birthplace system, similar to Placidus",
    "descriptionLocalized": "Doğum yeri sistemi, Placidus'a benzer",
    "recommended": false,
    "bestFor": "Most latitudes",
    "bestForLocalized": "Çoğu enlem"
  }
]
```

---

## ⭐ Aspect Types API

```
GET /api/util/aspect-types?language=tr
```

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
  },
  {
    "code": "SEXTILE",
    "name": "Sextile",
    "nameLocalized": "Sekstil",
    "angle": 60.0,
    "symbol": "⚹",
    "meaning": "Opportunity, harmony",
    "meaningLocalized": "Fırsat, uyum",
    "color": "#4CAF50"
  },
  {
    "code": "SQUARE",
    "name": "Square",
    "nameLocalized": "Kare",
    "angle": 90.0,
    "symbol": "□",
    "meaning": "Tension, challenge",
    "meaningLocalized": "Gerilim, zorluk",
    "color": "#F44336"
  },
  {
    "code": "TRINE",
    "name": "Trine",
    "nameLocalized": "Trigon",
    "angle": 120.0,
    "symbol": "△",
    "meaning": "Flow, ease, talent",
    "meaningLocalized": "Akış, kolaylık, yetenek",
    "color": "#2196F3"
  },
  {
    "code": "OPPOSITION",
    "name": "Opposition",
    "nameLocalized": "Karşıt",
    "angle": 180.0,
    "symbol": "☍",
    "meaning": "Polarity, awareness",
    "meaningLocalized": "Karşıtlık, farkındalık",
    "color": "#FF9800"
  }
]
```

---

## 🌟 Zodiac System Picker API

```
GET /api/util/zodiac-systems?language=tr
```

**Response:**
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
    "name": "Sidereal",
    "nameLocalized": "Sidereal",
    "description": "Based on fixed stars (used in Vedic astrology)",
    "descriptionLocalized": "Sabit yıldızlara dayalı (Vedik astrolojide kullanılır)",
    "supported": false
  }
]
```

---

## 📦 Tek Seferde Tüm Picker Verilerini Al (Önerilen!)

```
GET /api/util/picker-options?language=tr
```

**Response:**
```json
{
  "zodiacSystems": [ /* zodiac systems array */ ],
  "houseSystems": [ /* house systems array */ ],
  "aspectTypes": [ /* aspect types array */ ],
  "language": "tr"
}
```

**Avantajları:**
- ✅ Tek API çağrısı ile tüm picker verileri
- ✅ App başlangıcında bir kez çağır, cache'le
- ✅ Network overhead azalır

---

## 📍 Location Picker API (Ülke → İl → İlçe)

### 1. Ülkeleri Getir

```
GET /api/location/countries
```

**Response:**
```json
[
  {
    "code": "TR",
    "name": "Turkey",
    "nameLocalized": "Türkiye",
    "flag": "🇹🇷"
  },
  {
    "code": "US",
    "name": "United States",
    "nameLocalized": "Amerika Birleşik Devletleri",
    "flag": "🇺🇸"
  }
  // ... more countries
]
```

### 2. Seçilen Ülkenin İllerini Getir

```
GET /api/location/countries/TR/regions
```

**Response:**
```json
[
  {
    "code": "34",
    "name": "Istanbul",
    "nameLocalized": "İstanbul",
    "countryCode": "TR"
  },
  {
    "code": "06",
    "name": "Ankara",
    "nameLocalized": "Ankara",
    "countryCode": "TR"
  }
  // ... more regions
]
```

### 3. Seçilen İlin İlçelerini Getir

```
GET /api/location/countries/TR/regions/34/places?level=DISTRICT
```

**Response:**
```json
[
  {
    "id": "GEODB:123456",
    "name": "Kadıköy",
    "nameLocalized": "Kadıköy",
    "countryCode": "TR",
    "regionCode": "34",
    "type": "DISTRICT",
    "latitude": 40.983,
    "longitude": 29.029,
    "timezoneId": "Europe/Istanbul"
  }
  // ... more districts
]
```

### 4. Arama (Alternatif)

```
GET /api/location/geocode?q=Kadıköy&countryCode=TR
```

**Response:** Yukarıdaki ile aynı format

### 5. Reverse Geocoding (Lat/Lon → Yer)

```
GET /api/location/reverse?lat=40.983&lon=29.029
```

**Response:**
```json
{
  "id": "NOMINATIM:40.983,29.029",
  "name": "Kadıköy",
  "nameLocalized": "Kadıköy",
  "countryCode": "TR",
  "regionCode": "34",
  "type": "DISTRICT",
  "latitude": 40.983,
  "longitude": 29.029,
  "timezoneId": "Europe/Istanbul"
}
```

---

## 💡 Mobil App İş Akışı

### Uygulama Başlangıcında

```kotlin
// 1. Tüm picker verilerini al (cache'le)
val pickerOptions = api.getPickerOptions(language = "tr")

// 2. Varsayılan değerleri ayarla
val defaultHouseSystem = pickerOptions.houseSystems.first { it.recommended }
val defaultZodiac = pickerOptions.zodiacSystems.first { it.supported }
```

### Doğum Formu Doldururken

```kotlin
// 1. Timezone Picker
val popularTimezones = api.getPopularTimezones(language = "tr")
// Dropdown/Picker göster

// 2. Location Picker (3 aşamalı)
// a) Ülke seç
val countries = api.getCountries()
// Dropdown göster

// b) Kullanıcı TR seçti → İlleri getir
val regions = api.getRegions(countryCode = "TR")
// Dropdown göster

// c) Kullanıcı 34 seçti → İlçeleri getir
val places = api.getPlaces(
    countryCode = "TR", 
    regionCode = "34", 
    level = "DISTRICT"
)
// Dropdown göster

// d) Kullanıcı Kadıköy seçti → Koordinatları al
val selectedPlace = places.first { it.name == "Kadıköy" }
val latitude = selectedPlace.latitude
val longitude = selectedPlace.longitude
val suggestedTimezone = selectedPlace.timezoneId
```

### Natal Chart Hesapla

```kotlin
val request = NatalChartRequest(
    birthDateTimeLocal = "1996-04-23T14:35:00",
    timeZoneId = suggestedTimezone, // "Europe/Istanbul"
    latitude = latitude,            // 40.983
    longitude = longitude,          // 29.029
    zodiac = "TROPICAL",
    houseSystem = selectedHouseSystem.code, // "PLACIDUS"
    includeAspects = true,
    language = "tr"
)

val chart = api.calculateNatalChart(request)
```

---

## 📱 Flutter/Dart Örneği

```dart
// API Service
class AstroApiService {
  final Dio _dio = Dio(BaseOptions(
    baseUrl: 'http://localhost:8080/api',
  ));

  // Get picker options (app başlangıcında)
  Future<PickerOptionsResponse> getPickerOptions({String language = 'tr'}) async {
    final response = await _dio.get('/util/picker-options', 
      queryParameters: {'language': language}
    );
    return PickerOptionsResponse.fromJson(response.data);
  }

  // Get popular timezones
  Future<List<TimezoneDTO>> getPopularTimezones({String language = 'tr'}) async {
    final response = await _dio.get('/util/timezones/popular',
      queryParameters: {'language': language}
    );
    return (response.data['timezones'] as List)
        .map((e) => TimezoneDTO.fromJson(e))
        .toList();
  }

  // Location picker - countries
  Future<List<CountryDTO>> getCountries() async {
    final response = await _dio.get('/location/countries');
    return (response.data as List)
        .map((e) => CountryDTO.fromJson(e))
        .toList();
  }

  // Location picker - regions
  Future<List<RegionDTO>> getRegions(String countryCode) async {
    final response = await _dio.get('/location/countries/$countryCode/regions');
    return (response.data as List)
        .map((e) => RegionDTO.fromJson(e))
        .toList();
  }

  // Location picker - districts
  Future<List<PlaceDTO>> getPlaces({
    required String countryCode,
    required String regionCode,
    String level = 'DISTRICT'
  }) async {
    final response = await _dio.get(
      '/location/countries/$countryCode/regions/$regionCode/places',
      queryParameters: {'level': level}
    );
    return (response.data as List)
        .map((e) => PlaceDTO.fromJson(e))
        .toList();
  }

  // Calculate natal chart
  Future<NatalChartResponse> calculateNatalChart(NatalChartRequest request) async {
    final response = await _dio.post('/astro/natal-chart', 
      data: request.toJson()
    );
    return NatalChartResponse.fromJson(response.data);
  }
}

// UI Widget Example
class BirthLocationPicker extends StatefulWidget {
  @override
  _BirthLocationPickerState createState() => _BirthLocationPickerState();
}

class _BirthLocationPickerState extends State<BirthLocationPicker> {
  final _api = AstroApiService();
  
  List<CountryDTO>? countries;
  List<RegionDTO>? regions;
  List<PlaceDTO>? places;
  
  String? selectedCountry;
  String? selectedRegion;
  PlaceDTO? selectedPlace;

  @override
  void initState() {
    super.initState();
    _loadCountries();
  }

  Future<void> _loadCountries() async {
    countries = await _api.getCountries();
    setState(() {});
  }

  Future<void> _onCountrySelected(String countryCode) async {
    selectedCountry = countryCode;
    regions = await _api.getRegions(countryCode);
    setState(() {});
  }

  Future<void> _onRegionSelected(String regionCode) async {
    selectedRegion = regionCode;
    places = await _api.getPlaces(
      countryCode: selectedCountry!,
      regionCode: regionCode,
      level: 'DISTRICT'
    );
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        // Country Dropdown
        DropdownButton<String>(
          hint: Text('Ülke Seçin'),
          value: selectedCountry,
          items: countries?.map((c) => DropdownMenuItem(
            value: c.code,
            child: Text('${c.flag} ${c.nameLocalized}')
          )).toList(),
          onChanged: (value) => _onCountrySelected(value!),
        ),

        // Region Dropdown
        if (regions != null)
          DropdownButton<String>(
            hint: Text('İl Seçin'),
            value: selectedRegion,
            items: regions!.map((r) => DropdownMenuItem(
              value: r.code,
              child: Text(r.nameLocalized)
            )).toList(),
            onChanged: (value) => _onRegionSelected(value!),
          ),

        // Place Dropdown
        if (places != null)
          DropdownButton<PlaceDTO>(
            hint: Text('İlçe Seçin'),
            value: selectedPlace,
            items: places!.map((p) => DropdownMenuItem(
              value: p,
              child: Text(p.nameLocalized)
            )).toList(),
            onChanged: (value) {
              setState(() => selectedPlace = value);
              // Use: selectedPlace.latitude, selectedPlace.longitude
            },
          ),

        // Display coordinates
        if (selectedPlace != null)
          Text('Lat: ${selectedPlace!.latitude}, Lon: ${selectedPlace!.longitude}'),
      ],
    );
  }
}
```

---

## 🎨 UI/UX Önerileri

### Timezone Picker
```
📍 [Dropdown: Popular Timezones]
   → İstanbul (+03:00) 🇹🇷
   → Londra (+00:00) 🇬🇧
   → New York (-05:00) 🇺🇸
   
   [Show All Timezones...] (Genişlet)
```

### Location Picker (3 Aşamalı)
```
🌍 Ülke:   [Türkiye 🇹🇷        ▼]
🏙️ İl:     [İstanbul           ▼]
📍 İlçe:   [Kadıköy            ▼]

✅ Seçilen Konum: Kadıköy, İstanbul, Türkiye
   Koordinatlar: 40.983°N, 29.029°E
```

### House System Picker
```
🏠 Ev Sistemi:

⭐ Placidus (Önerilen)
   En popüler sistem, çoğu enlem için uygun
   
○ Tam Burç (Whole Sign)
   Geleneksel sistem, tüm enlemler
   
○ Eşit (Equal)
   En basit sistem
```

---

## ⚡ Performance İpuçları

### Cache Strategy
```kotlin
// App başlangıcında bir kez çağır
val pickerOptions = api.getPickerOptions()
SharedPreferences.save("pickerOptions", pickerOptions)
// TTL: 7 gün

// Sonraki kullanımlarda cache'den oku
val cached = SharedPreferences.get("pickerOptions")
if (cached.age < 7.days) {
    return cached
} else {
    // Refresh
}
```

### Lazy Loading
```kotlin
// Ülkeler: App başlangıcında yükle
loadCountries()

// İller: Kullanıcı ülke seçtiğinde yükle
onCountrySelected { loadRegions(it) }

// İlçeler: Kullanıcı il seçtiğinde yükle
onRegionSelected { loadPlaces(it) }
```

---

## 🎯 Özet: Tüm Picker Endpoint'leri

| Picker | Endpoint | Öneri |
|--------|----------|-------|
| Timezone | `/api/util/timezones/popular` | ⭐ Popular list kullan |
| House System | `/api/util/house-systems` | ✅ Tümünü göster |
| Zodiac | `/api/util/zodiac-systems` | ℹ️ Sadece TROPICAL destekleniyor |
| Aspect Types | `/api/util/aspect-types` | 📊 UI için renk bilgisi var |
| **Hepsi Birden** | `/api/util/picker-options` | 🚀 **En performanslı** |
| Ülkeler | `/api/location/countries` | 🌍 Cache'le |
| İller | `/api/location/countries/{code}/regions` | 🏙️ Lazy load |
| İlçeler | `/api/location/countries/{code}/regions/{region}/places` | 📍 Lazy load |

---

**Tüm endpoint'ler hazır! Mobil uygulamaya entegre edebilirsin!** 📱✨
