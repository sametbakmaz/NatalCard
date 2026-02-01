package com.natalcard.natalcard.i18n;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Astrology term translations (Turkish/English)
 */
@Component
public class AstroTranslations {

    // Zodiac signs translations
    private static final Map<String, Map<String, String>> SIGN_TRANSLATIONS = new HashMap<>();

    // Planet names translations
    private static final Map<String, Map<String, String>> PLANET_TRANSLATIONS = new HashMap<>();

    // Aspect types translations
    private static final Map<String, Map<String, String>> ASPECT_TRANSLATIONS = new HashMap<>();

    // House system translations
    private static final Map<String, Map<String, String>> HOUSE_SYSTEM_TRANSLATIONS = new HashMap<>();

    // Warning translations
    private static final Map<String, Map<String, String>> WARNING_TRANSLATIONS = new HashMap<>();

    static {
        // ZODIAC SIGNS
        initSignTranslations();

        // PLANETS
        initPlanetTranslations();

        // ASPECTS
        initAspectTranslations();

        // HOUSE SYSTEMS
        initHouseSystemTranslations();

        // WARNINGS
        initWarningTranslations();
    }

    private static void initSignTranslations() {
        addTranslation(SIGN_TRANSLATIONS, "ARIES", "en", "Aries");
        addTranslation(SIGN_TRANSLATIONS, "ARIES", "tr", "Koç");

        addTranslation(SIGN_TRANSLATIONS, "TAURUS", "en", "Taurus");
        addTranslation(SIGN_TRANSLATIONS, "TAURUS", "tr", "Boğa");

        addTranslation(SIGN_TRANSLATIONS, "GEMINI", "en", "Gemini");
        addTranslation(SIGN_TRANSLATIONS, "GEMINI", "tr", "İkizler");

        addTranslation(SIGN_TRANSLATIONS, "CANCER", "en", "Cancer");
        addTranslation(SIGN_TRANSLATIONS, "CANCER", "tr", "Yengeç");

        addTranslation(SIGN_TRANSLATIONS, "LEO", "en", "Leo");
        addTranslation(SIGN_TRANSLATIONS, "LEO", "tr", "Aslan");

        addTranslation(SIGN_TRANSLATIONS, "VIRGO", "en", "Virgo");
        addTranslation(SIGN_TRANSLATIONS, "VIRGO", "tr", "Başak");

        addTranslation(SIGN_TRANSLATIONS, "LIBRA", "en", "Libra");
        addTranslation(SIGN_TRANSLATIONS, "LIBRA", "tr", "Terazi");

        addTranslation(SIGN_TRANSLATIONS, "SCORPIO", "en", "Scorpio");
        addTranslation(SIGN_TRANSLATIONS, "SCORPIO", "tr", "Akrep");

        addTranslation(SIGN_TRANSLATIONS, "SAGITTARIUS", "en", "Sagittarius");
        addTranslation(SIGN_TRANSLATIONS, "SAGITTARIUS", "tr", "Yay");

        addTranslation(SIGN_TRANSLATIONS, "CAPRICORN", "en", "Capricorn");
        addTranslation(SIGN_TRANSLATIONS, "CAPRICORN", "tr", "Oğlak");

        addTranslation(SIGN_TRANSLATIONS, "AQUARIUS", "en", "Aquarius");
        addTranslation(SIGN_TRANSLATIONS, "AQUARIUS", "tr", "Kova");

        addTranslation(SIGN_TRANSLATIONS, "PISCES", "en", "Pisces");
        addTranslation(SIGN_TRANSLATIONS, "PISCES", "tr", "Balık");
    }

    private static void initPlanetTranslations() {
        addTranslation(PLANET_TRANSLATIONS, "SUN", "en", "Sun");
        addTranslation(PLANET_TRANSLATIONS, "SUN", "tr", "Güneş");

        addTranslation(PLANET_TRANSLATIONS, "MOON", "en", "Moon");
        addTranslation(PLANET_TRANSLATIONS, "MOON", "tr", "Ay");

        addTranslation(PLANET_TRANSLATIONS, "MERCURY", "en", "Mercury");
        addTranslation(PLANET_TRANSLATIONS, "MERCURY", "tr", "Merkür");

        addTranslation(PLANET_TRANSLATIONS, "VENUS", "en", "Venus");
        addTranslation(PLANET_TRANSLATIONS, "VENUS", "tr", "Venüs");

        addTranslation(PLANET_TRANSLATIONS, "MARS", "en", "Mars");
        addTranslation(PLANET_TRANSLATIONS, "MARS", "tr", "Mars");

        addTranslation(PLANET_TRANSLATIONS, "JUPITER", "en", "Jupiter");
        addTranslation(PLANET_TRANSLATIONS, "JUPITER", "tr", "Jüpiter");

        addTranslation(PLANET_TRANSLATIONS, "SATURN", "en", "Saturn");
        addTranslation(PLANET_TRANSLATIONS, "SATURN", "tr", "Satürn");

        addTranslation(PLANET_TRANSLATIONS, "URANUS", "en", "Uranus");
        addTranslation(PLANET_TRANSLATIONS, "URANUS", "tr", "Uranüs");

        addTranslation(PLANET_TRANSLATIONS, "NEPTUNE", "en", "Neptune");
        addTranslation(PLANET_TRANSLATIONS, "NEPTUNE", "tr", "Neptün");

        addTranslation(PLANET_TRANSLATIONS, "PLUTO", "en", "Pluto");
        addTranslation(PLANET_TRANSLATIONS, "PLUTO", "tr", "Plüton");
    }

