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
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.chaos.bluetooth.hidden.HiddenBluetoothDeviceCompat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author Chaos
// *         03/03/2017
 */

public class BluetoothDeviceCompat {

    private static final String TAG = "BluetoothDeviceCompat";

    /**
     * Broadcast Action: Remote device disappeared.
     * <p>Sent when a remote device that was found in the last discovery is not
     * found in the current discovery.
     * <p>Always contains the extra field {@link BluetoothDevice#EXTRA_DEVICE}.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} to receive.
     */
    public static final String ACTION_DISAPPEARED =
            "android.bluetooth.device.action.DISAPPEARED";

    /**
     * Broadcast Action: Indicates the alias of a remote device has been
     * changed.
     * <p>Always contains the extra field {@link BluetoothDevice#EXTRA_DEVICE}.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} to receive.
     */
    public static final String ACTION_ALIAS_CHANGED =
            "android.bluetooth.device.action.ALIAS_CHANGED";

    /**
     * Used as an int extra field in {@link #ACTION_PAIRING_REQUEST}
     * intents for unbond reason.
     */
    public static final String EXTRA_REASON = "android.bluetooth.device.extra.REASON";

    /**
     * Used as an int extra field in {@link #ACTION_PAIRING_REQUEST}
     * intents to indicate pairing method used. Possible values are:
     * {@link #PAIRING_VARIANT_PIN},
     * {@link #PAIRING_VARIANT_PASSKEY_CONFIRMATION},
     */
    public static final String EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT";

    /**
     * Used as an int extra field in {@link #ACTION_PAIRING_REQUEST}
     * intents as the value of passkey.
     */
    public static final String EXTRA_PAIRING_KEY = "android.bluetooth.device.extra.PAIRING_KEY";

    /**
     * Bluetooth device type, Unknown
     */
    public static final int DEVICE_TYPE_UNKNOWN = 0;

    /**
     * Bluetooth device type, Classic - BR/EDR devices
     */
    public static final int DEVICE_TYPE_CLASSIC = 1;

    /**
     * Bluetooth device type, Low Energy - LE-only
     */
    public static final int DEVICE_TYPE_LE = 2;

    /**
     * Bluetooth device type, Dual Mode - BR/EDR/LE
     */
    public static final int DEVICE_TYPE_DUAL = 3;

    /**
     * Broadcast Action: This intent is used to broadcast the {@link UUID}
     * wrapped as a {@link android.os.ParcelUuid} of the remote device after it
     * has been fetched. This intent is sent only when the UUIDs of the remote
     * device are requested to be fetched using Service Discovery Protocol
     * <p> Always contains the extra field {@link BluetoothDevice#EXTRA_DEVICE}
     * <p> Always contains the extra field {@link #EXTRA_UUID}
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} to receive.
     */
    public static final String ACTION_UUID =
            "android.bluetooth.device.action.UUID";

    public static final String ACTION_MAS_INSTANCE =
            "android.bluetooth.device.action.MAS_INSTANCE";

    /**
     * Broadcast Action: Indicates a failure to retrieve the name of a remote
     * device.
     * <p>Always contains the extra field {@link BluetoothDevice#EXTRA_DEVICE}.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH} to receive.
     */
    public static final String ACTION_NAME_FAILED =
            "android.bluetooth.device.action.NAME_FAILED";

    /**
     * Broadcast Action: This intent is used to broadcast PAIRING REQUEST
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN} to
     * receive.
     */
    public static final String ACTION_PAIRING_REQUEST =
            "android.bluetooth.device.action.PAIRING_REQUEST";

    public static final String ACTION_PAIRING_CANCEL =
            "android.bluetooth.device.action.PAIRING_CANCEL";

    /**
     * A bond attempt succeeded
     */
    public static final int BOND_SUCCESS = 0;

    /**
     * A bond attempt failed because pins did not match, or remote device did
     * not respond to pin request in time
     */
    public static final int UNBOND_REASON_AUTH_FAILED = 1;

    /**
     * A bond attempt failed because the other side explicitly rejected
     * bonding
     */
    public static final int UNBOND_REASON_AUTH_REJECTED = 2;

    /**
     * A bond attempt failed because we canceled the bonding process
     */
    public static final int UNBOND_REASON_AUTH_CANCELED = 3;

    /**
     * A bond attempt failed because we could not contact the remote device
     */
    public static final int UNBOND_REASON_REMOTE_DEVICE_DOWN = 4;

    /**
     * A bond attempt failed because a discovery is in progress
     */
    public static final int UNBOND_REASON_DISCOVERY_IN_PROGRESS = 5;

