package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hainb21127.poly.appfastfood_admin.R;

public class ChooseImage extends AppCompatActivity {

    ImageView choose_image;
    EditText ed_image;
    Button btn_image;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri; // Để lưu trữ đường dẫn hình ảnh đã chọn

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        choose_image = findViewById(R.id.btn_choose_image);
        ed_image = findViewById(R.id.ed_image_choose);
        btn_image = findViewById(R.id.upload_image);

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToFirebase(selectedImageUri);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ed_image.setText(selectedImageUri.toString());
            choose_image.setImageURI(selectedImageUri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            String imageName = System.currentTimeMillis() + ".jpg";
            StorageReference imageRef = storageRef.child("images/" + imageName);

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUrl) {
                                    // Sử dụng downloadUrl theo nhu cầu của bạn
                                    String imageUrl = downloadUrl.toString();
                                    Log.d("FirebaseStorage", "Image URL: " + imageUrl);
                                    // Tại đây, bạn có thể lưu imageUrl vào cơ sở dữ liệu hoặc thực hiện các hành động cụ thể khác.
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("FirebaseStorage", "Error uploading image: " + e.getMessage());
                        }
                    });
        }
    }
}
