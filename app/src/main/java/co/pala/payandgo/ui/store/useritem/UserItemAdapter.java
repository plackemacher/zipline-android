package co.pala.payandgo.ui.store.useritem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.pala.payandgo.R;
import co.pala.payandgo.ui.store.useritem.UserItemAdapter.VHUserItem;
import co.pala.payandgo.model.UserItem;

public class UserItemAdapter extends RecyclerView.Adapter<VHUserItem> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<UserItem> mUserItems;

    public UserItemAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public UserItemAdapter(Context context, List<UserItem> userItems) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mUserItems = userItems;
    }

    @Override
    public VHUserItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.listitem_useritem, parent, false);

        return new VHUserItem(view);
    }

    @Override
    public void onBindViewHolder(VHUserItem holder, int position) {
        UserItem userItem = getItem(position);

        holder.mTitleView.setText(userItem.getStoreItem().getTitle());
        holder.mQuantityView.setText(mContext.getString(R.string.quantity_multiplier, userItem.getQuantity()));
        holder.mPriceView.setText(userItem.getTotalPriceString());
    }

    public UserItem getItem(int position) {
        return mUserItems.get(position);
    }

    public List<UserItem> getItems() {
        return Collections.unmodifiableList(mUserItems);
    }

    @Override
    public int getItemCount() {
        return mUserItems.size();
    }

    public void addItem(UserItem userItem) {
        mUserItems.add(userItem);
        notifyItemInserted(getItemCount() - 1);
    }

    static class VHUserItem extends ViewHolder {

        @Bind(android.R.id.title)
        TextView mTitleView;

        @Bind(R.id.quantity)
        TextView mQuantityView;

        @Bind(R.id.price)
        TextView mPriceView;

        public VHUserItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
