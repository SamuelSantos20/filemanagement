package filemanagement.dev.git.file.controllerTest;

import filemanagement.dev.git.file.controller.VideoController;
import filemanagement.dev.git.file.dto.VideoDto;
import filemanagement.dev.git.file.mapper.VideoMapper;
import filemanagement.dev.git.file.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VideoControllerTest {

    @Mock
    private VideoService videoService;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoController videoController;

    private Video testeVideo;
    private VideoDto testeVideoDto;

    @BeforeEach
    void setUp() {
        // Configurar o vídeo de teste
        testeVideo = new Video();
        testeVideo.setVideoId(1);
        testeVideo.setTitle("Vídeo de Teste");
        testeVideo.setFilepath("/videos/teste.mp4");
        testeVideo.setSize(1024.0f);
        testeVideo.setDuration(Duration.ofMinutes(5));
        testeVideo.setUploadDate(LocalDateTime.now());
        testeVideo.setOwnerId(1);
        testeVideo.setQualityOptions(List.of("720p", "1080p"));

        // Configurar o DTO de teste
        testeVideoDto = new VideoDto(
                "Vídeo de Teste",
                "/videos/teste.mp4",
                1024.0f,
                Duration.ofMinutes(5),
                1,
                LocalDateTime.now(),
                List.of("720p", "1080p")
        );

        // Configurar comportamento padrão do mapper
        when(videoMapper.toDto(any(Video.class))).thenReturn(testeVideoDto);
        when(videoMapper.toEntity(any(VideoDto.class))).thenReturn(testeVideo);
    }

    @Test
    void uploadVideo_Success() {
        when(videoService.saveVideo(any(Video.class))).thenReturn(testeVideo);

        ResponseEntity<VideoDto> response = videoController.uploadVideo(testeVideoDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(videoService, times(1)).saveVideo(any(Video.class));
    }

    @Test
    void getVideo_Success() {
        when(videoService.getVideo(1)).thenReturn(Optional.of(testeVideo));

        ResponseEntity<VideoDto> response = videoController.getVideo(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(videoService, times(1)).getVideo(1);
    }

    @Test
    void getVideo_NotFound() {
        when(videoService.getVideo(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> videoController.getVideo(1));
        verify(videoService, times(1)).getVideo(1);
    }

    @Test
    void getAllVideos_Success() {
        when(videoService.getAllVideos()).thenReturn(List.of(testeVideo));

        ResponseEntity<List<VideoDto>> response = videoController.getAllVideos();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(videoService, times(1)).getAllVideos();
    }

    @Test
    void deleteVideo_Success() {
        doNothing().when(videoService).deleteVideo(1);

        ResponseEntity<Void> response = videoController.deleteVideo(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(videoService, times(1)).deleteVideo(1);
    }

    @Test
    void uploadVideo_Error() {
        when(videoService.saveVideo(any(Video.class))).thenThrow(new RuntimeException("Erro ao salvar vídeo"));

        assertThrows(RuntimeException.class, () -> videoController.uploadVideo(testeVideoDto));
        verify(videoService, times(1)).saveVideo(any(Video.class));
    }

    @Test
    void deleteVideo_Error() {
        doThrow(new RuntimeException("Erro ao deletar vídeo")).when(videoService).deleteVideo(1);

        assertThrows(RuntimeException.class, () -> videoController.deleteVideo(1));
        verify(videoService, times(1)).deleteVideo(1);
    }
}
