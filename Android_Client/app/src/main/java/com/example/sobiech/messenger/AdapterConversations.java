package com.example.sobiech.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdapterConversations extends ArrayAdapter<DTO.Message>{

    List<DTO.Message> conversiationsObj = new ArrayList<>();
    Context context;
    int resource;

    public AdapterConversations(Context context, int resource, List<DTO.Message> conversationsObj) {
        super(context, resource, conversationsObj);
        this.context = context;
        this.resource = resource;
        this.conversiationsObj = conversationsObj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DTO.Message conversation = conversiationsObj.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.conversation_listview, parent, false);

        TextView tvFriendName = convertView.findViewById(R.id.tvFriendName);
        TextView tvLastMessage = convertView.findViewById(R.id.tvLastMessage);

        if (conversation.getSender().equals(LoginRegisterActivity.userName))
            tvFriendName.setText(conversation.getReceiver());
        else
            tvFriendName.setText(conversation.getSender());
        tvLastMessage.setText(conversation.getSender() + ": " + conversation.getMessageContent());

        return convertView;
    }
}
