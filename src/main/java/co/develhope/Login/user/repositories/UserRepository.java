package co.develhope.Login.user.repositories;

import co.develhope.Login.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findGetByActivationCode(String activationCode);

    User findByPasswordResetCode (String passwordResetCode);
}

