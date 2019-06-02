package com.example.administrator.familydoctor.WenZhen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.familydoctor.Personal.appointment.MainAppointment;
import com.example.administrator.familydoctor.R;

import java.util.List;

//import com.example.administrator.needletherapy.toolClass.Constant;
//import com.example.administrator.needletherapy.toolClass.EaseCommonUtils;
//import com.hyphenate.EMMessageListener;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMConversation;
//import com.hyphenate.chat.EMMessage;
//import com.hyphenate.chat.EMMessage.ChatType;
//import com.hyphenate.chat.EMTextMessageBody;

public class ChatActivity extends Activity {
    private RecyclerView recyclerView;
    private int chatType = 1;
    private Button btn_send;
    private EditText et_content;
    private List<String> msgList;
    MessageAdapter adapter;
    //private EMConversation conversation;
    protected int pagesize = 20;
    RelativeLayout relativeLayout;

    final public int USER=0;
    final public int OTHER=1;
    final public int PHOTO_USER=2;
    final public int PHOTO_OTHER=3;

    ImageView back;

    public int counter=0;

    String conver[]={"我昨晚睡觉吹了一晚上空调醒来半边脸都不了，怎么办？","您好，根据您的描述，我怀疑是面部偏瘫。方便拍一张照片让我确认下吗？",
            "好的",String.valueOf(R.mipmap.xieziao),"您好，根据您的状况，我推荐您采用针灸疗法请问您是否接受？","好的","我已经向系统提交了您的预约申请，您可以在\"我的预约\"中查看，也可单击此处继续"};
    int flag[]={0,1,0,2,1,0,1};

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_chat);

        relativeLayout=(RelativeLayout)findViewById(R.id.activity_chat_layout);
        relativeLayout.setFocusable(true);
        relativeLayout.setFocusableInTouchMode(true);
        relativeLayout.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                relativeLayout.setFocusable(true);
                relativeLayout.setFocusableInTouchMode(true);
                relativeLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                return false;
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        btn_send = (Button) this.findViewById(R.id.btn_send);
        et_content = (EditText) this.findViewById(R.id.et_content);

        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btn_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter<7){
                    counter++;
                    adapter.notifyItemInserted(counter-1);
                    adapter.notifyItemRangeChanged(counter-1,1);
                    recyclerView.smoothScrollToPosition(counter-1);
                }
            }

        });

        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }



    class MessageAdapter extends RecyclerView.Adapter {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Holder holder;
            HolderPic holderpic;
            switch (viewType){
                case 0:
                    holder=new Holder(LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_message_sent,parent,false));
                    return holder;
                case 1:
                    holder=new Holder(LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_message_received,parent,false));
                    return holder;
                case 2:
                    holderpic=new HolderPic(LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_message_sent_pic,parent,false));
                    return holderpic;
                case 3:
                    holderpic=new HolderPic(LayoutInflater.from(ChatActivity.this).inflate(R.layout.item_message_received_pic,parent,false));
                    return holderpic;
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(flag[position]<2){
                ((Holder)holder).tv.setText(conver[position]);
            }
            else {
                ((HolderPic)holder).iv.setAdjustViewBounds(true);
                ((HolderPic)holder).iv.setBackgroundResource(Integer.parseInt(conver[position]));
            }
            if(position==flag.length-1){
                ((Holder)holder).tv.setTextColor(Color.BLUE);
                ((Holder)holder).tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ChatActivity.this, MainAppointment.class));
                    }
                });
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ChatActivity.this, MainAppointment.class));
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return counter;
        }

        @Override
        public int getItemViewType(int position){
            return flag[position];
        }
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView tv;
        public Holder(View v) {
            super(v);
            tv=(TextView)v.findViewById(R.id.tv_chatcontent);
        }
    }
    public class HolderPic extends RecyclerView.ViewHolder{

        ImageView iv;
        public HolderPic(View v) {
            super(v);
            iv=(ImageView) v.findViewById(R.id.tv_chatcontent);
        }
    }

}
