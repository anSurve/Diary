package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diary.data.MyDBHandler;
import com.example.diary.models.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
//    public static final String userName = "com.diary.userName";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Diary");

/*    public void SeePics(View view){
        //build Intent to open Scrolling activity
        Intent intent = new Intent(this, ScrollingActivity.class);
        startActivity(intent);
    }*/

    public void NewExpenditure(View view){
        //build Intent to open Scrolling activity
        Intent intent = new Intent(this, AppNewExpenditures.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Aniket","here in main");

/*        MyDBHandler db  = new MyDBHandler(MainActivity.this);
        Category CatOne = new Category("GROCERY","NECESSARY");
        Category CatTwo = new Category("HOUSE RENT","NECESSARY");
        Category CatThree = new Category("OTHER","NOT NECESSARY");

        db.addCategory(CatOne);
        db.addCategory(CatTwo);
        db.addCategory(CatThree);*/
//        Toast.makeText(MainActivity.this,"FireBase Connection done", Toast.LENGTH_LONG).show();
    }
}
