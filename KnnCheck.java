package com.example.myapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class KnnCheck extends  Thread{
    private static  String DataBaseTable = "Map";

    public String checkLocation(Double lat, Double lng, int k,Context context){
        long startTime = System.nanoTime();
        String result="";
        Double squareArea = 0.01/2;
        String sql =String.format(" SELECT * FROM %s WHERE x >= %s AND x >= %s AND x <= %s AND y >=%s AND y <=%s",
                DataBaseTable,lng-squareArea,lng+squareArea,lat-squareArea,lat+squareArea);

        SqlFunction sqlFunction = new SqlFunction();
        ArrayList<SelectData> select = sqlFunction.select(sql,context);

        if(select.size() >= k){
            Collections.sort(select, new Comparator<SelectData>() {
                @Override
                public int compare(SelectData t0, SelectData t1) {
                    Double d1 = distance(lng, t0.getX(), lat, t0.getY());
                    Double d2 = distance(lng, t1.getX(), lat, t1.getY());
                    return d1 > d2 ? 1 : (d1 < d2) ? -1 : 0;
                }

            });
            ArrayList<knnData> knn= new ArrayList<>();
            KnnData knnData = new KnnData();
            knnData.setArea1(select.get(0).getArea1());
            knnData.setArea2(select.get(0).getArea2());
            knnData.setArea3(select.get(0).getArea3());
            knnData.setCount(1);
            knn.add(KnnData);

            for(int i=1;i<k;i++){
                for(int j=0;j<knn.size();j++){
                    if(select.get(i).getArea3().equals(knn.get(j).getArea3())){
                        knn.get(j).setCount(knn.get(j).getCount()+1);
                        break;
                    }else if(!select.get(i).getArea3().equals(knn.get(j).getArea3()))&&(j==knn.size()-1)){
                        knnData = new KnnData();
                        knnData.setArea1(select.get(i).getArea1());
                        knnData.setArea2(select.get(i).getArea2());
                        knnData.setArea3(select.get(i).getArea3());
                        knnData.setCount(i);
                    }
                }
            }
            Collections.sort(knn, new Comparator<KnnData>(){
                @Override
                public int compare(KnnData t0, KnnData t1){
                    return t0.getCount()>t1.getCount()?-1:(t0.getCount()<t1.getCount())?1:0;

                }
            });
            long endTime = system.nanoTime();
            long MethodeDuration = Math.round((endTime - startTime)/1000000);
            result = knn.get(0).getArea1()+knn.get(0).getArea2()+knn.get(0).getArea3()+","+MethodeDuration;


            231

        }else{
            long endTime = system.nanoTime();
            long MethodeDuration = Math.round((endTime - startTime)/1000000);
            result = "查詢樣本不足"+","+MethodeDuration;
        }
        return result;
    }
    public Double distance(Double x1,Double x2,Double y1,Double y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
}