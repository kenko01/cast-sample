package com.example.sampleapp;

import android.content.Context;

import com.memo.CastOptions;
import com.memo.OptionsProvider;

/**
 * Created by 0 on 2017-12-20.
 */

public class CastOptionsProvider implements OptionsProvider {

    @Override
    public CastOptions getCastOptions(Context context) {
        CastOptions castOptions = new CastOptions.Builder()
                .setReceiverApplicationId(context.getPackageName())
                .build();
        return castOptions;
    }

}
