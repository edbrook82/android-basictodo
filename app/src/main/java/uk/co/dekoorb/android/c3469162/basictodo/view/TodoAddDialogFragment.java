package uk.co.dekoorb.android.c3469162.basictodo.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import uk.co.dekoorb.android.c3469162.basictodo.R;

/**
 * Created by c3469162 on 11/10/2017.
 */

public class TodoAddDialogFragment extends DialogFragment {

    private TodoAddDialogCallbacks mCallback;

    public interface TodoAddDialogCallbacks {
        void onAddTodo(String todo);
    }

    @Override
    public void onAttach(Context context) {
        if (!(context instanceof TodoAddDialogCallbacks)) {
            throw new IllegalStateException("");
        }
        mCallback = (TodoAddDialogCallbacks) context;
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_todo_dialog, null);

        final EditText todoText = dialogView.findViewById(R.id.et_todo);

        return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCallback.onAddTodo(todoText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null)
                .create();
    }
}
