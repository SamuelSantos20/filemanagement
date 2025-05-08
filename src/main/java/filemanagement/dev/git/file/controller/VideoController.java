package filemanagement.dev.git.file.controller;

import filemanagement.dev.git.file.dto.VideoDto;
import filemanagement.dev.git.file.mapper.VideoMapper;
import filemanagement.dev.git.file.model.Video;
import filemanagement.dev.git.file.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operações relacionadas a vídeos.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/videos")
@Slf4j
public class VideoController {

    private final VideoService videoService;

    private final VideoMapper videoMapper;

    /**
     * Faz upload de um novo vídeo.
     *
     * @param 'video' dados do vídeo a ser enviado
     * @return ResponseEntity com mensagem de confirmação
     */
    @PostMapping
    public ResponseEntity<VideoDto> uploadVideo(@RequestBody @Validated VideoDto videoDto) {
        // Validação explícita do filepath
        if (videoDto.filepath() == null || videoDto.filepath().trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio");
        }

        // Log para debug
        log.debug("Recebendo VideoDto com filepath: {}", videoDto.filepath());

        Video videoEntity = videoMapper.toEntity(videoDto);

        // Verificação adicional após o mapeamento
        if (videoEntity.getFilepath() == null) {
            throw new IllegalStateException("FilePath foi perdido durante o mapeamento");
        }

        VideoDto dto = videoMapper.toDto(videoService.saveVideo(videoEntity));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Recupera um vídeo específico pelo ID.
     *
     * @param videoId ID do vídeo a ser recuperado
     * @return ResponseEntity contendo o vídeo encontrado
     */
    @GetMapping("/{videoId}")
    public ResponseEntity<VideoDto> getVideo(@PathVariable int videoId) {
        VideoDto dto = videoMapper.toDto(videoService.getVideo(videoId).get());
        Optional<VideoDto> videoDto = Optional.of(dto);

        return videoDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{videoId}")
    public ResponseEntity<VideoDto> updateVideo(@PathVariable(value = "videoId" ) Integer id, @RequestBody @Validated VideoDto videoDto) {

        VideoDto dto = videoMapper.toDto(videoService.upadeVideo(id, videoDto));

        return ResponseEntity.ok(dto);

    }

    /**
     * Lista todos os vídeos disponíveis.
     *
     * @return ResponseEntity com a lista de vídeos
     */
    @GetMapping
    public ResponseEntity<List<VideoDto>> getAllVideos() {
        List<VideoDto> list = videoService.getAllVideos().stream().map(videoMapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

    /**
     * Remove um vídeo pelo ID.
     *
     * @param videoId ID do vídeo a ser removido
     * @return ResponseEntity com status da operação
     */
    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable int videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.noContent().build();
    }

}


