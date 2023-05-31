package ru.itis.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_file_name")
    private String originalFileName;
    @Column(name = "storage_file_name")
    private String storageFileName;
    private Long size;
    @Column(name = "mime_type")
    private String mimeType;

    @OneToOne(mappedBy = "fileInfo")
    private Product product;
}

