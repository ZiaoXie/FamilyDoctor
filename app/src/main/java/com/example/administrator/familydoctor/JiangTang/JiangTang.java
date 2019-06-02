package com.example.administrator.familydoctor.JiangTang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.example.administrator.familydoctor.R;
import com.example.administrator.familydoctor.toolClass.DatabaseUtil;
import com.example.administrator.familydoctor.toolClass.SearchView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link JiangTang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JiangTang extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RelativeLayout relativeLayout;
    EditText editText;
    ImageView delete;
    RecyclerView recyclerView;

    private final String DATABASE_PATH = "/data/data/com.example.administrator.familydoctor" + "/databases";
    //private final String DATABASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/databases";
    private final String DATABASE_FILENAME = "needle.db";

    ArticleDatabaseHelper databaseHelper;
    SQLiteDatabase sqldb;

    Integer integer[]=new Integer[]{R.mipmap.a0000,R.mipmap.a0001,R.mipmap.a0002,R.mipmap.a0003,R.mipmap.a0004,R.mipmap.a0005,R.mipmap.a0006};
    ArrayList<String> titles=new ArrayList<String>();
    ArrayList<String> abstr=new ArrayList<String>();

    View view;

    public JiangTang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JiangTang.
     */
    // TODO: Rename and change types and number of parameters
    public static JiangTang newInstance(String param1, String param2) {
        JiangTang fragment = new JiangTang();
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
        view=inflater.inflate(R.layout.fragment_jiang_tang, container, false);


        DatabaseUtil util = new DatabaseUtil(getContext(),"needle.db");
        // 判断数据库是否存在
        boolean dbExist = util.checkDataBase();

        if (dbExist) {
            Log.i("tag", "The database is exist.");
        } else {// 不存在就把raw里的数据库写入手机
            try {
                util.copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

        String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
        sqldb = SQLiteDatabase.openDatabase(databaseFilename, null,
                SQLiteDatabase.OPEN_READONLY);


//        databaseHelper=new ArticleDatabaseHelper(getContext(),"needle.db",null,1);
//        if(databaseHelper==null){
//            System.out.println("数据库空");
//        }
//        sqldb=databaseHelper.getWritableDatabase();
        Cursor cursor=sqldb.query("jiangtang",new String[]{"id","title","Abstract"},null,null,null,null,null);
        cursor.moveToFirst();
        do{
            titles.add(cursor.getString(1));
            abstr.add(cursor.getString(2));
        } while (cursor.moveToNext());
        System.out.println(cursor.getCount());

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));



        relativeLayout=(RelativeLayout) view.findViewById(R.id.jiang_tang);
        delete=(ImageView)view.findViewById(R.id.search_iv_delete);
        editText=(EditText)view.findViewById(R.id.search_et_input);

        new SearchView(editText,delete,new View[]{relativeLayout,recyclerView}).init(getActivity());
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public class NetworkImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }


    class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Holder holder=new Holder(LayoutInflater.from(getActivity()).inflate(R.layout.item_jiang_tang,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Holder)holder).iv.setImageResource(integer[position]);
            ((Holder)holder).title.setText(titles.get(position));
            ((Holder)holder).abst.setText(abstr.get(position));
        }

        @Override
        public int getItemCount() {
            return titles.size();
        }

        class Holder extends RecyclerView.ViewHolder{
            ImageView iv;
            TextView title,abst;

            public Holder(View v) {
                super(v);
                iv=(ImageView) v.findViewById(R.id.articlepic);
                abst=(TextView)v.findViewById(R.id.articleabstract);
                title=(TextView)v.findViewById(R.id.title);
            }
        }
    }


}
