package jp.co.axa.apidemo.service;

import com.sun.tools.javac.util.List;
import jp.co.axa.apidemo.entities.AppUser;
import jp.co.axa.apidemo.services.impl.AppUserAuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class AppUserAuthenticationServiceImplTest {

    @Spy
    AppUserAuthenticationServiceImpl appUserAuthenticationServiceImpl;

    @Mock
    OAuth2Authentication authentication;

    @Test
    public void validate_userRole() {
        //given
        given(authentication.isAuthenticated()).willReturn(true);
        Set<String> scope = new HashSet<>();
        scope.add("READ");
        OAuth2Request request = new OAuth2Request(null, null, null, true, scope, null, null, null, null);
        User userPrincipal = new User("user", "", true, true, true, true, List.of(new SimpleGrantedAuthority(AppUser.Role.USER.name())));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of(new SimpleGrantedAuthority(AppUser.Role.USER.name())));
        authentication = new OAuth2Authentication(request, authenticationToken);
        given(appUserAuthenticationServiceImpl.getUserAuthenticatedRole(authentication)).willReturn(AppUser.Role.USER);

        //when
        boolean result = appUserAuthenticationServiceImpl.validateUserRole(authentication);


        //then
        assertThat(result).isNotNull();
        assertThat(result).isTrue();
    }
}
