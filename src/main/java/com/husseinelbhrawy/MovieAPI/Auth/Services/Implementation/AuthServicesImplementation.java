package com.husseinelbhrawy.MovieAPI.Auth.Services.Implementation;

import com.husseinelbhrawy.MovieAPI.Auth.DTO.AuthResponse;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.LoginRequest;
import com.husseinelbhrawy.MovieAPI.Auth.DTO.RegisterRequest;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.Roles;
import com.husseinelbhrawy.MovieAPI.Auth.Repository.RolesRepository;
import com.husseinelbhrawy.MovieAPI.Auth.Entity.User;
import com.husseinelbhrawy.MovieAPI.Auth.Repository.UserRepository;
import com.husseinelbhrawy.MovieAPI.Auth.Security.JWTTokenProvider;
import com.husseinelbhrawy.MovieAPI.Auth.Security.RefreshTokenServices;
import com.husseinelbhrawy.MovieAPI.Auth.Services.Base.AuthService;
import com.husseinelbhrawy.MovieAPI.Exceptions.MovieAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServicesImplementation implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private  final RefreshTokenServices refreshTokenServices;
    private  final RolesRepository rolesRepository;
    private  final JWTTokenProvider jwtTokenProvider;
    private  final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication =  authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user  = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + loginRequest.getUsername()));


        var accessToken = jwtTokenProvider.generateToken(user);
        var refreshToken = refreshTokenServices.createRefreshToken(loginRequest.getUsername());


        return  AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles((Roles) user.getRoles().toArray()[0])
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        //! add check if username exists in database
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException( "Username is already taken");
        }

        //! add check if email exists in database
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException( "Email is already taken");
        }

        Set<Roles> roles = new HashSet<>();

        Roles userRole = rolesRepository.findByName("USER").orElseThrow(() -> new MovieAPIException("Role not found"));


        roles.add(userRole);

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
//        Authentication authentication =  authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);


        //! Save to database
        User savedUser =  userRepository.save(user);
        var accessToken = jwtTokenProvider.generateToken(savedUser);
        var refreshToken = refreshTokenServices.createRefreshToken(savedUser.getUsername());

        return  AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .name(savedUser.getName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .roles((Roles) savedUser.getRoles().toArray()[0])
                .build();
    }
}
