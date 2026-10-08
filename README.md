# Combined Security Experiment 2.3.1
JWT + Spring Security + JWT filter + RBAC + @PreAuthorize + AES + BCrypt + access/refresh tokens + H2.
Run: mvn clean spring-boot:run
Auth endpoints use /auth/** (NOT /api/auth/**).
Important fix: RefreshTokenService.create() is @Transactional, so login no longer fails on deleteByUsername.
