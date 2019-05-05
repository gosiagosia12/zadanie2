package com.mr.zadanie2;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ActivityAdd extends AppCompatActivity {


    String picPath = null;
    String selectedImage = "";
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Bundle extras = getIntent().getExtras();

        if(extras!=null) {Random rand = new Random();
            int a = rand.nextInt(16);
            switch(a){
                case 0: selectedImage = "avatar 1"; break;
                case 1: selectedImage = "avatar 2"; break;
                case 2: selectedImage = "avatar 3"; break;
                case 3: selectedImage = "avatar 4"; break;
                case 4: selectedImage = "avatar 5"; break;
                case 5: selectedImage = "avatar 6"; break;
                case 6: selectedImage = "avatar 7"; break;
                case 7: selectedImage = "avatar 8"; break;
                case 8: selectedImage = "avatar 9"; break;
                case 9: selectedImage = "avatar 10"; break;
                case 10: selectedImage = "avatar 11"; break;
                case 11: selectedImage = "avatar 12"; break;
                case 12: selectedImage = "avatar 14"; break;
                case 13: selectedImage = "avatar 15"; break;
                case 14: selectedImage = "avatar 16"; break;
                default: selectedImage = "avatar 3";
            }

        }
        else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File image = null;
                try {
                    image = makeFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri photoURI = FileProvider.getUriForFile(this, getString(R.string.myFileprovider), image);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }


        }

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText taskTitleEditTxt = findViewById(R.id.Title);
                EditText taskDirectorEditTxt = findViewById(R.id.Director);
                EditText taskPremiereEditTxt = findViewById(R.id.Premiere);
                String taskTitle = taskTitleEditTxt.getText().toString();
                String taskDirector = taskDirectorEditTxt.getText().toString();

                String taskPremiere = taskPremiereEditTxt.getText().toString();

                Intent intent = new Intent();
                if(picPath == null){
                    intent.putExtra("selectedImage", selectedImage);
                }

                intent.putExtra("taskTitle", taskTitle);
                intent.putExtra("taskDirector", taskDirector);
                intent.putExtra("picPath", picPath);
                intent.putExtra("taskPremiere", taskPremiere);
             //   intent.putExtra("selectedImage", selectedImage);
                setResult(RESULT_OK, intent);
                finish();
        }
    });
    }

    private File makeFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        picPath = image.getAbsolutePath();
        return image;
    }

  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            Camera = data.getBooleanExtra("Camera", true);
        }
        else if(resultCode == RESULT_CANCELED) {}
    }*/
}

