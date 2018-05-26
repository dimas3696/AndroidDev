package com.example.user.mobiledevelop3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
    }

    public void onClickOK(View view){

        Spinner font_spinner = (Spinner) findViewById(R.id.font_spinner);
        String font = font_spinner.getSelectedItem().toString();

        EditText original_text = (EditText) findViewById(R.id.origin_text);
        String text = original_text.getText().toString();

        TextView final_text = (TextView) findViewById(R.id.final_text);
        Typeface type = Typeface.createFromAsset(getAssets(),"ThannhaeuserC.ttf");;

        if (font.equals("Edo")){
            type = Typeface.createFromAsset(getAssets(),"Edo.ttf");
        }
        if (font.equals("SpikeyBit")){
            type = Typeface.createFromAsset(getAssets(),"SpikeyBit.ttf");
        }
        if (font.equals("ThannhaeuserC")){
            type = Typeface.createFromAsset(getAssets(),"ThannhaeuserC.ttf");
        }

        if (original_text.getText().length()==0){
            Toast toast = Toast.makeText(this, "Введите текст!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 160);
            toast.show();
        }
        else{
            final_text.setTypeface(type);
            final_text.setText(original_text.getText());
        }
    }

    public void onClickCancel(View view){

        EditText original_text = (EditText) findViewById(R.id.origin_text);

        original_text.setText(null);

        TextView final_text = (TextView) findViewById(R.id.final_text);

        final_text.setText(null);
    }



    public void onClickSave(View view){
        //create content for data
        ContentValues cv = new ContentValues();

        Spinner font_spinner = (Spinner) findViewById(R.id.font_spinner);
        String font = font_spinner.getSelectedItem().toString();

        EditText original_text = (EditText) findViewById(R.id.origin_text);
        String text = original_text.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Date currentTime = Calendar.getInstance().getTime();

        Log.d(LOG_TAG, "---Insert to db---");
        cv.put("time", currentTime.getHours() + ":" + currentTime.getMinutes() + ":" + currentTime.getSeconds());
        //cv.put("time", currentTime.toString());
        cv.put("font_name", font);
        cv.put("text", text);

        long rowId = db.insert("HISTORY", null, cv);
        //Log.d(LOG_TAG, "row inserted Id = " + rowId);
        Toast toast = Toast.makeText(this, "row inserted Id = " + rowId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 160);
        toast.show();

        db.close();
    }

    public void onClickCheck(View view){
        Log.d(LOG_TAG, "--- Rows in HISTORY: ---");

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("HISTORY", null, null, null, null, null, null);

        TextView db_text = (TextView) findViewById(R.id.db_text);

        if(c.moveToFirst()){
            int idColIndex = c.getColumnIndex("id");
            int timeColIndex = c.getColumnIndex("time");
            int font_nameColIndex = c.getColumnIndex("font_name");
            int textColIndex = c.getColumnIndex("text");

            do{
                db_text.setText("ID = " + c.getInt(idColIndex) +
                        ", Time = " + c.getString(timeColIndex) +
                        ", Font = " + c.getString(font_nameColIndex) +
                        ", Text = " + c.getString(textColIndex));
            }while (c.moveToNext() != false);
        }else{
            Toast toast = Toast.makeText(this, "Database void!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 160);
            toast.show();
        }

        db.close();
    }

    class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, "MyDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "---onCreate database---");

            db.execSQL("CREATE TABLE HISTORY (" +
                    "id integer PRIMARY KEY AUTOINCREMENT, " +
                    "time text, " +
                    "font_name text, " +
                    "text text" +
                    ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}


