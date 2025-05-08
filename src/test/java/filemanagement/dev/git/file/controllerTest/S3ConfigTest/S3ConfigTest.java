package filemanagement.dev.git.file.S3ConfigTest;

import filemanagement.dev.git.file.config.S3ClientConfig;
import filemanagement.dev.git.file.config.S3Properties;
import filemanagement.dev.git.file.service.S3CompatibleClient;

import java.time.Duration;


/**
 * This class demonstrates how to configure and use the S3 client for Cloudflare R2.
 * It includes methods to list buckets, list objects in a bucket, and generate a pre-signed upload URL.
 */
public class S3ConfigTest {

    public static void main(String[] args) {
        S3ClientConfig config = new S3ClientConfig();

        S3Properties s3Properties = new S3Properties();

        // Set the necessary configuration properties
        S3CompatibleClient r2Client = new S3CompatibleClient(config.buildS3Client(s3Properties), config.buildS3Presigner(s3Properties));

        // List buckets
        System.out.println("Available buckets:");
        r2Client.listBuckets().forEach(bucket ->
                System.out.println("* " + bucket.name())
        );

        // List objects in a specific bucket
        String bucketName = "demos";
        System.out.println("\nObjects in bucket '" + bucketName + "':");
        r2Client.listObjects(bucketName).forEach(object ->
                System.out.printf("* %s (size: %d bytes, modified: %s)%n",
                        object.key(),
                        object.size(),
                        object.lastModified())
        );

        // config the client as before

        // Generate a pre-signed upload URL valid for 15 minutes
        String uploadUrl = r2Client.generatePresignedUploadUrl(
                "demos",
                "README.md",
                Duration.ofMinutes(15)
        );
        System.out.println("Pre-signed Upload URL (valid for 15 minutes):");
        System.out.println(uploadUrl);


    }


}





