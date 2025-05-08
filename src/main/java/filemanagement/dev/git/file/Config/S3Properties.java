package filemanagement.dev.git.file.Config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "amazon.aws.credentials")
@Data
@Slf4j
public class S3Properties {
    private String accountId;
    private String accessKey;
    private String secretKey;
    private String endpoint;
    private String bucketName;
    private String region;


    @PostConstruct
    public void logProperties() {
        log.info("S3 Properties loaded:");
        log.info("Account ID: {}", accountId);
        log.info("Access Key: {}", accessKey != null ? "***" : "null");
        log.info("Secret Key: {}", secretKey != null ? "***" : "null");
        log.info("Endpoint: {}", endpoint);
        log.info("Bucket Name: {}", bucketName);
    }
}