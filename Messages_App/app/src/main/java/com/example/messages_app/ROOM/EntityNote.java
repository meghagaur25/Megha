package com.example.messages_app.ROOM;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "message_table")
public class EntityNote {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String PhoneNumber;
    private String Message;

    public EntityNote( String phoneNumber, String message) {

        this.PhoneNumber = phoneNumber;
        this.Message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}