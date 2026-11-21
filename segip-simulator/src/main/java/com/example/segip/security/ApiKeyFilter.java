
package com.example.segip.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
@Profile("cloud")
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String apiKey;

    public ApiKeyFilter(
            @Value("${SEGIP_API_KEY}") String apiKey
    ) {
        if (apiKey.isBlank()) {
            throw new IllegalStateException(
                    "SEGIP_API_KEY no puede estar vacía"
            );
        }

        this.apiKey = apiKey;
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {
        return !(
                request.getMethod().equals("POST")
                        && request.getServletPath().equals(
                        "/api/v1/identidades/verificar"
                )
        );
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String receivedKey = request.getHeader("X-API-Key");

        boolean authorized = receivedKey != null
                && MessageDigest.isEqual(
                apiKey.getBytes(StandardCharsets.UTF_8),
                receivedKey.getBytes(StandardCharsets.UTF_8)
        );

        if (!authorized) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "API Key inválida o ausente"
            );
            return;
        }

        filterChain.doFilter(request, response);
    }
}
