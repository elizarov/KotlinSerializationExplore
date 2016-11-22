import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import de.javakaffee.kryoserializers.ArraysAsListSerializer
import org.objenesis.strategy.SerializingInstantiatorStrategy
import org.objenesis.strategy.StdInstantiatorStrategy
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

/**
* @author Roman Elizarov
*/


private fun testCustomKryo(kryo: Kryo, obj: Any): Result {
    val baos = ByteArrayOutputStream()
    val out = Output(baos)
    kryo.writeObject(out, obj)
    out.close()

    val b = baos.toByteArray()
    val bais = ByteArrayInputStream(b)
    val inp = Input(bais)
    try {
        return Result(obj, kryo.readObject(inp, obj.javaClass), b.size, b.toHexString())
    } catch(e: Exception) {
        return Result(obj, obj, b.size, b.toHexString(), e)
    } finally {
        inp.close()
    }
}

// Out-of-the box configuration
fun testKryo(obj: Any): Result {
    val kryo = Kryo()
    return testCustomKryo(kryo, obj)
}

// A strategy that handles Serializable classes by clearding "blank" state for deserialization
fun testKryoSer(obj: Any): Result {
    val kryo = Kryo()
    kryo.instantiatorStrategy = SerializingInstantiatorStrategy()
    return testCustomKryo(kryo, obj)
}

// Manual configuration to handle everything we need
fun testKryoExt(obj: Any): Result {
    val kryo = Kryo()
    kryo.register(Arrays.asList("").javaClass, ArraysAsListSerializer())
    kryo.instantiatorStrategy = Kryo.DefaultInstantiatorStrategy(StdInstantiatorStrategy())
    return testCustomKryo(kryo, obj)
}

fun main(args: Array<String>) {
    testAll(::testKryoExt)
}