    /**
     * A bond attempt failed because of authentication timeout
     */
    public static final int UNBOND_REASON_AUTH_TIMEOUT = 6;

    /**
     * A bond attempt failed because of repeated attempts
     */
    public static final int UNBOND_REASON_REPEATED_ATTEMPTS = 7;

    /**
     * A bond attempt failed because we received an Authentication Cancel
     * by remote end
     */
    public static final int UNBOND_REASON_REMOTE_AUTH_CANCELED = 8;

    /**
     * An existing bond was explicitly revoked
     */
    public static final int UNBOND_REASON_REMOVED = 9;

    /**
     * The user will be prompted to enter a pin or
     * an app will enter a pin for user.
     */
    public static final int PAIRING_VARIANT_PIN = 0;

    /**
     * The user will be prompted to enter a passkey
     */
    public static final int PAIRING_VARIANT_PASSKEY = 1;

    /**
     * The user will be prompted to confirm the passkey displayed on the screen or
     * an app will confirm the passkey for the user.
     */
    public static final int PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2;

    /**
     * The user will be prompted to accept or deny the incoming pairing request
     */
    public static final int PAIRING_VARIANT_CONSENT = 3;

    /**
     * The user will be prompted to enter the passkey displayed on remote device
     * This is used for Bluetooth 2.1 pairing.
     */
    public static final int PAIRING_VARIANT_DISPLAY_PASSKEY = 4;

    /**
     * The user will be prompted to enter the PIN displayed on remote device.
     * This is used for Bluetooth 2.0 pairing.
     */
    public static final int PAIRING_VARIANT_DISPLAY_PIN = 5;

    /**
     * The user will be prompted to accept or deny the OOB pairing request
     */
    public static final int PAIRING_VARIANT_OOB_CONSENT = 6;

    /**
     * The user will be prompted to enter a 16 digit pin or
     * an app will enter a 16 digit pin for user.
     */
    public static final int PAIRING_VARIANT_PIN_16_DIGITS = 7;

    /**
     * Used as an extra field in {@link #ACTION_UUID} intents,
     * Contains the {@link android.os.ParcelUuid}s of the remote device which
     * is a parcelable version of {@link UUID}.
     */
    public static final String EXTRA_UUID = "android.bluetooth.device.extra.UUID";

    /**
     * No preferrence of physical transport for GATT connections to remote dual-mode devices
     */
    public static final int TRANSPORT_AUTO = 0;

    /**
     * Prefer BR/EDR transport for GATT connections to remote dual-mode devices
     */
    public static final int TRANSPORT_BREDR = 1;

    /**
     * Prefer LE transport for GATT connections to remote dual-mode devices
     */
    public static final int TRANSPORT_LE = 2;

    protected BluetoothDeviceCompat() {
        // Not publicly instantiable, but may be extended.
    }

    /**
     * Get the Bluetooth device type of the remote device.
     *
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}
     *
     * @return the device type {@link #DEVICE_TYPE_CLASSIC}, {@link #DEVICE_TYPE_LE}
     * {@link #DEVICE_TYPE_DUAL}.
     * {@link #DEVICE_TYPE_UNKNOWN} if it's not available
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static int getType(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 18) {
            return BluetoothDeviceCompatJellyBeanMR2.getType(device);
        } else {
            return DEVICE_TYPE_UNKNOWN;
        }
    }

    /**
     * Get the Bluetooth alias of the remote device.
     * <p>Alias is the locally modified name of a remote device.
     *
     * @return the Bluetooth alias, or null if no alias or there was a problem
     */
    public static String getAlias(BluetoothDevice device) {
        return HiddenBluetoothDeviceCompat.getAlias(device);
    }

    /**
     * Set the Bluetooth alias of the remote device.
     * <p>Alias is the locally modified name of a remote device.
     * <p>This method overwrites the alias. The changed
     * alias is saved in the local storage so that the change
     * is preserved over power cycle.
     *
     * @return true on success, false on error
     */
    public static boolean setAlias(BluetoothDevice device, String alias) {
        return HiddenBluetoothDeviceCompat.setAlias(device, alias);
    }

    /**
     * Get the Bluetooth alias of the remote device.
     * If Alias is null, get the Bluetooth name instead.
     *
     * @return the Bluetooth alias, or null if no alias or there was a problem
     * @see #getAlias(BluetoothDevice)
     * @see BluetoothDevice#getName()
     */
    public static String getAliasName(BluetoothDevice device) {
        return HiddenBluetoothDeviceCompat.getAliasName(device);
    }

