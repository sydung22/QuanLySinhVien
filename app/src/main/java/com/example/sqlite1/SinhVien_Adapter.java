package com.example.sqlite1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SinhVien_Adapter extends ArrayAdapter<SinhVien> {
    ArrayList<SinhVien> arr;
    Context ct;
    public SinhVien_Adapter(@NonNull Context context, int resource,ArrayList<SinhVien> o) {
        super(context, resource,o);
        this.ct=context;
        this.arr=new ArrayList<SinhVien>(data.getDt().arrSV) ;
    }
    public void notifyDataSetChanged(){
        this.arr =data.getDt().arrSV;
        super.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        viewHodler v;
        View row =convertView;
            if(row==null){
                LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row =inflater.inflate(R.layout.item_sinhvien,null);
                v=new viewHodler(row);
                row.setTag(v);
            }else {
                v=(viewHodler)row.getTag();
            }


            if(arr.size()>0){
                v.setView(arr.get(position));
            }
        return row;
    }
    class viewHodler{
        TextView txtten, txtsdt, txtemail, txtlop;
        ImageView imgGT;
        public viewHodler(View v){
            txtten=(TextView) v.findViewById(R.id.tvname);
            txtsdt=(TextView) v.findViewById(R.id.tvsdt);
            txtemail=(TextView) v.findViewById(R.id.tvemail);
            txtlop=(TextView) v.findViewById(R.id.tvlop);
            imgGT=(ImageView) v.findViewById(R.id.imggt);
        }
       public void setView(SinhVien sv){
            txtten.setText(sv.getTen());
           txtlop.setText(sv.getLophoc());
           txtsdt.setText(sv.getSdt());
           txtemail.setText(sv.getEmail());
           if(sv.getGioitinh().equals("nữ")){
               imgGT.setImageResource(R.drawable.nu);
           }else {
               imgGT.setImageResource(R.drawable.nam);
           }

       }
    }
}
