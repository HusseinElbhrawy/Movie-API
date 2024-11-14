package com.husseinelbhrawy.MovieAPI.Auth.Security;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private  final  JWTTokenProvider jwtTokenProvider;
    private  final UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        //! Get JWT Token from http request
        String token = getTokenFromRequest(request);

        //! Validate Token
        if (StringUtil.notNullNorEmpty(token) && jwtTokenProvider.validateToken(token)) {
            //! Get username from token
            String username = jwtTokenProvider.getUsername(token);

            //! Load the user associated with token
            var userDetails = this.userDetailsService.loadUserByUsername(username);

            //! Create Auth Token
            var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //! Set the authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private  String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtil.notNullNorEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
