package com.example.sobiech.messenger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentInvitations extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_invitations, container, false);

        ListView lvSearchUsers = rootView.findViewById(R.id.lvInvitations);
        List<String> allUsers = new ArrayList<>();
        //TODO GET INVITATIONS
        allUsers.add(new String ("Karol Krawczyk"));
        AdapterInvitationsAndSearchUsers  adapter = new AdapterInvitationsAndSearchUsers(rootView.getContext(), R.layout.invitation_and_search_users_listview, allUsers);
        adapter.setType(AdapterInvitationsAndSearchUsers.Type.INVITATION);
        lvSearchUsers.setAdapter(adapter);

        return rootView;
    }
}
