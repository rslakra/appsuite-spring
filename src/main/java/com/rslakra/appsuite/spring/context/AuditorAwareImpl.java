package com.rslakra.appsuite.spring.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.AuditorAware;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Rohtash Lakra
 * @created 8/4/21 5:45 PM
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    // ANONYMOUS
    private static final Optional<String> ANONYMOUS = Optional.of("anonymousUser");
    private static final Optional<String> SYSTEM = Optional.of("system");
    private static final Optional<String> GUEST = Optional.of("guest");
    private static final List<Optional<String>> ANONYMOUS_USERS = Arrays.asList(ANONYMOUS, SYSTEM, GUEST);

   /**
    * Returns the <code>Authentication</code> object.
    *
    * @return
    */
   public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
   }

   /**
    * @return
    */
   public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null ? authentication.isAuthenticated() : false;
   }

   /**
    * @param authentication
    * @return
    */
   public static boolean isAnonymousUser(Authentication authentication) {
        return ANONYMOUS.equals(Optional.of(authentication.getPrincipal().toString()));
   }

   /**
    * @param authentication
    * @return
    */
   public static boolean isSystemUser(Authentication authentication) {
        return SYSTEM.equals(Optional.of(authentication.getPrincipal().toString()));
   }

   /**
    * @param authentication
    * @return
    */
   public static boolean isGuestUser(Authentication authentication) {
        return GUEST.equals(Optional.of(authentication.getPrincipal().toString()));
   }
   

    /**
     * TODO: Use Spring Security to return currently logged in user
     * <code>return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()</code>
     *
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = AuditorAwareImpl.getAuthentication();
        if (!AuditorAwareImpl.isAuthenticated(authentication) 
                || AuditorAwareImpl.isAnonymousUser(authentication)) {
            return SYSTEM;
        }
        
        return Optional.of(authentication.getName());
    }
}
