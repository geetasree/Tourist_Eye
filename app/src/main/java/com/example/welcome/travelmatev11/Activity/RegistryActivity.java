package com.example.welcome.travelmatev11.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.welcome.travelmatev11.DBCRUD.UserManager;
import com.example.welcome.travelmatev11.Model.UserInfo;
import com.example.welcome.travelmatev11.R;

import java.io.IOException;

public class RegistryActivity extends AppCompatActivity {

    private EditText regUserNameEdt;
    private EditText regPasswordEdt;
    private EditText regConfirmPasswordEdt;
    private EditText regEmailEdt;
    private UserManager manager;
    private RadioGroup regGenderGroup;
    private RadioButton regGenderButton;
    private ImageView userPicImg;
    private int PICK_IMAGE_REQUEST;
    private String picturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
        initializing();

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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,LogInActivity.class);
        startActivity(intent);
        finish();
    }

    private void initializing() {
        regUserNameEdt=(EditText)findViewById(R.id.edtRegLogIn);
        regPasswordEdt=(EditText)findViewById(R.id.edtRegPassword);
        regConfirmPasswordEdt=(EditText)findViewById(R.id.edtRegPasswordConfirmation);
        regEmailEdt=(EditText)findViewById(R.id.edtRegEmail);
        regGenderGroup=(RadioGroup)findViewById(R.id.rdgGender);
        userPicImg=(ImageView) findViewById(R.id.imgPic);
        manager=new UserManager(this);
        PICK_IMAGE_REQUEST = 1;
        picturePath="@drawable/userimage";
    }

    public void registration(View view) {
        String userName = regUserNameEdt.getText().toString();
        String password = regPasswordEdt.getText().toString();
        String confirmPassword = regConfirmPasswordEdt.getText().toString();
        String email = regEmailEdt.getText().toString();
        String gender="";
        String picPath=picturePath;
        int selectedId=regGenderGroup.getCheckedRadioButtonId();
        regGenderButton = (RadioButton) findViewById(selectedId);

        if(regGenderButton.equals("Male")){
            gender = "Male";
        } else if(regGenderButton.equals("Female")){
            gender = "FeMale";
        }

        if(onCheckFieldValidity(userName,password,confirmPassword,email)){

            if(confirmPassword.equals(password)){
                Log.d("USERNAME:",userName);
                Log.d("Upassword:",password);
                Log.d("Ugender:",gender);
                Log.d("Uemail:",email);
                Log.d("UpicPath:",picPath);
                UserInfo userInfo=new UserInfo(userName,password,gender,email,picPath);
                boolean bf=manager.addUserInfo(userInfo);
                if (bf) {
                    Toast.makeText(RegistryActivity.this, "USER Registration Complete", Toast.LENGTH_SHORT).show();
                    Intent in=new Intent(this,LogInActivity.class);
                    startActivity(in);
                } else {
                    Toast.makeText(RegistryActivity.this, "USER Registration Failed ", Toast.LENGTH_SHORT).show();
                }
            }
            else
                regPasswordEdt.setError("Password Not matched");
        }
    }

    private boolean onCheckFieldValidity(String userName, String password, String confirmPassword, String email) {
        int error = 0;

        int count=manager.getUserCount(userName);
        if (count>0)
            regUserNameEdt.setError("Username Already used");

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern))
        {
            regEmailEdt.setError("Invalid email address");
            error ++;
        }

        if(userName.trim().equals("")){
            error ++;
            regUserNameEdt.setError("Empty");
        }
        if(password.trim().equals("")){
            error ++;
            regPasswordEdt.setError("Empty");
        }
        if(confirmPassword.trim().equals("")){
            error ++;
            regConfirmPasswordEdt.setError("Empty");
        }
        if(email.trim().equals("")){
            error ++;
            regEmailEdt.setError("Empty");
        }
        if(error == 0){
            return true;
        }else{
            return false;
        }
    }

    public void choosePicture(View view) {

        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            String[] projection = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            Log.d("VALUE", DatabaseUtils.dumpCursorToString(cursor));

            int columnIndex = cursor.getColumnIndex(projection[0]);
            picturePath = cursor.getString(columnIndex); // returns null
            Log.d("picturePath", picturePath);
            cursor.close();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                 Log.d("bitmap", String.valueOf(bitmap));


                userPicImg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
