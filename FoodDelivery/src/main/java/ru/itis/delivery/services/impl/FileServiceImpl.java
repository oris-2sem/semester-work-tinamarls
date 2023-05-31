package ru.itis.delivery.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.delivery.dto.file.FileDto;
import ru.itis.delivery.mappers.FileMapper;
import ru.itis.delivery.models.FileInfo;
import ru.itis.delivery.repositories.FileRepository;
import ru.itis.delivery.services.FileService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${upload.directory}")
    private String storagePath;

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    @Override
    public FileInfo saveFile(MultipartFile file) {

        String originalFileName = file.getOriginalFilename();
        String storageFileName = UUID.randomUUID() + "_" + originalFileName;

        FileInfo fileInfo = FileInfo.builder()
                .size(file.getSize())
                .mimeType(file.getContentType())
                .originalFileName(originalFileName)
                .storageFileName(storageFileName)
                .build();

        Path filePath = Path.of(storagePath, storageFileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Неудачная попытка сохранения файла", e);
            throw new IllegalArgumentException(e);
        }

        return fileRepository.save(fileInfo);

    }

    @Override
    public FileInfo updateFile(String storageFileName, MultipartFile file) {

        FileInfo fileForDelete = fileRepository.findByStorageFileName(storageFileName);
        fileRepository.delete(fileForDelete);

        FileInfo fileInfo = FileInfo.builder()
                .size(file.getSize())
                .mimeType(file.getContentType())
                .originalFileName(file.getOriginalFilename())
                .storageFileName(storageFileName)
                .build();

        Path filePath = Path.of(storagePath, fileInfo.getStorageFileName());

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Неудачная попытка изменения файла по имени файла", e);
            throw new RuntimeException(e);
        }

        return fileRepository.save(fileInfo);

    }

    @Override
    public FileDto getFileByName(String fileName) {

        FileInfo fileInfo = fileRepository.findByStorageFileName(fileName);

        FileDto fileDto = fileMapper.toDto(fileInfo);
        fileDto.setPath(Paths.get(storagePath + "\\" + fileInfo.getStorageFileName()));

        return fileDto;
    }
}
