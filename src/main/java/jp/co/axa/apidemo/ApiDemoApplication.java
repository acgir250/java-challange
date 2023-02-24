package jp.co.axa.apidemo;

import jp.co.axa.apidemo.entities.AppUser;
import jp.co.axa.apidemo.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableCaching
public class ApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDemoApplication.class, args);
    }

    /**
     * Method is used to load all the basic user/ default user to access the application.
     * @param appUserRepository
     * @param encoder
     * @return
     */
    @Bean   /// Default Loading of the admin and user
    CommandLineRunner runner(AppUserRepository appUserRepository, PasswordEncoder encoder) {
        return app -> {
            AppUser user = new AppUser();
            user.setEmail("test@gmail.com");
            user.setRole(AppUser.Role.ADMIN);
            user.setPassword(encoder.encode("pass"));
            appUserRepository.save(user);

            user = new AppUser();
            user.setEmail("test2@gmail.com");
            user.setPassword(encoder.encode("123"));
            user.setRole(AppUser.Role.USER);
            appUserRepository.save(user);
        };
    }
}
