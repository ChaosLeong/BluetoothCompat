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

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.RequiresApi;

/**
 * @author Chaos
 *         05/03/2017
 */
@RequiresApi(14)
@TargetApi(14)
class HiddenBluetoothDeviceCompatIceCreamSandwich {

    public static String getAlias(BluetoothDevice device) {
        return (String) new MethodInvoker(device, "getAlias").invoke();
    }

    public static boolean setAlias(BluetoothDevice device, String alias) {
        return (boolean) new MethodInvoker(device, "setAlias", String.class).invoke(alias);
    }

    public static String getAliasName(BluetoothDevice device) {
        String name = getAlias(device);
        if (name == null) {
            name = device.getName();
        }
        return name;
    }
}
