package co.pala.payandgo.ui.store.useritem;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.pala.payandgo.R;
import co.pala.payandgo.model.StoreItem;
import co.pala.payandgo.model.UserItem;
import jp.wasabeef.recyclerview.animators.FadeInLeftAnimator;

/**
 * A fragment representing a list of Items.
 * <p>
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class UserItemFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private static final String SNACKBAR_BARCODE = "016000442825";

    private UserItemAdapter mAdapter;

    private final List<UserItem> mFutureUserItems = new ArrayList<>();

    @Bind(android.R.id.list)
    RecyclerView mRecyclerView;

    @Bind(R.id.ad)
    ImageView mAdImageView;

    public static UserItemFragment newInstance() {
        UserItemFragment fragment = new UserItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_useritem, container, false);

        ButterKnife.bind(this, parent);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*List<UserItem> userItems = Arrays.asList(
                UserItem.builder().setStoreItem(Dummy.STORE_ITEMS.get(0))
                        .setQuantity(2)
                        .build(),
                UserItem.builder().setStoreItem(Dummy.STORE_ITEMS.get(1)).build(),
                UserItem.builder().setStoreItem(Dummy.STORE_ITEMS.get(2)).build()
        );                          */

        mAdapter = new UserItemAdapter(getActivity()/*, userItems*/);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new FadeInLeftAnimator());

        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .colorResId(R.color.divider)
                        .sizeResId(R.dimen.divider)
                        .build());

        return parent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && mFutureUserItems.size() > 0) {
            boolean showAd = false;

            for (UserItem userItem : mFutureUserItems) {
                mAdapter.addItem(userItem);

                if (userItem.getStoreItem().getBarcodeValue().equals(SNACKBAR_BARCODE)) {
                    showAd = true;
                }
            }
            mFutureUserItems.clear();

            if (showAd) {
                mAdImageView.animate()
                        .alpha(1.f)
                        .start();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }

    public void onUserAddStoreItem(StoreItem storeItem) {
        UserItem userItem = UserItem.builder()
                .setStoreItem(storeItem)
                .build();

        mFutureUserItems.add(userItem);
    }

    public List<UserItem> getUserItems() {
        return mAdapter.getItems();
    }
}
