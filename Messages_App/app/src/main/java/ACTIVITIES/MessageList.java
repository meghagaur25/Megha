package ACTIVITIES;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messages_app.R;
import Room.Entity.AllMessages;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import MessageAdapter.AllMessagesAdapter;
import ViewModels.AllMessagesModel;


public class MessageList extends AppCompatActivity implements AllMessagesAdapter.OnChatSummaryListener{

        private AllMessagesModel allMessagesModel;
        private String TAG = this.getClass().getSimpleName();
        private RecyclerView recyclerView;
        private List<AllMessages> allMessagesList;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.message_list);
            allMessagesModel = new AllMessagesModel(getApplication());

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Intent i = new Intent(MessageList.this, sendMessage.class);
                    startActivity(i);
                }
            });

            recyclerView = (RecyclerView)findViewById(R.id.allMessagesRecyclerView);
            final AllMessagesAdapter adapter = new AllMessagesAdapter(this, this);

            recyclerView.setAdapter(adapter);

            allMessagesModel.getAllChats().observe(this, new Observer<List<AllMessages>>() {
                @Override
                public void onChanged(@Nullable List<AllMessages> allMessages) {
                    adapter.setChatNotes(allMessages);
                    allMessagesList = allMessages;
                }
            });


            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        public void onChatSummaryClick(int position) {
            Log.i(TAG, position + "item clicked");
            Intent i = new Intent(MessageList.this, chatActivity.class);
            i.putExtra("contactNumber",allMessagesList.get(position).getContactName());
            startActivity(i);
        }
    }

