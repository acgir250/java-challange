package jp.co.axa.apidemo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *  This is the application user entity class with fields id,email,password and role.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "The email must not be empty")
    @Email
    private String email;
    @JsonIgnore
    @NotEmpty(message = "The password must not be empty")
    @Size(min=4,message = "The password must contain more or equal to four characters")
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {USER, ADMIN, USER_MANAGER}

}


