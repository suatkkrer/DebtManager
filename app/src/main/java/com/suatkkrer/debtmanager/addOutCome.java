package com.suatkkrer.debtmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addOutCome extends AppCompatActivity {

    TextInputLayout name,amount,description;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String OutcomeName,OutcomeDesc,OutcomeAmount;
    int OutcomeId;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_out_come);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.nameSurnameOutcome);
        amount = findViewById(R.id.moneyAmountOutCome);
        description = findViewById(R.id.descriptionOutCome);
        deleteButton = findViewById(R.id.deleteOutcome);

        deleteButton.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        OutcomeName = intent.getStringExtra("nameOutcome");
        OutcomeDesc = intent.getStringExtra("descriptionOutcome");
        OutcomeAmount = intent.getStringExtra("amountOutcome");
        OutcomeId = intent.getIntExtra("idOutcome",-1);

        if (OutcomeId != -1){
            deleteButton.setVisibility(View.VISIBLE);
            name.getEditText().setText(OutcomeName);
            amount.getEditText().setText(OutcomeAmount);
            description.getEditText().setText(OutcomeDesc);
        }

        amount.getEditText().addTextChangedListener(new NumberTextWatcherForThousand(amount.getEditText()));

        NumberTextWatcherForThousand.trimCommaOfString(amount.getEditText().getText().toString());
    }

    public void cancelOutcome(View view) {

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("outcome","outcome");
        startActivity(intent);

    }

    public void deleteOutcome(View view) {
        if (OutcomeId != -1) {
            try {
                SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Outcome", MODE_PRIVATE, null);

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                sqLiteDatabase.execSQL("DELETE FROM outcome WHERE id = " + OutcomeId + "");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("outcome", "outcome");
                startActivity(intent);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void saveOutcome(View view) {

        if (OutcomeId == -1) {

            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Outcome", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            String nameIncome = name.getEditText().getText().toString();
            String amountIncome = amount.getEditText().getText().toString();
            amountIncome = amountIncome.replaceAll(",","");
            amountIncome = amountIncome.replaceAll("\\.","");
            String descriptionIncome = description.getEditText().getText().toString();
            String dayIncome = dateFormat.format(calendar.getTime());

            String sql = "INSERT INTO outcome (name,amount,description,date) VALUES (?,?,?,?)";


            // sqLiteDatabase.execSQL("INSERT INTO memories (title,memory,date) VALUES ('" + title1 + "','" + memory1 + "','" + day11 + "')");

            SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
            statement.bindString(1, nameIncome);
            statement.bindString(2, amountIncome);
            statement.bindString(3, descriptionIncome);
            statement.bindString(4, dayIncome);
            statement.execute();


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("outcome", "outcome");
            startActivity(intent);
        } else {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Outcome", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            String nameIncome = name.getEditText().getText().toString();
            String amountIncome = amount.getEditText().getText().toString();
            String descriptionIncome = description.getEditText().getText().toString();
            String dayIncome = dateFormat.format(calendar.getTime());

            String sql = "INSERT INTO outcome (name,amount,description,date) VALUES (?,?,?,?)";


            // sqLiteDatabase.execSQL("INSERT INTO memories (title,memory,date) VALUES ('" + title1 + "','" + memory1 + "','" + day11 + "')");

            SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
            statement.bindString(1, nameIncome);
            statement.bindString(2, amountIncome);
            statement.bindString(3, descriptionIncome);
            statement.bindString(4, dayIncome);
            statement.execute();

            sqLiteDatabase.execSQL("DELETE FROM outcome WHERE id = " + OutcomeId + "");


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("outcome", "outcome");
            startActivity(intent);
        }
    }
}