package com.example.administrator.familydoctor.Main.Body;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.familydoctor.R;

public class XueWeiInfo extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView back;

    int flag;

    String title[]={"相关症状","位置","按摩方法","所属经络","相关穴位","备注"};
    String content[][]={
            {"","","","","",""}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xue_wei_info);

        flag=getIntent().getIntExtra("flag",0);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(XueWeiInfo.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(XueWeiInfo.this, LinearLayoutManager.VERTICAL));
    }

    class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Holder holder=new Holder(LayoutInflater.from(XueWeiInfo.this).inflate(R.layout.item_xuewei,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Holder)holder).title.setText(title[position]);
            ((Holder)holder).content.setText(content[flag][position]);
        }

        @Override
        public int getItemCount() {
            return title.length;
        }

        class Holder extends RecyclerView.ViewHolder{
            TextView title,content;

            Holder(View v){
                super(v);
                title=(TextView)v.findViewById(R.id.title);
                content=(TextView)v.findViewById(R.id.content);
            }
        }
    }
}
