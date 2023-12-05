package com.example.sqliteapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText edit_name, edit_surname, edit_marks, edit_id;
    Button add_button, view_button, update_button, delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        edit_name = findViewById(R.id.edit_text_name);
        edit_surname = findViewById(R.id.edit_text_surname);
        edit_marks = findViewById(R.id.edit_text_marks);
        edit_id = findViewById(R.id.edit_text_id);


        add_button = findViewById(R.id.button);
        view_button = findViewById(R.id.view_button);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);


        AddData();
        viewAllData();
        UpdateData();
        DeleteData();
    }

    public void AddData(){
        add_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(edit_name.getText().toString(),edit_surname.getText().toString(),edit_name.getText().toString());
                        if (isInserted == true){
                            Toast.makeText(MainActivity.this, "Data is Successfully Inserted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Data is not Successfully Inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void viewAllData(){
        view_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor result = myDB.getAllData();
                        if (result.getCount() == 0){
                            //show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (result.moveToNext()){
                            buffer.append("ID :"+ result.getString(0)+"\n");
                            buffer.append("NAME :"+ result.getString(1)+"\n");
                            buffer.append("SURNAME :"+ result.getString(2)+"\n");
                            buffer.append("MARKS :"+ result.getString(3)+"\n\n");
                        }

                        // show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void UpdateData(){
        update_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDB.updateData(
                                edit_id.getText().toString(),
                                edit_name.getText().toString(),
                                edit_surname.getText().toString(),
                                edit_marks.getText().toString()
                        );

                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void DeleteData(){
        delete_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB.deleteData(edit_id.getText().toString());
                        if (deletedRows > 0){
                            Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Deletion is not Successfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}