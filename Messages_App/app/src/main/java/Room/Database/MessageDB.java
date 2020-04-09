package Room.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import Room.Dao.AllMessagesDao;
import Room.Dao.SingleChatDao;

import Room.Entity.AllMessages;
import Room.Entity.SingleChat;

@Database(entities = {AllMessages.class, SingleChat.class}, version = 1, exportSchema = false)
public abstract class MessageDB extends RoomDatabase {

    public abstract AllMessagesDao allMessagesDao();
    public abstract SingleChatDao singleChatDao();

    private static volatile MessageDB messageDB;

    public static MessageDB getMessageDB(final Context context){
        if(messageDB == null){
            synchronized (MessageDB.class){
                if(messageDB == null){
                    messageDB = Room.databaseBuilder(context.getApplicationContext(),
                            MessageDB.class,"MESSAGE_DATABASE" )
                            .build();
                }
            }
        }
        return messageDB;
    }
}

