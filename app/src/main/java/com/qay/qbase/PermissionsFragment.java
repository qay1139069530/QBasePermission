/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.qay.qbase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.qay.qbase.permissionlibrary.PermissionGrant;
import com.qay.qbase.permissionlibrary.PermissionUtil;

public class PermissionsFragment extends Fragment implements View.OnClickListener, FragmentCompat.OnRequestPermissionsResultCallback, PermissionGrant {

    private PermissionGrant permissionGrant;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_permission, null);
        initView(root);
        permissionGrant = this;
        return root;
    }

    private void initView(View root) {

        Button writeExternalStorage = (Button) root.findViewById(R.id.write_external_storage);
        Button readExternalStorage = (Button) root.findViewById(R.id.read_external_storage);
        Button accessCoarseLocation = (Button) root.findViewById(R.id.access_coarse_location);
        Button accessFineLocation = (Button) root.findViewById(R.id.access_fine_location);
        Button readPhoneState = (Button) root.findViewById(R.id.read_phone_state);
        Button callPhone = (Button) root.findViewById(R.id.call_phone);
        Button getAccounts = (Button) root.findViewById(R.id.get_accounts);
        Button recordAudio = (Button) root.findViewById(R.id.record_audio);
        Button showCamera = (Button) root.findViewById(R.id.show_camera);

        writeExternalStorage.setOnClickListener(this);
        readExternalStorage.setOnClickListener(this);
        accessCoarseLocation.setOnClickListener(this);
        accessFineLocation.setOnClickListener(this);
        readPhoneState.setOnClickListener(this);
        callPhone.setOnClickListener(this);
        getAccounts.setOnClickListener(this);
        recordAudio.setOnClickListener(this);
        showCamera.setOnClickListener(this);
    }

    @Override
    public void onPermissionGranted(int requestCode) {
        switch (requestCode) {
            case PermissionUtil.CODE_RECORD_AUDIO:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_GET_ACCOUNTS:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_READ_PHONE_STATE:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_CALL_PHONE:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_CAMERA:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_ACCESS_FINE_LOCATION:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_ACCESS_COARSE_LOCATION:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_READ_EXTERNAL_STORAGE:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                break;
            case PermissionUtil.CODE_WRITE_EXTERNAL_STORAGE:
                Toast.makeText(getActivity(), "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.write_external_storage:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_WRITE_EXTERNAL_STORAGE, permissionGrant);
                break;
            case R.id.read_external_storage:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_READ_EXTERNAL_STORAGE, permissionGrant);
                break;
            case R.id.access_coarse_location:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_ACCESS_COARSE_LOCATION, permissionGrant);
                break;
            case R.id.access_fine_location:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_ACCESS_FINE_LOCATION, permissionGrant);
                break;
            case R.id.read_phone_state:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_READ_PHONE_STATE, permissionGrant);
                break;
            case R.id.call_phone:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_CALL_PHONE, permissionGrant);
                break;
            case R.id.get_accounts:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_GET_ACCOUNTS, permissionGrant);
                break;
            case R.id.record_audio:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_RECORD_AUDIO, permissionGrant);
                break;
            case R.id.show_camera:
                PermissionUtil.requestPermission(getActivity(), PermissionUtil.CODE_CAMERA, permissionGrant);
                break;
            default:
                break;
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.requestPermissionsResult(getActivity(), requestCode, permissions, grantResults, permissionGrant);
    }
}
