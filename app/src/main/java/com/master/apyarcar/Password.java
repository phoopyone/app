package com.master.apyarcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends AppCompatActivity {

    EditText editText;
    Button button;
    String dtname="mybase";
    String passwords="555555";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editText=findViewById(R.id.editTextTextPersonName);
        button=findViewById(R.id.button2);
        // editText.requestFocus();
        // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        SharedPreferences sharedPreferences=getSharedPreferences(dtname,MODE_PRIVATE);
        final  SharedPreferences.Editor e=sharedPreferences.edit();
        boolean hasLogin=sharedPreferences.getBoolean("hasLogin",false);
        if(hasLogin){
            Intent intent=new Intent(Password.this,Webview.class);
            startActivity(intent);
            Password.this.finish();
        }else {
            Toast.makeText(Password.this,"First Login With (555555) ", Toast.LENGTH_LONG).show();

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass=editText.getText().toString();
                if(pass.equals(passwords)){
                    Intent in=new Intent(Password.this,Webview.class);
                    startActivity(in);
                    e.putBoolean("hasLogin",true);
                    e.commit();

                }else {
                    Toast.makeText(Password.this,"Your Password Is Wrong",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}