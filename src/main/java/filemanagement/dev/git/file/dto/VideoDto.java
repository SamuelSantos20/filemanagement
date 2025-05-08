package filemanagement.dev.git.file.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public record VideoDto(
        @NotBlank(message = "O título não pode estar em branco")
        String title,
        @NotBlank(message = "O caminho do arquivo não pode estar em branco")
        String filepath,  // Garanta que este campo está sendo preenchido
        @NotNull(message = "O tamanho não pode ser nulo")
        Float size,
        @NotNull(message = "A duração não pode ser nula")
        Duration duration,
        @NotNull(message = "O ID do proprietário não pode ser nulo")
        Integer ownerId,
        LocalDateTime uploadDate,
        @NotEmpty(message = "As opções de qualidade não podem estar vazias")
        List<String> qualityOptions
) {}
