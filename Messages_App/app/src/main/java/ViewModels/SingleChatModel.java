package ViewModels;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import Room.Entity.SingleChat;
import Room.Database.MessageDB;
import Room.Dao.SingleChatDao;

import java.util.Date;
import java.util.List;

public class SingleChatModel {

        private String TAG = this.getClass().getSimpleName();
        private SingleChatDao singleChatDao;
        private MessageDB messageDB;
        private LiveData<List<SingleChat>> singleChat;

        public SingleChatModel(@NonNull Context context){
            messageDB = MessageDB.getMessageDB(context);
            singleChatDao = messageDB.singleChatDao();
        }


        public void insert(SingleChat singleChat){
            Log.i(TAG,"Inside insert method");
            singleChat.setSmsTimestamp(new Date());
            new InsertAsyncTask(singleChatDao).execute(singleChat);
        }

        private class InsertAsyncTask extends AsyncTask<SingleChat, Void, Void> {

            SingleChatDao singleChatDao;

            public InsertAsyncTask(SingleChatDao singleChatDao){
                this.singleChatDao = singleChatDao;
            }

            @Override
            protected Void doInBackground(SingleChat... singleChats) {

                singleChatDao.insert(singleChats[0]);
                return null;
            }
        }

        public LiveData<List<SingleChat>> getAllChats(String contactNumber){
            singleChat = singleChatDao.getAllChats(contactNumber);

            return singleChat;
        }
}
