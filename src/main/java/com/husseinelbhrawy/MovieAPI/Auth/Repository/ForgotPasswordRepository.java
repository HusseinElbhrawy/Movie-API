package com.husseinelbhrawy.MovieAPI.Auth.Repository;

import com.husseinelbhrawy.MovieAPI.Auth.Entity.ForgotPassword;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword , Integer> {

    @Query("select fp from ForgotPassword fp where fp.otp = ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(int otp , User user);

    @Transactional
    @Modifying
    @Query("update ForgotPassword fb set fb.isUsed = true where fb.id = ?1")
    void  updateIsUsed(int id);


}
