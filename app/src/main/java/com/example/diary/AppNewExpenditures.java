package com.example.diary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.diary.models.Expenditure;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Map;

public class AppNewExpenditures extends AppCompatActivity {

    //private static final String[] items={"Cat1","Cat2","Cat3"};
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Diary").child("Expenditure");
    Expenditure expenditure;
    TextView Date;
    Button Btn;
    Calendar Cal;
    DatePickerDialog Dpd;
    private ArrayList<String> categories = new ArrayList<String>();

    public void submitExp(View view){
        try {
            EditText date = (EditText) findViewById(R.id.Date);
            EditText moneySpent = (EditText) findViewById(R.id.MoneySpent);
            EditText desc = (EditText) findViewById(R.id.Description);
            Log.d("Aniket","before spinner");
            Spinner cats_spinner = findViewById(R.id.cats_spinner);
            String spinVal = cats_spinner.getSelectedItem().toString();
            String Edate = date.getText().toString().trim();

            Log.d("Aniket","All fetched");
            String[] arrOfStr = Edate.split("/");
            Calendar c1 = Calendar.getInstance();
            int cnt = 0;
            for (String a : arrOfStr) {
                if (cnt == 0) {
                    c1.set(Calendar.DATE, Integer.parseInt(a.trim()));
                } else if (cnt == 1) {
                    c1.set(Calendar.MONTH, Integer.parseInt(a.trim()) - 1);
                } else {
                    c1.set(Calendar.YEAR, Integer.parseInt(a.trim()));
                }
                cnt += 1;
            }
            Date dateOne = c1.getTime();

            int EMS = Integer.parseInt(moneySpent.getText().toString());
            String EDesc = desc.getText().toString().trim();
            String Ecat = spinVal;
            expenditure = new Expenditure(dateOne, EMS, Ecat, EDesc);
            //myRef.push().setValue(expenditure);

            Log.d("Aniket","getting ref" + Ecat);
            DatabaseReference abc = getCategoryRef(Ecat);
            Log.d("Aniket","ref-" + abc);
/*            Intent intent = new Intent(this, addedExpenditure.class);
            startActivity(intent);*/
        }catch (Exception e){
            Toast.makeText(AppNewExpenditures.this,"Don't keep fields empty !!", Toast.LENGTH_LONG).show();
            Log.d("Aniket","Exception : "+e.toString());
        }

        Intent intent = new Intent(this, addedExpenditure.class);
        startActivity(intent);
    }

    public DatabaseReference getCategoryRef(final String category){
        DatabaseReference myDbRef = database.getReference("Diary").child("Category");
        final ArrayList<DatabaseReference> catRef = new ArrayList<DatabaseReference>();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot categoryChild = dataSnapshot.child(category);
                catRef.add(categoryChild.getRef());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Aniket", "Cancelled");
            }
        };
        myDbRef.addValueEventListener(postListener);
        DatabaseReference ref = database.getReference("Diary").child("Category");
        for (DatabaseReference Cref : catRef){
            ref = Cref;
        }
        Log.d("Aniket","ref : "+ref);
        return ref;
    }

    public void getCategories(){
/*        DatabaseReference myDbRef = database.getReference("Diary").child("Category");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean b = dataSnapshot.exists();
                if (b) {
                    long Children = dataSnapshot.getChildrenCount();
                    //ArrayList<String> categories = new ArrayList<String>();
                    if(Children > 0) {
                        for(DataSnapshot child : dataSnapshot.getChildren()){
                            String category = child.getKey();
                            categories.add(category);
                            Log.d("Aniket",category+" : "+ child.getValue().toString());
                        }
                    }
                }else{
                    Log.d("Aniket","Categories do not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Aniket", "Cancelled");
            }
        };
        myDbRef.addValueEventListener(postListener);*/
        categories.add("Grocery");
        categories.add("House Rent");
        categories.add("Travelling");
        categories.add("Other");
        Log.d("Aniket","categories"+categories);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_new_expenditures);
        getCategories();

        Spinner spinner = (Spinner) findViewById(R.id.cats_spinner);
        Log.d("Aniket", "before");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Date = (TextView)findViewById(R.id.Date);
        Btn = (Button) findViewById(R.id.DatePick);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cal = Calendar.getInstance();
                int Day = Cal.get(Calendar.DAY_OF_MONTH);
                int Month = Cal.get(Calendar.MONTH);
                int Year = Cal.get(Calendar.YEAR);
                Dpd = new DatePickerDialog(AppNewExpenditures.this, date, Year, Month, Day);
                Dpd.show();
            }
        });
    }
    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Date.setText(dayOfMonth +" / "+(monthOfYear+1)+" / "+year);
        }
    };
}