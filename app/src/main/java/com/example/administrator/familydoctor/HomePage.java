package com.example.administrator.familydoctor;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.familydoctor.JiangTang.JiangTang;
import com.example.administrator.familydoctor.Main.UserUI;
import com.example.administrator.familydoctor.Personal.Personal;
import com.example.administrator.familydoctor.WenZhen.WenZhen;
import com.example.administrator.familydoctor.toolClass.BottomNavigationViewHelper;

public class HomePage extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    public String username;
    BottomNavigationView bottomNavigationView;
    FragmentManager fm;
    FragmentTransaction transaction;

    int flag;

    String needPermissions[]={Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        username=getSharedPreferences("userinfo",MODE_PRIVATE).getString("username","");

        flag=getIntent().getIntExtra("flag",1);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        for(int i=0;i<needPermissions.length;i++){
            if (ContextCompat.checkSelfPermission(HomePage.this, needPermissions[i])
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.shouldShowRequestPermissionRationale(HomePage.this,needPermissions[i]);
                ActivityCompat.requestPermissions(HomePage.this, needPermissions, i);//自定义的code
                //MPermissions.requestPermissions(HomePage.this, i, needPermissions[i]);
            }
            //适配小米机型
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), getPackageName());
            if (checkOp == AppOpsManager.MODE_IGNORED) {
                ActivityCompat.requestPermissions(HomePage.this,
                        new String[]{needPermissions[i]}, i);
            }
        }

        flag=getIntent().getIntExtra("flag",1);
        fm=getSupportFragmentManager();
        transaction=fm.beginTransaction();
        switch (flag){
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.item_zhuye);
                transaction.replace(R.id.detail,new UserUI());
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.item_wenzhen);
                transaction.replace(R.id.detail,new WenZhen());
                break;
            case 3:
                bottomNavigationView.setSelectedItemId(R.id.item_jiangtang);
                transaction.replace(R.id.detail,new JiangTang());
                break;
            case 4:
                bottomNavigationView.setSelectedItemId(R.id.item_personal);
                transaction.replace(R.id.detail,new Personal());
        }
        transaction.commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction=fm.beginTransaction();
                switch (item.getItemId()){
                    case R.id.item_zhuye:
                        transaction.replace(R.id.detail,new UserUI());
                        break;
                    case R.id.item_wenzhen:
                        if(username==null){
                            Toast.makeText(HomePage.this,"请先登录",Toast.LENGTH_SHORT).show();
                        }else {
                            transaction.replace(R.id.detail,new WenZhen());
                        }
                        break;
                    case R.id.item_jiangtang:
                        transaction.replace(R.id.detail,new JiangTang());
                        break;
                    case R.id.item_personal:
                        transaction.replace(R.id.detail,new Personal());
                        break;
                    default:
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

//        flag=getIntent().getIntExtra("flag",1);
//        transaction=fm.beginTransaction();
//        switch (flag){
//            case 1:
//                bottomNavigationView.setSelectedItemId(R.id.item_zhuye);
//                transaction.replace(R.id.detail,new UserUI());
//                break;
//            case 2:
//                bottomNavigationView.setSelectedItemId(R.id.item_wenzhen);
//                transaction.replace(R.id.detail,new WenZhen());
//                break;
//            case 3:
//                bottomNavigationView.setSelectedItemId(R.id.item_jiangtang);
//                transaction.replace(R.id.detail,new JiangTang());
//                break;
//            case 4:
//                bottomNavigationView.setSelectedItemId(R.id.item_personal);
//                transaction.replace(R.id.detail,new Personal());
//        }
//
//        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//            if (grantResults!=null&&grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//
//                boolean isSecondRequest = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);
//                if (isSecondRequest)
//                /**重新请求授予权限，显示权限说明（该说明属于系统UI内容，区别第一次弹窗）**/
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
//                else
//                    Toast.makeText(this, "权限被禁用，请在权限管理修改", Toast.LENGTH_SHORT).show();
//            }
        System.out.println("requestCode"+requestCode);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
