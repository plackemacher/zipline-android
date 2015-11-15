package co.pala.payandgo.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import org.parceler.Parcel;
import org.parceler.ParcelFactory;
import org.parceler.ParcelProperty;

import co.pala.payandgo.util.CurrencyUtils;

@AutoValue
@Parcel
public abstract class StoreItem {
    StoreItem() {
    }

    @ParcelProperty("title")
    public abstract String getTitle();

    @ParcelProperty("description")
    public abstract String getDescription();

    @ParcelProperty("price")
    public abstract long getPrice();

    @Nullable
    @ParcelProperty("barcodeValue")
    public abstract String getBarcodeValue();

    @DrawableRes
    @ParcelProperty("drawableResId")
    public abstract int getDrawableResId();

    public String getPriceString() {
        return CurrencyUtils.format(getPrice());
    }

    public static Builder builder() {
        return new AutoValue_StoreItem.Builder()
                .setDrawableResId(0);
    }

    @ParcelFactory
    public static StoreItem create(String title, String description, long price, String barcodeValue, int drawableResId) {
        return builder()
                .setTitle(title)
                .setDescription(description)
                .setPrice(price)
                .setBarcodeValue(barcodeValue)
                .setDrawableResId(drawableResId)
                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setTitle(String title);
        public abstract Builder setDescription(String description);
        public abstract Builder setPrice(long price);
        public abstract Builder setBarcodeValue(String barcodeValue);
        public abstract Builder setDrawableResId(@DrawableRes int drawableResId);
        public abstract StoreItem build();
    }
}
