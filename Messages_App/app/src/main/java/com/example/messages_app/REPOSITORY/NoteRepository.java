package com.example.messages_app.REPOSITORY;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.messages_app.ROOM.EntityNote;
import com.example.messages_app.ROOM.NoteDAO;
import com.example.messages_app.ROOM.NoteDatabase;

import java.util.List;

public class NoteRepository {
    private NoteDAO noteDAO;
    private LiveData<List<EntityNote>> allmessages;


    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDAO = database.noteDAO();
        allmessages = noteDAO.fetchAllDetails();
    }

    public void insert(EntityNote message) {
        new InsertNoteAsyncTask(noteDAO).execute(message);

    }

    public void update(EntityNote message) {
       new UpdateNoteAsyncTask(noteDAO).execute(message);
    }

    public void deleteAllNotes() {
        new deleteAllNoteAsyncTask(noteDAO).execute();

    }


    public LiveData<List<EntityNote>> fetchAllDetails() {
        return allmessages;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<EntityNote, Void, Void> {
        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(EntityNote... messages) {
            noteDAO.insert(messages[0]);
            return null;
        }
    }

        private static class UpdateNoteAsyncTask extends AsyncTask<EntityNote, Void, Void> {
            private NoteDAO noteDAO;

            private UpdateNoteAsyncTask(NoteDAO noteDAO) {
                this.noteDAO = noteDAO;
            }

            @Override
            protected Void doInBackground(EntityNote... messages) {
                noteDAO.update(messages[0]);
                return null;
            }
        }

            private static class deleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
                private NoteDAO noteDAO;

                private deleteAllNoteAsyncTask(NoteDAO noteDAO) {
                    this.noteDAO = noteDAO;
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    noteDAO.deleteAllMessages();
                    return null;
                }

            }
        }




