package com.example.diary.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.diary.models.Category;
import com.example.diary.models.Expenditure;
import com.example.diary.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCATEGORY = "CREATE TABLE CATEGORY(" +
                "ID INTEGER PRIMARY KEY," +
                "CATEGORY VARCHAR(50)," +
                "CATEGORY_TYPE VARCHAR(50))";

        String createEXPEND = "CREATE TABLE EXPENDITURES(" +
                "ID INTEGER PRIMARY KEY," +
                "EDATE DATE," +
                "MONEY INTEGER," +
                "CATEGORY INTEGER," +
                "DESCRIPTION VARCHAR(100)," +
                "FOREIGN KEY(CATEGORY) REFERENCES CATEGORY(ID))";

        String createREMINDER = "CREATE TABLE REMINDER(" +
                "ID INTEGER PRIMARY KEY," +
                "RDATE DATE," +
                "EXPENDITURE INTEGER," +
                "RESOLVED VARCHAR(1) DEFAULT 'N')";
        db.execSQL(createCATEGORY);
        db.execSQL(createEXPEND);
        db.execSQL(createREMINDER);
        Log.d("Aniket","Categories Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCategory(Category Cat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CATEGORY",Cat.getCategory());
        values.put("CATEGORY_TYPE",Cat.getCategoryType());
        db.insert("CATEGORY",null,values);
        Log.d("categoryInsert","Successfully Inserted");
        db.close();
    }

    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM CATEGORY";
        Cursor cursor = db.rawQuery(Query,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String category = cursor.getString(1);
                String categoryType = cursor.getString(2);
                Category cat = new Category(category,categoryType);
                categories.add(cat);
            }while (cursor.moveToNext());
        }
        return categories;
    }
}
