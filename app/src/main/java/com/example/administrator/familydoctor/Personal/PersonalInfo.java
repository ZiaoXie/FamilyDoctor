package com.example.administrator.familydoctor.Personal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.familydoctor.R;

import java.util.Calendar;

public class PersonalInfo extends AppCompatActivity {

    String username;
    private String[] data={"昵称","性别","生日"},shuxing={"nickname","sex","birthday"};
    String information[]=new String[data.length],result[][];
    Intent intent;
    Holder holder;
    MyAdapter myAdapter;
    ListView listView;
    EditText editText[]=new EditText[data.length];
    Button logout;
    int cunzhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        username=getSharedPreferences("userinfo",MODE_PRIVATE).getString("username","");

        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(PersonalInfo.this);
                builder.setTitle("是否退出当前账号？").setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor=getSharedPreferences("userinfo",MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        finish();
                    }
                }).show();
            }
        });

        ImageView back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalInfo.this.finish();
            }
        });

        listView=(ListView)findViewById(R.id.listview);
        myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);

        Button save=(Button) findViewById(R.id.edit);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfo.this);
                builder.setTitle("是否保存?").setNegativeButton(
                        "取消", null);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

            }
        });

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            View v=null;
            final Holder holder;
            if(view==null){
                v= View.inflate(PersonalInfo.this,R.layout.listview_personinfo,null);
                holder=new Holder();
                holder.tv = (TextView) v.findViewById(R.id.item_textview);
                holder.et=(TextView)v.findViewById(R.id.personalInformation);
                v.setTag(holder);
            }
            else{
                v=view;
                holder=(Holder) v.getTag();
            }
            holder.tv.setText(data[position]);
            holder.et.setText(information[position]);

            if(position!=0) holder.et.setFocusable(false);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int p = position;
                    //Holder holder=(Holder) view.getTag(p);
                    switch (p) {
                        case 0:
                            EditText editText=new EditText(PersonalInfo.this);
//                            AlertDialog.Builder builder=new AlertDialog.Builder(PersonalCenter.this).setTitle("请输入昵称");
                            break;
                        case 1:
                            final AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfo.this);
                            builder.setTitle("请选择性别");
                            final String[] sex = {"男", "女"};
                            builder.setItems(sex, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    information[p]=sex[i];
                                    myAdapter.notifyDataSetChanged();
                                }
                            });
                            builder.show();
                            break;
                        case 2:
                            Calendar c = Calendar.getInstance();
                            DatePickerDialog dpd=new DatePickerDialog(PersonalInfo.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    information[p]= String.format( "%04d-%02d-%02d",year,month+1,dayOfMonth );
                                    myAdapter.notifyDataSetChanged();
                                }
                            },c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                            dpd.show();
                            break;
                    }
                }
            });

            return v;
        }
    }

    class Holder{
        public TextView tv;
        public TextView et;
    }
}
