package com.example.myapplication;


import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    private static final String DataBaseName = "DataBaseIt";
    private static String DataBaseTable ="Map";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        
        SqlFunction sqlFunction=new SqlFunction();
        String sql = String.format("SELECT * FROM %s LIMIT 1",DataBaseTable);
        ArrayList<SelectData> select =sqlFunction.select(sql,this);
        if(select.size()==0){
            CopyDataBase copyDataBase = new CopyDataBase();
            try {
                copyDataBase.copy(DataBaseName,this);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        boolean locationHasGone = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            String[] permissions;
            if(!locationHasGone){
                permissions = new String[1];
            }
        }
    }
}
