package com.natalcard.natalcard.config;

import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration - DISABLED
 *
 * Caching is disabled to simplify the application.
 * All calculations are done fresh on each request.
 */
@Configuration
// @EnableCaching - DISABLED for simplicity
public class CacheConfig {
    // All cache beans removed
}
