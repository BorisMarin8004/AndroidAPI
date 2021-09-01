package com.example.androidapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapi.DataClasses.User;
import com.example.androidapi.R;

import java.util.List;

public class UsersListViewAdapter extends ArrayAdapter<User> {

    private int resource;
    private Context context;

    public UsersListViewAdapter(Context context, int resource, List<User> items) {
        super(context, resource, items);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resource, null);
        }

        User user = getItem(position);

        if (user != null) {
            TextView itemTitle = v.findViewById(R.id.ListItemTitle);
            TextView itemBody = v.findViewById(R.id.ListItemBody);

            if (itemTitle != null) {
                itemTitle.setText(user.getUsername());
            }

            if (itemBody != null) {
                itemBody.setText(user.getPassword());
            }
        }

        return v;
    }
}
