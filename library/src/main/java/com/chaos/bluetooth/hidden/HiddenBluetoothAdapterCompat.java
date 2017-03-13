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

import android.bluetooth.BluetoothAdapter;
import android.os.Build;

/**
 * @author Chaos
 *         12/03/2017
 */

public class HiddenBluetoothAdapterCompat {

    public static boolean setScanMode(BluetoothAdapter adapter, int mode, int duration) {
        return (boolean) new MethodInvoker(adapter, "setScanMode", new Class[]{int.class, int.class})
                .invoke(mode, duration);
    }

    public static boolean setScanMode(BluetoothAdapter adapter, int mode) {
        return setScanMode(adapter, mode, 120);
    }

    public static int getDiscoverableTimeout(BluetoothAdapter adapter) {
        return (int) new MethodInvoker(adapter, "getDiscoverableTimeout").invoke();
    }

    public static void setDiscoverableTimeout(BluetoothAdapter adapter, int timeout) {
        new MethodInvoker(adapter, "setDiscoverableTimeout", int.class).invoke(timeout);
    }

    public static int getConnectionState(BluetoothAdapter adapter) {
        if (Build.VERSION.SDK_INT >= 16) {
            return HiddenBluetoothAdapterCompatGingerbread.getConnectionState(adapter);
        }
        return BluetoothAdapter.STATE_DISCONNECTED;
    }
}
