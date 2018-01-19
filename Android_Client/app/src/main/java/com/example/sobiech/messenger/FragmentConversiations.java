package com.example.sobiech.messenger;


import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.Arrays;
import java.util.List;

import DTO.Message;
import Database.Database;
import Helpers.UserAndMessage;
import Modules.CommunicationModule;
import Modules.ProfileModule;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentConversiations extends Fragment{

    private CommunicationModule communicationModule;
    private ProfileModule profileModule;

    String []friendsList = null;
    List <UserAndMessage> listConversations = new ArrayList<>();
    AdapterConversations adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        ListView listView = rootView.findViewById(R.id.lvConversations);

        communicationModule = CommunicationModule.getInstance();
        profileModule = ProfileModule.getInstance();

        adapter = new AdapterConversations(getActivity(), R.layout.conversation_listview, listConversations);
        listView.setAdapter(adapter);

        tryGetListConverstaions();

        //TODO OPEN SCREEN WITH CORRECT CONVERSATION
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(rootView.getContext(), OneConversationActivity.class);
                startActivityForResult(intent,0);
            }
        });

        return rootView;
    }

    private void tryGetListConverstaions() {

        class GetListConversationsAsyncTask extends AsyncTask<Void, Integer, UserAndMessage[]> {

            @Override
            protected UserAndMessage[] doInBackground(Void... voids) {
                listConversations.clear();
                friendsList = profileModule.getFriendsList();
                UserAndMessage[] result = new UserAndMessage[friendsList.length];
                for (int i = 0 ; i < friendsList.length ; i++) {
                    DTO.Message message = communicationModule.getLastMessageContentFromConversation(friendsList[i]);
                    String user = friendsList[i];
                    result[i] = new UserAndMessage(user, message);
                }
                return result;
            }

            @Override
            protected void onPostExecute(UserAndMessage[] messages) {
                super.onPostExecute(messages);
                listConversations.clear();
                listConversations.addAll(new ArrayList<>(Arrays.asList(messages)));
                adapter.notifyDataSetChanged();
            }
        }

        new GetListConversationsAsyncTask().execute();
    }
}
