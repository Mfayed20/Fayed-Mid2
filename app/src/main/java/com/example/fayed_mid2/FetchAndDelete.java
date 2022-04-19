package com.example.fayed_mid2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class FetchAndDelete extends AppCompatActivity {


    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_and_delete);

        myDB = new DatabaseHelper(this);


        Button deleteRecordBttn = (Button) findViewById(R.id.deleteRecordBttn);
        Button displayAllDb = (Button) findViewById(R.id.displayAllDb);

        EditText deleteIDET = (EditText) findViewById(R.id.deleteIDET);
        TextView AllDBtxt = (TextView) findViewById(R.id.AllDBtxt);



        deleteRecordBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = deleteIDET.getText().toString();

                if (myDB.deleteData(id)){
//                    Log.d("Fayed","Deletion successful");
                    Toasty.success(getBaseContext(), "Record with ID: "+ id+" deleted", Toast.LENGTH_SHORT, true).show();
                    Log.d("Fayed-DeleteBttn","Record with ID: + id+ deleted");
                    
                }

            }
        });

        displayAllDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur = myDB.getListContents();
                StringBuffer buffer = new StringBuffer();

                while(cur.moveToNext()){
                    buffer.append("id: "+cur.getString(0)+"\n");
                    buffer.append("Name: "+cur.getString(1)+"\n");
                    buffer.append("Surname: "+cur.getString(2)+"\n");
                    buffer.append("National ID: "+cur.getString(3)+"\n\n");

                }
                AllDBtxt.setText(buffer.toString());

                // display all data in the database in a table format




                

            }
        });
    }
}