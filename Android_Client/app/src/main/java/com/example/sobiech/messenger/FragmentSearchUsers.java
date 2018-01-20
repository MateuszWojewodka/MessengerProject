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

import Helpers.UserAndFriendFlag;
import Modules.AuthenticationModule;
import Modules.ProfileModule;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentSearchUsers extends Fragment {

    private AuthenticationModule authenticationModule = AuthenticationModule.getInstance();
    private ProfileModule profileModule = ProfileModule.getInstance();
    private List<UserAndFriendFlag> allUsers = new ArrayList<>();
    private AdapterInvitationsAndSearchUsers  adapter;
    private ListView lvSearchUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search_users, container, false);

        lvSearchUsers = rootView.findViewById(R.id.lvSearchUsers);

        adapter = new AdapterInvitationsAndSearchUsers(getActivity(), R.layout.invitation_and_search_users_listview, allUsers);
        adapter.setType(AdapterInvitationsAndSearchUsers.Type.SEARCH_USERS);
        lvSearchUsers.setAdapter(adapter);

        startSearchAllUsers();

        return rootView;
    }

    private void trySearchAllUsers() {

        class SearchAllUsersAsyncTask extends AsyncTask<Void, Integer, UserAndFriendFlag[]> {

            @Override
            protected UserAndFriendFlag[] doInBackground(Void... voids) {
                String[] registeredUsers = authenticationModule.getAllRegisteredUsers();
                String[] friendsList = profileModule.getFriendsList();
                UserAndFriendFlag[] result = new UserAndFriendFlag[registeredUsers.length];

                for (int i = 0 ; i < registeredUsers.length ; i++) {
                    result[i] = new UserAndFriendFlag(registeredUsers[i], false);
                    for (int j = 0 ; j < friendsList.length ; j++) {
                        if (registeredUsers[i].equals(friendsList[j])) {
                            result[i] = new UserAndFriendFlag(registeredUsers[i], true);
                            break;
                        }
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(UserAndFriendFlag[] userAndFriendFlags) {
                super.onPostExecute(userAndFriendFlags);
                allUsers.clear();
                allUsers.addAll(new ArrayList<>(Arrays.asList(userAndFriendFlags)));
                adapter.notifyDataSetChanged();
            }
        }

        new SearchAllUsersAsyncTask().execute();
    }

    private void startSearchAllUsers () {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                trySearchAllUsers();
                lvSearchUsers.postDelayed(this,300);
            }
        });
    }
}
