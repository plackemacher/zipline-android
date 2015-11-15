package co.pala.payandgo.util;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    public static String format(long value) {
        return CURRENCY_FORMAT.format((double)value / 100);
    }
}
