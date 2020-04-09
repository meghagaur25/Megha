package ViewModels;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import Room.Dao.AllMessagesDao;
import Room.Entity.AllMessages;
import Room.Database.MessageDB;

import java.util.Date;
import java.util.List;


public class AllMessagesModel {

        private String TAG = this.getClass().getSimpleName();
        private AllMessagesDao allMessagesDao;
        private MessageDB messageDB;
        private LiveData<List<AllMessages>> allMessageList;

        public AllMessagesModel(@NonNull Context context) {
            messageDB = MessageDB.getMessageDB(context);
            allMessagesDao = messageDB.allMessagesDao();
            allMessageList = allMessagesDao.getAllChats();
        }


        public void insert(AllMessages allMessages) {
            Log.i(TAG, "Inside insert method");
            allMessages.setSmsTimestamp(new Date());
            new InsertAsyncTask(allMessagesDao).execute(allMessages);
        }

        private class InsertAsyncTask extends AsyncTask<AllMessages, Void, Void> {

            AllMessagesDao allMessagesDao;

            public InsertAsyncTask(AllMessagesDao allMessagesDao) {
                this.allMessagesDao = allMessagesDao;
            }

            @Override
            protected Void doInBackground(AllMessages... allMessagesEntity) {

                allMessagesDao.insert(allMessagesEntity[0]);
                return null;
            }
        }

        public LiveData<List<AllMessages>> getAllChats() {
            allMessageList = allMessagesDao.getAllChats();

            return allMessageList;
        }

    }


