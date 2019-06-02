package com.example.administrator.familydoctor.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.administrator.familydoctor.Main.Body.Body;
import com.example.administrator.familydoctor.Main.FindDoctor.FindDoctor;
import com.example.administrator.familydoctor.Main.GuaHao.GuaHao;
import com.example.administrator.familydoctor.Main.HealthInfo.HealthInfo;
import com.example.administrator.familydoctor.Main.Near.Near;
import com.example.administrator.familydoctor.R;
import com.example.administrator.familydoctor.toolClass.BottomNavigationViewHelper;
import com.example.administrator.familydoctor.toolClass.SearchView;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UserUI#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserUI extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    BottomNavigationView bottomNavigationView;
    RelativeLayout moreinfo;
    LinearLayout linearLayout;
    EditText editText;
    ImageView delete;
    ConvenientBanner convenientBanner;

    Integer integer[]=new Integer[]{R.mipmap.lishizheng,R.mipmap.shanggutianzhenlun,R.mipmap.door,R.mipmap.bamboo};

    View view;

    public UserUI() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserUI.
     */
    // TODO: Rename and change types and number of parameters
    public static UserUI newInstance(String param1, String param2) {
        UserUI fragment = new UserUI();
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
        view=inflater.inflate(R.layout.fragment_user_ui, container, false);

        linearLayout=(LinearLayout) view.findViewById(R.id.user_ui);
        delete=(ImageView)view.findViewById(R.id.search_iv_delete);
        editText=(EditText)view.findViewById(R.id.search_et_input);

        new SearchView(editText,delete,new View[]{linearLayout}).init(getActivity());


        bottomNavigationView=(BottomNavigationView)view.findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_doctor:
                        startActivity(new Intent(getContext(), FindDoctor.class));
                        break;
                    case R.id.item_guahao:
                        startActivity(new Intent(getContext(), GuaHao.class));
                        break;
                    case R.id.item_near:
                        startActivity(new Intent(getContext(), Near.class));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        convenientBanner=(ConvenientBanner)view.findViewById(R.id.banner);
        convenientBanner.startTurning(4000);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, Arrays.asList(integer))
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        moreinfo=(RelativeLayout)view.findViewById(R.id.more_info);
        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HealthInfo.class));
            }
        });

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

}
