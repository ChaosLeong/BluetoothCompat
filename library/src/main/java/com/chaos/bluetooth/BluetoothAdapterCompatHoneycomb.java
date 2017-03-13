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

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.support.annotation.RequiresApi;

/**
 * @author Chaos
 *         12/03/2017
 */
@RequiresApi(11)
@TargetApi(11)
class BluetoothAdapterCompatHoneycomb {

    public static boolean getProfileProxy(BluetoothAdapter adapter, Context context,
            BluetoothProfile.ServiceListener listener, int profile) {
        return adapter.getProfileProxy(context, listener, profile);
    }

    public static void closeProfileProxy(BluetoothAdapter adapter, int profile, BluetoothProfile proxy) {
        adapter.closeProfileProxy(profile, proxy);
    }
}
