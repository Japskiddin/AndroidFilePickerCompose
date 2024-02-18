package io.github.japskiddin.androidfilepickercompose.filesystem.usb

data class UsbOtgRepresentation(val serialNumber: String?, val productID: Int, val vendorID: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is UsbOtgRepresentation) return false
        return productID == other.productID && vendorID == other.vendorID && serialNumber == other.serialNumber
    }

    override fun hashCode(): Int {
        var result = productID
        result = 37 * result + vendorID
        result = 37 * result + (serialNumber?.hashCode() ?: 0)
        return result
    }
}