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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import com.example.diary.models.Expenditure;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AppNewExpenditures extends AppCompatActivity {

    private static final String[] items={"Cat1","Cat2","Cat3"};
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Diary").child("Expenditure");
    Expenditure expenditure;
    TextView Date;
    Button Btn;
    Calendar Cal;
    DatePickerDialog Dpd;

    public void submitExp(View view){
        Log.d("Aniket","here it is");
        EditText date = (EditText)findViewById(R.id.Date);
        EditText moneySpent = (EditText)findViewById(R.id.MoneySpent);
        EditText desc = (EditText)findViewById(R.id.Description);
        Spinner cats_spinner = findViewById(R.id.cats_spinner);
        String spinVal = cats_spinner.getSelectedItem().toString();
        String Edate = date.getText().toString().trim();

        String[] arrOfStr = Edate.split("/");
        Calendar c1 = Calendar.getInstance();
        int cnt = 0;
        for (String a : arrOfStr){
            if(cnt ==0){
                c1.set(Calendar.DATE, Integer.parseInt(a.trim()));
            }else if(cnt ==1){
                c1.set(Calendar.MONTH, Integer.parseInt(a.trim())-1);
            }else{
                c1.set(Calendar.YEAR, Integer.parseInt(a.trim()));
            }
            cnt+=1;
        }
        Date dateOne = c1.getTime();
        Log.d("Aniket","date is"+dateOne);

        int EMS = Integer.parseInt(moneySpent.getText().toString());
        String EDesc = desc.getText().toString().trim();
        String Ecat = spinVal;
        expenditure = new Expenditure(dateOne,EMS,Ecat,EDesc);
        //myRef.push().setValue(expenditure);
        Intent intent = new Intent(this, addedExpenditure.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_new_expenditures);
        //getCategories();
        Spinner spinner = (Spinner) findViewById(R.id.cats_spinner);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                items);
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