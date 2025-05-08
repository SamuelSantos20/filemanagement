package filemanagement.dev.git.file.service;

import filemanagement.dev.git.file.dto.VideoDto;
import filemanagement.dev.git.file.mapper.VideoMapper;
import filemanagement.dev.git.file.model.Video;
import filemanagement.dev.git.file.repository.VideoRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Slf4j
public class VideoService {

    private final VideoRepository videoRepository;

    private final VideoMapper videoMapper;

    private final EntityManager entityManager;


// Add your service methods here
    // For example, you can add methods to handle video-related operations
    // such as uploading, deleting, and retrieving videos.

    //method to save a video
    public Video saveVideo(Video video) {
        log.info("Salvando vídeo: {}", video);
        if (video.getFilepath() == null) {
            log.error("FilePath está nulo!");
            throw new IllegalArgumentException("FilePath não pode ser nulo");
        }

        Video  video1 = mergeVideo(videoRepository.save(video));


        return video1;
    }

    public Video upadeVideo(Integer videoId, VideoDto videoDto) {
        Video video = videoRepository.findById(videoId).orElse(null);


        videoMapper.updateEntityFromDTO(videoDto, video);

        if (video != null) {
            return videoRepository.save(video);
        }
        return null;
    }

    //method to delete a video
    public void deleteVideo(int videoId) {
        // Implement the logic to delete a video by its ID
        // delete the video from the database
        videoRepository.deleteById(videoId);
    }

    //method to retrieve a video
    @Transactional(readOnly = true)
    public Optional<Video> getVideo(int videoId) {
        // Implement the logic to retrieve a video by its ID
        return videoRepository.findById(videoId);
    }

    //method to retrieve all videos
    @Transactional(readOnly = true)
    public List<Video> getAllVideos() {
        // Implement the logic to retrieve all videos
        return videoRepository.findAll();
    }

    @Transactional
    public Video mergeVideo(Video video) {
        log.info("Realizando merge do vídeo: {}", video);
        try {
            Video mergedVideo = entityManager.merge(video);
            entityManager.flush();
            log.info("Merge realizado com sucesso para o vídeo ID: {}", mergedVideo.getVideoId());
            return mergedVideo;
        } catch (Exception e) {
            log.error("Erro ao realizar merge do vídeo: {}", e.getMessage());
            throw new RuntimeException("Erro ao realizar merge do vídeo", e);
        }
    }


}