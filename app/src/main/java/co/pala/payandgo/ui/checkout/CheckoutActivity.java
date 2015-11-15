package co.pala.payandgo.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.pala.payandgo.R;
import co.pala.payandgo.model.CheckoutSummary;
import co.pala.payandgo.ui.checkoutfinish.CheckoutFinishActivity;

public class CheckoutActivity extends AppCompatActivity {

    public static final String EXTRA_CHECKOUT_SUMMARY = CheckoutActivity.class.getName() + ".checkoutSummary";

    @Bind(android.R.id.list)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        CheckoutSummary checkoutSummary = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_CHECKOUT_SUMMARY));

        CheckoutAdapter adapter = new CheckoutAdapter(this, checkoutSummary);
        adapter.setOnConfirmCheckoutClickListener(() -> {
            Intent intent = new Intent(this, CheckoutFinishActivity.class);
            startActivity(intent);
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }
}
