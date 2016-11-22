import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.StringWriter

/**
 * @author Roman Elizarov
 */

fun testCustomJackson(mapper: ObjectMapper, obj: Any): Result {
    val sw = StringWriter()
    mapper.writeValue(sw, obj)
    val str = sw.toString()
    val res = try {
        mapper.readValue(str, obj::class.java)
    } catch (ex: Exception) {
        return Result(obj, obj, str.length, str, ex)
    }
    return Result(obj, res, str.length, str)
}

fun testJackson(obj: Any): Result {
    return testCustomJackson(ObjectMapper(), obj)
}

fun testJacksonK(obj: Any): Result {
    val mapper = ObjectMapper()
    mapper.registerModule(KotlinModule())
    return testCustomJackson(mapper, obj)
}

fun main(args: Array<String>) {
    testAll(::testJacksonK)
}