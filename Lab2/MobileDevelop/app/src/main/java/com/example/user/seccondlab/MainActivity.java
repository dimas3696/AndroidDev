package com.example.user.seccondlab;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
