package com.qbase.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Qay on 2017/3/8.
 */
public class PermissionUtil {

    public static final int CODE_READ_EXTERNAL_STORAGE = 1;
    public static final int CODE_WRITE_EXTERNAL_STORAGE = CODE_READ_EXTERNAL_STORAGE + 1;
    public static final int CODE_CALL_PHONE = CODE_WRITE_EXTERNAL_STORAGE + 1;
    public static final int CODE_READ_PHONE_STATE = CODE_CALL_PHONE + 1;
    public static final int CODE_CAMERA = CODE_READ_PHONE_STATE + 1;
    public static final int CODE_GET_ACCOUNTS = CODE_CAMERA + 1;
    public static final int CODE_READ_ACCOUNTS = CODE_GET_ACCOUNTS + 1;
    public static final int CODE_WRITE_ACCOUNTS = CODE_READ_ACCOUNTS + 1;
    public static final int CODE_RECORD_AUDIO = CODE_WRITE_ACCOUNTS + 1;
    public static final int CODE_ACCESS_FINE_LOCATION = CODE_RECORD_AUDIO + 1;
    public static final int CODE_ACCESS_COARSE_LOCATION = CODE_ACCESS_FINE_LOCATION + 1;
    public static final int CODE_SYSTEM_ALERT_WINDOW = CODE_ACCESS_COARSE_LOCATION + 1;
    public static final int CODE_SELF_MULTI_PERMISSION = 100;
    public static final int CODE_MULTI_PERMISSION = 101;

    private static final String PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    private static final String PERMISSION_READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    private static final String PERMISSION_READ_ACCOUNTS = Manifest.permission.READ_CONTACTS;
    private static final String PERMISSION_WRITE_ACCOUNTS = Manifest.permission.WRITE_CONTACTS;
    private static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    private static final String PERMISSION_ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String PERMISSION_SYSTEM_ALERT_WINDOW = Manifest.permission.SYSTEM_ALERT_WINDOW;


    private static final String[] requestPermissions = {
            PERMISSION_READ_EXTERNAL_STORAGE,
            PERMISSION_WRITE_EXTERNAL_STORAGE,
            PERMISSION_CALL_PHONE,
            PERMISSION_READ_PHONE_STATE,
            PERMISSION_CAMERA,
            PERMISSION_GET_ACCOUNTS,
            PERMISSION_READ_ACCOUNTS,
            PERMISSION_WRITE_ACCOUNTS,
            PERMISSION_RECORD_AUDIO,
            PERMISSION_ACCESS_FINE_LOCATION,
            PERMISSION_ACCESS_COARSE_LOCATION,
            PERMISSION_SYSTEM_ALERT_WINDOW
    };


    /**
     * get request permission
     */
    private static String getPermission(int requestCode) {
        String permission = "";
        switch (requestCode) {
            case CODE_READ_EXTERNAL_STORAGE:
                permission = PERMISSION_READ_EXTERNAL_STORAGE;
                break;
            case CODE_WRITE_EXTERNAL_STORAGE:
                permission = PERMISSION_WRITE_EXTERNAL_STORAGE;
                break;
            case CODE_READ_PHONE_STATE:
                permission = PERMISSION_READ_PHONE_STATE;
                break;
            case CODE_CALL_PHONE:
                permission = PERMISSION_CALL_PHONE;
                break;
            case CODE_GET_ACCOUNTS:
                permission = PERMISSION_GET_ACCOUNTS;
                break;
            case CODE_READ_ACCOUNTS:
                permission = PERMISSION_READ_ACCOUNTS;
                break;
            case CODE_WRITE_ACCOUNTS:
                permission = PERMISSION_WRITE_ACCOUNTS;
                break;
            case CODE_CAMERA:
                permission = PERMISSION_CAMERA;
                break;
            case CODE_RECORD_AUDIO:
                permission = PERMISSION_RECORD_AUDIO;
                break;
            case CODE_ACCESS_FINE_LOCATION:
                permission = PERMISSION_ACCESS_FINE_LOCATION;
                break;
            case CODE_ACCESS_COARSE_LOCATION:
                permission = PERMISSION_ACCESS_COARSE_LOCATION;
                break;
            case CODE_SYSTEM_ALERT_WINDOW:
                permission = PERMISSION_SYSTEM_ALERT_WINDOW;
                break;
            case CODE_MULTI_PERMISSION:
                permission = requestPermissions.toString();
                break;
        }

        return permission;

    }

