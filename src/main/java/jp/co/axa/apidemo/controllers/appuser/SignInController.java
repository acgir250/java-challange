package jp.co.axa.apidemo.controllers.appuser;

import jp.co.axa.apidemo.entities.AppUser;
import jp.co.axa.apidemo.repositories.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static jp.co.axa.apidemo.constants.ApiConstant.USER_BASE_URL;
import static jp.co.axa.apidemo.constants.ApiConstant.VALIDATE_EMAIL;

/**
 *  This is the application user Sign up controller to use this application.
 */
@RestController
@Validated
@Slf4j
@RequestMapping(USER_BASE_URL)
public class SignInController {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;


    public SignInController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *  This endpoint is for the user to signup using email and password parameters.
     *  This will provide a default user role for signup user.
     * @param email
     * @param password
     * @return AppUser
     */
    @PostMapping
    AppUser signin(@RequestParam String email, @RequestParam String password) {
        return appUserRepository.save(new AppUser(null, email, passwordEncoder.encode(password), AppUser.Role.USER));
    }

    /**
     * This endpoint is use to validate the email is existed in the system or not.
     * @param email
     * @return boolean
     */
    @PostMapping(VALIDATE_EMAIL)
    Boolean emailExists(@RequestParam String email) {
        return appUserRepository.existsByEmail(email);
    }
}
