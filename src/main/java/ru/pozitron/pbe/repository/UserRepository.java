package ru.pozitron.pbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pozitron.pbe.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByActivationCode(String code);
    User findByUsernameLike(String username);
}
