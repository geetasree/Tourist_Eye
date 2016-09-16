package com.example.welcome.travelmatev11.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.welcome.travelmatev11.Adapters.CustomUserAdapter;
import com.example.welcome.travelmatev11.DBCRUD.UserManager;
import com.example.welcome.travelmatev11.Model.UserInfo;
import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;

import java.util.ArrayList;

public class LogInActivity extends AppCompatActivity {
    EditText logInName;
    EditText password;
    MyPreference preference;
    UserManager manager;
    ListView lstUserInfo;
    ArrayList<UserInfo> arrUserInfoList;
    CustomUserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logInName=(EditText)findViewById(R.id.edtLoginName);
        password=(EditText)findViewById(R.id.edtPassword);
        preference=new MyPreference(this);
        manager=new UserManager(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void listData(){
        arrUserInfoList=manager.getAllUserInfo();
        userAdapter=new CustomUserAdapter(LogInActivity.this, arrUserInfoList);
        lstUserInfo.setAdapter(userAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }


    public void logIn(View view) {
        String logName = logInName.getText().toString();
        String pass = password.getText().toString();

        UserInfo arr = manager.getUserInfo(logName);

        if (pass.equals(arr.getPassword()))
        {
            Log.d("EMAIL: ",arr.geteMail());
            //Log.d("picpath: ",arr.getPicpath());
            preference.saveUserData(arr.getUserName(),arr.getPassword(),arr.geteMail(),arr.getGender(),arr.getPicpath());
            Intent intent=new Intent(LogInActivity.this,EventListActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            TextView errorLogin= (TextView)findViewById(R.id.loginError);
            errorLogin.setVisibility(View.VISIBLE);
        }
    }

    public void ree(View view) {
        Intent intent=new Intent(LogInActivity.this,RegistryActivity.class);
        startActivity(intent);
    }
}
