import java.io.Serializable
import kotlin.reflect.KProperty

data class ImmutableData(
    val id: Int,
    val name: String
) : Serializable

data class MutableData(
    var id: Int,
    var name: String
) : Serializable

data class DefaultsData(
    var id: Int = 0,
    var name: String = "empty"
) : Serializable

class MappedData(
    val map: Map<String, String> = emptyMap()
) : Serializable {
    val name: String by map
    val desc: String by map
    override fun toString(): String = "MappedData(map=$map)"
    override fun equals(o: Any?) = o is MappedData &&
        name == o.name &&
        desc == o.desc
}

class LazyData(
    val first: String = "",
    val last: String = ""
) : Serializable {
    val full: String by lazy @JvmName("FullName") { first + " " + last }
    override fun toString(): String = "LazyData(full=$full)"
    override fun equals(o: Any?) = o is LazyData && toString() == o.toString()
}

data class DataList(
    val list: List<DefaultsData> = emptyList()
) : Serializable

data class DataStringMap(
    val map: Map<String, DefaultsData> = emptyMap()
) : Serializable

data class DataIntMap(
    val map: Map<Int, DefaultsData> = emptyMap()
) : Serializable

data class DataDataDoubleMap(
    val map: Map<DefaultsData, Double> = emptyMap()
) : Serializable

enum class Color { RED, GREEN, BLUE }

data class DataEnumColors(
    val red: Color = Color.RED,
    val green: Color = Color.GREEN,
    val blue: Color = Color.BLUE
) : Serializable

data class SpecialDoubles(
    var zero: Double = 0.0,
    var negZero: Double = -0.0,
    var nan: Double = Double.NaN,
    var posInf: Double = Double.POSITIVE_INFINITY,
    var negInf: Double = Double.NEGATIVE_INFINITY
) : Serializable

object StatelessObject : Serializable {
    val answer = 42
    override fun toString(): String = "StatelessObject(answer=$answer)"
}

object StatefulObject : Serializable {
    var state = "ACTIVE"
    override fun toString(): String = "StatefulObject(state=$state)"
}

enum class EnumSingleton {
    SINGLETON;
    var state = "ACTIVE"
}

class ContinuationCapture : Serializable {
    suspend fun delay(cont: Continuation<Unit>) {}
}

fun capture(coroutine cr: ContinuationCapture.() -> Continuation<Unit>): Continuation<Unit> {
    val c = ContinuationCapture()
    return c.cr()
}

val simpleCont = capture {
    println("simpleCont")
}

val suspendCont = capture {
    println("suspendCont")
    delay()
    println("continue")
}

val computeCont = capture {
    val time = System.currentTimeMillis()
    delay()
    println("Done in ${System.currentTimeMillis() - time}")
}

// ------------------------------------

data class Case(
    val data: Any,
    val prop: KProperty<*>? = null,
    val name: String = data.javaClass.simpleName
)

val testCases: List<Case> = listOf(
    Case(ImmutableData(1, "Moscow"), ImmutableData::name),
    Case(MutableData(1, "Moscow"), MutableData::name),
    Case(DefaultsData(1, "Moscow"), DefaultsData::name),
    Case(MappedData(mutableMapOf("name" to "Kotlin", "desc" to "Language")), MappedData::name),
    Case(LazyData("John", "Doe"), LazyData::full),
    Case(DataList(listOf(DefaultsData(1, "Moscow"), DefaultsData(2, "SPb"))), DataList::list, "DataList(list)"),
    Case(DataList(arrayListOf(DefaultsData(1, "Moscow"), DefaultsData(2, "SPb"))), DataList::list, "DataList(arrayList)"),
    Case(DataStringMap(hashMapOf("1" to DefaultsData(1, "Moscow"), "2" to DefaultsData(2, "SPb"))), DataStringMap::map, "DataStringMap(hashMap)"),
    Case(DataIntMap(hashMapOf(1 to DefaultsData(1, "Moscow"), 2 to DefaultsData(2, "SPb"))), DataIntMap::map, "DataIntMap(hashMap)"),
    Case(DataDataDoubleMap(hashMapOf(DefaultsData(1, "Moscow") to 15e6, DefaultsData(2, "SPb") to 5e6)), DataDataDoubleMap::map, "DataDataDoubleMap(hashMap)"),
    Case(DataEnumColors()),
    Case(SpecialDoubles()),
    Case(StatelessObject, StatelessObject::answer),
    Case(StatefulObject, StatefulObject::state),
    Case(EnumSingleton.SINGLETON, EnumSingleton::state),
    Case(simpleCont, name="simpleCont"),
    Case(suspendCont, name="suspendCont"),
    Case(computeCont, name="computeCont")
)

