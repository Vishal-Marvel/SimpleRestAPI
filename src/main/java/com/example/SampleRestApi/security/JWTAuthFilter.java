package com.example.SampleRestApi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTTokenProvider jwtTokenProvider;
    private final SecurityService securityService;

    public JWTAuthFilter(JWTTokenProvider jwtTokenProvider, SecurityService securityService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        String username;
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            username = jwtTokenProvider.getUsername(token);
            UserDetails userDetails = securityService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);

    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = null;
        if (bearerToken!=null && bearerToken.startsWith("Bearer")){
            token = bearerToken.substring(7);
        }
        return token;
    }
}
