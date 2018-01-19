package com.example.sobiech.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Helpers.UserAndMessage;


public class AdapterConversations extends ArrayAdapter<UserAndMessage>{

    List<UserAndMessage> conversiationsObj = new ArrayList<>();
    Context context;
    int resource;

    public AdapterConversations(Context context, int resource, List<UserAndMessage> conversationsObj) {
        super(context, resource, conversationsObj);
        this.context = context;
        this.resource = resource;
        this.conversiationsObj = conversationsObj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserAndMessage conversation = conversiationsObj.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.conversation_listview, parent, false);

        TextView tvFriendName = convertView.findViewById(R.id.tvFriendName);
        TextView tvLastMessage = convertView.findViewById(R.id.tvLastMessage);

        tvFriendName.setText(conversation.user);
        tvLastMessage.setText(conversation.message.getSender() + ": " + conversation.message.getMessageContent());

        return convertView;
    }
}
