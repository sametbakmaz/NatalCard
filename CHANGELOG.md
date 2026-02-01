# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- 🔄 **Location API Providers** - Switched from GeoDB to more reliable free alternatives:
  - **Countries:** Now using REST Countries API (https://restcountries.com) - FREE, no auth
  - **Regions/Cities:** Using GeoNames API (requires free account)
  - **Fallback:** Nominatim for geocoding
- 📚 Updated documentation with new provider setup instructions

### Fixed
- 🔧 GeoDB Cities API SSL certificate error - replaced with REST Countries API
- 🔧 GeoDB redirect/HTML response error - migrated to reliable alternatives
- 🧪 Test compatibility with Spring Boot 4.x (fixed JSON formatting assertions)

### Added
- 📚 Complete API Reference documentation (API_REFERENCE.md)
- 📦 Postman collection for easy API testing
- 🛠️ Troubleshooting section in README with GeoNames setup guide
- ⚡ Better error messages when GeoNames username is not configured

## [1.0.0] - 2026-02-01

### Added
- ✨ Complete natal chart calculation engine
- 🔮 Accurate planet positions using Astronomy Engine (VSOP87)
- 📐 Ascendant (ASC) and Midheaven (MC) calculation
- 🏠 Multiple house systems:
  - PLACIDUS with automatic high-latitude fallback
  - WHOLE SIGN (traditional Hellenistic system)
  - EQUAL houses
  - KOCH system
- ⭐ Major aspects calculation:
  - Conjunction (0°)
  - Sextile (60°)
  - Square (90°)
  - Trine (120°)
  - Opposition (180°)
- 🌍 Multi-language support:
  - English (en)
  - Turkish (tr)
  - Extensible translation system
- 📍 Location services:
  - Hierarchical location data (Country → Region → City → District)
  - Multiple providers: GeoDB Cities, GeoNames, Nominatim
  - Automatic provider fallback
  - Geocoding and reverse geocoding
- 💾 Intelligent caching with Caffeine:
  - Countries/Regions/Cities: 7 days TTL
  - Place details: 30 days TTL
  - Geocode results: 30 days TTL
- ⚠️ Warning system for edge cases:
  - High latitude fallback warnings
  - Solver failure warnings
- ✅ Input validation using Jakarta Bean Validation
- 🚦 Proper HTTP status codes (200, 400, 422, 503)
- 📝 Comprehensive API documentation
- 🧪 Unit tests for core calculations

### Technical Details
- **Zodiac System**: TROPICAL only (by design)
- **Celestial Bodies**: Sun, Moon, Mercury, Venus, Mars, Jupiter, Saturn, Uranus, Neptune, Pluto
- **Aspect Orbs**: 8° for Sun/Moon, 6° for other planets
- **Architecture**: Clean separation of concerns (API, Service, Calculation layers)
- **Performance**: Non-blocking HTTP clients using WebFlux

### Known Limitations
- `isApplying` field returns `null` (planet speeds not calculated)
- SIDEREAL zodiac not supported
- Minor aspects not included

## [Unreleased]

### Planned for v2.0
- [ ] Planet speeds & applying/separating aspect detection
- [ ] Minor aspects (Semisextile, Semisquare, Quincunx, etc.)
- [ ] Additional celestial points:
  - North Node / South Node
  - Chiron
  - Part of Fortune
- [ ] More house systems (Campanus, Regiomontanus, Porphyry)
- [ ] Arabic Parts calculation
- [ ] Transits & Progressions
- [ ] Additional languages (Spanish, German, French)
- [ ] WebSocket support for real-time calculations
- [ ] GraphQL API
- [ ] Batch calculation endpoint

---

## Version History

### [1.0.0] - 2026-02-01
**Initial Release** - Production-ready astrology backend API

---

## Migration Guides

### Upgrading to 1.0.0
This is the initial release. No migration needed.

---

## Release Notes

### v1.0.0 - "Stellar Launch" 🌟

We're excited to announce the first production-ready release of NatalCard!

**Highlights:**
- 🎯 100% accurate WHOLE_SIGN house system implementation
- 🌍 Full Turkish localization alongside English
- 🏠 Four house systems with intelligent fallbacks
- 📍 Comprehensive location services with caching
- ⚡ High-performance calculations
- 📚 Complete documentation

**What's Working:**
- All core astrology calculations verified ✅
- Multi-language support fully functional ✅
- Location services with provider fallback ✅
- Caching optimized for production ✅
- Warning system for edge cases ✅

**What's Next:**
See [Unreleased](#unreleased) section for v2.0 roadmap.

**Breaking Changes:**
None (initial release)

**Deprecations:**
None

**Security:**
- No known vulnerabilities
- Input validation on all endpoints
- Rate limiting for external APIs

**Credits:**
Special thanks to:
- Astronomy Engine for precise calculations
- GeoDB Cities API for location data
- Spring Boot team for the amazing framework

---

## Support

For questions, bug reports, or feature requests, please:
- 📧 Open an issue on [GitHub](https://github.com/YOUR_USERNAME/NatalCard/issues)
- 💬 Start a discussion in [Discussions](https://github.com/YOUR_USERNAME/NatalCard/discussions)
- 📖 Check the [README](README.md) for documentation

---

**[View all releases](https://github.com/YOUR_USERNAME/NatalCard/releases)**
