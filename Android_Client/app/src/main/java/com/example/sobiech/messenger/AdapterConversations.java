package com.example.sobiech.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdapterConversations extends ArrayAdapter<ConversationListObj>{

    List<ConversationListObj> conversiationsObj = new ArrayList<>();
    Context context;
    int resource;

    public AdapterConversations(Context context, int resource, List<ConversationListObj> conversationsObj) {
        super(context, resource, conversationsObj);
        this.context = context;
        this.resource = resource;
        this.conversiationsObj = conversationsObj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ConversationListObj conversation = conversiationsObj.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.conversation_listview, parent, false);

        TextView tvUsername = convertView.findViewById(R.id.tvUsername);
        TextView tvLastMessage = convertView.findViewById(R.id.tvLastMessage);
        TextView tvTime = convertView.findViewById(R.id.tvTime);

        tvUsername.setText(conversation.getUsername());
        tvLastMessage.setText(conversation.getLastMessageSender() + ": " + conversation.getLastMessageContent());
        tvTime.setText(conversation.getLastMessageTime());

        return convertView;
    }
}
