package ACTIVITIES;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messages_app.R;
import Room.Entity.AllMessages;
import Room.Entity.SingleChat;

import java.util.List;

import MessageAdapter.SingleChatAdater;
import ViewModels.AllMessagesModel;
import ViewModels.SingleChatModel;


public class chatActivity extends AppCompatActivity {

        EditText TextMessage;
        Button sendMessage;
        private AllMessagesModel allMessagesModel;
        private SingleChatModel singleChatModel;
        private String contactNumber;
        private String TAG = this.getClass().getSimpleName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);
            Intent i = getIntent();
            contactNumber = i.getStringExtra("contactNumber");
            this.setTitle(contactNumber);

            TextMessage = (EditText)findViewById(R.id.typeMessage);
            sendMessage = (Button)findViewById(R.id.sendButton);

            Log.i(TAG, "Contact Number is "+contactNumber);
            Toast.makeText(this, "contact number is "+contactNumber, Toast.LENGTH_LONG).show();

            allMessagesModel = new AllMessagesModel(getApplication());
            singleChatModel = new SingleChatModel(getApplication());

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.singleChatRecyclerView);
            final SingleChatAdater adapter = new SingleChatAdater(this);

            recyclerView.setAdapter(adapter);

            singleChatModel.getAllChats(contactNumber).observe(this, new Observer<List<SingleChat>>() {
                @Override
                public void onChanged(@Nullable List<SingleChat> individualChatList) {
                    adapter.setChatNotes(individualChatList);
                }
            });

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        public void sendMessage(View view){
            String messageBody = TextMessage.getText().toString();

            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
            SmsManager sms = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                sms = SmsManager.getSmsManagerForSubscriptionId(SubscriptionManager.getDefaultSubscriptionId());
            }
            sms.sendTextMessage(contactNumber,null,messageBody,pi,null);


            AllMessages allMessages = new AllMessages(contactNumber,messageBody,"sent");
            allMessagesModel.insert(allMessages);

            SingleChat individualChatEntity = new SingleChat(contactNumber,messageBody,"sent");
            singleChatModel.insert(individualChatEntity);

            TextMessage.setText("");

            Toast.makeText(this, "Message Sent", Toast.LENGTH_LONG);

        }

}

