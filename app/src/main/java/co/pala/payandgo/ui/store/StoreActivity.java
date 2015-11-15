package co.pala.payandgo.ui.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.vision.barcode.Barcode;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.pala.payandgo.R;
import co.pala.payandgo.model.CheckoutSummary;
import co.pala.payandgo.model.StoreItem;
import co.pala.payandgo.model.UserItem;
import co.pala.payandgo.ui.checkout.CheckoutActivity;
import co.pala.payandgo.ui.store.useritem.UserItemFragment;

public class StoreActivity extends AppCompatActivity
        implements ScanFragment.OnFragmentInteractionListener, UserItemFragment.OnFragmentInteractionListener, StoreItemDialogFragment.OnItemDialogButtonListener {

    private static final double TAX_RATE = 0.0575;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Bind(R.id.container)
    ViewPager mViewPager;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0 && mFab.getVisibility() == View.VISIBLE) {
                    mFab.hide();
                } else if (position == 1 && mFab.getVisibility() != View.VISIBLE) {
                    mFab.show();
                }
            }
        });
    }

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        Intent intent = new Intent(this, CheckoutActivity.class);
        List<UserItem> userItems = new ArrayList<>(mSectionsPagerAdapter.getUserItemFragment().getUserItems());

        CheckoutSummary checkoutSummary = CheckoutSummary.builder()
                .setTaxRate(TAX_RATE)
                .setUserItems(userItems)
                .build();

        intent.putExtra(CheckoutActivity.EXTRA_CHECKOUT_SUMMARY, Parcels.wrap(CheckoutSummary.class, checkoutSummary));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBarcodeScanned(Barcode barcode) {
        StoreItemDialogFragment fragment = StoreItemDialogFragment.newInstance(barcode);

        fragment.show(getSupportFragmentManager(), StoreItemDialogFragment.DIALOG_TAG);
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onItemDialogAddClick(StoreItemDialogFragment dialogFragment, StoreItem storeItem) {
        Snackbar.make(mFab, R.string.item_added_to_your_cart, Snackbar.LENGTH_LONG).show();

        mSectionsPagerAdapter.getUserItemFragment().onUserAddStoreItem(storeItem);
    }

    @Override
    public void onItemDialogCancelClick(StoreItemDialogFragment dialogFragment) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private int mContainerId;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ScanFragment.newInstance();
                case 1:
                    return UserItemFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SCAN";
                case 1:
                    return "ITEMS";
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mContainerId = container.getId();
            return super.instantiateItem(container, position);
        }

        private String getFragmentTag(int position) {
            return "android:switcher:" + mContainerId + ":" + position;
        }

        public ScanFragment getScanFragment() {
            return (ScanFragment)getSupportFragmentManager().findFragmentByTag(getFragmentTag(0));
        }

        public UserItemFragment getUserItemFragment() {
            return (UserItemFragment)getSupportFragmentManager().findFragmentByTag(getFragmentTag(1));
        }
    }
}
