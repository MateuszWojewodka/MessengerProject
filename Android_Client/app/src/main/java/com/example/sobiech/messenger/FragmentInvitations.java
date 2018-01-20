package com.example.sobiech.messenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Database.DatabaseHandler;
import Helpers.UserAndFriendFlag;
import Modules.AuthenticationModule;
import Modules.ProfileModule;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentInvitations extends Fragment {

    List<UserAndFriendFlag> allUsers = new ArrayList<>();
    AdapterInvitationsAndSearchUsers  adapter;
    ListView lvInvitations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_invitations, container, false);

        lvInvitations = rootView.findViewById(R.id.lvInvitations);
        adapter = new AdapterInvitationsAndSearchUsers(rootView.getContext(), R.layout.invitation_and_search_users_listview, allUsers);
        adapter.setType(AdapterInvitationsAndSearchUsers.Type.INVITATION);
        lvInvitations.setAdapter(adapter);

        startUpdateInvitations();

        return rootView;
    }

    private void trySearchInvitations() {

        class SearchInvitationsAsyncTask extends AsyncTask<Void, Integer, UserAndFriendFlag[]> {

            @Override
            protected UserAndFriendFlag[] doInBackground(Void... voids) {
                String[] friendRequestSenders = DatabaseHandler.getNotifications().friendRequestsSenders.toArray(new String[0]);
                UserAndFriendFlag[] userAndFriendFlag = new UserAndFriendFlag[friendRequestSenders.length];
                for (int i = 0 ; i < friendRequestSenders.length ; i++)
                    userAndFriendFlag[i] = new UserAndFriendFlag(friendRequestSenders[i], false);
                return userAndFriendFlag;
            }

            @Override
            protected void onPostExecute(UserAndFriendFlag[] userAndFriendFlags) {
                super.onPostExecute(userAndFriendFlags);
                allUsers.clear();
                allUsers.addAll(new ArrayList<>(Arrays.asList(userAndFriendFlags)));
                adapter.notifyDataSetChanged();
            }
        }

        new SearchInvitationsAsyncTask().execute();
    }

    private void startUpdateInvitations () {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                trySearchInvitations();
                lvInvitations.postDelayed(this, 500);
            }
        });
    }
}
