/**
 *
 * This class is for the application service to provide information regarding the user
 * mainly purpose for user information from the database.
 */
package jp.co.axa.apidemo.services.impl;
import com.sun.tools.javac.util.List;
import jp.co.axa.apidemo.entities.AppUser;
import jp.co.axa.apidemo.repositories.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Service;


@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(username).orElseThrow(() -> new OAuth2Exception("User not found:::" + username));
        return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().name())));
    }

    private GrantedAuthority getAuthorities(AppUser appuser) {
        return new SimpleGrantedAuthority(appuser.getRole().name());
    }

}