    /**
     * open setting activity
     */
    private static void openSetting(Context context) {
        try {
            Uri packageURI = Uri.parse("package:" + context.getPackageName());
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Requests permission.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestPermission(@NonNull Activity activity, int requestCode, @NonNull PermissionGrant permissionGrant) {
        if (requestCode < 0 || requestCode >= requestPermissions.length) {
            return;
        }
        final String requestPermission = getPermission(requestCode);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            boolean isExit = permissionExist(activity, requestPermission);
            if (isExit) {
                //check permission is in manifest
                permissionGrant.onPermissionGranted(requestCode);
            } else {
                //TODO no permission
                Toast.makeText(activity, requestPermission, Toast.LENGTH_SHORT).show();
            }

            return;
        }

        // system alert
        if (requestCode == CODE_SYSTEM_ALERT_WINDOW) {
            requestAlertPermission(activity, permissionGrant);
            return;
        }

        //is permission
        boolean permission_granted = checkPermission(activity, requestPermission);

        if (permission_granted) {
            //has permission
            Toast.makeText(activity, "opened:" + requestPermissions[requestCode], Toast.LENGTH_SHORT).show();
            permissionGrant.onPermissionGranted(requestCode);
        } else {
            //no permission
            boolean rationale = showRequestRationale(activity, requestPermission);
            if (rationale) {
                //TODO show dialog
                shouldShowRationale(activity, requestCode, requestPermission);
                Toast.makeText(activity, requestPermission, Toast.LENGTH_SHORT).show();
            } else {
                requestPermission(activity, requestPermission, requestCode);
            }
        }
    }

    /**
     * Requests permissions.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestMultiPermissions(@NonNull Activity activity, int requestCode, @NonNull int[] permissions, @NonNull PermissionGrant permissionGrant) {
        if (permissions.length == 0) {
            return;
        }
        final List<String> permissionsList = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = getPermission(permissions[i]);
            permissionsList.add(permission);
        }
        if (permissionsList.size() > 0) {
            String[] permission = permissionsList.toArray(new String[permissionsList.size()]);
            requestPermissions(activity, permission, requestCode);
            return;
        }
        permissionGrant.onPermissionGranted(requestCode);
    }


    /**
     * Requests permissions.
     *
     * @param activity
     * @param requestCode request code, e.g. if you need request CAMERA permission,parameters is PermissionUtils.CODE_CAMERA
     */
    public static void requestMultiPermissions(@NonNull Activity activity, int requestCode, @NonNull PermissionGrant permissionGrant) {

        //final List<String> permissionsList = getNoGrantedPermission(activity, false);
        final List<String> permissionsList = new ArrayList<>();
        final List<String> shouldRationalePermissionsList = getNoGrantedPermission(activity, true);

        if (permissionsList != null && permissionsList.size() > 0) {
            String[] permission = permissionsList.toArray(new String[permissionsList.size()]);
            requestPermissions(activity, permission, CODE_MULTI_PERMISSION);
            return;
        }

        if (shouldRationalePermissionsList != null && shouldRationalePermissionsList.size() > 0) {
            //TODO dialog
            String[] permission = shouldRationalePermissionsList.toArray(new String[shouldRationalePermissionsList.size()]);

//            showMessageOKCancel(activity, "should open those permission",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(activity, permission, CODE_MULTI_PERMISSION);
//                        }
//                    });
            shouldShowRationale(activity, requestCode, permission.toString());
            Toast.makeText(activity, permission.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
    }

    /**
     * @param activity
     * @param isShouldRationale true: return no granted and shouldShowRequestPermissionRationale permissions, false:return no granted and !shouldShowRequestPermissionRationale
     * @return
     */
    private static ArrayList<String> getNoGrantedPermission(Activity activity,
                                                            boolean isShouldRationale) {
        ArrayList<String> permissions = new ArrayList<>();
        for (int i = 0; i < requestPermissions.length; i++) {
            String requestPermission = requestPermissions[i];
            if (!requestPermission.equals(PERMISSION_SYSTEM_ALERT_WINDOW)) {
                //has permission
                boolean permission_granted = checkPermission(activity, requestPermission);
                if (!permission_granted) {
                    //no permission
                    boolean rationale = showRequestRationale(activity, requestPermission);
                    if (rationale) {
                        if (isShouldRationale) {
                            permissions.add(requestPermission);
                        }
                    } else {
                        if (!isShouldRationale) {
                            permissions.add(requestPermission);
                        }
                    }
                }
            }
        }
        return permissions;
    }

    /**
     * return true if permission is granted, false otherwise.
     * <p/>
     * can be used outside of activity.
     * PackageManager.PERMISSION_GRANTED  has permission
     */

    private static boolean checkPermission(@NonNull Context context, @NonNull String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @return true if explanation needed.
     */
    private static boolean showRequestRationale(@NonNull Activity context, @NonNull String permissionName) {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, permissionName);
    }

    /**
     * request permission
     */
    private static void requestPermission(@NonNull Activity activity, @NonNull String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    /**
     * request permission
     */
    private static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * @return true if permission exists in the manifest, false otherwise.
     */
    private static boolean permissionExist(@NonNull Context context, @NonNull String permissionName) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (packageInfo.requestedPermissions != null) {
                for (String p : packageInfo.requestedPermissions) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * used only for {@link Manifest.permission#SYSTEM_ALERT_WINDOW}
     * alert permission
     */
    private static void requestAlertPermission(@NonNull Activity activity, @NonNull PermissionGrant permissionGrant) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (!isSystemAlertGranted(activity)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
                    activity.startActivityForResult(intent, CODE_SYSTEM_ALERT_WINDOW);
                } else {
                    permissionGrant.onPermissionGranted(CODE_SYSTEM_ALERT_WINDOW);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            permissionGrant.onPermissionGranted(CODE_SYSTEM_ALERT_WINDOW);
        }
    }

    /**
     * @return true if {@link Manifest.permission#SYSTEM_ALERT_WINDOW} is granted
     */
    private static boolean isSystemAlertGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(activity);
        }
        return true;
    }

    /**
     * @param activity
     * @param requestCode  Need consistent with requestPermission
     * @param permissions
     * @param grantResults
     */
    public static void requestPermissionsResult(@NonNull Activity activity, int requestCode, @NonNull String[] permissions,
                                                @NonNull int[] grantResults, @NonNull PermissionGrant permissionGrant) {
        if (requestCode < 0) {
            Toast.makeText(activity, "illegal requestCode:" + requestCode, Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == CODE_MULTI_PERMISSION) {
            requestMultiResult(activity, permissions, grantResults, permissionGrant);
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //TODO success, do something, can use callback
            permissionGrant.onPermissionGranted(requestCode);

        } else {
            //TODO hint user this permission function  dialog
            openSetting(activity);
        }

    }

    /**
     * @param activity
     * @param permissions
     * @param grantResults
     */
    private static void requestMultiResult(@NonNull Activity activity, @NonNull String[] permissions, @NonNull int[] grantResults, @NonNull PermissionGrant permissionGrant) {
        //TODO
        Map<String, Integer> perms = new HashMap<>();
        ArrayList<String> notGranted = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            perms.put(permissions[i], grantResults[i]);
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGranted.add(permissions[i]);
            }
        }
        if (notGranted.size() == 0) {
            Toast.makeText(activity, "all permission success" + notGranted, Toast.LENGTH_SHORT).show();
            permissionGrant.onPermissionGranted(CODE_MULTI_PERMISSION);
        } else {
            //TODO hint user this permission function
            openSetting(activity);
        }

    }


    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        //TODO
        String[] permissionsHint = activity.getResources().getStringArray(R.array.permissions);
        showMessageOKCancel(activity, "Rationale: " + requestPermission, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
            }
        });
    }

    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}
