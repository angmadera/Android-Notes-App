package com.example.christopher.recycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "Main Activity";
    Notes position;
    int pos;
    private final List<Notes> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter mAdapter;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JSON loads files
        notesList.clear();
        notesList.addAll(loadFile());

        //RecyclerView
        recyclerView = findViewById(R.id.recycler);
        mAdapter = new NotesAdapter(notesList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //The passing of results from Edit Activity
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleResult);

        //Changing Title
        int count = mAdapter.getItemCount();
        if (count == 0) {
            this.setTitle("Android Notes");
        } else {
            this.setTitle("Android Notes (" + count + ")");
        }
    }

    //JSON loads notes
    private ArrayList<Notes> loadFile() {
        ArrayList<Notes> notesList = new ArrayList<>();
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String desc = jsonObject.getString("description");
                long dateTime = jsonObject.getLong("dateTime");
                Notes notes = new Notes(title, desc, dateTime);
                notesList.add(notes);
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG, "loadFile: No JSON product file");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notesList;
    }
    //JSON saves notes
    private void saveNote() {
        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(notesList);
            printWriter.close();
            fos.close();
            Log.d(TAG, "saveNote: Note is saved!");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    //menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //menu selection for adding a note and going to About activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.more_info) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return false;
        } else if (item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(this, EditActivity.class);
            activityResultLauncher.launch(intent);
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //When pressing a note, passes data to Edit Activity to be edited
    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        position = notesList.get(pos);
        String a = position.getName();
        String b = position.getDescription();
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("EDIT_NAME", a);
        intent.putExtra("EDIT_DESCRIPTION", b);
        activityResultLauncher.launch(intent);
    }

    //Dialogue to confirm deletion of pressed note
    //If presses yes, will delete note.
    //Else, nothing
    @Override
    public boolean onLongClick(View v) {
        if (!notesList.isEmpty()) {
            pos = recyclerView.getChildLayoutPosition(v);
            Notes m = notesList.get(pos);
            String a = m.getName();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Note '" + a + "'?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                notesList.remove(m);
                mAdapter.notifyItemRemoved(pos);
                int count = mAdapter.getItemCount();
                if (count == 0) {
                    this.setTitle("Android Notes");
                }
                else {
                    this.setTitle("Android Notes (" + count + ")");
                }
                saveNote();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {
            });
            AlertDialog ad = builder.create();
            ad.show();
        }
        return false;
    }

    //Handle result decides if data received is either an a new note or an edited note with result code
    //if no result code, does nothing
    //if result code is RESULT_FIRST_USER, then adds new note
    //if result code is RESULT_CANCELED, deletes old note and adds new note that was edited
    public void handleResult(ActivityResult result) {
        if (result == null || result.getData() == null) {
            return; }
        Intent data = result.getData();
        if (result.getResultCode() == RESULT_FIRST_USER) {
            String text1 = data.getStringExtra("TITLE");
            String text2 = data.getStringExtra("DESCRIPTION");
            notesList.add(0, new Notes(text1, text2, new Date().getTime()));
            mAdapter.notifyItemInserted(0);
            int count = mAdapter.getItemCount();
            this.setTitle("Android Notes (" + count + ")");
            saveNote();
        }
        else if (result.getResultCode() == RESULT_CANCELED) {
            notesList.remove(position);
            mAdapter.notifyItemRemoved(pos);
            String text1 = data.getStringExtra("TITLE");
            String text2 = data.getStringExtra("DESCRIPTION");
            notesList.add(0, new Notes(text1, text2, new Date().getTime()));
            mAdapter.notifyItemInserted(0);
            saveNote();
        }
    }
}