package com.example.sobiech.messenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import Database.Database;
import Modules.CommunicationModule;

public class OneConversationActivity extends AppCompatActivity {

    private CommunicationModule communicationModule = CommunicationModule.getInstance();
    private EditText etNewMessage;
    private ListView lvMessages;
    private ImageButton btSend;
    private List <DTO.Message> messages;
    private List<String> senderAndMessage = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Runnable runnable = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_conversation);
        etNewMessage = findViewById(R.id.etNewMessage);
        lvMessages = findViewById(R.id.lvMessages);
        btSend = findViewById(R.id.btSend);
        setTitle(FragmentConversiations.selectedUserName);
        communicationModule.startMessageUpdater(FragmentConversiations.selectedUserName);
        waitForDownloadFirstData(300);
        senderAndMessage.clear();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, senderAndMessage);
        lvMessages.setAdapter(adapter);
        setBtSendOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startUpdateConversation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopUpdateConversation();
    }

    private void trySendMessage(String userName, String friendName, String message) {

        class SendMessageAsyncTask extends AsyncTask<String, Integer, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(String... strings) {
                if (strings[2].trim().length() > 0)
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

    private void startUpdateConversation () {
        runOnUiThread(runnable = new Runnable() {
            @Override
            public void run() {
                senderAndMessage.clear();

                messages = Database.INSTANCE.conversations.get(FragmentConversiations.selectedUserName);

                for (int i = 0 ; i < messages.size() ; i++) {
                    senderAndMessage.add(messages.get(i).getSender() + ": " + messages.get(i).getMessageContent());
                }

                adapter.notifyDataSetChanged();
                lvMessages.postDelayed(this, 300);
            }
        });
    }

    private void stopUpdateConversation () {
        lvMessages.removeCallbacks(runnable);
    }

    private void waitForDownloadFirstData (int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setBtSendOnClickListener () {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trySendMessage(LoginRegisterActivity.myUserName, FragmentConversiations.selectedUserName, etNewMessage.getText().toString());
            }
        });
    }
}
