package com.example.messages_app.ROOM;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    Long insert(EntityNote message);

    @Update
    void update(EntityNote message);


    @Query("DELETE FROM message_table")
    void deleteAllMessages();


    @Query("SELECT * FROM message_table ORDER BY id desc")
    LiveData<List<EntityNote>> fetchAllDetails();



}