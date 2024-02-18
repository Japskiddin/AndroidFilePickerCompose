package io.github.japskiddin.androidfilepickercompose.filesystem.usb

import android.net.Uri


class SingletonUsbOtg private constructor() {
    companion object {
        @Volatile
        private var instance: SingletonUsbOtg? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: SingletonUsbOtg().also { instance = it }
        }
    }

    private var connectedDevice: UsbOtgRepresentation? = null

    private var usbOtgRoot: Uri? = null

    fun setConnectedDevice(connectedDevice: UsbOtgRepresentation?) {
        this.connectedDevice = connectedDevice
    }

    fun isDeviceConnected(): Boolean {
        return connectedDevice != null
    }

    fun setUsbOtgRoot(root: Uri?) {
        checkNotNull(connectedDevice) { "No device connected!" }
        usbOtgRoot = root
    }

    fun resetUsbOtgRoot() {
        connectedDevice = null
        usbOtgRoot = null
    }

    fun getUsbOtgRoot(): Uri? {
        return usbOtgRoot
    }

    fun checkIfRootIsFromDevice(device: UsbOtgRepresentation): Boolean {
        return usbOtgRoot != null && connectedDevice.hashCode() == device.hashCode()
    }
}