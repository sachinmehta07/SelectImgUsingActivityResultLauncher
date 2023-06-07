package com.example.selectimgusingactivityresultlauncher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 123;
    private static final int REQUEST_CODE_GALLERY = 456;
    private static final int MAX_IMAGE_COUNT = 5;

    private Button galleryButton;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImages;

    private ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryButton = findViewById(R.id.gallery_button);
        recyclerView = findViewById(R.id.recycler_view);

        // Initialize selectedImages list
        selectedImages = new ArrayList<>();

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imageAdapter = new ImageAdapter(selectedImages);
        recyclerView.setAdapter(imageAdapter);

        // Check permission to access external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }

        // Initialize the pickMultipleMedia launcher
        pickMultipleMedia = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(MAX_IMAGE_COUNT), uris -> {
            if (!uris.isEmpty()) {
                selectedImages.addAll(uris);
                imageAdapter.notifyDataSetChanged();
                Log.d("PhotoPicker", "Number of items selected: " + uris.size());
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });

        // Set click listener for gallery button
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        // Launch the photo picker and let the user choose only images.
//        ActivityResultContracts.PickVisualMedia.VisualMediaType mediaType = (ActivityResultContracts.PickVisualMedia.VisualMediaType) ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE;
//        PickVisualMediaRequest request = new PickVisualMediaRequest.Builder()
//                .setMediaType(mediaType)
//                .build();
//        pickMultipleMedia.launch(request);
    }

    // ... Rest of the code remains the same
}
