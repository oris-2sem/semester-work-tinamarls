package ru.itis.delivery.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.delivery.models.FileInfo;

import java.io.InputStream;
import java.nio.file.Path;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long size;
    private String fileName;
    private String mimeType;
    private String originalFileName;
    private InputStream fileStream;
    private Path path;

    public static FileDto from(FileInfo fileInfo) {
        return FileDto.builder()
                .size(fileInfo.getSize())
                .originalFileName(fileInfo.getOriginalFileName())
                .mimeType(fileInfo.getMimeType())
                .fileName(fileInfo.getStorageFileName())
                .build();
    }

}
