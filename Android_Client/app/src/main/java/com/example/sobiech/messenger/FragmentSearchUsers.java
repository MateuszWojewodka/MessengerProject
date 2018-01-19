package com.example.sobiech.messenger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Modules.AuthenticationModule;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentSearchUsers extends Fragment {

    private AuthenticationModule authenticationModule = AuthenticationModule.getInstance();
    private List<String> allUsers = new ArrayList<>();
    private AdapterInvitationsAndSearchUsers  adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search_users, container, false);

        ListView lvSearchUsers = rootView.findViewById(R.id.lvSearchUsers);

        adapter = new AdapterInvitationsAndSearchUsers(getActivity(), R.layout.invitation_and_search_users_listview, allUsers);
        adapter.setType(AdapterInvitationsAndSearchUsers.Type.SEARCH_USERS);
        lvSearchUsers.setAdapter(adapter);

        trySearchAllUsers();

        return rootView;
    }

    private void trySearchAllUsers() {

        class LogUserInAsyncTask extends AsyncTask<Void, Integer, String[]> {

            @Override
            protected String[] doInBackground(Void... voids) {
                return authenticationModule.getAllRegisteredUsers();
            }

            @Override
            protected void onPostExecute(String[] strings) {
                super.onPostExecute(strings);
                allUsers.clear();
                allUsers.addAll(new ArrayList<String>(Arrays.asList(strings)));

                adapter.notifyDataSetChanged();
            }
        }

        new LogUserInAsyncTask().execute();
    }
}
