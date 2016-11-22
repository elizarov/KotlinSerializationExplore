# KotlinSerializationExplore
Explore Kotlin's compatibility with various popular serialization libraries.

It produces the following report. See code for various test-cases and serializer implementations.


```
+----------------------------+------------+------------+------------+------------+------------+------------+------------+------------+
| Case                       | JavaSerial | Kryo       | KryoSer    | KryoExt    | Gson       | GsonSPF    | Jackson    | JacksonK   |
+----------------------------+------------+------------+------------+------------+------------+------------+------------+------------+
| ImmutableData              | 80         | RD-FAIL    | 9          | 9          | 24         | 24         | RD-FAIL    | 24         |
| MutableData                | 78         | RD-FAIL    | 9          | 9          | 24         | 24         | RD-FAIL    | 24         |
| DefaultsData               | 79         | 9          | 9          | 9          | 24         | 24         | 24         | 24         |
| MappedData                 | 272        | 64         | 64         | 64         | 147        | 147        | RD-FAIL    | RD-FAIL    |
| LazyData                   | 198        | RD-FAIL    | 54         | 54         | RD-FAIL    | RD-FAIL    | RD-FAIL    | RD-FAIL    |
| DataList(list)             | 251        | RD-FAIL    | RD-FAIL    | 37         | 57         | 57         | 57         | 57         |
| DataList(arrayList)        | 200        | 55         | RD-FAIL    | 55         | 57         | 57         | 57         | 57         |
| DataStringMap(hashMap)     | 235        | 61         | 61         | 61         | 64         | 64         | 64         | 64         |
| DataIntMap(hashMap)        | 311        | 57         | 57         | 57         | 64         | 64         | 64         | 64         |
| DataDataDoubleMap(hashMap) | 325        | 71         | 71         | 71         | RD-FAIL    | RD-FAIL    | RD-FAIL    | RD-FAIL    |
| DataEnumColors             | 160        | 7          | 7          | 7          | 43         | 43         | 43         | 43         |
| SpecialDoubles             | 116        | 41         | 41         | 41         | WR-FAIL    | 74         | 80         | 80         |
| StatelessObject            | 36 {!}     | 1 {!}      | 1 {!}      | 1 {!}      | 2 {!}      | 2 {!}      | RD-FAIL    | RD-FAIL    |
| StatefulObject             | 35 {!}     | 1 {!}      | 1 {!}      | 1 {!}      | 2 {!}      | 2 {!}      | 18 {!}     | 18 {!}     |
| EnumSingleton              | 75 {S}     | 2 {S}      | 2 {S}      | 2 {S}      | 11 {S}     | 11 {S}     | 11 {S}     | 11 {S}     |
| simpleCont                 | 224 {!}    | 25 {!}     | 25 {!}     | 25 {!}     | RD-FAIL    | RD-FAIL    | RD-FAIL    | RD-FAIL    |
| suspendCont                | 225 {!}    | 25 {!}     | 25 {!}     | 25 {!}     | RD-FAIL    | RD-FAIL    | RD-FAIL    | RD-FAIL    |
| computeCont                | 239 {!}    | 26 {!}     | 26 {!}     | 26 {!}     | RD-FAIL    | RD-FAIL    | RD-FAIL    | RD-FAIL    |
+----------------------------+------------+------------+------------+------------+------------+------------+------------+------------+
```
