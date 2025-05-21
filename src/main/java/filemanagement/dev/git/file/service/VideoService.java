package filemanagement.dev.git.file.service;

import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Format;
import com.github.kokorin.jaffree.ffprobe.Stream;
import filemanagement.dev.git.file.config.FFmpegConfig;
import filemanagement.dev.git.file.dto.VideoDto;
import filemanagement.dev.git.file.mapper.VideoMapper;
import filemanagement.dev.git.file.model.Video;
import filemanagement.dev.git.file.repository.VideoRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
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

    private static final String VIDEO_CODEC_TYPE = "video";

    private final FFmpegConfig fFmpegConfig;

    private static final List<String> DEFAULT_QUALITY_OPTIONS = List.of("720p", "1080p");

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


    public void AnalyzeVideo(String fileName, File file) {

        //Indica o caminho do video que está sendo carregado no S3 e passa para o Path
        Path videoPath = Path.of(file.getAbsolutePath());

        // Cria um objeto FFprobe para obter informações sobre o vídeo
        // Aqui você pode usar o FFprobe para obter informações sobre o vídeo
        // Exemplo: obter informações sobre o formato do vídeo
        //FFprobe ffprobe = FFprobe.atPath(null).setInput(videoPath.toString());
        // O execute retorna um objeto Format com informações sobre o vídeo

        verificarFFprobe(fFmpegConfig.ffprobeExecutablePath());

        FFprobeResult result = FFprobe.atPath(Paths.get(fFmpegConfig.ffprobeExecutablePath())).setInput(videoPath.toString()).setShowFormat(true)
                .setShowStreams(true).execute();

        Format format = result.getFormat();

        if (format == null) {
            log.error("Formato do vídeo não pôde ser analisado. FFprobe não retornou dados de formato.");
            throw new RuntimeException("Formato do vídeo não pôde ser analisado.");
        }


        // Aqui você pode acessar as informações do formato do vídeo
        // Exemplo: obter a duração do vídeo
        log.info("Duração do vídeo: {}", format.getDuration());
        //Tamanho do vídeo
        log.info("Tamanho do vídeo: {}", format.getSize());
        //Formato do vídeo
        log.info("Formato do vídeo: {}", format.getFormatName());
        //Quantidade de bites por segundo
        log.info("Bitrate do vídeo: {}", format.getBitRate());

        for(Stream stream: result.getStreams()){
            //Codec do vídeo(audio ou video)
            //stream.getCodecType();

            if (VIDEO_CODEC_TYPE.equals(stream.getCodecType())) {
                Video video = createVideoFromStream(stream, format, fileName, file, 1);
                Video video1 = saveVideo(video);

                log.info("Video: {}", video1);

            }

        }

    }
    // Método auxiliar para criar um objeto Video a partir de um Stream
    private Video createVideoFromStream(Stream stream, Format format, String fileName,
                                  File file, Integer ownerId) {
        Video video = new Video();
        video.setDuration(convertToDuration(stream.getDuration()));
        video.setSize(convertToFloat(format.getSize()));
        video.setTitle(fileName);
        video.setFilepath(file.getAbsolutePath());
        video.setOwnerId(ownerId);
        video.setQualityOptions(DEFAULT_QUALITY_OPTIONS);
        return video;
    }



    // Método auxiliar para converter o valor de duração para um objeto Duration
    private Duration convertToDuration(Object duration) {
        return Duration.ofMinutes(Long.parseLong(String.valueOf(duration)));
    }



    // Método auxiliar para converter o valor de tamanho para um Float
    private Float convertToFloat(Object size) {
        return Float.parseFloat(String.valueOf(size));
    }

    private void verificarFFprobe(String ffprobePath) {
        File ffprobe = new File(ffprobePath);
        if (!ffprobe.exists() || !ffprobe.canExecute()) {
            throw new RuntimeException("FFprobe não encontrado ou não executável em: " + ffprobePath);
        }
        else{
            log.info("FFprobe encontrado em: {}", ffprobePath);
        }

    }


}