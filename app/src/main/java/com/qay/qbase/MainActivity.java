package com.qay.qbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qay.qbase.permissionlibrary.PermissionGrant;
import com.qay.qbase.permissionlibrary.PermissionUtil;

public class MainActivity extends AppCompatActivity implements PermissionGrant {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PermissionsFragment fragment = new PermissionsFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case PermissionUtil.CODE_RECORD_AUDIO:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_GET_ACCOUNTS:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_READ_PHONE_STATE:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_CALL_PHONE:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_CAMERA:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_ACCESS_FINE_LOCATION:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_ACCESS_COARSE_LOCATION:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_READ_EXTERNAL_STORAGE:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_WRITE_EXTERNAL_STORAGE:
                Toast.makeText(MainActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * Called when the 'show camera' button is clicked.
     * Callback is defined in resource layout definition.
     */
    public void showCamera(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_CAMERA, this);
    }

    public void getAccounts(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_GET_ACCOUNTS, this);
    }

    public void callPhone(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_CALL_PHONE, this);
    }

    public void readPhoneState(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_READ_PHONE_STATE, this);
    }

    public void accessFineLocation(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_ACCESS_FINE_LOCATION, this);
    }

    public void accessCoarseLocation(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_ACCESS_COARSE_LOCATION, this);
    }

    public void readExternalStorage(View view) {
        PermissionUtil.requestPermission(this, PermissionUtil.CODE_READ_EXTERNAL_STORAGE, this);
    }

    public void writeExternalStorage(View view) {
        //PermissionUtil.requestPermission(this, PermissionUtil.CODE_WRITE_EXTERNAL_STORAGE, this);

        PermissionUtil.requestMultiPermissions(this, PermissionUtil.CODE_MULTI_PERMISSION, this);
    }

    public void recordAudio(View view) {
        PermissionUtil.requestMultiPermissions(this, PermissionUtil.CODE_SELF_MULTI_PERMISSION,new int[]{PermissionUtil.CODE_CAMERA,PermissionUtil.CODE_GET_ACCOUNTS}, this);
        //PermissionUtil.requestPermission(this, PermissionUtil.CODE_RECORD_AUDIO, this);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtil.requestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }
}
