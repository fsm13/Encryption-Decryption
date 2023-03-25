

import java.io.File
fun shiftEnc(string: String, key: Int, enc: String = "enc"): String {
    val abc = ('a'..'z').toList()
    val ix = 26 - key

    var outString = ""
    for (chr in string) {
        outString += if (chr in abc ) {
            if (chr >= abc[key] && enc == "dec") abc[abc.indexOf(chr - key)]
            else if (chr < abc[key] && enc == "dec") abc[abc.indexOf(chr + ix)]

            else if (chr <= abc[ix] && enc == "enc") abc[abc.indexOf(chr + key)]
            else if (chr > abc[ix] && enc == "enc") abc[abc.indexOf(chr - ix)] else ""
        }
        else chr
    }

    return outString
}

fun uniEnc(string: String, key: Int = 0, enc: String = "enc"): String {
    var outString = ""
    for (chr in string) {
        outString += when (enc) {
            "enc" -> chr + key
            "dec" -> chr - key
            else -> ""
        }
    }
    return outString
}
fun encString(str: String, key: Int = 0, enc: String = "enc", alg: String = "shift"): String =
    if (alg == "shift") shiftEnc(str, key, enc) else uniEnc(str, key, enc)



fun readFile(fileName: String, userDir: String): String =
    if (File("$userDir$fileName").exists()) File("$userDir$fileName").readText() else "Error"
fun writeFile(fileName: String, text: String, userDir: String) =
    File("$userDir$fileName").writeText(text)

fun main(args: Array<String>) {
    var inp = ""
    var str = ""

    val mode = args[args.indexOf("-mode") + 1]
    val alg = args[args.indexOf("-alg") + 1]
    val key = args[args.indexOf("-key") + 1].toInt()
    if ("-data" in args) str = args[args.indexOf("-data") + 1]
    else if ("-in" in args) inp = args[args.indexOf("-in") + 1]
    val out = args[args.indexOf("-out") + 1]

    val userDir = System.getProperty ("user.dir") + File.separator

    if(str != "") println(encString(str, key, mode, alg))

    else if (inp != "") {
        str = readFile(inp, userDir)
        if (str != "Error") {
            str = encString(str, key, mode, alg)
            writeFile(out, str, userDir)
        } else print(str)
    } else  println(str)

}