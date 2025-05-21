package filemanagement.dev.git.file.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FFmpegConfig {
    // Define o caminho do executável do FFmpeg
    @Value("${ffmpeg.path}")
    private String ffprobePath;

    @Bean
    public String ffprobeExecutablePath () {
        return ffprobePath;
    }

    @PostConstruct
    public void init() {
        log.info("FFmpeg path: {}", ffprobePath);
    }

}
