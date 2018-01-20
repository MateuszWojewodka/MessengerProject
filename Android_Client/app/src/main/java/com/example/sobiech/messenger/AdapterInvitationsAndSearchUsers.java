package com.example.sobiech.messenger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Helpers.UserAndFriendFlag;
import Modules.ProfileModule;

/**
 * Created by WOJTEK on 2018-01-14.
 */

public class AdapterInvitationsAndSearchUsers extends ArrayAdapter <UserAndFriendFlag> {

    private static final String MESSAGE_ACCEPT_INVITATION_SUCCESFULLY = "Zaakceptowane zaproszenie";
    private static final String MESSAGE_SEND_INVITATION = "Wysłano zaproszenie";

    private ProfileModule profileModule = ProfileModule.getInstance();

    public enum Type {
        INVITATION,
        SEARCH_USERS;
    }

    List<UserAndFriendFlag> users = new ArrayList<>();
    Context context;
    int resource;
    private Type type;

    public AdapterInvitationsAndSearchUsers(@NonNull Context context, int resource, @NonNull List<UserAndFriendFlag> users) {
        super(context, resource, users);
        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final UserAndFriendFlag oneUser = users.get(position);

        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.invitation_and_search_users_listview, parent, false);

        TextView tvUserName = convertView.findViewById(R.id.tvUName);
        ImageButton btAccept = convertView.findViewById(R.id.btAccept);

        tvUserName.setText(oneUser.userName);

        if (oneUser.isFriend && type == Type.SEARCH_USERS)
            btAccept.setVisibility(View.INVISIBLE);

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == Type.INVITATION) {
                    tryAcceptInvitation(oneUser.userName);
                }
                if (type == Type.SEARCH_USERS) {
                    trySendInvitation(oneUser.userName);
                }
            }
        });

        return convertView;
    }

    public void setType (Type type) {
        this.type = type;
    }

    private void tryAcceptInvitation(final String friendName) {

        class AcceptInvitationAsyncTask extends AsyncTask<Void, Integer, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                profileModule.acceptFriendRequest(friendName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(),MESSAGE_ACCEPT_INVITATION_SUCCESFULLY,Toast.LENGTH_SHORT).show();
            }
        }

        new AcceptInvitationAsyncTask().execute();
    }

    private void trySendInvitation (final String friendName) {

        class SendInvitationAsyncTask extends AsyncTask<Void, Integer, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                profileModule.sendFriendRequest(friendName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(),MESSAGE_SEND_INVITATION,Toast.LENGTH_SHORT).show();
            }
        }

        new SendInvitationAsyncTask().execute();
    }
}
