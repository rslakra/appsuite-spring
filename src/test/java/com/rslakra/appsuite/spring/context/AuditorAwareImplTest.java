package com.rslakra.appsuite.spring.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 11/28/24 8:00 PM
 */
@ExtendWith(SpringExtension.class)
public class AuditorAwareImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditorAwareImplTest.class);

    private AuditorAwareImpl auditorAware;
    private SecurityContext securityContext;
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        auditorAware = new AuditorAwareImpl();
        securityContext = Mockito.mock(SecurityContext.class);
        authentication = Mockito.mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void contextLoads() {
    }

    /**
     * Tests getAuthentication returns the authentication from SecurityContext.
     */
    @Test
    public void testGetAuthentication() {
        LOGGER.debug("+testGetAuthentication()");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Authentication result = AuditorAwareImpl.getAuthentication();
        assertEquals(authentication, result);
        LOGGER.debug("-testGetAuthentication()");
    }

    /**
     * Tests isAuthenticated returns true when authentication is valid.
     */
    @Test
    public void testIsAuthenticatedWithValidAuthentication() {
        LOGGER.debug("+testIsAuthenticatedWithValidAuthentication()");
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        assertTrue(AuditorAwareImpl.isAuthenticated(authentication));
        LOGGER.debug("-testIsAuthenticatedWithValidAuthentication()");
    }

    /**
     * Tests isAuthenticated returns false when authentication is null.
     */
    @Test
    public void testIsAuthenticatedWithNullAuthentication() {
        LOGGER.debug("+testIsAuthenticatedWithNullAuthentication()");
        assertFalse(AuditorAwareImpl.isAuthenticated(null));
        LOGGER.debug("-testIsAuthenticatedWithNullAuthentication()");
    }

    /**
     * Tests isAuthenticated returns false when not authenticated.
     */
    @Test
    public void testIsAuthenticatedReturnsFalse() {
        LOGGER.debug("+testIsAuthenticatedReturnsFalse()");
        Mockito.when(authentication.isAuthenticated()).thenReturn(false);
        assertFalse(AuditorAwareImpl.isAuthenticated(authentication));
        LOGGER.debug("-testIsAuthenticatedReturnsFalse()");
    }

    /**
     * Tests isAnonymousUser returns true for anonymous user.
     */
    @Test
    public void testIsAnonymousUserReturnsTrue() {
        LOGGER.debug("+testIsAnonymousUserReturnsTrue()");
        Mockito.when(authentication.getPrincipal()).thenReturn("anonymousUser");
        assertTrue(AuditorAwareImpl.isAnonymousUser(authentication));
        LOGGER.debug("-testIsAnonymousUserReturnsTrue()");
    }

    /**
     * Tests isAnonymousUser returns false for regular user.
     */
    @Test
    public void testIsAnonymousUserReturnsFalse() {
        LOGGER.debug("+testIsAnonymousUserReturnsFalse()");
        Mockito.when(authentication.getPrincipal()).thenReturn("regularUser");
        assertFalse(AuditorAwareImpl.isAnonymousUser(authentication));
        LOGGER.debug("-testIsAnonymousUserReturnsFalse()");
    }

    /**
     * Tests isSystemUser returns true for system user.
     */
    @Test
    public void testIsSystemUserReturnsTrue() {
        LOGGER.debug("+testIsSystemUserReturnsTrue()");
        Mockito.when(authentication.getPrincipal()).thenReturn("system");
        assertTrue(AuditorAwareImpl.isSystemUser(authentication));
        LOGGER.debug("-testIsSystemUserReturnsTrue()");
    }

    /**
     * Tests isSystemUser returns false for regular user.
     */
    @Test
    public void testIsSystemUserReturnsFalse() {
        LOGGER.debug("+testIsSystemUserReturnsFalse()");
        Mockito.when(authentication.getPrincipal()).thenReturn("regularUser");
        assertFalse(AuditorAwareImpl.isSystemUser(authentication));
        LOGGER.debug("-testIsSystemUserReturnsFalse()");
    }

    /**
     * Tests isGuestUser returns true for guest user.
     */
    @Test
    public void testIsGuestUserReturnsTrue() {
        LOGGER.debug("+testIsGuestUserReturnsTrue()");
        Mockito.when(authentication.getPrincipal()).thenReturn("guest");
        assertTrue(AuditorAwareImpl.isGuestUser(authentication));
        LOGGER.debug("-testIsGuestUserReturnsTrue()");
    }

    /**
     * Tests isGuestUser returns false for regular user.
     */
    @Test
    public void testIsGuestUserReturnsFalse() {
        LOGGER.debug("+testIsGuestUserReturnsFalse()");
        Mockito.when(authentication.getPrincipal()).thenReturn("regularUser");
        assertFalse(AuditorAwareImpl.isGuestUser(authentication));
        LOGGER.debug("-testIsGuestUserReturnsFalse()");
    }

    /**
     * Tests getCurrentAuditor returns "system" when not authenticated.
     */
    @Test
    public void testGetCurrentAuditorReturnsSystemWhenNotAuthenticated() {
        LOGGER.debug("+testGetCurrentAuditorReturnsSystemWhenNotAuthenticated()");
        Mockito.when(securityContext.getAuthentication()).thenReturn(null);
        Optional<String> result = auditorAware.getCurrentAuditor();
        assertTrue(result.isPresent());
        assertEquals("system", result.get());
        LOGGER.debug("-testGetCurrentAuditorReturnsSystemWhenNotAuthenticated()");
    }

    /**
     * Tests getCurrentAuditor returns "system" for anonymous user.
     */
    @Test
    public void testGetCurrentAuditorReturnsSystemForAnonymousUser() {
        LOGGER.debug("+testGetCurrentAuditorReturnsSystemForAnonymousUser()");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getPrincipal()).thenReturn("anonymousUser");
        Optional<String> result = auditorAware.getCurrentAuditor();
        assertTrue(result.isPresent());
        assertEquals("system", result.get());
        LOGGER.debug("-testGetCurrentAuditorReturnsSystemForAnonymousUser()");
    }

    /**
     * Tests getCurrentAuditor returns username for authenticated user.
     */
    @Test
    public void testGetCurrentAuditorReturnsUsernameForAuthenticatedUser() {
        LOGGER.debug("+testGetCurrentAuditorReturnsUsernameForAuthenticatedUser()");
        String username = "testUser";
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.isAuthenticated()).thenReturn(true);
        Mockito.when(authentication.getPrincipal()).thenReturn(username);
        Mockito.when(authentication.getName()).thenReturn(username);
        Optional<String> result = auditorAware.getCurrentAuditor();
        assertTrue(result.isPresent());
        assertEquals(username, result.get());
        LOGGER.debug("-testGetCurrentAuditorReturnsUsernameForAuthenticatedUser()");
    }

}

