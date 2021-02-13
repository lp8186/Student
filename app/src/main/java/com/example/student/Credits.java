package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Credits.
 *
 * @author Liad Peretz
 * @version 1.0
 * @since 12 /2/2021 Short description- Credits.
 */
public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);


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
            Intent f = new Intent(this, Students.class);
            startActivity(f);}
        if (id==R.id.sorting){
            Intent f = new Intent(this, Sorting.class);
            startActivity(f);
        }
        if(id==R.id.grades){
            Intent f = new Intent(this, Grades.class);
            startActivity(f);
        }
        if (id==R.id.newStudent){
            Intent f = new Intent(this, NewStudent.class);
            startActivity(f);
        }
        return true;
    }
}