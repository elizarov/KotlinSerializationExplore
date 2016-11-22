/**
 * @author Roman Elizarov
 */

fun ByteArray.toHexString(): String {
    return this.map { b ->
        Integer.toHexString((b.toInt() shr 4) and 0xf) +
        Integer.toHexString((b.toInt()) and 0xf)
    }.joinToString("")
}