import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * @author Roman Elizarov
 */

fun testJavaSerial(obj: Any): Result {
    val baos = ByteArrayOutputStream()
    val out = ObjectOutputStream(baos)
    try {
        out.writeObject(obj)
    } catch(e: Exception) {
        return Result(obj, obj, -1, "", e)
    } finally {
        out.close()
    }

    val b = baos.toByteArray()
    val bios = ByteArrayInputStream(b)
    val inp = ObjectInputStream(bios)
    try {
        return Result(obj, inp.readObject(), b.size, b.toHexString())
    } catch(e: Exception) {
        return Result(obj, obj, b.size, b.toHexString(), e)
    } finally {
        inp.close()
    }
}

fun main(args: Array<String>) {
    testAll(::testJavaSerial)
}
