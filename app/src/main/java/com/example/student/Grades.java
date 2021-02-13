package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.student.Users.TABLE_USERS;

/**
 * Grades.
 * @author Liad Peretz
 * @version 1.0
 * @since 12 /2/2021 Short description- You can enter new grades to your students in this activity.
 */
public class Grades extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    TextView studentName;
    ListView studentsNames;
    Spinner time;
    EditText subject, grade;
    ArrayList<String> tbl;
    ArrayAdapter adp;


    String[] columns = {Users.NAME};
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    String limit = null;
    String names;


    String [] time2= {"select the quarter","1","2","3","4"};
    String po="0", name="", time3="0", subject2, grade2;
    ArrayList<String> places2= new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);


        studentsNames= (ListView) findViewById(R.id.studentsNames);
        studentName= (TextView) findViewById(R.id.studentName);
        studentName.setVisibility(View.INVISIBLE);
        subject= (EditText) findViewById(R.id.subject);
        grade= (EditText) findViewById(R.id.grade);
        time= (Spinner) findViewById(R.id.time);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();

        crsr= db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 = crsr.getColumnIndex(Users.NAME);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            names = crsr.getString(col1);
            tbl.add(names);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        studentsNames.setAdapter(adp);
        studentsNames.setOnItemClickListener(this);

        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, time2);
        time.setAdapter(adp);
        time.setOnItemSelectedListener(this);


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
            places2.add(String.valueOf(crsr.getInt(keep)));
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }

    /**
     * OnItemClick.
     * Short description- Get the name and the key id of the chosen student.
     * <p>
     *      AdapterView<?> parent
     *      View view
     *      int position
     *      long id
     * @param parent- the chosen listView, view- the chosen item, position- the place of the chosen item, id- the chosen line.
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        po= places2.get(position);
        columns = null;
        selection = Users.KEY_ID+"=?";
        selectionArgs = new String[]{po};
        groupBy = null;
        having = null;
        orderBy = null;
        limit = null;

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        crsr= db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 = crsr.getColumnIndex(Users.NAME);
        crsr.moveToFirst();
        name= crsr.getString(col1);
        crsr.close();
        db.close();
        studentName.setVisibility(View.VISIBLE);
        studentName.setText(name);
    }

    /**
     * OnItemSelected.
     * Short description- Get the time in the year of the new grade.
     * <p>
     *      AdapterView<?> parent
     *      View view
     *      int position
     *      long id
     * @param parent- the chosen spinner, view- the chosen item, position- the place of the chosen item, id- the chosen line.
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        time3= String.valueOf(position);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * SubmitGrade.
     * Short description- Enter the new grade into the SQLite data base.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void submitGrade(View view) {
        subject2= subject.getText().toString();
        grade2= grade.getText().toString();
        if ((!(subject2.equals("")))&&(!(grade2.equals("")))&&(!(time3.equals("0")))&&(!(po.equals(("0"))))){
            ContentValues cv = new ContentValues();
            cv.put(SaveGrades.GRADE_ID,po);
            cv.put(SaveGrades.SUBJECT, subject2);
            cv.put(SaveGrades.GRADE, grade2);
            cv.put(SaveGrades.TIME,time3);

            db = hlp.getWritableDatabase();
            db.insert(SaveGrades.TABLE_SAVEGRADES, null, cv);
            db.close();

            subject.setText("");
            grade.setText("");

        }
        else
            Toast.makeText(this, "Missing Information- make sure you entered everything", Toast.LENGTH_SHORT).show();
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
            Intent d = new Intent(this, Students.class);
            startActivity(d);}
        if (id==R.id.sorting){
            Intent d = new Intent(this, Sorting.class);
            startActivity(d);
        }
        if (id==R.id.newStudent){
            Intent d = new Intent(this, NewStudent.class);
            startActivity(d);
        }
        if(id==R.id.credits){
            Intent d = new Intent(this, Credits.class);
            startActivity(d);
        }
        return true;
    }
}