package com.example.fluttermethodchannel;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.HashMap;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "samples.flutter.dev/battery";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        HashMap<String,String> map = new HashMap<>();
        map.put("text","This string is passed from android to flutter");

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                // Call the desired channel message here.
                new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL)
                        .invokeMethod("openFlutter", map);
            }
        });


        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL)
                .setMethodCallHandler(
                        (call,result) ->{
                            if (call.method.equals("getBatteryLevel")) {
                                String text = call.argument("text");
                                openAndroidActivity(text);
//                                int batteryLevel =  getBatteryLevel();
//                                if (batteryLevel != -1) {
//                                    result.success(batteryLevel);
//                                } else {
//                                    result.error("Unavailable", "Battery level not available", null);
//                                }
                            } else {
                                result.notImplemented();
                            }
                        }
                );

    }

    private void openAndroidActivity(String text) {
        Intent intent = new Intent(this, FirstAndroidActivity.class);
        Bundle b = new Bundle();
        b.putString("title",text);
        intent.putExtras(b);
        startActivity(intent);
    }

    private int getBatteryLevel() {
        int batteryLevel = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        } else {
            Intent intent = new ContextWrapper(getApplicationContext()).
                    registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            batteryLevel = (intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100) /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        }
        batteryLevel = 29;
        return batteryLevel;
    }
}
