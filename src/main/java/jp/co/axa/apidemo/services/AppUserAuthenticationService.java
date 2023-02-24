/**
 * This interface is for Application User Authentication
 */
package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.AppUser;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.validation.constraints.NotNull;

public interface AppUserAuthenticationService {

     boolean validateUserRole(OAuth2Authentication oAuth2Authentication);

    AppUser.Role getUserAuthenticatedRole(OAuth2Authentication oAuth2Authentication) ;

    boolean isAuthenticateUser(@NotNull OAuth2Authentication oAuth2Authentication);
    boolean validateAdminUser(OAuth2Authentication oAuth2Authentication);
}
