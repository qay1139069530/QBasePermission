package com.qbase.permission;

/**
 * Created by Qay on 2017/3/8.
 */

public interface PermissionGrant {
    /**
     * permission request success
     */
    void onPermissionGranted(int requestCode);
}
