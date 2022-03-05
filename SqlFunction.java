package com.example.myapplication;


import

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;


public class SqlFunction {

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseTable = 1;
    private static String DataBaseTable = "Map";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;

    public void  insert(ArrayList<PlaceData> csvDataList,Context context){
        sqlDataBaseHelper = new SqlDataBaseHelper(context,DataBaseName,factory.null,DataBaseVersion,DataBaseTable );
        db = sqlDataBaseHelper .getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        for(int i=1;i< csvDataList.size();i++){
            long id;
            contentValues.put("area1",csvDataList.get(i).getArea1());
            contentValues.put("area2",csvDataList.get(i).getArea2());
            contentValues.put("area3",csvDataList.get(i).getArea3());
            contentValues.put("x",csvDataList.get(i).getX());
            contentValues.put("y",csvDataList.get(i).getY());
            id = db.insert(DataBaseTable,null,contentValues);
            Log.i("id",String.valueOf(id));
        }
        db.close();
    }
    public ArrayList<SelectData> select(String sql, Context context){
        sqlDataBaseHelper = new sqlDataBaseHelper(context,DataBaseName,factory.null,DataBaseVersion,DataBaseTable );
        db = sqlDataBaseHelper .getWritableDatabase();
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        ArrayList<SelectData> select = new ArrayList<>();
        SelectData selectData =new SelectData();
        for(int i=0;i<c.getCount();i++){
            selectData = new SelectData();
            selectData.setArea1(c.getString(0));
            selectData.setArea1(c.getString(1));
            selectData.setArea1(c.getString(2));
            selectData.setX(Double.valueOf(c.getString(3)));
            selectData.setY(Double.valueOf(c.getString(4)));
            c.moveToNext();
            select.add(selectData);
        }
        db.close();
        return select;

    }
}