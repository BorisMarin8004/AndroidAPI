package com.example.androidapi.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androidapi.DataClasses.Post;
import com.example.androidapi.R;

import java.util.List;

/**
 * PostsListViewAdapter specifies how Post will be displayed in ListView
 */
public class PostsListViewAdapter extends ArrayAdapter<Post> {

    private int resource;
    private Context context;

    public PostsListViewAdapter(Context context, int resource, List<Post> items) {
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

        Post post = getItem(position);

        if (post != null) {
            TextView itemTitle = v.findViewById(R.id.ListItemTitle);
            TextView itemBody = v.findViewById(R.id.ListItemBody);

            if (itemTitle != null) {
                itemTitle.setText(post.getTitle());
            }

            if (itemBody != null) {
                itemBody.setText(post.getBody());
            }
        }

        return v;
    }

}
