package com.rentspace.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;
import com.rentspace.util.ModelMapperFuncs;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ImageService extends ModelMapperFuncs {
    private static final String bucketName = "rent-space";
    private final GoogleCredentials credentials;

    public ImageService() throws IOException {
        String secretDate = System.getenv("GOOGLE_CLOUD_CONFIG");
        ByteArrayInputStream credentialsStream = new ByteArrayInputStream(secretDate.getBytes(StandardCharsets.UTF_8));

        this.credentials = GoogleCredentials.fromStream(credentialsStream).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String objectName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
    }

    public List<String> uploadMultipleFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return null;

        int numThreads = Math.min(files.size(), 4);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<String>> futures = new ArrayList<>();

        for (MultipartFile file : files) {
            Future<String> future = executor.submit(() -> this.uploadFile(file));
            futures.add(future);
        }

        executor.shutdown();

        List<String> urls = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                urls.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return urls;
    }
}
