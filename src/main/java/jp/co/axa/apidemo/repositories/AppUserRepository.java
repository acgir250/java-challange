package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 *  This class perform the database manipualtion/operation for the application user.
 *
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String username);

    boolean existsByEmail(String username);
}
