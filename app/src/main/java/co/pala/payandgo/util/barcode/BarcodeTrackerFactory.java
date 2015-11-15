/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.pala.payandgo.util.barcode;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.vision.Detector.Detections;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Factory for creating a tracker and associated graphic to be associated with a new barcode.  The
 * multi-processor uses this factory to create barcode trackers as needed -- one for each barcode.
 */
public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private final BarcodeDetectorListener mBarcodeDetectorListener;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper());

    public BarcodeTrackerFactory(BarcodeDetectorListener barcodeDetectorListener) {
        mBarcodeDetectorListener = barcodeDetectorListener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        Log.d("BarcodeTrackerFactory", "BarcodeTrackerFactory with barcode: " + barcode.displayValue);

        return new Tracker<Barcode>() {
            @Override
            public void onNewItem(int id, Barcode item) {
                Log.d("BarcodeTrackerFactory", "onNewItem");
                mMainHandler.post(() -> {
                    mBarcodeDetectorListener.onBarcodeVisible(barcode);
                });
            }

            @Override
            public void onMissing(Detections<Barcode> detections) {
                Log.d("BarcodeTrackerFactory", "onMissing");
                mMainHandler.post(() -> {
                    mBarcodeDetectorListener.onBarcodeDisappear(barcode);
                });
            }
        };
    }
}

