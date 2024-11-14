package com.husseinelbhrawy.MovieAPI.Auth.Controller;

import com.husseinelbhrawy.MovieAPI.Auth.DTO.ChangePassword;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.MailBody;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.OTPRequest;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.ForgotPassword;
import com.husseinelbhrawy.MovieAPI.Auth.Repository.ForgotPasswordRepository;
import com.husseinelbhrawy.MovieAPI.Auth.Repository.UserRepository;
import com.husseinelbhrawy.MovieAPI.Auth.Services.Implementation.EmailServices;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private  final UserRepository userRepository;
    private  final EmailServices emailServices;
    private  final ForgotPasswordRepository forgotPasswordRepository;
    private  final PasswordEncoder passwordEncoder;


    @PostMapping("/verifyMail")
    public ResponseEntity<Map<String ,String>> verifyMail(@RequestPart("email") String email) throws MessagingException {
        System.out.println("Email Sent is " + email);
        var user =  userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
        var otp = otpGenerator();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .body("This is the OTP for your Forgot Password : " + otp)
                .subject("OTP for Forgot password request" )
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000)) //? 70 seconds
                .user(user)
                .build();

        emailServices.sendSimpleEmail(mailBody);
        forgotPasswordRepository.save(forgotPassword);

        Map<String, String> response = new HashMap<>();
        response.put("message", "We have sent a code to your email address: " + email);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyOtp")
    public  ResponseEntity<Map<String , String>> verifyOtp(@RequestBody OTPRequest otpRequest){
        Map<String, String> response = new HashMap<>();

        var user =  userRepository.findByEmail(otpRequest.email()).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + otpRequest.email()  ));

        var forgotPassword = forgotPasswordRepository.findByOtpAndUser(otpRequest.otp()  , user).orElseThrow(() -> new UsernameNotFoundException("OTP You have entered is invalid : " + otpRequest.otp()));


        if(forgotPassword. getExpirationTime().before(Date.from(java.time.Instant.now()))){
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            response.put("message" , "OTP has expired !");
            return new ResponseEntity<>(response ,  HttpStatus.EXPECTATION_FAILED );
        }else if (forgotPassword.isUsed()){
            response.put("message" , "OTP is already used");
            return new ResponseEntity<>(response ,  HttpStatus.EXPECTATION_FAILED );
        }else{
            response.put("message" , "OTP verified");
            forgotPasswordRepository.updateIsUsed(forgotPassword.getId());
            return ResponseEntity.ok(response);
        }


    }

    @PostMapping("/changePassword")
    public  ResponseEntity<Map<String , String>> changePassword (@Valid @RequestBody ChangePassword changePasswordRecord){

        Map<String , String> response = new HashMap<>();

        if(!Objects.equals(changePasswordRecord.password() , changePasswordRecord.confirmPassword())){
            response.put("message" , "Please Enter same password");
            return  new ResponseEntity<>(response , HttpStatus.EXPECTATION_FAILED );
        }

        String newPassword = passwordEncoder.encode(changePasswordRecord.password());

        userRepository.updatePassword(newPassword , changePasswordRecord.email());
        response.put("message" , "Password has been changed successfully");
        return ResponseEntity.ok(response);


    }

    private  int otpGenerator(){
        return  (100000 + (int) (Math.random() * 999999));
    }


}
