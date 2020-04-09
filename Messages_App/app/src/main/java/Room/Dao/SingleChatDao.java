package Room.Dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import Room.Entity.SingleChat;

import java.util.List;


@Dao
    public interface SingleChatDao {

        @Insert
        void insert(SingleChat singleChatEntity);

        @Query("Select * from SingleChat where contactNumber = :contactNumber")
        LiveData<List<SingleChat>> getAllChats(String contactNumber);
    }

