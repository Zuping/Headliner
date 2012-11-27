package com.uiproject.headliner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LocationDialogFragment extends DialogFragment {
	
    public interface LocationDialogListener {
        public void onDialogItemClick(int which);
    }

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.choose_location)
				.setItems(R.array.locations,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// The 'which' argument contains the index
								// position of the selected item
								mListener.onDialogItemClick(which);
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
	
	LocationDialogListener mListener;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LocationDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
