package Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import Room.Entity.AllMessages;

import java.util.List;

@Dao
public interface AllMessagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AllMessages allMessagesEntity);

    @Update
    void update(AllMessages allMessagesEntity);

    @Query("Select * from AllMessages Order By smsTimestamp DESC")
    LiveData<List<AllMessages>> getAllChats();
}