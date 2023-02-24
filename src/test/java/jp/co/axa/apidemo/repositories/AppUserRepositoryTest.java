package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.AppUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AppUserRepositoryTest {

    private final AppUser STUB_USER = AppUser.builder().email("test@gmail.com").password("test").role(AppUser.Role.USER).build();
    @Autowired
    AppUserRepository appUserRepository;

    @BeforeEach
    void setUp() {
        appUserRepository.save(STUB_USER);
    }

    @AfterEach
    void setDown() {
        appUserRepository.delete(STUB_USER);
    }

    @Test
    public void should_return_User_findByEmail() {
        //given


        //when
        Optional<AppUser> user = appUserRepository.findByEmail("test@gmail.com");

        //then
        assertThat(user).isNotEmpty();
        assertThat(user.get().getRole()).isEqualTo(AppUser.Role.USER);
    }

    @Test
    public void should_return_True_existsByEmail() {
        //given

        //when
        boolean result = appUserRepository.existsByEmail("test@gmail.com");

        //then
        assertThat(result).isNotNull();
        assertThat(result).isTrue();
    }
}
