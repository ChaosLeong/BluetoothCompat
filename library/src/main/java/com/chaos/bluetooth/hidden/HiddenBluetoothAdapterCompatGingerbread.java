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
import android.bluetooth.BluetoothAdapter;
import android.support.annotation.RequiresApi;

/**
 * @author Chaos
 *         13/03/2017
 */
@RequiresApi(16)
@TargetApi(16)
class HiddenBluetoothAdapterCompatGingerbread {

    public static int getConnectionState(BluetoothAdapter adapter) {
        return (int) new MethodInvoker(adapter, "getConnectionState").invoke();
    }
}
