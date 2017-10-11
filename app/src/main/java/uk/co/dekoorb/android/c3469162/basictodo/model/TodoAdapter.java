package uk.co.dekoorb.android.c3469162.basictodo.model;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import uk.co.dekoorb.android.c3469162.basictodo.R;

/**
 * Created by c3469162 on 10/10/2017.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private static final String TAG = "TodoAdapter";

    public interface TodoAdapterCallbacks {
        void removeTodo(long id);
    }

    private TodoAdapterCallbacks mCallback;

    private Cursor mCursor;
    private HashMap<String, Integer> mColumnMap;

    public class TodoHolder extends RecyclerView.ViewHolder {
        private long todoId;
        public final TextView todoText;

        public TodoHolder(View view) {
            super(view);
            todoText = view.findViewById(R.id.tv_todo);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mCallback.removeTodo(todoId);
                    return true;
                }
            });
        }

        public void bind(Cursor cursor) {
            todoId = mCursor.getLong(mColumnMap.get(TodoContract.Todo._ID));
            todoText.setText(cursor.getString(mColumnMap.get(TodoContract.Todo.COLUMN_NOTE)));
        }
    }

    public TodoAdapter(TodoAdapterCallbacks callback) {
        mCallback = callback;
        mCursor = null;
        mColumnMap = new HashMap<>();
        swapCursor(mCursor);
    }

    private void updateColumnMap() {
        mColumnMap.clear();

        if (mCursor == null) {
            return;
        }

        mColumnMap.put(TodoContract.Todo._ID,
                mCursor.getColumnIndex(TodoContract.Todo._ID));

        mColumnMap.put(TodoContract.Todo.COLUMN_NOTE,
                mCursor.getColumnIndex(TodoContract.Todo.COLUMN_NOTE));

        mColumnMap.put(TodoContract.Todo.COLUMN_COMPLETED,
                mCursor.getColumnIndex(TodoContract.Todo.COLUMN_COMPLETED));
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = cursor;
        updateColumnMap();
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        if (mCursor.moveToPosition(position)) {
            return mCursor.getLong(mColumnMap.get(TodoContract.Todo._ID));
        }
        return super.getItemId(position);
    }

    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.todo_item, parent, false);
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.bind(mCursor);
    }

    @Override
    public int getItemCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }
}
