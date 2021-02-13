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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.student.SaveGrades.TABLE_SAVEGRADES;
import static com.example.student.Users.KEY_ID;
import static com.example.student.Users.TABLE_USERS;
import static java.util.Collections.max;

/**
 * Students.
 * @author Liad Peretz
 * @version	1.0
 * @since 12/2/2021
 * short description- you can see and update all the information about your students.
 */
public class Students extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    Button submit2;
    EditText name2, phone2, address2, phoneAddress2, dad2,phoneDad2, mom2, phoneMom2;
    String name3, phone3, address3, phoneAddress3, dad3,phoneDad3, mom3, phoneMom3;
    String switch1="1",switch2;
    Switch change;
    ListView studentsNames;
    ArrayList<String> tbl;
    ArrayAdapter adp;

    String[] columns = {Users.NAME, Users.KEY_ID};
    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    String limit = null;
    String names;

    int place,place2,remember2;
    ArrayList<String> saver= new ArrayList<>();
    boolean exists;
    ContentValues cv2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        submit2= (Button) findViewById(R.id.submit2);
        change= (Switch) findViewById(R.id.change);
        studentsNames= (ListView) findViewById(R.id.studentsNames);
        name2= (EditText) findViewById(R.id.name2);
        phone2= (EditText) findViewById(R.id.phone2);
        address2= (EditText) findViewById(R.id.address2);
        phoneAddress2= (EditText) findViewById(R.id.phoneAddress2);
        dad2= (EditText) findViewById(R.id.dad2);
        phoneDad2= (EditText) findViewById(R.id.phoneDad2);
        mom2= (EditText) findViewById(R.id.mom2);
        phoneMom2= (EditText) findViewById(R.id.phoneMom2);

        submit2.setVisibility(View.INVISIBLE);
        change.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);
        phone2.setVisibility(View.INVISIBLE);
        address2.setVisibility(View.INVISIBLE);
        phoneAddress2.setVisibility(View.INVISIBLE);
        dad2.setVisibility(View.INVISIBLE);
        phoneDad2.setVisibility(View.INVISIBLE);
        mom2.setVisibility(View.INVISIBLE);
        phoneMom2.setVisibility(View.INVISIBLE);


        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        crsr= db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 = crsr.getColumnIndex(Users.NAME);
        int col2 = crsr.getColumnIndex(Users.KEY_ID);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            names = crsr.getString(col1);
            tbl.add(names);
            saver.add(String.valueOf(crsr.getInt(col2)));//extra
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
        studentsNames.setAdapter(adp);

        studentsNames.setOnItemClickListener(this);


    }

    /**
     * Change.
     * Short description- Change the activity status.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void change(View view) {
        if (switch1.equals("1"))
            switch1="0";
        else
            switch1="1";
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
        remember2= position;
        submit2.setVisibility(View.VISIBLE);
        change.setVisibility(View.VISIBLE);
        name2.setVisibility(View.VISIBLE);
        phone2.setVisibility(View.VISIBLE);
        address2.setVisibility(View.VISIBLE);
        phoneAddress2.setVisibility(View.VISIBLE);
        dad2.setVisibility(View.VISIBLE);
        phoneDad2.setVisibility(View.VISIBLE);
        mom2.setVisibility(View.VISIBLE);
        phoneMom2.setVisibility(View.VISIBLE);


        String po= String.valueOf(saver.get(position));
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
        int col1 = crsr.getColumnIndex(Users.ACTIVE);
        int col2 = crsr.getColumnIndex(Users.NAME);
        int col3 = crsr.getColumnIndex(Users.PHONE_NUM);
        int col4 = crsr.getColumnIndex(Users.ADDRESS);
        int col5 = crsr.getColumnIndex(Users.PHONE_ADDRESS);
        int col6 = crsr.getColumnIndex(Users.DAD);
        int col7 = crsr.getColumnIndex(Users.PHONE_DAD);
        int col8 = crsr.getColumnIndex(Users.MOM);
        int col9 = crsr.getColumnIndex(Users.PHONE_MOM);
        crsr.moveToFirst();
            switch1= crsr.getString(col1);
            name3= crsr.getString(col2);
            phone3= crsr.getString(col3);
            address3= crsr.getString(col4);
            phoneAddress3= crsr.getString(col5);
            dad3= crsr.getString(col6);
            phoneDad3=crsr.getString(col7);
            mom3= crsr.getString(col8);
            phoneMom3=crsr.getString(col9);

        crsr.close();
        db.close();
        switch2= switch1;
        name2.setText(name3);
        phone2.setText(phone3);
        address2.setText(address3);
        phoneAddress2.setText(phoneAddress3);
        dad2.setText(dad3);
        phoneDad2.setText(phoneDad3);
        mom2.setText(mom3);
        phoneMom2.setText(phoneMom3);
        if (switch1.equals("1"))
            change.setChecked(true);
        else
            change.setChecked(false);
        place2= position;
    }

    /**
     * Submit2.
     * Short description- Present the selected student's information.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void submit2(View view) {
        if (name2.getText().toString().equals(""))
            Toast.makeText(this, "Enter the name", Toast.LENGTH_SHORT).show();
        else {
            if ((!(switch1.equals(switch2))) || (!(name2.getText().toString().equals(name3))) || (!(phone2.getText().toString().equals(phone3)))|| (!(address2.getText().toString().equals(address3))) || (!(phoneAddress2.getText().toString().equals(phoneAddress3))) ||(!(dad2.getText().toString().equals(dad3))) || (!(phoneDad2.getText().toString().equals(phoneDad3))) ||(!(mom2.getText().toString().equals(mom3)))||(!(phoneMom2.getText().toString().equals(phoneMom3)))) {
                db = hlp.getWritableDatabase();
                db.delete(TABLE_USERS, KEY_ID + "=?", new String[]{saver.get(place2)});
                cv2 = new ContentValues();
                cv2.put(Users.ACTIVE, switch1);
                cv2.put(Users.NAME, name2.getText().toString());
                cv2.put(Users.PHONE_NUM, phone2.getText().toString());
                cv2.put(Users.ADDRESS, address2.getText().toString());
                cv2.put(Users.PHONE_ADDRESS, phoneAddress2.getText().toString());
                cv2.put(Users.DAD, dad2.getText().toString());
                cv2.put(Users.PHONE_DAD, phoneDad2.getText().toString());
                cv2.put(Users.MOM, mom2.getText().toString());
                cv2.put(Users.PHONE_MOM, phoneMom2.getText().toString());
                db.insert(Users.TABLE_USERS, null, cv2);
                db.close();

                columns = new String[]{KEY_ID};
                selection = null;
                selectionArgs = null;
                groupBy = null;
                having = null;
                orderBy = null;
                limit = null;

                db = hlp.getWritableDatabase();
                crsr = db.query(TABLE_USERS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                int col3 = crsr.getColumnIndex(KEY_ID);
                crsr.moveToFirst();
                while (!crsr.isAfterLast()) {
                    place = crsr.getInt(col3);
                    crsr.moveToNext();
                }
                crsr.close();
                db.close();
                String remember = saver.get(place2);
                saver.set(place2, String.valueOf(place));


                columns = new String[]{SaveGrades.GRADE_ID};
                selection = null;
                selectionArgs = null;
                groupBy = null;
                having = null;
                orderBy = null;
                limit = null;
                db = hlp.getWritableDatabase();
                crsr = db.query(TABLE_SAVEGRADES, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
                int changing = crsr.getColumnIndex(SaveGrades.GRADE_ID);
                exists = false;
                crsr.moveToFirst();
                while (!crsr.isAfterLast()) {
                    if (crsr.getString(changing).equals(remember)) {
                        exists = true;
                    }
                    crsr.moveToNext();
                }
                db.close();
                crsr.close();
                if (exists) {
                    cv2 = new ContentValues();
                    cv2.put(SaveGrades.GRADE_ID, place);
                    db = hlp.getWritableDatabase();
                    db.update(TABLE_SAVEGRADES, cv2, SaveGrades.GRADE_ID + "=?", new String[]{remember});
                    db.close();
                }
                tbl.set(remember2,name2.getText().toString());
                adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, tbl);
                studentsNames.setAdapter(adp);

            }

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
        if (id==R.id.sorting){
            Intent b = new Intent(this, Sorting.class);
            startActivity(b);
        }
        if(id==R.id.grades){
            Intent b = new Intent(this, Grades.class);
            startActivity(b);
        }
        if (id==R.id.newStudent){
            Intent b = new Intent(this, NewStudent.class);
            startActivity(b);
        }
        if(id==R.id.credits){
            Intent b = new Intent(this, Credits.class);
            startActivity(b);
        }
        return true;
    }
}