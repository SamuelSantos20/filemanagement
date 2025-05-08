package filemanagement.dev.git.file.model;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "video")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Slf4j
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;

    private String title;

    private String filepath;

    private Float size;

    private Duration duration;

    @CreatedDate
    private LocalDateTime uploadDate;

    private Integer ownerId;

    private List<String> qualityOptions;

    @PostConstruct
    public void postLoad() {
        log.info("Video Id: {}", videoId);
        log.info("Video Title: {}", title);
        log.info("Video File Path: {}", filepath);
        log.info("Video Size: {}", size);
        log.info("Video Duration: {}", duration);
        log.info("Video Upload Date: {}", uploadDate);
        log.info("Video Owner Id: {}", ownerId);
        log.info("Video Quality Options: {}", qualityOptions);

    }



}