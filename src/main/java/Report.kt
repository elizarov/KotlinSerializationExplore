import kotlin.reflect.KFunction

/**
 * @author Roman Elizarov
 */

@Suppress("UNCHECKED_CAST")
class Method(func: KFunction<*>) {
    val method = func as (Any) -> Result
    val name = func.name.replace(Regex("^test"), "")
}

val testMethods: List<Method> = listOf(
    Method(::testJavaSerial),
    Method(::testKryo),
    Method(::testKryoSer),
    Method(::testKryoExt),
    Method(::testGson),
    Method(::testGsonSPF),
    Method(::testJackson),
    Method(::testJacksonK)
)

fun main(args: Array<String>) {
    val results = testCases.map { case ->
        testMethods.map { method ->
            method.method(case.data)
        }
    }
    val maxDataLen = testCases.map { it.name.length }.max()!!
    val maxMethodLen = testMethods.map { it.name.length }.max()!!

    fun header() {
        print("| ${"Case".padEnd(maxDataLen)} |")
        testMethods.forEach { method ->
            print(" ${method.name.padEnd(maxMethodLen)} |")
        }
        println()
    }

    fun tearLine() {
        print("+${"-".repeat(maxDataLen + 2)}+")
        testMethods.forEach { method ->
            print("${"-".repeat(maxMethodLen + 2)}+")
        }
        println()
    }

    tearLine()
    header()
    tearLine()

    testCases.zip(results).forEach { (case, caseResults) ->
        print("| ${case.name.padEnd(maxDataLen)} |")
        caseResults.forEach { result ->
            print(" ${result.report.padEnd(maxMethodLen)} |")
        }
        println()
    }

    tearLine()
}