package uk.abhishek.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.abhishek.moneymanager.entity.ProfileEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {
    Optional<ProfileEntity> findByEmail(String email);
    Optional<ProfileEntity> findByActivationToken(String activation_token);
}
