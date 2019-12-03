import conceptproofs.HttpServerPOC
import hashing.HashProblemSolver
import model.Word
import org.json.JSONArray
import org.json.JSONObject

/**
 * Starting point for the multi-threaded SHA256 code matcher
 * Created by stephen on 11/12/15.
 */

fun main() {

    val mainThread = Thread.currentThread()
    val serverThread = Thread {
        HttpServerPOC.sparkRun(mainThread)
    }
    // Scratch: This works!!! In like 2.5h
    serverThread.start()
    HashProblemSolver().runIt()
}

/**
 * This is my glorified debugging. Just be glad I'm not using my pretty print
 * library functions that I built.
 */
fun JSONArray.printAsWords(): Unit {
    this.filterIsInstance<JSONObject>().forEach { println(Word.fromJSONObject(it)) }
}

