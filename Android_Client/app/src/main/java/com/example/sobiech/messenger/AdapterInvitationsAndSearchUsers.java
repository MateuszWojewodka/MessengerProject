package com.example.sobiech.messenger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WOJTEK on 2018-01-14.
 */

public class AdapterInvitationsAndSearchUsers extends ArrayAdapter <String> {

    public enum Type {
        INVITATION,
        SEARCH_USERS;
    }

    List<String> users = new ArrayList<>();
    Context context;
    int resource;
    private Type type;

    public AdapterInvitationsAndSearchUsers(@NonNull Context context, int resource, @NonNull List<String> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String oneUser = users.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.invitation_and_search_users_listview, parent, false);

        TextView tvUserName = convertView.findViewById(R.id.tvUName);
        ImageButton btAccept = convertView.findViewById(R.id.btAccept);

        tvUserName.setText(oneUser);

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == Type.INVITATION) {
                    //TODO ACCEPT INVITATION
                }
                if (type == Type.SEARCH_USERS) {
                    //TODO SEND INVITATION
                }
            }
        });

        return convertView;
    }

    public void setType (Type type) {
        this.type = type;
    }
}
