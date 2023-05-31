package ru.itis.delivery.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.delivery.dto.file.FileDto;
import ru.itis.delivery.models.FileInfo;

public interface FileService {

    FileInfo saveFile(MultipartFile file);

    FileInfo updateFile(String storageFileName, MultipartFile file);

    FileDto getFileByName(String fileName);
}
