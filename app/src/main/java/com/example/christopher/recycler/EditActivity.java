package com.example.christopher.recycler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText title, notesDescription;
    Intent intent;
    String a;
    String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title = findViewById(R.id.EditTitle);
        notesDescription = findViewById(R.id.EditText);
        notesDescription.setMovementMethod(new ScrollingMovementMethod());

        intent = getIntent();
        if (intent.hasExtra("EDIT_NAME")) {
            a = intent.getStringExtra("EDIT_NAME");
            b = intent.getStringExtra("EDIT_DESCRIPTION");
            title.setText(a);
            notesDescription.setText(b);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            if (intent.hasExtra("EDIT_NAME")) {
                Intent data = new Intent();
                data.putExtra("TITLE", title.getText().toString());
                data.putExtra("DESCRIPTION", notesDescription.getText().toString());
                setResult(RESULT_CANCELED, data);
                finish();}
            else if (title.getText().toString().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("A title is needed!");
                builder.setNegativeButton("CANCEL", (dialogInterface, i) -> {
                });
                builder.setPositiveButton("OKAY", (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
                AlertDialog ad = builder.create();
                ad.show();
                return false;
            }
            else {
                Intent data = new Intent();
                data.putExtra("TITLE", title.getText().toString());
                data.putExtra("DESCRIPTION", notesDescription.getText().toString());
                setResult(RESULT_FIRST_USER, data);
                finish(); }
        } else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        boolean c = title.getText().toString().equals(a);
        boolean d = notesDescription.getText().toString().equals(b);
        if (title.getText().toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("A title is needed!");
            builder.setNegativeButton("CANCEL", (dialogInterface, i) -> {
            });
            builder.setPositiveButton("OKAY", (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
            AlertDialog ad = builder.create();
            ad.show();
        } else if (title.getText().toString().length() != 0 && (!c || !d)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Your note is not saved! \nSave note '" + title.getText().toString() + "'?");
            builder.setPositiveButton("YES", (dialogInterface, i) -> {
            if (intent.hasExtra("EDIT_NAME")) {
                Intent data = new Intent();
                data.putExtra("TITLE", title.getText().toString());
                data.putExtra("DESCRIPTION", notesDescription.getText().toString());
                setResult(RESULT_CANCELED, data);
                finish();}
            else {
                Intent data = new Intent();
                data.putExtra("TITLE", title.getText().toString());
                data.putExtra("DESCRIPTION", notesDescription.getText().toString());
                setResult(RESULT_FIRST_USER, data);
                finish();
                }
                });
                builder.setNegativeButton("NO", (dialogInterface, i) -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
                AlertDialog ad = builder.create();
                ad.show();
            }
        else if (c && d){
            finish();
        }
        else {
            super.onBackPressed();
        }
        }
    }