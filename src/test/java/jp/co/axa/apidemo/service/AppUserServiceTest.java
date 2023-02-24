package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.AppUser;
import jp.co.axa.apidemo.repositories.AppUserRepository;
import jp.co.axa.apidemo.services.impl.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @InjectMocks
    AppUserService appUserService;

    @Test
    public void should_return_appUser_when_loadByUserName() {
        //given
        given(appUserRepository.findByEmail(any())).willReturn(Optional.of(AppUser.builder().email("test@gmailcom").role(AppUser.Role.USER).password("123").build()));
        //when
        UserDetails result = appUserService.loadUserByUsername("test@gmailcom");

        //then
        verify(appUserRepository, times(1)).findByEmail(any());
    }


}
