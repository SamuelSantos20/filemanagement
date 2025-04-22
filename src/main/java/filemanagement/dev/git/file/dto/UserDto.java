package filemanagement.dev.git.file.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String passwordHash
) {
}
