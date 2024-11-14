package com.husseinelbhrawy.MovieAPI.Auth.Repository;

import com.husseinelbhrawy.MovieAPI.Auth.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username , String email);
    Optional<User> findByName(String name);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User u set u.password = ?1 where u.email = ?2")
    void  updatePassword(String password, String email);

}
