import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author Roman Elizarov
 */

fun testCustomGson(gson: Gson, obj: Any): Result {
    val json = try {
        gson.toJson(obj)
    } catch (ex: Exception) {
        return Result(obj, obj, -1, ex=ex)
    }
    val res = try {
        gson.fromJson(json, obj::class.java)
    } catch (ex: Exception) {
        return Result(obj, obj, json.length, json, ex)
    } ?:
        return Result(obj, obj, json.length, json, NullPointerException("null result"))
    return Result(obj, res, json.length, json)
}

fun testGson(obj: Any): Result {
    return testCustomGson(Gson(), obj)
}

fun testGsonSPF(obj: Any): Result {
    val gb = GsonBuilder()
    gb.serializeSpecialFloatingPointValues()
    return testCustomGson(gb.create(), obj)
}

fun main(args: Array<String>) {
    testAll(::testGsonSPF)
}