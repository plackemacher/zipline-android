package co.pala.payandgo.ui.checkoutfinish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.pala.payandgo.R;

public class CheckoutFinishActivity extends AppCompatActivity {

    @Bind(R.id.qr_code)
    ImageView mQRCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_finish);
        ButterKnife.bind(this);

        mQRCodeView.setImageResource(R.drawable.qrcode);
    }
}
