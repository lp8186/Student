package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.student.SaveGrades.TABLE_SAVEGRADES;
import static com.example.student.Users.KEY_ID;
import static com.example.student.Users.TABLE_USERS;

/**
 * Sorting.
 * @author Liad Peretz
 * @version	1.0
 * @since 12/2/2021
 * Short description- You can see in this activity all the grades of your students according to the student's name or subject or failed grades.
 */
public class Sorting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button enter;
    TextView text;
    Spinner options, options2;
    String[] setOptions = {"options", "grades according to student's name", "grades according to subject", "failed grades"};
    ArrayAdapter adp;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> tbl,tbl2;

    String names,information;
    String[] columns;
    String selection;
    String[] selectionArgs;
    String groupBy;
    String having;
    String orderBy;
    String limit;

    int pos1= 0,pos2=0;
    String chosenSubject;
    ArrayList <String>places= new ArrayList<>();

    int col1,col2,col3,col4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting);

        options = (Spinner) findViewById(R.id.options);
        options2 = (Spinner) findViewById(R.id.options2);
        enter = (Button) findViewById(R.id.enter);
        text= (TextView) findViewById(R.id.text);
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, setOptions);
        options.setAdapter(adp);
        options.setOnItemSelectedListener(this);
        options2.setOnItemSelectedListener(this);

        options2.setVisibility(View.INVISIBLE);
        enter.setVisibility(View.INVISIBLE);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        tbl.add(("list of students"));

        columns = new String[]{Users.NAME};
        selection = null;
        selectionArgs = null;
        groupBy = null;
        having = null;
        orderBy = null;
        limit = null;


        crsr = db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        col1 = crsr.getColumnIndex(Users.NAME);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            names = crsr.getString(col1);
            tbl.add(names);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();



        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        tbl2 = new ArrayList<>();
        tbl2.add(("list of subjects"));

        columns = new String[]{SaveGrades.SUBJECT};
        selection = null;
        selectionArgs = null;
        groupBy = null;
        having = null;
        orderBy = null;
        limit = null;

        crsr = db.query(TABLE_SAVEGRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        col1 = crsr.getColumnIndex(SaveGrades.SUBJECT);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            names = crsr.getString(col1);
            if (!(tbl2.contains(names))) {
                tbl2.add(names);
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();


        columns = new String[]{Users.KEY_ID};
        selection = null;
        selectionArgs = null;
        groupBy = null;
        having = null;
        orderBy = null;
        limit = null;

        db=hlp.getWritableDatabase();
        crsr= db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int keep = crsr.getColumnIndex(Users.KEY_ID);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            places.add(String.valueOf(crsr.getInt(keep)));
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }

    /**
     * OnItemSelected.
     * Short description- Get the desired sorting.
     * <p>
     *      AdapterView<?> parent
     *      View view
     *      int position
     *      long id
     * @param parent- the chosen spinner, view- the chosen item, position- the place of the chosen item, id- the chosen line.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text.setText("");
        if(parent==options) {
            pos1 = position;
            if (position == 1) {
                adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                options2.setAdapter(adp);
                options2.setVisibility(View.VISIBLE);
            } else if (position == 2) {
                adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl2);
                options2.setAdapter(adp);
                options2.setVisibility(View.VISIBLE);
            } else if (position == 3) {
                options2.setVisibility(View.INVISIBLE);
                enter.setVisibility(View.VISIBLE);
            }
            else{
                options2.setVisibility(View.INVISIBLE);
                enter.setVisibility(View.INVISIBLE);
            }
    }
        else if (parent==options2){
            pos2 = position;
            if (position!=0) {
                enter.setVisibility(View.VISIBLE);
                if (pos1 == 2) {
                    chosenSubject = tbl2.get(pos2);
                }
            }
        }
}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Enter.
     * Short description- Present the wanted information.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void enter(View view) {
        text.setText("");
        if ((pos1==1)&&(pos2!=0)){
            db = hlp.getWritableDatabase();
            columns = null;
            selection = SaveGrades.GRADE_ID+"=?";
            selectionArgs = new String[]{places.get(pos2-1)};
            groupBy = null;
            having = null;
            orderBy = null;
            limit = null;
            crsr = db.query(TABLE_SAVEGRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            col1 = crsr.getColumnIndex(SaveGrades.SUBJECT);
            col2 = crsr.getColumnIndex(SaveGrades.TIME);
            col3 = crsr.getColumnIndex(SaveGrades.GRADE);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                information = "subject "+crsr.getString(col1)+" time "+ crsr.getString(col2)+" grade "+crsr.getString(col3);
                text.setText(text.getText().toString()+"\n"+information);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
        }
        if ((pos1==2)&&(pos2!=0)){
            db = hlp.getWritableDatabase();
            columns = null;
            selection = SaveGrades.SUBJECT+"=?";
            selectionArgs = new String[]{chosenSubject};
            groupBy = null;
            having = null;
            orderBy = null;
            limit = null;

            crsr = db.query(TABLE_SAVEGRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            col1 = crsr.getColumnIndex(SaveGrades.SUBJECT);
            col2 = crsr.getColumnIndex(SaveGrades.TIME);
            col3 = crsr.getColumnIndex(SaveGrades.GRADE);
            col4 = crsr.getColumnIndex(SaveGrades.GRADE_ID);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                information ="name "+tbl.get(Integer.valueOf(places.indexOf(crsr.getString(col4)))+1)+ " subject "+crsr.getString(col1)+" time "+ crsr.getString(col2)+" grade "+crsr.getString(col3);
                text.setText(text.getText().toString()+"\n"+information);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
        }
        if ((pos1==3)){
            db = hlp.getWritableDatabase();
            crsr = db.query(TABLE_SAVEGRADES, null, null, null, null, null, null, null);
            col1 = crsr.getColumnIndex(SaveGrades.SUBJECT);
            col2 = crsr.getColumnIndex(SaveGrades.TIME);
            col3 = crsr.getColumnIndex(SaveGrades.GRADE);
            col4 = crsr.getColumnIndex(SaveGrades.GRADE_ID);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                if (Integer.valueOf(crsr.getString(col3)) <= 56) {
                    information ="name "+tbl.get(Integer.valueOf(places.indexOf(crsr.getString(col4)))+1) +" subject " + crsr.getString(col1) + " time " + crsr.getString(col2) + " grade " + crsr.getString(col3);
                    text.setText(text.getText().toString() + "\n" + information);
                }
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
        }
    }

    /**
     * OnCreateOptionsMenu.
     * Short descriptions- "Calls" the options menu.
     * <p>
     *    Menu menu
     * @param menu the menu
     * @return true if it worked.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * OnOptionsItemSelected.
     * Short description- Moves from this activity to other activity according to the selected item.
     * <p>
     *     MenuItem item
     * @param item the selected item
     * @return true if it worked.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.students){
            Intent c = new Intent(this, Students.class);
            startActivity(c);}
        if(id==R.id.grades){
            Intent c = new Intent(this, Grades.class);
            startActivity(c);
        }
        if (id==R.id.newStudent){
            Intent c = new Intent(this, NewStudent.class);
            startActivity(c);
        }
        if(id==R.id.credits){
            Intent c = new Intent(this, Credits.class);
            startActivity(c);
        }
        return true;
    }
}