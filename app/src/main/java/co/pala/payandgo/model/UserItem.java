package co.pala.payandgo.model;

import com.google.auto.value.AutoValue;

import org.parceler.Parcel;
import org.parceler.ParcelFactory;
import org.parceler.ParcelProperty;

import co.pala.payandgo.util.CurrencyUtils;

@AutoValue
@Parcel
public abstract class UserItem {
    UserItem() {

    }

    @ParcelProperty("storeItem")
    public abstract StoreItem getStoreItem();

    @ParcelProperty("quantity")
    public abstract int getQuantity();

    public long getTotalPrice() {
        return getStoreItem().getPrice() * getQuantity();
    }

    public String getTotalPriceString() {
        return CurrencyUtils.format(getTotalPrice());
    }

    public static Builder builder() {
        return new AutoValue_UserItem.Builder()
                .setQuantity(1);
    }

    @ParcelFactory
    public static UserItem create(StoreItem storeItem, int quantity) {
        return builder()
                .setStoreItem(storeItem)
                .setQuantity(quantity)
                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setStoreItem(StoreItem storeItem);
        public abstract Builder setQuantity(int quantity);
        public abstract UserItem build();
    }
}
