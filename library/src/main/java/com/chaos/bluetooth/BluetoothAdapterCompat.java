/*
 * Copyright 2017 Chaos
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

package com.chaos.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresPermission;

import com.chaos.bluetooth.hidden.HiddenBluetoothAdapterCompat;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Chaos
 *         03/03/2017
 */

public class BluetoothAdapterCompat {

    protected BluetoothAdapterCompat() {
        // Not publicly instantiable, but may be extended.
    }

    /**
     * Get the profile proxy object associated with the profile.
     *
     * <p>Profile can be one of {@link BluetoothProfile#HEALTH}, {@link BluetoothProfile#HEADSET},
     * {@link BluetoothProfile#A2DP}, {@link BluetoothProfile#GATT}, or
     * {@link BluetoothProfile#GATT_SERVER}. Clients must implement
     * {@link BluetoothProfile.ServiceListener} to get notified of
     * the connection status and to get the proxy object.
     *
     * @param context  Context of the application
     * @param listener The service Listener for connection callbacks.
     * @param profile  The Bluetooth profile; either {@link BluetoothProfile#HEALTH},
     *                 {@link BluetoothProfile#HEADSET}, {@link BluetoothProfile#A2DP}.
     *                 {@link BluetoothProfile#GATT} or {@link BluetoothProfile#GATT_SERVER}.
     * @return true on success, false on error
     */
    public static boolean getProfileProxy(BluetoothAdapter adapter, Context context,
            BluetoothProfile.ServiceListener listener, int profile) {
        if (Build.VERSION.SDK_INT >= 11) {
            return BluetoothAdapterCompatHoneycomb.getProfileProxy(adapter, context, listener,
                    profile);
        }
        return false;
    }

    /**
     * Close the connection of the profile proxy to the Service.
     *
     * <p> Clients should call this when they are no longer using
     * the proxy obtained from {@link #getProfileProxy}.
     * Profile can be one of  {@link BluetoothProfile#HEALTH}, {@link BluetoothProfile#HEADSET} or
     * {@link BluetoothProfile#A2DP}
     *
     * @param profile
     * @param proxy   Profile proxy object
     */
    public static void closeProfileProxy(BluetoothAdapter adapter, int profile,
            BluetoothProfile proxy) {
        if (Build.VERSION.SDK_INT >= 11) {
            BluetoothAdapterCompatHoneycomb.closeProfileProxy(adapter, profile, proxy);
        }
    }

    /**
     * Create a listening, insecure RFCOMM Bluetooth socket with Service Record.
     * <p>The link key is not required to be authenticated, i.e the communication may be
     * vulnerable to Man In the Middle attacks. For Bluetooth 2.1 devices,
     * the link will be encrypted, as encryption is mandartory.
     * For legacy devices (pre Bluetooth 2.1 devices) the link will not
     * be encrypted. Use {@link BluetoothAdapter#listenUsingRfcommWithServiceRecord}, if an
     * encrypted and authenticated communication channel is desired.
     * <p>Use {@link BluetoothServerSocket#accept} to retrieve incoming
     * connections from a listening {@link BluetoothServerSocket}.
     * <p>The system will assign an unused RFCOMM channel to listen on.
     * <p>The system will also register a Service Discovery
     * Protocol (SDP) record with the local SDP server containing the specified
     * UUID, service name, and auto-assigned channel. Remote Bluetooth devices
     * can use the same UUID to query our SDP server and discover which channel
     * to connect to. This SDP record will be removed when this socket is
     * closed, or if this application closes unexpectedly.
     * <p>Use {@link BluetoothDevice#createRfcommSocketToServiceRecord} to
     * connect to this socket from another device using the same {@link UUID}.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}
     *
     * @param name service name for SDP record
     * @param uuid uuid for SDP record
     * @return a listening RFCOMM BluetoothServerSocket
     * @throws IOException on error, for example Bluetooth not available, or
     *                     insufficient permissions, or channel in use.
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static BluetoothServerSocket listenUsingInsecureRfcommWithServiceRecord(
            BluetoothAdapter adapter, String name, UUID uuid) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            return BluetoothAdapterCompatGingerbreadMR1.listenUsingInsecureRfcommWithServiceRecord(
                    adapter, name, uuid);
        }
        return null;
    }

    /**
     * Get the current connection state of a profile.
     * This function can be used to check whether the local Bluetooth adapter
     * is connected to any remote device for a specific profile.
     * Profile can be one of {@link BluetoothProfile#HEALTH}, {@link BluetoothProfile#HEADSET},
     * {@link BluetoothProfile#A2DP}.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}.
     *
     * <p> Return value can be one of
     * {@link BluetoothProfile#STATE_DISCONNECTED},
     * {@link BluetoothProfile#STATE_CONNECTING},
     * {@link BluetoothProfile#STATE_CONNECTED},
     * {@link BluetoothProfile#STATE_DISCONNECTING}
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static int getProfileConnectionState(BluetoothAdapter adapter, int profile) {
        if (Build.VERSION.SDK_INT >= 14) {
            return BluetoothAdapterCompatIceCreamSandwich.getProfileConnectionState(adapter, profile);
        }
        return 0;
    }

    /**
     * Get a {@link BluetoothDevice} object for the given Bluetooth hardware
     * address.
     * <p>Valid Bluetooth hardware addresses must be upper case, in a format
     * such as "00:11:22:33:AA:BB". The helper {@link BluetoothAdapter#checkBluetoothAddress} is
     * available to validate a Bluetooth address.
     * <p>A {@link BluetoothDevice} will always be returned for a valid
     * hardware address, even if this adapter has never seen that device.
     *
     * @param address valid Bluetooth MAC address
     * @throws IllegalArgumentException if address is invalid
     */
    public static BluetoothDevice getRemoteDevice(BluetoothAdapter adapter, byte[] address) {
        if (Build.VERSION.SDK_INT >= 16) {
            return BluetoothAdapterCompatJellyBean.getRemoteDevice(adapter, address);
        }
        return null;
    }

