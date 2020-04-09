package ACTIVITIES;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.messages_app.R;
import Room.Entity.AllMessages;
import Room.Entity.SingleChat;

import ViewModels.AllMessagesModel;
import ViewModels.SingleChatModel;

public class sendMessage extends AppCompatActivity {

            EditText recieverNumber;
            EditText TextMessage;
            Button sendMsgButton;
            private AllMessagesModel allMessagesModel;
            private SingleChatModel singleChatModel;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_send_message);

                recieverNumber = (EditText)findViewById(R.id.recieverNumber);
                TextMessage = (EditText)findViewById(R.id.typeMessage);
                sendMsgButton = (Button)findViewById(R.id.sendMessageButton);

                allMessagesModel = new AllMessagesModel(getApplication());
                singleChatModel = new SingleChatModel(getApplication());

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            public void sendMessage(View view){
                String contact = recieverNumber.getText().toString();
                String messageBody = TextMessage.getText().toString();

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                PendingIntent pi= PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
                SmsManager sms = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    sms = SmsManager.getSmsManagerForSubscriptionId(SubscriptionManager.getDefaultSubscriptionId());
                }
                sms.sendTextMessage(contact,null,messageBody,pi,null);


                AllMessages allMessages = new AllMessages(contact,messageBody,"sent");
                allMessagesModel.insert(allMessages);

                SingleChat singleChat = new SingleChat(contact,messageBody,"sent");
                singleChatModel.insert(singleChat);

                Toast.makeText(this, "Message Sent", Toast.LENGTH_LONG);

                Intent i = new Intent(sendMessage.this, chatActivity.class);
                i.putExtra("contactNumber",contact);
                startActivity(i);
            }
        }
