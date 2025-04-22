package filemanagement.dev.git.file.mapper;


import filemanagement.dev.git.file.dto.UserDto;
import filemanagement.dev.git.file.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserDto dto);

    UserDto toDTO(User entity);

    void updateEntityFromDTO(UserDto dto, @MappingTarget User entity);
}
