package com.husseinelbhrawy.MovieAPI.Auth.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users" ,uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "username"),
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name" , nullable = false  , unique = true)
    @NotBlank(message = "Please Provide User's Name..")
    private String name;

    @Column(name = "email" , nullable = false  , unique = true)
    @NotBlank(message = "Please Provide User's Email..")
    private String email;

    @Column(name = "password" , nullable = false)
    @NotBlank(message = "Please Provide User's Password..")
    @Size(min = 6 , message = "Password should be at least 6 characters")
    private String password;

    @Column(name = "username" , nullable = false , unique = true)
    @NotBlank(message = "Please Provide User's Username..")
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role" , joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
