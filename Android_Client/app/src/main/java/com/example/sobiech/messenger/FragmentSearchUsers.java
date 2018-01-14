package com.example.sobiech.messenger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WOJTEK on 2017-12-28.
 */

public class FragmentSearchUsers extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search_users, container, false);

        ListView lvSearchUsers = rootView.findViewById(R.id.lvSearchUsers);
        List <String> allUsers = new ArrayList<>();
        allUsers.add(new String ("Karol Krawczyk"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.invitation_listview, allUsers);
        lvSearchUsers.setAdapter(adapter);

        return rootView;
    }
}
