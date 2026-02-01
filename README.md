# 🌟 NatalCard — Professional Astrology Backend API

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**A production-ready REST API for calculating natal charts with multi-language support and location services**

[Features](#-features) •
[Quick Start](#-quick-start) •
[API](#-api-documentation) •
[Examples](#-examples) •
[i18n](#-internationalization) •
[Roadmap](#-roadmap)

</div>

---

## ✨ Features

### 🔮 Astrology Engine
- ✅ Accurate planet positions (Astronomy Engine / VSOP87-based)
- ✅ 10 celestial bodies: Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto
- ✅ Angles: Ascendant (ASC) & Midheaven (MC)
- ✅ Major aspects: Conjunction (0°), Sextile (60°), Square (90°), Trine (120°), Opposition (180°)
- ✅ Smart orb system: **±8°** for Sun/Moon aspects, **±6°** for other planets

### 🏠 House Systems
- ✅ **PLACIDUS** — common Western system (with high-latitude fallback)
- ✅ **WHOLE_SIGN** — traditional system (each house = one full sign)
- ✅ **EQUAL** — each house = 30° from ASC
- ✅ **KOCH** — birthplace system (optional/experimental depending on implementation)
- ✅ Automatic fallback + warning system for edge cases

### 🌍 Multi-Language Support (i18n)
- ✅ English (`en`)
- ✅ Turkish (`tr`)
- ✅ Dual fields: raw + localized (`sign` + `signLocalized`, etc.)
- ✅ Easy to extend with new languages

### 📍 Location Services
- ✅ Hierarchical pickers: Country → Region → City → District
- ✅ Multiple providers with fallback
- ✅ Intelligent caching (Caffeine) for 7–30 days TTL
- ✅ Geocoding + Reverse geocoding endpoints

### 🚀 Production Ready
- ✅ Jakarta Bean Validation
- ✅ Standard error handling (400 / 422 / 503)
- ✅ Warning system for non-fatal edge cases
- ✅ Non-blocking HTTP clients for external calls (WebClient)
- ✅ Clean architecture (controllers/services/clients/calc separation)

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 17+ | Runtime |
| Spring Boot | 3.x | REST API framework |
| Maven | 3.6+ | Build tool |
| Astronomy Engine | Latest | Planet positions |
| Caffeine | 3.x | In-memory cache |
| WebClient (WebFlux) | - | External HTTP calls |
| Lombok (optional) | - | Reduce boilerplate |
| Jakarta Validation | - | Request validation |

---

## 📦 Prerequisites
- Java 17+  
- Maven 3.6+

Optional (for enhanced location data):
- GeoNames account (requires username)

---

## 💿 Installation

### 1️⃣ Clone
```bash
git clone https://github.com/sametbakmaz/NatalCard.git
cd NatalCard