    /**
     * Start the bonding (pairing) process with the remote device.
     * <p>This is an asynchronous call, it will return immediately. Register
     * for {@link BluetoothDevice#ACTION_BOND_STATE_CHANGED} intents to be notified when
     * the bonding process completes, and its result.
     * <p>Android system services will handle the necessary user interactions
     * to confirm and complete the bonding process.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}.
     *
     * @return false on immediate error, true if bonding will begin
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public static boolean createBond(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 19) {
            return BluetoothDeviceCompatKitKat.createBond(device);
        } else {
            return HiddenBluetoothDeviceCompat.createBond(device);
        }
    }

    /**
     * Cancel an in-progress bonding request started with {@link #createBond}.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}.
     *
     * @return true on success, false on error
     */
    public static boolean cancelBondProcess(BluetoothDevice device) {
        return HiddenBluetoothDeviceCompat.cancelBondProcess(device);
    }

    /**
     * Remove bond (pairing) with the remote device.
     * <p>Delete the link key associated with the remote device, and
     * immediately terminate connections to that device that require
     * authentication and encryption.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}.
     *
     * @return true on success, false on error
     */
    public static boolean removeBond(BluetoothDevice device) {
        return HiddenBluetoothDeviceCompat.removeBond(device);
    }

    /**
     * Returns whether there is an open connection to this device.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}.
     *
     * @return True if there is at least one open connection to this device.
     * False if no connection on this device or device's API less than 21.
     */
    public static boolean isConnected(BluetoothDevice device) {
        return HiddenBluetoothDeviceCompat.isConnected(device);
    }

    /**
     * Returns the supported features (UUIDs) of the remote device.
     *
     * <p>This method does not start a service discovery procedure to retrieve the UUIDs
     * from the remote device. Instead, the local cached copy of the service
     * UUIDs are returned.
     * <p>Use {@link #fetchUuidsWithSdp} if fresh UUIDs are desired.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}.
     *
     * @return the supported features (UUIDs) of the remote device,
     * or null on error
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static ParcelUuid[] getUuids(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 15) {
            return BluetoothDeviceCompatIceCreamSandwichMR1.getUuids(device);
        } else {
            return HiddenBluetoothDeviceCompat.getUuids(device);
        }
    }

    /**
     * Perform a service discovery on the remote device to get the UUIDs supported.
     *
     * <p>This API is asynchronous and {@link #ACTION_UUID} intent is sent,
     * with the UUIDs supported by the remote end. If there is an error
     * in getting the SDP records or if the process takes a long time,
     * {@link #ACTION_UUID} intent is sent with the UUIDs that is currently
     * present in the cache. Clients should use the {@link #getUuids} to get UUIDs
     * if service discovery is not to be performed.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}.
     *
     * @return False if the sanity check fails, True if the process
     * of initiating an ACL connection to the remote device
     * was started.
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static boolean fetchUuidsWithSdp(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 15) {
            return BluetoothDeviceCompatIceCreamSandwichMR1.fetchUuidsWithSdp(device);
        } else {
            return HiddenBluetoothDeviceCompat.fetchUuidsWithSdp(device);
        }
    }

    /**
     * Set the pin during pairing when the pairing method is {@link #PAIRING_VARIANT_PIN}
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}.
     *
     * @return true pin has been set
     * false for error
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public static boolean setPin(BluetoothDevice device, byte[] pin) {
        if (Build.VERSION.SDK_INT >= 19) {
            return BluetoothDeviceCompatKitKat.setPin(device, pin);
        } else {
            return HiddenBluetoothDeviceCompat.setPin(device, pin);
        }
    }

    /**
     * Confirm passkey for {@link #PAIRING_VARIANT_PASSKEY_CONFIRMATION} pairing.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH_ADMIN}.
     *
     * @return true confirmation has been sent out
     * false for error
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public static boolean setPairingConfirmation(BluetoothDevice device, boolean confirm) {
        if (Build.VERSION.SDK_INT >= 19) {
            return BluetoothDeviceCompatKitKat.setPairingConfirmation(device, confirm);
        } else {
            return HiddenBluetoothDeviceCompat.setPairingConfirmation(device, confirm);
        }
    }

    /**
     * Create an RFCOMM {@link BluetoothSocket} socket ready to start an insecure
     * outgoing connection to this remote device using SDP lookup of uuid.
     * <p> The communication channel will not have an authenticated link key
     * i.e it will be subject to man-in-the-middle attacks. For Bluetooth 2.1
     * devices, the link key will be encrypted, as encryption is mandatory.
     * For legacy devices (pre Bluetooth 2.1 devices) the link key will
     * be not be encrypted. Use {@link BluetoothDevice#createRfcommSocketToServiceRecord}
     * if an encrypted and authenticated communication channel is desired.
     * <p>This is designed to be used with {@link
     * BluetoothAdapter#listenUsingInsecureRfcommWithServiceRecord} for peer-peer
     * Bluetooth applications.
     * <p>Use {@link BluetoothSocket#connect} to initiate the outgoing
     * connection. This will also perform an SDP lookup of the given uuid to
     * determine which channel to connect to.
     * <p>The remote device will be authenticated and communication on this
     * socket will be encrypted.
     * <p>Hint: If you are connecting to a Bluetooth serial board then try
     * using the well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB.
     * However if you are connecting to an Android peer then please generate
     * your own unique UUID.
     * <p>Requires {@link android.Manifest.permission#BLUETOOTH}
     *
     * @param uuid service record uuid to lookup RFCOMM channel
     * @return a RFCOMM BluetoothServerSocket ready for an outgoing connection
     * @throws IOException on error, for example Bluetooth not available, or
     *                     insufficient permissions
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static BluetoothSocket createInsecureRfcommSocketToServiceRecord(BluetoothDevice device,
            UUID uuid) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            return BluetoothDeviceCompatGingerbreadMR1.createInsecureRfcommSocketToServiceRecord(
                    device, uuid);
        } else {
            return null;
        }
    }

    /**
     * Check that a pin is valid and convert to byte array.
     *
     * Bluetooth pin's are 1 to 16 bytes of UTF-8 characters.
     *
     * @param pin pin as java String
     * @return the pin code as a UTF-8 byte array, or null if it is an invalid
     * Bluetooth pin.
     */
    public static byte[] convertPinToBytes(String pin) {
        if (pin == null) {
            return null;
        }
        byte[] pinBytes;
        try {
            pinBytes = pin.getBytes("UTF-8");
        } catch (UnsupportedEncodingException uee) {
            Log.e(TAG, "UTF-8 not supported?!?");  // this should not happen
            return null;
        }
        if (pinBytes.length <= 0 || pinBytes.length > 16) {
            return null;
        }
        return pinBytes;
    }

