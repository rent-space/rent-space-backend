package com.rentspace.initializer;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FirebaseInitializer {
    public static void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream("C:\\Users\\User\\Documents\\key-firebase.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setStorageBucket("rent-space")
                        .build();

                FirebaseApp.initializeApp(options);
            }else {
                System.out.println("FirebaseApp already initialized.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
