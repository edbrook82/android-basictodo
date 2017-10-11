package uk.co.dekoorb.android.c3469162.basictodo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.dekoorb.android.c3469162.basictodo.model.TodoAdapter;
import uk.co.dekoorb.android.c3469162.basictodo.model.TodoContract;
import uk.co.dekoorb.android.c3469162.basictodo.model.TodoOpenHelper;
import uk.co.dekoorb.android.c3469162.basictodo.view.TodoAddDialogFragment;

public class MainActivity extends AppCompatActivity
        implements TodoAddDialogFragment.TodoAddDialogCallbacks,
        TodoAdapter.TodoAdapterCallbacks {

    public static final String ADD_TODO_TAG = "add_todo";

    private TodoAdapter mAdapter;
    private TodoOpenHelper mDbHelper;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        RecyclerView todoList = (RecyclerView) findViewById(R.id.rv_todo_list);
        todoList.setLayoutManager(manager);
        todoList.setHasFixedSize(false);

        mAdapter = new TodoAdapter(this);
        todoList.setAdapter(mAdapter);

        mDbHelper = new TodoOpenHelper(this);
        updateCursor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                showAddTodoFragment();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAddTodo(String todo) {
        if (todo.isEmpty()) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(TodoContract.Todo.COLUMN_NOTE, todo);
        values.put(TodoContract.Todo.COLUMN_COMPLETED, false);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.insert(TodoContract.Todo.TABLE_NAME, null, values);
        updateCursor();
    }

    @Override
    public void removeTodo(long id) {
        final long _id = id;
        Resources res = getResources();
        String note = mCursor.getString(mCursor.getColumnIndex(TodoContract.Todo.COLUMN_NOTE));
        String message = res.getString(R.string.confirm_delete, note);
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_todo)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String where = TodoContract.Todo._ID + "=?";
                        String[] whereArgs = new String[] { Long.toString(_id) };
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        db.delete(TodoContract.Todo.TABLE_NAME, where, whereArgs);
                        updateCursor();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .create()
                .show();
    }

    private void updateCursor() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.query(TodoContract.Todo.TABLE_NAME,
                TodoContract.Todo.ALL_COLUMNS,
                null,
                null,
                null,
                null,
                TodoContract.Todo._ID);
        mCursor = cursor;
        mAdapter.swapCursor(cursor);
    }

    private void showAddTodoFragment() {
        FragmentManager manager = getSupportFragmentManager();
        DialogFragment dialog = new TodoAddDialogFragment();
        dialog.show(manager, ADD_TODO_TAG);
    }
}
