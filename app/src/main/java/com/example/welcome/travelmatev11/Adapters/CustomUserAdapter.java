package com.example.welcome.travelmatev11.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.welcome.travelmatev11.Model.UserInfo;
import com.example.welcome.travelmatev11.R;

import java.util.ArrayList;

/**
 * Created by Mobile App Develop on 12-7-16.
 */
public class CustomUserAdapter extends ArrayAdapter {
    private UserInfo userInfo;
    private TextView firstColumnTxt;
    private TextView secondColumTxt;
    private TextView thirdColumnTxt;
    private TextView fourthColumnTxt;
    private TextView fifthColumnTxt;
    private ArrayList<UserInfo> userList;
    private Context context;

    public CustomUserAdapter(Context context, ArrayList<UserInfo> userList) {

        super(context, R.layout.row_style,userList);
        this.context=context;
        this.userList=userList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.row_style,null);
        /*firstColumnTxt=(TextView)convertView.findViewById(R.id.firstColumn);
        secondColumTxt=(TextView)convertView.findViewById(R.id.secondColum);
        thirdColumnTxt=(TextView)convertView.findViewById(R.id.thirdColumn);
        fourthColumnTxt=(TextView)convertView.findViewById(R.id.fourthColumn);
        fifthColumnTxt=(TextView)convertView.findViewById(R.id.fifthColumn);*/

        firstColumnTxt.setText(userList.get(position).getUserName());
        secondColumTxt.setText(userList.get(position).getPassword());
        thirdColumnTxt.setText(userList.get(position).geteMail());
        fourthColumnTxt.setText(userList.get(position).getGender());
//        fifthColumnTxt.setText(userList.get(position).getBirthDay()+"");

        return convertView;
    }

}
