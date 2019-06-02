package com.example.administrator.familydoctor.Personal;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.familydoctor.R;


public class Register extends AppCompatActivity {


    RelativeLayout relativeLayout;
    Button sure;
    ImageView back;
    EditText username_input;
    EditText password_input,password_input_double;

    UserDatabaseHelper dbh;
    SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        relativeLayout=(RelativeLayout)findViewById(R.id.activity_register);
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

        username_input=(EditText)findViewById(R.id.username_input);
        password_input=(EditText)findViewById(R.id.password_input);
        password_input_double=(EditText)findViewById(R.id.password_input_double);


        dbh=new UserDatabaseHelper(this,"SWX.db",null,1);
        sqldb=dbh.getWritableDatabase();


        sure=(Button)findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(username_input.getText().length()!=0&&password_input.getText().length()!=0&&password_input_double.getText().toString().equals(password_input.getText().toString())) {
                    Cursor cursor=sqldb.query("User",new String[]{"id"},"id=?",new String[]{username_input.getText().toString()},null,null,null);
                    if(cursor.getCount()==0){
                        ContentValues contentValues=new ContentValues();
                        contentValues.put("id",username_input.getText().toString());
                        contentValues.put("password",password_input.getText().toString());
                        sqldb.insert("User",null,contentValues);

                        SharedPreferences.Editor editor=getSharedPreferences("userinfo",MODE_PRIVATE).edit();
                        editor.putString("username",username_input.getText().toString());
                        editor.commit();
                        Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();

                        finish();
                    }else {
                        Toast.makeText(Register.this,"该用户名已存在",Toast.LENGTH_SHORT).show();
                    }

                }
                else if(username_input.getText().length()==0){
                    Toast.makeText(Register.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }

                else if(password_input.getText().length()==0){
                    Toast.makeText(Register.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

                else if(!password_input_double.getText().toString().equals(password_input.getText().toString())){
                    Toast.makeText(Register.this, "密码不一致，请核对输入", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
