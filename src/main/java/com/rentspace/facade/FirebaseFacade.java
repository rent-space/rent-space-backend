package com.rentspace.facade;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.rentspace.initializer.FirebaseInitializer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Component
public class FirebaseFacade {

    public FirebaseFacade() {
        FirebaseInitializer.initialize();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String bucketName = "your-project-id.appspot.com";
        String objectName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        return objectName;
    }
}
