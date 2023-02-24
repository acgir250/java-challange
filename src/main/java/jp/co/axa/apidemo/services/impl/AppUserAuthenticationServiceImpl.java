package jp.co.axa.apidemo.services.impl;

import jp.co.axa.apidemo.entities.AppUser;
import jp.co.axa.apidemo.services.AppUserAuthenticationService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 *
 * This class is user to validate the user is authenticated before proceeding to operate endpoints.
 * Mainly purpose is used to validateRole,Authentication,getRole etc....
 */
@Service
public class AppUserAuthenticationServiceImpl implements AppUserAuthenticationService {

    public boolean validateUserRole(OAuth2Authentication oAuth2Authentication) {
        return getUserAuthenticatedRole(oAuth2Authentication).equals(AppUser.Role.USER);
    }

    public AppUser.Role getUserAuthenticatedRole(OAuth2Authentication oAuth2Authentication) {
        if (isAuthenticateUser(oAuth2Authentication)) {
            return AppUser.Role.valueOf(oAuth2Authentication.getUserAuthentication().getAuthorities().iterator().next().getAuthority());
        } else {
            throw new OAuth2Exception("User is not Authenticated");
        }
    }

    public boolean isAuthenticateUser(@NotNull OAuth2Authentication oAuth2Authentication) {
        return oAuth2Authentication.isAuthenticated();
    }

    public boolean validateAdminUser(OAuth2Authentication oAuth2Authentication) {
        return getUserAuthenticatedRole(oAuth2Authentication).equals(AppUser.Role.ADMIN);
    }
}