    /**
     * Connect to GATT Server hosted by this device. Caller acts as GATT client.
     * The callback is used to deliver results to Caller, such as connection status as well
     * as any further GATT client operations.
     * The method returns a BluetoothGatt instance. You can use BluetoothGatt to conduct
     * GATT client operations.
     *
     * @param callback    GATT callback handler that will receive asynchronous callbacks.
     * @param autoConnect Whether to directly connect to the remote device (false)
     *                    or to automatically connect as soon as the remote
     *                    device becomes available (true).
     * @throws IllegalArgumentException if callback is null
     */
    public static BluetoothGatt connectGatt(BluetoothDevice device, Context context,
            boolean autoConnect, BluetoothGattCallback callback) {
        if (Build.VERSION.SDK_INT >= 18) {
            return BluetoothDeviceCompatJellyBeanMR2.connectGatt(device, context, autoConnect, callback);
        } else {
            return null;
        }
    }

    /**
     * Connect to GATT Server hosted by this device. Caller acts as GATT client.
     * The callback is used to deliver results to Caller, such as connection status as well
     * as any further GATT client operations.
     * The method returns a BluetoothGatt instance. You can use BluetoothGatt to conduct
     * GATT client operations.
     *
     * @param callback    GATT callback handler that will receive asynchronous callbacks.
     * @param autoConnect Whether to directly connect to the remote device (false)
     *                    or to automatically connect as soon as the remote
     *                    device becomes available (true).
     * @param transport   preferred transport for GATT connections to remote dual-mode devices
     *                    {@link BluetoothDevice#TRANSPORT_AUTO} or
     *                    {@link BluetoothDevice#TRANSPORT_BREDR} or {@link BluetoothDevice#TRANSPORT_LE}
     * @throws IllegalArgumentException if callback is null
     */
    public static BluetoothGatt connectGatt(BluetoothDevice device, Context context,
            boolean autoConnect, BluetoothGattCallback callback, int transport) {
        if (Build.VERSION.SDK_INT >= 23) {
            return BluetoothDeviceCompatMarshmallow.connectGatt(device, context, autoConnect, callback,
                    transport);
        } else {
            return HiddenBluetoothDeviceCompat.connectGatt(device, context, autoConnect, callback,
                    transport);
        }
    }
}