package com.karthi.securityiitest.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // ── Step 1: Get the Authorization header ──
        final String authorizationHeader = request.getHeader("Authorization");

        // If no header or doesn't start with "Bearer " → skip this filter
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ── Step 2: Extract token from header ──
        // "Bearer eyJhbGci..." → "eyJhbGci..."
        String token = authorizationHeader.substring(7);

        // ── Step 3: Extract username from token ──
        final String username = jwtService.extractUsername(token);

        // ── Step 4: If username found AND user not already authenticated ──
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // ── Step 5: Load user from DB ──
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // ── Step 6: Validate token ──
            if(jwtService.isTokenValid(token, userDetails)) {
                // ── Step 7: Create authentication object ──
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // attach request details (IP, session etc)
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // ── Step 8: Set authentication in SecurityContext ──
                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);   // user is now authenticated
            }
        }

        // ── Step 9: Pass to next filter ──
        filterChain.doFilter(request, response);


    }
}
