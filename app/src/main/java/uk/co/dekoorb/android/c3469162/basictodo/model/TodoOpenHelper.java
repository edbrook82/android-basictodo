package uk.co.dekoorb.android.c3469162.basictodo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static uk.co.dekoorb.android.c3469162.basictodo.model.TodoContract.Todo;

/**
 * Created by c3469162 on 10/10/2017.
 */

public class TodoOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;

    public TodoOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Todo.TABLE_NAME + " (" +
                Todo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Todo.COLUMN_NOTE + " TEXT, " +
                Todo.COLUMN_COMPLETED + " INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + Todo.TABLE_NAME);
        onCreate(db);
    }
}
