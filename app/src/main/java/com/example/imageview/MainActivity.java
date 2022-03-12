package com.example.imageview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int[] manghinhbai ={R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5,
            R.drawable.c6,R.drawable.c7,R.drawable.c8,R.drawable.c9,R.drawable.c10,
            R.drawable.cj,R.drawable.cq,R.drawable.ck,
            R.drawable.d1,R.drawable.d2,R.drawable.d3,R.drawable.d4,R.drawable.d5,
            R.drawable.d6,R.drawable.d7,R.drawable.d8,R.drawable.d9,R.drawable.d10,
            R.drawable.dj,R.drawable.dq,R.drawable.dk,
            R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5,
            R.drawable.h6,R.drawable.h7,R.drawable.h8,R.drawable.h9,R.drawable.h10,
            R.drawable.hj,R.drawable.hq,R.drawable.hk,
            R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5,
            R.drawable.s6,R.drawable.s7,R.drawable.s8,R.drawable.s9,R.drawable.s10,
            R.drawable.sj,R.drawable.sq,R.drawable.sk};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tien = findViewById(R.id.tiencuoc);

        TextView kp = findViewById(R.id.kp);
        ImageView iv1 = findViewById(R.id.image_View1);
        ImageView iv2 = findViewById(R.id.image_View2);
        ImageView iv3 = findViewById(R.id.image_View3);
        TextView tv = findViewById(R.id.tv);
        ImageView iv4 = findViewById(R.id.imageView1);
        ImageView iv5 = findViewById(R.id.imageView2);
        ImageView iv6 = findViewById(R.id.imageView3);
        TextView tv1 = findViewById(R.id.tv1);
        Button bt = findViewById(R.id.button);

        String[] cuoc = {"5", "10", "20", "50", "100", "200", "500"};
        Spinner sp = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,cuoc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int tiencuoc = Integer.parseInt(sp.getSelectedItem().toString());
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int[] balabai= laysoNgauNhien(0,51);
                        int[] nguoi = new int[3];
                        int[] may = new int[3];
                        for (int i = 0; i<3; i++){
                            nguoi[i]=balabai[i];
                        }
                        for (int i = 0; i<3; i++){
                            may[i]=balabai[i+3];
                        }
                        iv1.setImageResource(manghinhbai[balabai[0]]);
                        iv2.setImageResource(manghinhbai[balabai[1]]);
                        iv3.setImageResource(manghinhbai[balabai[2]]);
                        tv.setText(tinhDiem(nguoi));

                        iv4.setImageResource(manghinhbai[balabai[3]]);
                        iv5.setImageResource(manghinhbai[balabai[4]]);
                        iv6.setImageResource(manghinhbai[balabai[5]]);
                        tv1.setText(tinhDiem(may));

                        int t = Integer.parseInt(tien.getText().toString());

                        if ((tinhSoTay(nguoi) == 3 && tinhSoTay(may) != 3) || (tong(nguoi) > tong(may))) t += tiencuoc;
//                        if (tong(nguoi) > tong(may)) t += tiencuoc;
                        if ((tinhSoTay(nguoi) != 3 && tinhSoTay(may) == 3 )|| (tong(nguoi) < tong(may))) t -= tiencuoc;
//                        if (tong(nguoi) < tong(may)) t -= tiencuoc;

                        tien.setText(String.valueOf(t));

                        kp.setText(ketQua(nguoi, may));

                        if (t<0)
                            tien.setText("0");
                        if (t < tiencuoc){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Thông báo");
                            alertDialog.setMessage("Bạn không đủ tiền cược");
                            alertDialog.setCancelable(true);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Chơi lại",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            tien.setText("1000");
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Đổi mức cược", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MainActivity.this.onResume();
                                }
                            });
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Nghỉ chơi",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            MainActivity.this.finish();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String ketQua(int[] a, int[] b){
        String f = "";
        if (tinhSoTay(a) == 3 && tinhSoTay(b) == 3)
            f = "Hòa";
        else if (tinhSoTay(a) == 3 && tinhSoTay(b) != 3)
            f = " Thắng ";
        else if (tinhSoTay(a) != 3 && tinhSoTay(b) == 3)
            f = " Thua ";
        else {
            if (tong(a) == tong(b))
                f = " Hòa ";
            else if (tong(a) > tong(b))
                f = " Thắng ";
            else if (tong(a) < tong(b))
                f = " Thua ";
        }
        return f;
    }

    private int tong(int[] arr){
        int tong = 0;
        for (int i=0; i < arr.length; i++) {
            if (arr[i] % 13 < 10 )
                tong += (arr[i] % 13 + 1);
        }
        return tong%10;
    }

    private String tinhDiem(int[] arr){
        String ketQua = "";
        if(tinhSoTay(arr) == 3)
            ketQua = "Kết quả: 3 Tây";
        else {
            int tong = 0;
            for (int i=0; i < arr.length; i++) {
                if (arr[i] % 13 < 10)
                    tong += arr[i] % 13 + 1;
            if(tong %10 ==0)
                ketQua = "Kết quả: Bù(0 điểm)";
            else
                ketQua = "Kết quả: "+ tong%10 +" điểm";
            }
        }
        return ketQua;
    }

    private  int tinhSoTay(int[] arr){
        int k=0;
        for (int i=0; i < arr.length; i++){
            if (arr[i] % 13 >= 10)k++;
        }
        return k;
    }

    private int[] laysoNgauNhien(int min, int max){
        int[] baso = new int[6];
        int i=0;
        baso[i++] = random(min, max);
        do {
            int k = random(min, max);
            if(!kiemtratrung(k, baso))
                baso[i++] = k;
        }while(i<6);
        return baso;
    }

    private boolean kiemtratrung(int k, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == k) return true;
        }
        return false;
    }

    private int random(int min, int max){
        return min + (int)(Math.random()*(max-min)+1);
    }
}