package com.example.weker.powermanagement;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {

    static final int ADMIN_INTENT = 15;

    DevicePolicyManager deviceManager;
    ActivityManager activityManager;
    ComponentName compName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this, MyAdminReceiver.class);

        setContentView(R.layout.activity_main);
        Button lock = (Button) findViewById(R.id.lock);
        lock.setOnClickListener(this);

        Button disable = (Button) findViewById(R.id.disable);
        Button enable = (Button) findViewById(R.id.enable);
        Button hardReset = (Button) findViewById(R.id.hardReset);
        disable.setOnClickListener(this);
        enable.setOnClickListener(this);
        hardReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lock:
            boolean active = deviceManager.isAdminActive(compName);
            if (active) {
                deviceManager.lockNow();
            }else{
                Toast.makeText(getApplicationContext(), "Not Registered as admin", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.enable:
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "enabled");
            startActivityForResult(intent, ADMIN_INTENT);
            break;

            case R.id.disable:
            deviceManager.removeActiveAdmin(compName);
            Toast.makeText(getApplicationContext(), "Admin registration removed", Toast.LENGTH_SHORT).show();
            break;

            case R.id.hardReset:
            boolean yes = deviceManager.isAdminActive(compName);
            if (yes) {
                deviceManager.wipeData(0);
            }else{
                Toast.makeText(getApplicationContext(), "Not Registered as admin", Toast.LENGTH_SHORT).show();
            }
            break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_INTENT) {
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Registered As Admin", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to register as Admin", Toast.LENGTH_SHORT).show();
                }
        }
    }
}



