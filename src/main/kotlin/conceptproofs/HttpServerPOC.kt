package conceptproofs

//import com.fox.io.log.ConsoleColor
//import com.fox.io.log.ConsoleLogger
import hashing.HashProblemSolver
import message.MessageHub
import model.WordGenerator
import org.json.JSONArray
import org.json.JSONObject
import spark.Request
import spark.Spark.get
import spark.Spark.post
import java.util.*

/**
 * Created by stephen on 11/18/15.
 */
object HttpServerPOC {

    fun sparkRun(toInterrupt: Thread) {
        get("/hello") { req, _ -> "Hello, world: length = ${req.contentLength()}" }
        get("/work") { _, _ -> answerWorkRequest() }
        get("/work/:count") { req, _ ->
            val count = req.params(":count").toInt()
            answerWorkRequest(count)
        }
        post("/work", "application/json") { req, res -> dealWorkResult(req, toInterrupt) }
    }

    private fun answerWorkRequest(count: Int = 20): String {

        val mapForJson = HashMap<String, Any>()

        mapForJson["toMatch"] = HashProblemSolver().hashToFind

        mapForJson["triples"] = WordGenerator.getNextPermutationBatch(count)

        mapForJson["fives"] = JSONArray(WordGenerator.listOfFives)

        return JSONObject(mapForJson).toString()
    }

    private fun dealWorkResult(request: Request?, targetToInterrupt: Thread): String {
        println("We received the POST")
        if (request != null && request.contentLength() > 0) {
            val reqBodyJson = JSONObject(request.body())
            val isFound = reqBodyJson.getBoolean("found")
            if (isFound) {
                val targetSentence = reqBodyJson.getString("sentence")
                MessageHub.getLocalMessageHub().writeMessage(this, targetSentence)
                targetToInterrupt.interrupt()
            } else {
                println("Target sentence was not found.")
            }
        }
        //    response?.body("Here's a reply")

//        ConsoleLogger.writeLine("Delimiter", ConsoleColor.YELLOW, 1)
        println("Delimiter")

        return "Testing string return"
    }
}