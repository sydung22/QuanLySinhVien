package com.example.sqlite1;

import java.util.ArrayList;

public class data {
    public static data dt;
    ArrayList<SinhVien> arrSV;
    static {
        dt=new data();

    }
    public static data getDt(){
        return dt;
    }

}
