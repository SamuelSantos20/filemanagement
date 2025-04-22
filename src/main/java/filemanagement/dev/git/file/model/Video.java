package filemanagement.dev.git.file.model;

import jakarta.persistence.*;
import lombok.*;
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
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer videoId;

    private String title;

    private String filePath;

    private Float size;

    private Duration duration;

    @CreatedDate
    private LocalDateTime uploadDate;

    private Integer ownerId;

    private List<String> qualityOptions;

}
