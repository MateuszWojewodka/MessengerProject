package com.example.sobiech.messenger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DTO.Message;

public class OneConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_conversation);
        ListView lvMessages = findViewById(R.id.lvMessages);
        EditText etNewMessage = findViewById(R.id.etNewMessage);
        ImageButton ibSend = findViewById(R.id.ibSend);

        List<String> messages = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++)
            messages.add(new String ("Roman Polański: Kręcą mnie trzynastki"));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messages);
        lvMessages.setAdapter(adapter);
    }

}
