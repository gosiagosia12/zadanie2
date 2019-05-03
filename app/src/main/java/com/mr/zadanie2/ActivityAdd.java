package com.mr.zadanie2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class ActivityAdd extends AppCompatActivity {

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
                String selectedImage;

                Random rand = new Random();
                int a = rand.nextInt(3);
                switch(a){
                    case 0: selectedImage = "drawable 1"; break;
                    case 1: selectedImage = "drawable 2"; break;
                    case 2: selectedImage = "drawable 3"; break;
                    default: selectedImage = "drawable 3";
                }


                Intent intent = new Intent();
                intent.putExtra("taskTitle", taskTitle);
                intent.putExtra("taskDirector", taskDirector);
            //    intent.putExtra("taskPremiere", taskPremiere);
                intent.putExtra("selectedImage", selectedImage);
                setResult(RESULT_OK, intent);
                finish();
        }
    });
    }

}

