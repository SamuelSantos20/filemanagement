package filemanagement.dev.git.file.mapper;

import filemanagement.dev.git.file.dto.VideoDto;
import filemanagement.dev.git.file.model.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    @Mapping(source = "filepath", target = "filepath")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "duration", target = "duration")
    @Mapping(source = "ownerId", target = "ownerId")
    @Mapping(source = "qualityOptions", target = "qualityOptions")
    Video toEntity(VideoDto videoDto);

    @Mapping(source = "filepath", target = "filepath")
    VideoDto toDto(Video video);

    @Mapping(source = "filepath", target = "filepath")
    void updateEntityFromDTO(VideoDto dto, @MappingTarget Video entity);
}