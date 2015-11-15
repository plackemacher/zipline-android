package co.pala.payandgo.ui.checkout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.pala.payandgo.R;
import co.pala.payandgo.model.CheckoutSummary;
import co.pala.payandgo.model.UserItem;
import co.pala.payandgo.ui.checkout.CheckoutAdapter.VHCheckout;

public class CheckoutAdapter extends RecyclerView.Adapter<VHCheckout> {

    private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SUMMARY = 1;

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<UserItem> mUserItems;
    private final CheckoutSummary mCheckoutSummary;

    private OnConfirmCheckoutClickListener mOnConfirmCheckoutClickListener;

    public CheckoutAdapter(Context context, CheckoutSummary checkoutSummary) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mUserItems = checkoutSummary.getUserItems();
        mCheckoutSummary = checkoutSummary;
    }

    public void setOnConfirmCheckoutClickListener(OnConfirmCheckoutClickListener onConfirmCheckoutClickListener) {
        mOnConfirmCheckoutClickListener = onConfirmCheckoutClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getUserItemCount()) {
            return TYPE_ITEM;

        } else if (position == getUserItemCount()) {
            return TYPE_SUMMARY;
        } else {
            throw new RuntimeException("Unexpected item");
        }
    }

    @Override
    public VHCheckout onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return onCreateItemViewHolder(parent);
        } else if (viewType == TYPE_SUMMARY) {
            return onCreateSummaryViewHolder(parent);
        } else {
            throw new RuntimeException("Unexpected view type");
        }
    }

    private VHCheckoutItem onCreateItemViewHolder(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listitem_useritem, parent, false);
        return new VHCheckoutItem(view);
    }

    private VHCheckoutSummary onCreateSummaryViewHolder(ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listitem_checkout_summary, parent, false);
        return new VHCheckoutSummary(view);
    }

    @Override
    public void onBindViewHolder(VHCheckout holder, int position) {
        if (holder instanceof VHCheckoutItem) {
            onBindItemViewHolder((VHCheckoutItem) holder, position);
        } else if (holder instanceof VHCheckoutSummary) {
            onBindSummaryViewHolder((VHCheckoutSummary) holder);
        } else {
            throw new RuntimeException("Unexpected view holder: " + holder.getClass().getName());
        }
    }

    private void onBindItemViewHolder(VHCheckoutItem holder, int position) {
        UserItem userItem = getUserItem(position);

        holder.mTitleView.setText(userItem.getStoreItem().getTitle());
        holder.mQuantityView.setText(mContext.getString(R.string.quantity_multiplier, userItem.getQuantity()));
        holder.mPriceView.setText(userItem.getTotalPriceString());
    }

    private void onBindSummaryViewHolder(VHCheckoutSummary holder) {
        holder.mPriceTaxView.setText(mCheckoutSummary.getTotalTaxString());
        holder.mPriceTotalView.setText(mCheckoutSummary.getTotalString());

        holder.mConfirmButton.setOnClickListener(v -> {
            if (mOnConfirmCheckoutClickListener != null) {
                mOnConfirmCheckoutClickListener.onConfirmCheckout();
            }
        });
    }

    public UserItem getUserItem(int position) {
        return mUserItems.get(position);
    }

    public int getUserItemCount() {
        return mUserItems.size();
    }

    @Override
    public int getItemCount() {
        return getUserItemCount() + 1;
    }

    static class VHCheckout extends ViewHolder {
        public VHCheckout(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class VHCheckoutItem extends VHCheckout {
        @Bind(android.R.id.title)
        TextView mTitleView;

        @Bind(R.id.quantity)
        TextView mQuantityView;

        @Bind(R.id.price)
        TextView mPriceView;

        public VHCheckoutItem(View itemView) {
            super(itemView);
        }
    }

    static class VHCheckoutSummary extends VHCheckout {

        @Bind(R.id.price_tax)
        TextView mPriceTaxView;

        @Bind(R.id.price_total)
        TextView mPriceTotalView;

        @Bind(R.id.confirm_button)
        Button mConfirmButton;

        public VHCheckoutSummary(View itemView) {
            super(itemView);
        }
    }

    public interface OnConfirmCheckoutClickListener {
        void onConfirmCheckout();
    }
}
