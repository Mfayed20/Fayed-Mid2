package com.example.fayed_mid2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MySQLite extends AppCompatActivity {


    DatabaseHelper myDB;
    EditText edID, nameET, surnameET,edNId;

    String id,name,surname,nationalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sqlite);



        Button inserDbBttn = (Button) findViewById(R.id.inserDbBttn);

        Button goToFirst = (Button) findViewById(R.id.goToFirst);
        Button goToThird = (Button) findViewById(R.id.goToThird);


        myDB = new DatabaseHelper(this);

        edID = (EditText) findViewById(R.id.edID);
        nameET = (EditText) findViewById(R.id.nameET);
        surnameET = (EditText) findViewById(R.id.surnameET);
        edNId = (EditText) findViewById(R.id.edNId);

        inserDbBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = edID.getText().toString();
                name = nameET.getText().toString();
                surname = surnameET.getText().toString();
                nationalID = edNId.getText().toString();

                if (id.equals("") || name.equals("") || surname.equals("") || nationalID.equals(""))
                {
                    Toast.makeText(MySQLite.this,
                            "Fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!myDB.addData(id, name,surname,nationalID)){
//                    Toast.makeText(MySQLite.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                    Log.d("Fayed-InsertBttn","Insertion Successful");
                    Toasty.success(getBaseContext(), "Insertion Successful", Toast.LENGTH_SHORT, true).show();

                }
//
                else
                    Toast.makeText(MySQLite.this,
                            "Insertion Success", Toast.LENGTH_SHORT).show();


            }
        });

        goToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MySQLite.this,MainActivity.class));
            }
        });

        goToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MySQLite.this,FetchAndDelete.class));
            }
        });


    }
}