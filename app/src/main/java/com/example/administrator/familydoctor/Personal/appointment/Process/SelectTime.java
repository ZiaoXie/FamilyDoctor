package com.example.administrator.familydoctor.Personal.appointment.Process;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.familydoctor.HomePage;
import com.example.administrator.familydoctor.R;

import java.util.Calendar;

public class SelectTime extends AppCompatActivity {

    ImageView back;
    Button yuyue;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Calendar c = Calendar.getInstance();
        date=(TextView)findViewById(R.id.date);
        date.setText(String.format( "%04d-%02d-%02d",c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH) ));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dpd=new DatePickerDialog(SelectTime.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(String.format( "%04d-%02d-%02d",year,month+1,dayOfMonth ));

                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        yuyue=(Button)findViewById(R.id.yuyue_button2);
        yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SelectTime.this);
                builder.setTitle("预约成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(SelectTime.this, HomePage.class);
                        intent.putExtra("flag",4);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}
