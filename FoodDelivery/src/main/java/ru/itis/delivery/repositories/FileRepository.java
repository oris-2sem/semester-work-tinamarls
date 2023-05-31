package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.delivery.models.FileInfo;

public interface FileRepository extends JpaRepository<FileInfo, Long> {
    FileInfo findByStorageFileName(String fileName);
}
