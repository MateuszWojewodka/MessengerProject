package com.example.sobiech.messenger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentConversiations extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversations, container, false);

        ListView listView = rootView.findViewById(R.id.lvConversations);

        List <ConversationListObj> listConversations = new ArrayList<>();

        /**
         *
         * adding a list of conversations from the server database
         *
         * */

        AdapterConversations adapter = new AdapterConversations(getActivity(), R.layout.conversation_listview, listConversations);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /**
                 * jump to messageActivity
                 */
            }
        });

        return rootView;
    }

}
