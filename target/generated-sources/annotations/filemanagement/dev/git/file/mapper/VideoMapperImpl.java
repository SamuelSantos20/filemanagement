package filemanagement.dev.git.file.mapper;

import filemanagement.dev.git.file.dto.VideoDto;
import filemanagement.dev.git.file.model.Video;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T19:22:55-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class VideoMapperImpl implements VideoMapper {

    @Override
    public Video toEntity(VideoDto videoDto) {
        if ( videoDto == null ) {
            return null;
        }

        Video video = new Video();

        video.setFilepath( videoDto.filepath() );
        video.setTitle( videoDto.title() );
        video.setSize( videoDto.size() );
        video.setDuration( videoDto.duration() );
        video.setOwnerId( videoDto.ownerId() );
        List<String> list = videoDto.qualityOptions();
        if ( list != null ) {
            video.setQualityOptions( new ArrayList<String>( list ) );
        }
        video.setUploadDate( videoDto.uploadDate() );

        return video;
    }

    @Override
    public VideoDto toDto(Video video) {
        if ( video == null ) {
            return null;
        }

        String filepath = null;
        String title = null;
        Float size = null;
        Duration duration = null;
        Integer ownerId = null;
        LocalDateTime uploadDate = null;
        List<String> qualityOptions = null;

        filepath = video.getFilepath();
        title = video.getTitle();
        size = video.getSize();
        duration = video.getDuration();
        ownerId = video.getOwnerId();
        uploadDate = video.getUploadDate();
        List<String> list = video.getQualityOptions();
        if ( list != null ) {
            qualityOptions = new ArrayList<String>( list );
        }

        VideoDto videoDto = new VideoDto( title, filepath, size, duration, ownerId, uploadDate, qualityOptions );

        return videoDto;
    }

    @Override
    public void updateEntityFromDTO(VideoDto dto, Video entity) {
        if ( dto == null ) {
            return;
        }

        entity.setFilepath( dto.filepath() );
        entity.setTitle( dto.title() );
        entity.setSize( dto.size() );
        entity.setDuration( dto.duration() );
        entity.setUploadDate( dto.uploadDate() );
        entity.setOwnerId( dto.ownerId() );
        if ( entity.getQualityOptions() != null ) {
            List<String> list = dto.qualityOptions();
            if ( list != null ) {
                entity.getQualityOptions().clear();
                entity.getQualityOptions().addAll( list );
            }
            else {
                entity.setQualityOptions( null );
            }
        }
        else {
            List<String> list = dto.qualityOptions();
            if ( list != null ) {
                entity.setQualityOptions( new ArrayList<String>( list ) );
            }
        }
    }
}
