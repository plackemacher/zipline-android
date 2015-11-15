package co.pala.payandgo.model.dummy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.pala.payandgo.R;
import co.pala.payandgo.model.Store;
import co.pala.payandgo.model.StoreItem;

public class Dummy {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<StoreItem> STORE_ITEMS;
    public static final Map<String, StoreItem> STORE_ITEM_MAP;

    static {
        // Add 3 sample items.
        List<StoreItem> items = new ArrayList<>();

        items.add(StoreItem.builder()
                .setTitle("Tide Original Scent Liquid Laundry Detergent, 150 fl oz")
                .setDescription("")
                .setPrice(1797)
                .build());

        items.add(StoreItem.builder()
                .setTitle("LG 49UF6700 49'' 4K Ultra HD 2160p 120Hz LED HDTV (4K x 2K)")
                .setDescription("")
                .setPrice(54799)
                .build());

        items.add(StoreItem.builder()
                .setTitle("Coleman 36-Quart Xtreme 5 Cooler, Blue")
                .setDescription("Keep food and refreshments cold with the Coleman 36-Quart Cooler. It will hold 46 cans, as well as ice, and features 2\" insulation to keep everything cold for a longer duration.")
                .setPrice(4200)
                .setBarcodeValue("9780201379624")
                .build());

        items.add(StoreItem.builder()
                .setTitle("Nature Valley 1.2 Ounce Sweet and Salty Peanut Granola Bar")
                .setDescription("Nature Valley® Sweet & Salty Peanut Granola Bars offer a perfect balance of savory nuts and sweet granola.  Each granola bar is bursting with peanuts and dipped in a creamy peanut-butter coating.")
                .setPrice(95)
                .setBarcodeValue("016000442825")
                .setDrawableResId(R.drawable.granola_bar)
                .build());

        items.add(StoreItem.builder()
                .setTitle("Yoplait Original Mountain Berry Yogurt (6 oz)")
                .setDescription("Yoplait® Original is proof that classic is always a good choice. Each cup bursts with the delicious, flavorful creaminess that isn't just good, it is SO good!")
                .setPrice(135)
                .setBarcodeValue("070470409658")
                .setDrawableResId(R.drawable.yoplait)
                .build());

        STORE_ITEMS = Collections.unmodifiableList(items);

        Map<String, StoreItem> map = new HashMap<>();
        for (StoreItem item : STORE_ITEMS) {
            if (item.getBarcodeValue() != null) {
                map.put(item.getBarcodeValue(), item);
            }
        }

        STORE_ITEM_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Store> STORES;

    static {
        // Add 3 sample items.
        List<Store> items = new ArrayList<>();

        items.add(Store.builder().setName("Trader Joe's").build());
        items.add(Store.builder().setName("Whole Foods").build());

        STORES = Collections.unmodifiableList(items);
    }
}
