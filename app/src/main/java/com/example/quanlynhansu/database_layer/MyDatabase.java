package com.example.quanlynhansu.database_layer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.quanlynhansu.model.NhanSu;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
  private static String DB_NAME = "QLNS";
  private static int DB_VERSION = 1;

  private static String TABLE_NAME = "nhanvien";
  private static String ID = "id";
  private static String NAME = "name";
  private static String DEGREE = "degree";
  private static String HOPPIES = "hoppies";

  public MyDatabase(@Nullable Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }


  @Override
  public void onCreate(SQLiteDatabase db) {
    String sql = String.format("CREATE TABLE %s (" +
            "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "%s TEXT, " +
            "%s TEXT, " +
            "%s TEXT)", TABLE_NAME, ID, NAME, DEGREE, HOPPIES);

    db.execSQL(sql);
  }

  public void saveMembers(ArrayList<NhanSu> listMembers) {
    SQLiteDatabase db = getWritableDatabase();

    for (NhanSu item : listMembers) {
      ContentValues values = new ContentValues();
      values.put(NAME, item.getName());
      values.put(DEGREE, item.getDegree());
      values.put(HOPPIES, item.getHoppies());

      db.insert(TABLE_NAME, null, values);
    }

    db.close();
  }

  public void getMembers(ArrayList<NhanSu> listMembers) {
    SQLiteDatabase db = getWritableDatabase();

    Cursor cursor = db.query(TABLE_NAME, new String[]{ID, NAME, DEGREE, HOPPIES}, null, null, null, null, NAME);

    if (cursor.moveToFirst()) {
      do {
        NhanSu nhansu = new NhanSu();

        nhansu.setID(cursor.getString(cursor.getColumnIndex(ID)));
        nhansu.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        nhansu.setDegree(cursor.getString(cursor.getColumnIndex(DEGREE)));
        nhansu.setHoppies(cursor.getString(cursor.getColumnIndex(HOPPIES)));

        listMembers.add(nhansu);

      } while (cursor.moveToNext());
    }

    db.close();
  }

  public void removeMembers(NhanSu nhansu) {
    SQLiteDatabase db = getWritableDatabase();

    db.delete(TABLE_NAME, ID, new String[] {nhansu.getID()});

    db.close();
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
