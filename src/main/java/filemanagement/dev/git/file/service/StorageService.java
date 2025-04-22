package filemanagement.dev.git.file.service;

import jakarta.persistence.Table;
import lombok.*;

@Table(name = "storageservice")
public class StorageService {

    private String serviceName;

    private String bucketName;

    private String apiKey;


}
