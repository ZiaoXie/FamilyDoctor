package com.example.administrator.familydoctor.Personal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.familydoctor.Personal.appointment.MainAppointment;
import com.example.administrator.familydoctor.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Personal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Personal extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    CircleImageView circleImageView;
    View view;

    String username;

    String titles[]={"我的档案","体质测试","慢病管理","咨询客服","我的预约"};
    Integer picture[]={R.mipmap.document,R.mipmap.heat,R.mipmap.clock,R.mipmap.server,R.mipmap.doctor};

    public Personal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Personal.
     */
    // TODO: Rename and change types and number of parameters
    public static Personal newInstance(String param1, String param2) {
        Personal fragment = new Personal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_personal, container, false);

        username=getContext().getSharedPreferences("userinfo",getContext().MODE_PRIVATE).getString("username","");

        circleImageView=(CircleImageView)view.findViewById(R.id.userhead);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume(){
        super.onResume();

        username=getContext().getSharedPreferences("userinfo",getContext().MODE_PRIVATE).getString("username","");
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username==""){
                    startActivity(new Intent(getContext(),login.class));
                }else {
                    startActivity(new Intent(getContext(),PersonalInfo.class));
                }

            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Holder holder=new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.item_personal,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
            ((Holder)holder).iv.setBackgroundResource(picture[position]);
            ((Holder)holder).tv.setText(titles[position]);
            ((Holder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 4:
                            startActivity(new Intent(getContext(), MainAppointment.class));
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        class Holder extends RecyclerView.ViewHolder{
            ImageView iv;
            TextView tv;

            public Holder(View v) {
                super(v);
                iv=(ImageView) v.findViewById(R.id.picture);
                tv=(TextView)v.findViewById(R.id.item_person);
            }
        }
    }

}