    private static void initAspectTranslations() {
        addTranslation(ASPECT_TRANSLATIONS, "CONJUNCTION", "en", "Conjunction");
        addTranslation(ASPECT_TRANSLATIONS, "CONJUNCTION", "tr", "Kavuşum");

        addTranslation(ASPECT_TRANSLATIONS, "SEXTILE", "en", "Sextile");
        addTranslation(ASPECT_TRANSLATIONS, "SEXTILE", "tr", "Sekstil");

        addTranslation(ASPECT_TRANSLATIONS, "SQUARE", "en", "Square");
        addTranslation(ASPECT_TRANSLATIONS, "SQUARE", "tr", "Kare");

        addTranslation(ASPECT_TRANSLATIONS, "TRINE", "en", "Trine");
        addTranslation(ASPECT_TRANSLATIONS, "TRINE", "tr", "Trigon");

        addTranslation(ASPECT_TRANSLATIONS, "OPPOSITION", "en", "Opposition");
        addTranslation(ASPECT_TRANSLATIONS, "OPPOSITION", "tr", "Karşıt");
    }

    private static void initHouseSystemTranslations() {
        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "PLACIDUS", "en", "Placidus");
        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "PLACIDUS", "tr", "Placidus");

        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "WHOLE_SIGN", "en", "Whole Sign");
        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "WHOLE_SIGN", "tr", "Tam Burç");

        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "EQUAL", "en", "Equal");
        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "EQUAL", "tr", "Eşit");

        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "KOCH", "en", "Koch");
        addTranslation(HOUSE_SYSTEM_TRANSLATIONS, "KOCH", "tr", "Koch");
    }

    private static void initWarningTranslations() {
        addTranslation(WARNING_TRANSLATIONS, "PLACIDUS_FALLBACK_EQUAL_HIGH_LAT", "en",
            "Placidus not reliable at high latitudes, using Equal houses");
        addTranslation(WARNING_TRANSLATIONS, "PLACIDUS_FALLBACK_EQUAL_HIGH_LAT", "tr",
            "Placidus yüksek enlemlerde güvenilir değil, Eşit ev sistemi kullanıldı");

        addTranslation(WARNING_TRANSLATIONS, "PLACIDUS_SOLVER_FAILED_FALLBACK_EQUAL", "en",
            "Placidus calculation failed, using Equal houses");
        addTranslation(WARNING_TRANSLATIONS, "PLACIDUS_SOLVER_FAILED_FALLBACK_EQUAL", "tr",
            "Placidus hesaplama başarısız, Eşit ev sistemi kullanıldı");
    }

    private static void addTranslation(Map<String, Map<String, String>> map,
                                      String key, String lang, String value) {
        map.computeIfAbsent(key, k -> new HashMap<>()).put(lang, value);
    }

    // Public translation methods

    public String translateSign(String sign, String language) {
        return translate(SIGN_TRANSLATIONS, sign, language);
    }

    public String translatePlanet(String planet, String language) {
        return translate(PLANET_TRANSLATIONS, planet, language);
    }

    public String translateAspect(String aspect, String language) {
        return translate(ASPECT_TRANSLATIONS, aspect, language);
    }

    public String translateHouseSystem(String houseSystem, String language) {
        return translate(HOUSE_SYSTEM_TRANSLATIONS, houseSystem, language);
    }

    public String translateWarning(String warning, String language) {
        return translate(WARNING_TRANSLATIONS, warning, language);
    }

    private String translate(Map<String, Map<String, String>> translations,
                            String key, String language) {
        if (key == null) return key;

        Map<String, String> langMap = translations.get(key);
        if (langMap == null) return key; // Return original if not found

        String translated = langMap.get(language);
        return translated != null ? translated : langMap.get("en"); // Fallback to English
    }
}
