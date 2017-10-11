package uk.co.dekoorb.android.c3469162.basictodo.model;

import android.provider.BaseColumns;

/**
 * Created by c3469162 on 10/10/2017.
 */

public class TodoContract {

    public static final class Todo implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_COMPLETED = "complete";

        public static final String[] ALL_COLUMNS = new String[] {
                _ID,
                COLUMN_NOTE,
                COLUMN_COMPLETED
        };
    }

}
