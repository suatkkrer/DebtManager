package com.suatkkrer.debtmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addIncome extends AppCompatActivity {

    TextInputLayout name,amount,description;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String incomeName,incomeDesc,incomeAmount;
    int incomeId;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name = findViewById(R.id.nameSurnameIncome);
        amount = findViewById(R.id.moneyAmountIncome);
        description = findViewById(R.id.descriptionIncome);
        deleteButton = findViewById(R.id.deleteIncome);

        deleteButton.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        incomeName = intent.getStringExtra("nameIncome");
        incomeDesc = intent.getStringExtra("descriptionIncome");
        incomeAmount = intent.getStringExtra("amountIncome");
        incomeId = intent.getIntExtra("idIncome", -1);

        if (incomeId != -1) {
            deleteButton.setVisibility(View.VISIBLE);
            name.getEditText().setText(incomeName);
            amount.getEditText().setText(incomeAmount);
            description.getEditText().setText(incomeDesc);
        }

        amount.getEditText().addTextChangedListener(new NumberTextWatcherForThousand(amount.getEditText()));

        NumberTextWatcherForThousand.trimCommaOfString(amount.getEditText().getText().toString());

    }

    public void cancelIncome(View view) {

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("income","income");
        startActivity(intent);
    }

    public void deleteIncome(View view) {

        if (incomeId != -1) {
            try {
                SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Income", MODE_PRIVATE, null);

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                sqLiteDatabase.execSQL("DELETE FROM income WHERE id = " + incomeId + "");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("income", "income");
                startActivity(intent);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public void saveIncome(View view) {

        if (incomeId == -1) {

            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Income", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            String nameIncome = name.getEditText().getText().toString();
            String amountIncome = amount.getEditText().getText().toString();
            amountIncome = amountIncome.replaceAll(",","");
            amountIncome = amountIncome.replaceAll("\\.","");
            String descriptionIncome = description.getEditText().getText().toString();
            String dayIncome = dateFormat.format(calendar.getTime());

            String sql = "INSERT INTO income (name,amount,description,date) VALUES (?,?,?,?)";


            // sqLiteDatabase.execSQL("INSERT INTO memories (title,memory,date) VALUES ('" + title1 + "','" + memory1 + "','" + day11 + "')");

            SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
            statement.bindString(1, nameIncome);
            statement.bindString(2, amountIncome);
            statement.bindString(3, descriptionIncome);
            statement.bindString(4, dayIncome);
            statement.execute();


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("income", "income");
            startActivity(intent);
        } else {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Income", MODE_PRIVATE, null);

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


            calendar = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            String nameIncome = name.getEditText().getText().toString();
            String amountIncome = amount.getEditText().getText().toString();
            String descriptionIncome = description.getEditText().getText().toString();
            String dayIncome = dateFormat.format(calendar.getTime());

            String sql = "INSERT INTO income (name,amount,description,date) VALUES (?,?,?,?)";


            // sqLiteDatabase.execSQL("INSERT INTO memories (title,memory,date) VALUES ('" + title1 + "','" + memory1 + "','" + day11 + "')");

            SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
            statement.bindString(1, nameIncome);
            statement.bindString(2, amountIncome);
            statement.bindString(3, descriptionIncome);
            statement.bindString(4, dayIncome);
            statement.execute();

            sqLiteDatabase.execSQL("DELETE FROM income WHERE id = " + incomeId + "");


            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("income", "income");
            startActivity(intent);
        }
        }

    }
