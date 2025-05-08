package filemanagement.dev.git.file.service;


import filemanagement.dev.git.file.Config.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.utils.IoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {

    private final S3Client s3Client;

    private final S3Properties s3Properties;

    // == methods ==
    /**
     * This method upload a file to your amazon s3 bucket.
     *
     * @param file the file
     * @return A {@code String} message
     */
               /// Tradução
    /**
     * Este método faz o upload de um arquivo para o seu bucket Amazon S3.
     *
     * @param file o arquivo
     * @return Uma mensagem do tipo {@code String}
     */
    public String uploadFile(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        log.info("Bucket configurado: {}", s3Properties.getBucketName());

        log.info("Nome do arquivo que será enviado ao bucket: {}", fileName);


        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(fileName) // uniquely identifies the object in an Amazon S3 bucket.
                .build();

        log.info("Endpoint usado pelo S3Client: {}", s3Properties.getEndpoint());


        this.s3Client.putObject(putObjectRequest, RequestBody.fromFile(fileObj));
        // Once the file has been uploaded from amazon s3 we need to delete it from our computer
        fileObj.delete();

        return "Your file " + fileName + " was successfully uploaded.";
    }

    /**
     * This method download a file from your amazon s3 bucket.
     *
     * @param fileName the filename
     * @return A {@code byte[]} file
     */
             /// Tradução
    /**
     * Este método faz o download de um arquivo do seu bucket Amazon S3.
     *
     * @param fileName o nome do arquivo
     * @return Um arquivo do tipo {@code byte[]}
     */
    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(fileName) // uniquely identifies the object in an Amazon S3 bucket.
                .build();

        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(getObjectRequest);

        try {
            byte[] content = IoUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method delete a file from your amazon s3 bucket.
     *
     * @param fileName the filename
     * @return A {@code String} message
     */
    public String deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(s3Properties.getBucketName())
                .key(fileName) // uniquely identifies the object in an Amazon S3 bucket.
                .build();

        s3Client.deleteObject(deleteObjectRequest);

        return "Your file " + fileName + " was successfully deleted.";
    }

    /**
     * A utility for converting multipart files into files.
     *
     * @param file the multipart file to be converted
     * @return A {@code File}
     */
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}

