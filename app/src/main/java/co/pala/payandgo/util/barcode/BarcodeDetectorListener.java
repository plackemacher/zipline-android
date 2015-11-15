package co.pala.payandgo.util.barcode;

import com.google.android.gms.vision.barcode.Barcode;

public interface BarcodeDetectorListener {
    void onBarcodeVisible(Barcode barcode);
    void onBarcodeDisappear(Barcode barcode);
}
