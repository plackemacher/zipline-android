package co.pala.payandgo.ui.store;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.pala.payandgo.R;
import co.pala.payandgo.model.StoreItem;
import co.pala.payandgo.model.dummy.Dummy;
import co.pala.payandgo.widget.BetterViewAnimator;

public class StoreItemDialogFragment extends DialogFragment {
    public static final String ARG_BARCODE = StoreItemDialogFragment.class.getName() + ".barcode";
    public static final String DIALOG_TAG = "ADD DIALOG";

    private OnItemDialogButtonListener mListener;

    @Bind(R.id.animator)
    BetterViewAnimator mViewAnimator;

    @Bind(android.R.id.icon)
    ImageView mImageView;

    @Bind(R.id.description)
    TextView mDescriptionView;

    public static StoreItemDialogFragment newInstance(Barcode barcode) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_BARCODE, barcode);

        StoreItemDialogFragment fragment = new StoreItemDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_storeitem, null);

        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        Barcode barcode = args.getParcelable(ARG_BARCODE);
        Log.d("ItemDialogFragment", "displayItem: " + barcode.rawValue);

        StoreItem item = Dummy.STORE_ITEM_MAP.get(barcode.rawValue);

        String title = null;
        if (item != null) {
            mViewAnimator.setDisplayedChildId(android.R.id.summary);
            title = item.getTitle();
            mDescriptionView.setText(item.getDescription());

            if (item.getDrawableResId() != 0) {
                mImageView.setImageResource(item.getDrawableResId());
            }
        }

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(title)
                .setPositiveButton(R.string.add, (dialog, which) -> {
                    mListener.onItemDialogAddClick(this, item);
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    mListener.onItemDialogCancelClick(this);
                })
                .create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemDialogButtonListener)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnItemDialogButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemDialogButtonListener {
        void onItemDialogAddClick(StoreItemDialogFragment dialogFragment, StoreItem storeItem);

        void onItemDialogCancelClick(StoreItemDialogFragment dialogFragment);
    }
}
