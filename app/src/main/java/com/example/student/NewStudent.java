package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * New Student.
 * @author Liad Peretz
 * @version	1.0
 * @since 12/2/2021
 * Short description- You can add new student in this activity.
 */
public class NewStudent extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;
    EditText name, phone, address, phoneAddress, dad,phoneDad, mom, phoneMom;
    String name1, phone1, address1, phoneAddress1, dad1,phoneDad1, mom1, phoneMom1;
    String active= "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_student);

        hlp = new HelperDB(this);

        name= (EditText) findViewById(R.id.name);
        phone= (EditText) findViewById(R.id.phone);
        address= (EditText) findViewById(R.id.address);
        phoneAddress= (EditText) findViewById(R.id.phoneAddress);
        dad= (EditText) findViewById(R.id.dad);
        phoneDad= (EditText) findViewById(R.id.phoneDad);
        mom= (EditText) findViewById(R.id.mom);
        phoneMom= (EditText) findViewById(R.id.phoneMom);
    }

    /**
     * Submit.
     * Short description- Enter the new student into the SQLite data base.
     * <p>
     *      View view
     * @param view- the chosen item.
     */
    public void submit(View view) {
        name1= name.getText().toString();
        if (name1.equals("")){
            Toast.makeText(this, "Enter the name", Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues cv = new ContentValues();
            phone1= phone.getText().toString();
            address1= address.getText().toString();
            phoneAddress1= phoneAddress.getText().toString();
            dad1= dad.getText().toString();
            phoneDad1= phoneDad.getText().toString();
            mom1= mom.getText().toString();
            phoneMom1= phoneMom.getText().toString();

            cv.put(Users.ACTIVE,active);
            cv.put(Users.NAME, name1);
            cv.put(Users.PHONE_NUM, phone1);
            cv.put(Users.ADDRESS, address1);
            cv.put(Users.PHONE_ADDRESS, phoneAddress1);
            cv.put(Users.DAD, dad1);
            cv.put(Users.PHONE_DAD, phoneDad1);
            cv.put(Users.MOM, mom1);
            cv.put(Users.PHONE_MOM, phoneMom1);


            db = hlp.getWritableDatabase();
            db.insert(Users.TABLE_USERS, null, cv);
            db.close();

            name.setText("");
            phone.setText("");
            address.setText("");
            phoneAddress.setText("");
            dad.setText("");
            phoneDad.setText("");
            mom.setText("");
            phoneMom.setText("");

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
            Intent e = new Intent(this, Students.class);
            startActivity(e);}
        if (id==R.id.sorting){
            Intent e = new Intent(this, Sorting.class);
            startActivity(e);
        }
        if(id==R.id.grades){
            Intent e = new Intent(this, Grades.class);
            startActivity(e);
        }
        if(id==R.id.credits){
            Intent e = new Intent(this, Credits.class);
            startActivity(e);
        }
        return true;
    }

}