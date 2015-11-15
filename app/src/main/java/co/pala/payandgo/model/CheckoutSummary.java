package co.pala.payandgo.model;

import com.google.auto.value.AutoValue;

import org.parceler.Parcel;
import org.parceler.ParcelFactory;
import org.parceler.ParcelProperty;

import java.util.List;

import co.pala.payandgo.util.CurrencyUtils;

@AutoValue
@Parcel
public abstract class CheckoutSummary {
    CheckoutSummary() {

    }

    @ParcelProperty("userItems")
    public abstract List<UserItem> getUserItems();

    @ParcelProperty("taxRate")
    public abstract double getTaxRate();

    public abstract long getTotal();
    public abstract long getTotalTax();


    public String getTotalString() {
        return CurrencyUtils.format(getTotal());
    }

    public String getTotalTaxString() {
        return CurrencyUtils.format(getTotalTax());
    }

    public static Builder builder() {
        return new AutoValue_CheckoutSummary.Builder();
    }

    @ParcelFactory
    public static CheckoutSummary create(List<UserItem> userItems, double taxRate) {
        return builder()
                .setUserItems(userItems)
                .setTaxRate(taxRate)
                .build();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setUserItems(List<UserItem> userItems);
        public abstract Builder setTaxRate(double taxRate);
        abstract Builder setTotal(long total);
        abstract Builder setTotalTax(long totalTax);

        abstract List<UserItem> getUserItems();
        abstract double getTaxRate();

        abstract CheckoutSummary autoBuild();

        public CheckoutSummary build() {
            long total = 0;
            for (UserItem userItem : getUserItems()) {
                total += userItem.getTotalPrice();
            }

            long totalTax = (long) Math.ceil(((double) total) * getTaxRate());
            setTotalTax(totalTax);
            setTotal(total + totalTax);

            return autoBuild();
        }
    }
}
