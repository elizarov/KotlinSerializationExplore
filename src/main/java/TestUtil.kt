/**
 * @author Roman Elizarov
 */

data class Result(
    val obj: Any,
    val res: Any,
    val size: Int,
    val str: String = "",
    val ex: Exception? = null
) {
    val report: String = if (ex == null) {
        "$size" +
            (if (obj != res) " {!}" else "") +
            (if (obj === res) " {S}" else "")
    } else {
        if (size < 0) "WR-FAIL" else "RD-FAIL"
    }
}

fun testOne(method: (Any) -> Result) {
    println("=== Running with $method ===")
    println()
    testCases.forEach { case ->
        println("--- ${case.name} with $method ---")
        val obj = case.data
        val res = method(obj)
        if (res.size >= 0) {
            println("Saved $obj @${obj.address} with ${res.size} bytes")
            if (res.ex == null) {
                println("Loaded ${res.res} @${res.res.address}, equals=${res.res == obj}")
                if (case.prop != null)
                    println("The value of ${case.prop.name} is ${case.prop.getter.call(res.res)}")
            }
        }
        if (res.ex != null)
            println("Exception is ${res.ex}")
        if (!res.str.isEmpty())
            println("External form is: ${res.str}")
        println()
    }
}

fun testAll(vararg methods: (Any) -> Result) = methods.forEach(::testOne)

val Any.address : String get() = Integer.toHexString(System.identityHashCode(this))
