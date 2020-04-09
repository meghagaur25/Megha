package Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import Room.Entity.AllMessages;
import Room.Entity.SingleChat;

import ViewModels.AllMessagesModel;
import ViewModels.SingleChatModel;


public class MessageReceiver extends BroadcastReceiver {


        private String TAG = this.getClass().getSimpleName();
        private AllMessagesModel allMessagesModel;
        private SingleChatModel singleChatModel;

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Log.i(TAG,"Message Recieved");
            if(bundle!=null){
                final Object[] pdusObj = (Object[])bundle.get("pdus");
                String format = bundle.get("format").toString();

                for(int i=0; i<pdusObj.length;i++){

                    SmsMessage message = SmsMessage.createFromPdu((byte[])pdusObj[i],format);
                    String recieverPhn = message.getDisplayOriginatingAddress();
                    String messageBody = message.getDisplayMessageBody();
                    Long timestamp = message.getTimestampMillis();

                    Log.i(TAG,"Phone "+recieverPhn);
                    Log.i(TAG, "Message Body "+messageBody);
                    Log.i(TAG, "timestamp "+timestamp);

                    allMessagesModel = new AllMessagesModel(context);
                    AllMessages allMessages = new AllMessages(recieverPhn,messageBody,"received");
                    allMessagesModel.insert(allMessages);

                    singleChatModel = new SingleChatModel(context);
                    SingleChat individualChatEntity = new SingleChat(recieverPhn,messageBody,"received");
                    singleChatModel.insert(individualChatEntity);

                    Toast.makeText(context, "Message Received",Toast.LENGTH_LONG).show();

                }
            }
        }

    }

