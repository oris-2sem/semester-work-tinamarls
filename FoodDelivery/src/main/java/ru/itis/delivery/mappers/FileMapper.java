package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.file.FileDto;
import ru.itis.delivery.models.FileInfo;

@Mapper
public interface FileMapper {
    FileDto toDto(FileInfo fileInfo);
}
