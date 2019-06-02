package com.example.administrator.familydoctor.Personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.familydoctor.R;


public class login extends Activity {
	Intent intent;
	int k;
	Button sure;
	TextView register;
	ImageView back;
	EditText username_input;
	EditText password_input;

	RelativeLayout relativeLayout;

	UserDatabaseHelper dbh;
	SQLiteDatabase sqldb;

	Message msg;
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what){
				case 1:
					Toast.makeText(login.this,(String)msg.obj , Toast.LENGTH_SHORT).show();
					break;
				case 2:
					username_input.setText(String.valueOf(k));
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

		register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,Register.class));
            }
        });

		relativeLayout=(RelativeLayout)findViewById(R.id.activity_login);
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



//		register.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				register.setText("退出");
//				sure.setText("注册");
//				username_input.setEnabled(false);
//				password_input.setText("");
//
//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						result= DataBaseHelper.Query("select userid from users",1);
//						if(result==null) k=1000;
//						else k=1000+result.length;
//						msg=new Message();
//						msg.what=2;
//						handler.sendMessage(msg);
//						intent.putExtra("username",String.valueOf(k));
//					}
//				}).start();
//
//				sure.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						if(password_input.getText().length()!=0)
//						{
//							new Thread(new Runnable() {
//								@Override
//								public void run() {
//									msg=new Message();
//									msg.what=2;
//									if(!DataBaseHelper.Execute("insert into users(userid,password) values('" + k+ "','" + password_input.getText().toString() + "')")){
//										msg.obj="注册失败";
//										return;
//									}
//									else msg.obj="注册成功";
//									handler.sendMessage(msg);
//
//									startActivity(intent);
//									login.this.finish();
//								}
//							}).start();
//						}
//						else
//							Toast.makeText(login.this, "密码不能为空", Toast.LENGTH_SHORT).show();
//					}
//				});
//			}
//		});

		dbh=new UserDatabaseHelper(this,"SWX.db",null,1);
		sqldb=dbh.getWritableDatabase();

		sure=(Button)findViewById(R.id.sure);
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(username_input.getText().length()!=0&&password_input.getText().length()!=0) {
					Cursor cursor=sqldb.query("User", null, "id=?",new String[]{username_input.getText().toString()}, null, null, null);
					if(cursor.getCount()==0) {
						Toast.makeText(login.this, "该账号不存在", Toast.LENGTH_SHORT).show();
					}
					else
					{
						cursor.moveToFirst();
						String a=cursor.getString(cursor.getColumnIndex("password"));
						String b=password_input.getText().toString();

						if(a.equals(b)) {
							SharedPreferences.Editor editor=getSharedPreferences("userinfo",MODE_PRIVATE).edit();
							editor.putString("username",username_input.getText().toString());
							editor.commit();
							finish();
						} else
						{
							Toast.makeText(login.this, "密码不正确", Toast.LENGTH_SHORT).show();
						}
					}
				} else if(username_input.getText().length()==0){
					Toast.makeText(login.this, "请输入用户名", Toast.LENGTH_SHORT).show();
				} else if(password_input.getText().length()==0) {
					Toast.makeText(login.this, "请输入密码", Toast.LENGTH_SHORT).show();
				}
			}

		});

	}
}