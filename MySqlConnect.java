package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MySqlConnect extends Thread{
    private String sql,area1,area2,area3,lat,lng;

    public MySqlConnect(String sql){
        this.sql = sql;
    }
    @Override
    public void run() {
        super.run();
        try {
            URL url = new URL("http://192.168.1.105/GetData.php?sql="+sql);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    JSONArray dataJson = new JSONArray(line);
                    int i = dataJson.length() - 1;
                    JSONObject info = dataJson.getJSONObject(i);
                    area1 = info.getString("area1");
                    area2 = info.getString("area2");
                    area3 = info.getString("area3");
                    lat = info.getString("lat");
                    lng = info.getString("lng");
                }
                inputStream.close();
            }
        } catch (Exception e) {
            area1 = e.toString();
        }
    }

    public String getResult(){
        return area1+area2+area3+"\nlat:"+lat+"\nlng:"+lng;
    }
    public Double getLat(){return Double.valueOf(lat);}
    public Double getLng(){return Double.valueOf(lng);}


}
