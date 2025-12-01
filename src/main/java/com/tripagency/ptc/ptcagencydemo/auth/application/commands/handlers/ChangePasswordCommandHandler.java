package com.tripagency.ptc.ptcagencydemo.auth.application.commands.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.tripagency.ptc.ptcagencydemo.auth.application.commands.ChangePasswordCommand;
import com.tripagency.ptc.ptcagencydemo.auth.presentation.dto.ChangePasswordRequestDto;
import com.tripagency.ptc.ptcagencydemo.auth.presentation.dto.ChangePasswordResponseDto;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.entities.User;
import com.tripagency.ptc.ptcagencydemo.users.infrastructure.repositories.interfaces.IUserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for ChangePasswordCommand.
 * Validates and changes the user's password.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChangePasswordCommandHandler {

    private final IUserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ChangePasswordResponseDto execute(ChangePasswordCommand command) {
        Long userId = command.getUserId();
        ChangePasswordRequestDto request = command.getRequest();

        log.info("🔑 [CHANGE_PASSWORD] Processing password change for user ID: {}", userId);

        // Validate that new password matches confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            log.warn("🔑 [CHANGE_PASSWORD] Password confirmation mismatch for user ID: {}", userId);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "La nueva contraseña y la confirmación no coinciden"
            );
        }

        // Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("🔑 [CHANGE_PASSWORD] User not found with ID: {}", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
                });

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            log.warn("🔑 [CHANGE_PASSWORD] Invalid current password for user ID: {}", userId);
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, 
                "La contraseña actual es incorrecta"
            );
        }

        // Check that new password is different from current
        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            log.warn("🔑 [CHANGE_PASSWORD] New password same as current for user ID: {}", userId);
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "La nueva contraseña debe ser diferente a la actual"
            );
        }

        // Update password
        String newPasswordHash = passwordEncoder.encode(request.getNewPassword());
        user.setPasswordHash(newPasswordHash);
        userRepository.save(user);

        log.info("🔑 [CHANGE_PASSWORD] Password changed successfully for user ID: {}", userId);

        return ChangePasswordResponseDto.builder()
                .success(true)
                .message("Contraseña actualizada exitosamente")
                .build();
    }
}
