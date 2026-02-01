# Security Policy

## Supported Versions

Currently supported versions with security updates:

| Version | Supported          |
| ------- | ------------------ |
| 1.0.x   | :white_check_mark: |

## Reporting a Vulnerability

We take the security of NatalCard seriously. If you believe you have found a security vulnerability, please report it to us as described below.

### 🔒 Please Do NOT:
- Open a public GitHub issue for security vulnerabilities
- Publicly disclose the vulnerability before it has been addressed

### ✅ Please DO:
1. **Email us** at: security@natalcard.example.com (replace with your actual email)
2. Include as much information as possible:
   - Type of vulnerability
   - Steps to reproduce
   - Affected versions
   - Potential impact
   - Suggested fixes (if any)

### 📅 Response Timeline
- **Initial Response**: Within 48 hours
- **Status Update**: Within 7 days
- **Fix Timeline**: Depends on severity
  - Critical: Within 7 days
  - High: Within 14 days
  - Medium: Within 30 days
  - Low: Next release cycle

## Security Best Practices

### For Users

1. **Keep Dependencies Updated**
   ```bash
   mvn versions:display-dependency-updates
   mvn versions:display-plugin-updates
   ```

2. **Environment Variables**
   - Never commit `.env` files
   - Use secure storage for API keys
   - Rotate credentials regularly

3. **API Security**
   - Use HTTPS in production
   - Implement rate limiting
   - Validate all input
   - Use authentication/authorization if exposed publicly

4. **Monitoring**
   - Monitor for unusual activity
   - Set up logging
   - Review access logs regularly

### For Contributors

1. **Code Review**
   - All PRs require review
   - Security-sensitive changes require maintainer approval

2. **Dependencies**
   - Only use well-maintained dependencies
   - Check for known vulnerabilities before adding dependencies
   - Keep dependencies up to date

3. **Input Validation**
   - Validate all user input
   - Use parameterized queries
   - Sanitize output

4. **Error Handling**
   - Don't expose stack traces to users
   - Log errors securely
   - Use generic error messages for users

## Known Security Considerations

### API Rate Limiting
The application implements rate limiting for external API calls:
- **Nominatim**: Max 1 request per second (OSM policy)
- **GeoDB/GeoNames**: Cached for 7-30 days to minimize requests

### Input Validation
All endpoints use Jakarta Bean Validation:
- Date/time formats validated
- Timezone IDs validated
- Latitude/Longitude ranges checked (-90 to 90, -180 to 180)

### Caching
- Cached data has appropriate TTL
- No sensitive user data cached
- Cache keys are properly sanitized

### External Dependencies
Regular security audits using:
```bash
mvn dependency-check:check
mvn versions:display-dependency-updates
```

## Security Updates

Security updates will be released as:
- **Patch versions** (1.0.x) for backwards-compatible fixes
- **Minor versions** (1.x.0) if new security features are needed
- **Major versions** (x.0.0) only if breaking changes are required

Updates will be announced via:
- GitHub Security Advisories
- Release notes
- CHANGELOG.md

## Disclosure Policy

We follow responsible disclosure:
1. Security issue reported privately
2. Issue confirmed and fix developed
3. Fix released as security update
4. Public disclosure after users have time to update (typically 30 days)
5. Credit given to reporter (if desired)

## Security Hall of Fame

We appreciate security researchers who help keep NatalCard secure:

<!-- List will be populated as vulnerabilities are responsibly disclosed -->
*No vulnerabilities reported yet*

## Contact

For security concerns, contact:
- **Email**: security@natalcard.example.com
- **PGP Key**: [Link to public key if available]

For general questions:
- **GitHub Issues**: [Issues page](https://github.com/YOUR_USERNAME/NatalCard/issues)
- **GitHub Discussions**: [Discussions page](https://github.com/YOUR_USERNAME/NatalCard/discussions)

---

**Last Updated**: 2026-02-01