    /**
     * Set the Bluetooth scan mode of the local Bluetooth adapter.
     * <p>The Bluetooth scan mode determines if the local adapter is
     * connectable and/or discoverable from remote Bluetooth devices.
     * <p>For privacy reasons, discoverable mode is automatically turned off
     * after <code>duration</code> seconds. For example, 120 seconds should be
     * enough for a remote device to initiate and complete its discovery
     * process.
     * <p>Valid scan mode values are:
     * {@link BluetoothAdapter#SCAN_MODE_NONE},
     * {@link BluetoothAdapter#SCAN_MODE_CONNECTABLE},
     * {@link BluetoothAdapter#SCAN_MODE_CONNECTABLE_DISCOVERABLE}.
     * <p>If Bluetooth state is not {@link BluetoothAdapter#STATE_ON}, this API
     * will return false. After turning on Bluetooth,
     * wait for {@link BluetoothAdapter#ACTION_STATE_CHANGED} with {@link BluetoothAdapter#STATE_ON}
     * to get the updated value.
     * <p>Requires {@link android.Manifest.permission#WRITE_SECURE_SETTINGS}
     * <p>Applications cannot set the scan mode. They should use
     * <code>startActivityForResult(
     * BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE})
     * </code>instead.
     *
     * @param mode     valid scan mode
     * @param duration time in seconds to apply scan mode, only used for
     *                 {@link BluetoothAdapter#SCAN_MODE_CONNECTABLE_DISCOVERABLE}
     * @return true if the scan mode was set, false otherwise
     */
    @RequiresPermission(Manifest.permission.WRITE_SECURE_SETTINGS)
    public static boolean setScanMode(BluetoothAdapter adapter, int mode, int duration) {
        return HiddenBluetoothAdapterCompat.setScanMode(adapter, mode, duration);
    }

    public static boolean setScanMode(BluetoothAdapter adapter, int mode) {
        return HiddenBluetoothAdapterCompat.setScanMode(adapter, mode);
    }

    public static int getDiscoverableTimeout(BluetoothAdapter adapter) {
        return HiddenBluetoothAdapterCompat.getDiscoverableTimeout(adapter);
    }

    public static void setDiscoverableTimeout(BluetoothAdapter adapter, int timeout) {
        HiddenBluetoothAdapterCompat.setDiscoverableTimeout(adapter, timeout);
    }

    public static int getConnectionState(BluetoothAdapter adapter) {
        return HiddenBluetoothAdapterCompat.getConnectionState(adapter);
    }
}