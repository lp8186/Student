package com.example.student;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.student.SaveGrades.GRADE;
import static com.example.student.SaveGrades.GRADE_ID;
import static com.example.student.SaveGrades.SUBJECT;
import static com.example.student.SaveGrades.TABLE_SAVEGRADES;
import static com.example.student.SaveGrades.TIME;
import static com.example.student.Users.ACTIVE;
import static com.example.student.Users.ADDRESS;
import static com.example.student.Users.DAD;
import static com.example.student.Users.KEY_ID;
import static com.example.student.Users.MOM;
import static com.example.student.Users.NAME;
import static com.example.student.Users.PHONE_ADDRESS;
import static com.example.student.Users.PHONE_DAD;
import static com.example.student.Users.PHONE_MOM;
import static com.example.student.Users.PHONE_NUM;
import static com.example.student.Users.TABLE_USERS;

/**
 * HelperDB.
 * @author Liad Peretz
 * @version	1.0
 * @since 12/2/2021
 */
public class HelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION =20;
    String strCreate, strDelete;

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+ TABLE_USERS;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+ACTIVE+" TEXT,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+PHONE_NUM+" TEXT,";
        strCreate+=" "+ADDRESS+" TEXT,";
        strCreate+=" "+PHONE_ADDRESS+" TEXT,";
        strCreate+=" "+DAD+" TEXT,";
        strCreate+=" "+PHONE_DAD+" TEXT,";
        strCreate+=" "+MOM+" TEXT,";
        strCreate+=" "+PHONE_MOM+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_SAVEGRADES;
        strCreate+=" ("+GRADE_ID+" TEXT,";
        strCreate+=" "+SUBJECT+" TEXT,";
        strCreate+=" "+GRADE+" TEXT,";
        strCreate+=" "+TIME+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_USERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+TABLE_SAVEGRADES;
        db.execSQL(strDelete);

        onCreate(db);


    }
}
