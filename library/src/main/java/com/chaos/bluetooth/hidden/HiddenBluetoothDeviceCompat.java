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

package com.chaos.bluetooth.hidden;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;

/**
 * @author Chaos
 *         05/03/2017
 */

public class HiddenBluetoothDeviceCompat {

    public static boolean createBond(BluetoothDevice device) {
        return (boolean) new MethodInvoker(device, "createBond").invoke();
    }

    public static boolean cancelBondProcess(BluetoothDevice device) {
        return (boolean) new MethodInvoker(device, "cancelBondProcess").invoke();
    }

    public static boolean removeBond(BluetoothDevice device) {
        return (boolean) new MethodInvoker(device, "removeBond").invoke();
    }

    public static ParcelUuid[] getUuids(BluetoothDevice device) {
        return (ParcelUuid[]) new MethodInvoker(device, "getUuids").invoke();
    }

    public static boolean fetchUuidsWithSdp(BluetoothDevice device) {
        return (boolean) new MethodInvoker(device, "fetchUuidsWithSdp").invoke();
    }

    public static boolean setPin(BluetoothDevice device, byte[] pin) {
        return (boolean) new MethodInvoker(device, "setPin", byte[].class).invoke(pin);
    }

    public static boolean setPairingConfirmation(BluetoothDevice device, boolean confirm) {
        return (boolean) new MethodInvoker(device, "setPairingConfirmation", boolean.class)
                .invoke(confirm);
    }

    public static String getAlias(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 14) {
            return HiddenBluetoothDeviceCompatIceCreamSandwich.getAlias(device);
        } else {
            return device.getName();
        }
    }

    public static boolean setAlias(BluetoothDevice device, String alias) {
        if (Build.VERSION.SDK_INT >= 14) {
            return HiddenBluetoothDeviceCompatIceCreamSandwich.setAlias(device, alias);
        } else {
            return false;
        }
    }

    public static String getAliasName(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 14) {
            return HiddenBluetoothDeviceCompatIceCreamSandwich.getAliasName(device);
        } else {
            return device.getName();
        }
    }

    public static boolean isConnected(BluetoothDevice device) {
        if (Build.VERSION.SDK_INT >= 21) {
            return HiddenBluetoothDeviceCompatLollipop.isConnected(device);
        } else {
            return false;
        }
    }

    public static BluetoothGatt connectGatt(BluetoothDevice device, Context context,
            boolean autoConnect, BluetoothGattCallback callback, int transport) {
        if (Build.VERSION.SDK_INT >= 21) {
            return HiddenBluetoothDeviceCompatLollipop.connectGatt(device, context, autoConnect,
                    callback, transport);
        } else {
            return null;
        }
    }
}

