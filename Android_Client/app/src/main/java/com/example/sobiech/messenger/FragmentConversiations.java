package com.example.sobiech.messenger;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Modules.CommunicationModule;
import Modules.ProfileModule;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentConversiations extends Fragment{

    private CommunicationModule communicationModule;
    private ProfileModule profileModule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        ListView listView = rootView.findViewById(R.id.lvConversations);

        List <DTO.Message> listConversations = new ArrayList<>();

        communicationModule = CommunicationModule.getInstance();
        profileModule = ProfileModule.getInstance();

        String [] friendList = profileModule.getFriendsList();
        for (String friend : friendList) {
            listConversations.add(communicationModule.getLastMessageContentFromConversation(friend));
        }

        AdapterConversations adapter = new AdapterConversations(getActivity(), R.layout.conversation_listview, listConversations);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(rootView.getContext(), OneConversationActivity.class);
                startActivityForResult(intent,0);
            }
        });

        return rootView;
    }

}
