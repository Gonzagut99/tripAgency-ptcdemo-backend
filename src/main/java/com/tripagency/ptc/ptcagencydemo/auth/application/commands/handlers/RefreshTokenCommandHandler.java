package com.tripagency.ptc.ptcagencydemo.auth.application.commands.handlers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tripagency.ptc.ptcagencydemo.auth.application.commands.RefreshTokenCommand;
import com.tripagency.ptc.ptcagencydemo.auth.application.services.CustomUserDetailsService;
import com.tripagency.ptc.ptcagencydemo.auth.application.services.JwtService;
import com.tripagency.ptc.ptcagencydemo.auth.domain.entities.DAuthenticatedUser;
import com.tripagency.ptc.ptcagencydemo.auth.domain.entities.DSession;
import com.tripagency.ptc.ptcagencydemo.auth.domain.repositories.ISessionRepository;
import com.tripagency.ptc.ptcagencydemo.auth.presentation.dto.AuthResponseDto;
import com.tripagency.ptc.ptcagencydemo.general.utils.exceptions.HtpExceptionUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for refresh token command.
 * Creates new access and refresh tokens from a valid refresh token.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenCommandHandler {

    private final JwtService jwtService;
    private final ISessionRepository sessionRepository;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public AuthResponseDto execute(RefreshTokenCommand command) {
        try {
            log.info("Processing refresh token request");

            // Validate refresh token
            if (!jwtService.validateToken(command.refreshToken())) {
                throw new IllegalArgumentException("Refresh token inválido");
            }

            if (!jwtService.isRefreshToken(command.refreshToken())) {
                throw new IllegalArgumentException("El token proporcionado no es un refresh token");
            }

            // Find existing session by refresh token
            DSession existingSession = sessionRepository.findByRefreshToken(command.refreshToken())
                    .orElseThrow(() -> new IllegalArgumentException("Sesión no encontrada"));

            // Verify session is valid for refresh
            if (!existingSession.canRefresh()) {
                throw new IllegalArgumentException("La sesión ha expirado o ha sido revocada. Por favor, inicie sesión nuevamente.");
            }

            // Get user details
            Long userId = jwtService.extractUserId(command.refreshToken());
            DAuthenticatedUser authenticatedUser = (DAuthenticatedUser) userDetailsService.loadUserById(userId);

            // Generate new tokens
            String newAccessToken = jwtService.generateAccessToken(authenticatedUser);
            String newRefreshToken = jwtService.generateRefreshToken(authenticatedUser);

            // Calculate new expiration times
            LocalDateTime accessTokenExpiresAt = LocalDateTime.now()
                    .plusSeconds(jwtService.getAccessTokenExpiration() / 1000);
            LocalDateTime refreshTokenExpiresAt = LocalDateTime.now()
                    .plusSeconds(jwtService.getRefreshTokenExpiration() / 1000);

            // Update session with new tokens (token rotation)
            existingSession.setToken(newAccessToken);
            existingSession.setRefreshToken(newRefreshToken);
            existingSession.setExpiresAt(accessTokenExpiresAt);
            existingSession.setRefreshExpiresAt(refreshTokenExpiresAt);
            existingSession.setUserAgent(Optional.ofNullable(command.userAgent()));
            existingSession.setIpAddress(Optional.ofNullable(command.ipAddress()));

            sessionRepository.update(existingSession);

            log.info("Token refreshed successfully for user ID: {}", userId);

            // Build response
            return AuthResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .tokenType("Bearer")
                    .expiresIn(jwtService.getAccessTokenExpiration() / 1000)
                    .refreshExpiresIn(jwtService.getRefreshTokenExpiration() / 1000)
                    .expiresAt(accessTokenExpiresAt)
                    .user(AuthResponseDto.UserInfoDto.builder()
                            .id(authenticatedUser.getUserId())
                            .email(authenticatedUser.getEmail())
                            .userName(authenticatedUser.getUserName().orElse(null))
                            .isActive(authenticatedUser.getUser().getIsActive())
                            .build())
                    .build();

        } catch (Exception e) {
            log.error("Token refresh failed: {}", e.getMessage());
            throw HtpExceptionUtils.processHttpException(e);
        }
    }
}
