package requests

import models.GeneralEventModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.util.ArrayList

class RequestAllEvents {

    lateinit var GeneralEventModel : GeneralEventModel

    fun fetchEvents(onSuccess: (ArrayList<GeneralEventModel>) -> Unit, onFailure: (String) -> Unit) : ArrayList<GeneralEventModel> {
        val url = "http://10.0.2.2:8888/all_events.php"

        val events = ArrayList<GeneralEventModel>()
        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFailure(e.message ?: "Unknown error")
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onFailure("HTTP Error: ${response.code}")
                    return
                }

                val responseBody = response.body?.string()
                if (responseBody.isNullOrBlank()) {
                    onFailure("Empty response body")
                    return
                }


                try {
                    val jsonArray = JSONArray(responseBody)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val eventName = jsonObject.getString("event_name")
                        val eventType = jsonObject.getString("event_type")
                        val eventStart = jsonObject.getString("event_start")
                        val eventFinish = jsonObject.getString("event_finish")
                        val eventInfo = jsonObject.getString("event_info")

                        val event = GeneralEventModel().apply {
                            setEventName(eventName)
                            setEventType(eventType)
                            setEventStartTime(eventStart)
                            setEventFinishTime(eventFinish)
                            setEventDescription(eventInfo)
                        }
                        events.add(event)
                    }
                    onSuccess(events)
                } catch (e: Exception) {
                    onFailure("Error parsing JSON: ${e.message}")
                }
            }
        })
        return events
    }
}