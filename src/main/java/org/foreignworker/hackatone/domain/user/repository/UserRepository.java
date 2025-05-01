package org.foreignworker.hackatone.domain.user.repository;

import org.foreignworker.hackatone.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
