package com.example.sobiech.messenger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import Database.Database;
import Modules.CommunicationModule;

public class OneConversationActivity extends AppCompatActivity {

    //TODO TIMER IMPLEMENTATION
    private Timer timer = new Timer();
    private CommunicationModule communicationModule = CommunicationModule.getInstance();
    EditText etNewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_conversation);
        ListView lvMessages = findViewById(R.id.lvMessages);
        etNewMessage = findViewById(R.id.etNewMessage);
        ImageButton btSend = findViewById(R.id.btSend);

        setTitle(FragmentConversiations.selectedUserName);

        List<String> senderAndMessage = new ArrayList<>();

        communicationModule.startMessageUpdater(FragmentConversiations.selectedUserName);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List <DTO.Message> messages = Database.INSTANCE.conversations.get(FragmentConversiations.selectedUserName);

        for (int i = 0 ; i < messages.size() ; i++) {
            senderAndMessage.add(messages.get(i).getSender() + ": " + messages.get(i).getMessageContent());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, senderAndMessage);
        lvMessages.setAdapter(adapter);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trySendMessage(LoginRegisterActivity.myUserName, FragmentConversiations.selectedUserName, etNewMessage.getText().toString());
            }
        });
    }

    private void trySendMessage(String userName, String friendName, String message) {

        class SendMessageAsyncTask extends AsyncTask<String, Integer, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                communicationModule.sendMessageToFriend(strings[0], strings[1], strings[2]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                etNewMessage.setText("");

            }
        }

        new SendMessageAsyncTask().execute(userName, friendName, message);
    }

}
