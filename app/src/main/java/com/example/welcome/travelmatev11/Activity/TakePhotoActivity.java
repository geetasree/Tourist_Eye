package com.example.welcome.travelmatev11.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.welcome.travelmatev11.DBCRUD.ImageManager;
import com.example.welcome.travelmatev11.Model.ImageInfo;
import com.example.welcome.travelmatev11.R;
import com.example.welcome.travelmatev11.SharedPreference.MyPreference;

public class TakePhotoActivity extends AppCompatActivity {

    private EditText caption;
    private Uri mCapturedImageURI;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageManager iManager;
    private AppCompatImageView imgView;
    private String picturePath;
    private Button save;
    int eventId;
    MyPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        Intent intent=getIntent();
        eventId= intent.getIntExtra("EVENTID",-1);

        initialization();
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        activeTakePhoto();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_logout) {
            preference.setUserName("");
            Intent intent=new Intent(this,StartActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MemoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    private void initialization() {
        caption = (EditText) findViewById(R.id.caption);
        imgView = (AppCompatImageView) findViewById(R.id.imgView);
        save = (Button) findViewById(R.id.button_save);
        iManager = new ImageManager(this);
        preference=new MyPreference(this);
    }

    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE &&
                        resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor =
                            managedQuery(mCapturedImageURI, projection, null,
                                    null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    picturePath = cursor.getString(column_index_data);


                    imgView.setImageURI(mCapturedImageURI);

                }

        }
    }

    public void saveImageInDatabase(View view) {

        ImageInfo image = new ImageInfo(preference.getUserName(),caption.getText().toString(),
                picturePath, System.currentTimeMillis(),eventId);
        boolean inserted = iManager.addImage(image);
        if (inserted) {
            Toast.makeText(TakePhotoActivity.this, "Image Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TakePhotoActivity.this, "Image is not Inserted", Toast.LENGTH_SHORT).show();
        }
        caption.setText("");
        imgView.setImageURI(null);
        Intent intent = new Intent(TakePhotoActivity.this, MemoryActivity.class);
        intent.putExtra("EVENTID",eventId);
        startActivity(intent);
        finish();
    }
}
