package com.example.sqlite1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SinhVien_Adapter adapter;
    TuongTacDatabase database;
    Dialog dialog;
    viewDialog viewDialog;
    boolean them = true, sua = false, xoa = false;
    SinhVien sv;
    int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lvsinhvien);
        database = new TuongTacDatabase(this);
        getDATA();
        adapter = new SinhVien_Adapter(this, 0, data.getDt().arrSV);
        listView.setAdapter(adapter);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_them_sua_xoa);
        viewDialog = new viewDialog(dialog);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actibar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idtrangchu:
                break;
            case R.id.idquanly:
                them = false;
                sua = true;

                viewDialog.setBTN();
                Toast.makeText(MainActivity.this, "Chọn sinh viên muốn sửa", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        sv = adapter.getItem(position);
                        h = position;
                        viewDialog.setTT(sv);
                        dialog.show();

                    }
                });
                break;
            case R.id.iddklop:
                them = true;
                sua = false;
//                xoa = false;
                viewDialog.setBTN();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.show();
                    }
                });

                dialog.show();
                break;
            case R.id.idtimkiem:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Cập nhật");
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.example_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case R.id.sua:
                them = false;
                sua = true;
//                xoa = false;
                viewDialog.setBTN();
                Toast.makeText(MainActivity.this, "Chọn sinh viên muốn sửa", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        sv = adapter.getItem(position);
                        h = position;
                        viewDialog.setTT(sv);
                        dialog.show();

                    }
                });
                break;
            case R.id.xoa:

                them = false;
                sua = false;
//                xoa = true;
                viewDialog.setBTN();
                Toast.makeText(MainActivity.this, "Chọn sinh viên muốn xóa", Toast.LENGTH_SHORT).show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setMessage("Bạn có muốn xóa!");
                        alertDialogBuilder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                sv = adapter.getItem(i);
                                h = i;
                                //viewDialog.setTT(sv);
                                //dialog.show();
                                database.open();
                                database.xoaSV(data.getDt().arrSV.get(h).getId());
                                database.close();
                                data.getDt().arrSV.remove(h);
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this,"Đã Xóa Thành Công", Toast.LENGTH_LONG).show();
                            }
                        });
                        alertDialogBuilder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //không làm gì
                            }
                        });
                        alertDialogBuilder.show();




                    }
                });

//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        sv = adapter.getItem(position);
//                        h = position;
//                        //viewDialog.setTT(sv);
//                        dialog.show();
//                        database.open();
//                        database.xoaSV(data.getDt().arrSV.get(h).getId());
//                        database.close();
//                        data.getDt().arrSV.remove(h);
//                        adapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDATA(){
        database.open();
        data.getDt().arrSV=new ArrayList<>(database.getAlldata());
        database.close();
    }
    class viewDialog {
        EditText edtten, edtsdt, edtemail;
        Button btnngaysinh, btnthem,btnsua,btnxoa;
        Spinner splLop;
        int ngay, thang, nam;
        String lop, gt = "nam";
        RadioGroup Gt;


        public viewDialog(Dialog v) {
            edtten = (EditText) v.findViewById(R.id.edtten);
            edtsdt = (EditText) v.findViewById(R.id.edtsdt);
            edtemail = (EditText) v.findViewById(R.id.edtmail);
            btnngaysinh = (Button) v.findViewById(R.id.btchonngay);
            btnthem = (Button) v.findViewById(R.id.btthem);
            btnsua = (Button) v.findViewById(R.id.btsua);


            splLop = (Spinner) v.findViewById(R.id.splLop);
//            Calendar c=Calendar.getInstance();
//            ngay=c.get(Calendar.DATE);
//            thang=1+c.get(Calendar.MONTH);
//            nam=c.get(Calendar.YEAR);
            Gt = (RadioGroup) v.findViewById(R.id.rdogr);

            setRD();

            setBtnthem();
            setBtnthem();
            setBtnsua();
//            setBtnxoa();

        }
        public void setBTN(){
            btnthem.setEnabled(them);
            btnsua.setEnabled(sua);
//            btnxoa.setEnabled(xoa);
        }
        public void setTT(SinhVien sv){
            edtsdt.setText(MainActivity.this.sv.getSdt());
            edtten.setText(MainActivity.this.sv.getTen());
            edtemail.setText(MainActivity.this.sv.getEmail());
            if(MainActivity.this.sv.getGioitinh().equals("nữ")){
                Gt.check(R.id.nu);
            }else {
                Gt.check(R.id.nam);
            }
            int c=0;
            for(int i=0;i<getResources().getStringArray(R.array.lop).length;i++){
                if(MainActivity.this.sv.getLophoc().equals(getResources().getStringArray(R.array.lop)[1])){
                    c=i;
                    break;
                }
            }
            splLop.setSelection(c);
        }
        public void setBtnthem() {
            btnthem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Gt.getCheckedRadioButtonId() == R.id.nam) {
                        gt = "nam";
                    } else {
                        gt = "nữ";
                    }
                    data.getDt().arrSV.add(getSV());
                    database.open();
                    database.themSV(getSV());
                    database.close();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
        }
        public void setBtnsua() {
            btnsua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Gt.getCheckedRadioButtonId() == R.id.nam) {
                        gt = "nam";
                    } else {
                        gt = "nữ";
                    }
                    SinhVien s = getSV();
                    s.setId(sv.getId());
                    data.getDt().arrSV.set(h, s);
                    database.open();
                    database.suaSV(s);
                    database.close();
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
        }
//        public void setBtnxoa() {
//            btnxoa.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    database.open();
//                    database.xoaSV(data.getDt().arrSV.get(h).getId());
//                    database.close();
//                    data.getDt().arrSV.remove(h);
//                    adapter.notifyDataSetChanged();
//                    dialog.dismiss();
//                }
//            });
//        }

        private void setRD() {
            splLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    lop = getResources().getStringArray(R.array.lop)[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    lop = getResources().getStringArray(R.array.lop)[0];
                }
            });

        }

        private SinhVien getSV(){
            SinhVien sv=new SinhVien();
            sv.setTen(edtten.getText().toString());
            sv.setEmail(edtemail.getText().toString());
            sv.setSdt(edtsdt.getText().toString());
            sv.setLophoc(lop);
            sv.setGioitinh(gt);
            return sv;
        }
    }
}