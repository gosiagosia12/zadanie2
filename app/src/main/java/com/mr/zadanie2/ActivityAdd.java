package com.mr.zadanie2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class ActivityAdd extends AppCompatActivity {


    protected boolean Camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText taskTitleEditTxt = findViewById(R.id.Title);
                EditText taskDirectorEditTxt = findViewById(R.id.Director);
            //    EditText taskPremiereEditTxt = findViewById(R.id.Premiere);
                String taskTitle = taskTitleEditTxt.getText().toString();
                String taskDirector = taskDirectorEditTxt.getText().toString();
            //    String taskPremiere = taskPremiereEditTxt.getText().toString();
                String selectedImage = "";
                Intent intent = new Intent();
                if(Camera == false) {Random rand = new Random();
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
                        }}
                        else selectedImage = "avatar 16";

                intent.putExtra("taskTitle", taskTitle);
                intent.putExtra("taskDirector", taskDirector);
            //    intent.putExtra("taskPremiere", taskPremiere);
                intent.putExtra("selectedImage", selectedImage);

                setResult(RESULT_OK, intent);
                finish();
        }
    });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            Camera = data.getBooleanExtra("Camera", true);
        }
        else if(resultCode == RESULT_CANCELED) {}
    }
}

