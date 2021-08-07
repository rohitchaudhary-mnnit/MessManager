package com.example.rohit.messmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddMeal extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    Button add;
    EditText cost,des;
    TextView date;
    public DatabaseReference databaseUsers;

    private Spinner title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        add = (Button) findViewById(R.id.meal_add);
        //progressDialog = new ProgressDialog(AddMeal.this);
        title= (Spinner) findViewById(R.id.meal_title);
        des = (EditText) findViewById(R.id.meal_description);
        cost = (EditText) findViewById(R.id.meal_cost);
        date = (TextView) findViewById(R.id.meal_date);
        databaseUsers = FirebaseDatabase.getInstance().getReference("meals");
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker ");

            }
        });
        List<String> list  = new ArrayList<String>();
        list.add("Select Meal");
        list.add("breakfast");
        list.add("lunch");
        list.add("snacks");
        list.add("dinner");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddMeal.this,android.R.layout.simple_spinner_item,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title.setAdapter(arrayAdapter);
        title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = databaseUsers.push().getKey();
                String tit = title.getSelectedItem().toString();
                String desc = des.getText().toString();
                String co = cost.getText().toString();
                //int val = Integer.parseInt(co);
                String d = date.getText().toString();
                Meal meal = new Meal(tit,desc,co,d,id);
                if(tit.compareTo("Select Meal")==0||desc.isEmpty()||co.isEmpty()||d.isEmpty()){
                    Toast.makeText(AddMeal.this,"Fill All Details",Toast.LENGTH_SHORT).show();
                }
                else {
                   // progressDialog.setMessage("Adding Meal");
                    //progressDialog.show();

                    try {
                        databaseUsers.child(id).setValue(meal);
                        Toast.makeText(AddMeal.this, "Meal Uploaded", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddMeal.this, "Network error please try later", Toast.LENGTH_SHORT).show();
                    }
                    //progressDialog.dismiss();

                    Intent intent = new Intent(AddMeal.this, ManagerHome.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        date.setText(formattedDate);
    }
}
