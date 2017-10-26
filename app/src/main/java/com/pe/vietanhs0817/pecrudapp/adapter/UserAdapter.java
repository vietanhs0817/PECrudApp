package com.pe.vietanhs0817.pecrudapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pe.vietanhs0817.pecrudapp.R;
import com.pe.vietanhs0817.pecrudapp.models.User;

import java.util.List;

/**
 * Created by vietanhs0817 on 10/26/2017.
 */

public class UserAdapter extends BaseAdapter {

    private List<User> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public UserAdapter(Context aContext, List<User> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item, null);
        TextView lblEmail = (TextView) convertView.findViewById(R.id.lbl_email);
        TextView lblName = (TextView) convertView.findViewById(R.id.lbl_name);
        TextView lblPassword = (TextView) convertView.findViewById(R.id.lbl_password);


        User user = this.listData.get(position);
        lblEmail.setText(user.getEmail());
        lblName.setText(user.getFullName());
        lblPassword.setText(user.getPassword());

        return convertView;
    }
}
