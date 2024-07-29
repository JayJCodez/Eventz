package com.example.optionsmenupractice.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.optionsmenupractice.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * A simple [Fragment] subclass.
 * Use the [EditMyEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditMyEventFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_my_event, container, false)

        val nametxt : TextView =  view.findViewById(R.id.nameEditText)
        val typetxt : TextView =  view.findViewById(R.id.typeEditText)
        val datetxt : TextView =  view.findViewById(R.id.dateEditText)
        val starttxt : TextView =  view.findViewById(R.id.startEditText)
        val finishtxt : TextView =  view.findViewById(R.id.finishEditText)
        val infotxt : TextView =  view.findViewById(R.id.infoEditText)

        val bundle = arguments
        val id = bundle?.getString("event_id")
        var name = bundle?.getString("event_name")
        var type = bundle?.getString("event_type")
        var date = bundle?.getString("event_date")
        var start = bundle?.getString("event_start")
        var finish = bundle?.getString("event_finish")
        var info = bundle?.getString("event_info")


        nametxt.text = name
        typetxt.text = type
        datetxt.text = date
        starttxt.text = start
        finishtxt.text = finish
        infotxt.text = info

        val submit : Button = view.findViewById(R.id.submit)

        submit.setOnClickListener{

            name = nametxt.text.toString()
            type = typetxt.text.toString()
            date = datetxt.text.toString()
            start = starttxt.text.toString()
            finish = finishtxt.text.toString()
            info = infotxt.text.toString()

            val manageReq = InsertEventTask()
            manageReq.execute(name, type, date, start, finish, info, id)
            Toast.makeText(context, "Successfully Updated Your Event Details", Toast.LENGTH_LONG).show()

        }

        // Inflate the layout for this fragment
        return view
    }

    inner class InsertEventTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {
            val eventName = params[0]
            val eventtype = params[1]
            val eventdate = params[2]
            val eventstart = params[3]
            val eventfinish = params[4]
            val eventinfo = params[5]
            val eventid = params[6]


            val url = URL("http://10.0.2.2:8888/ManageEvents.php")
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.doOutput = true
                urlConnection.requestMethod = "POST"

                val postData = "event_id=$eventid&event_name=$eventName&event_type=$eventtype&event_date=$eventdate&event_start=$eventstart&event_finish=$eventfinish&event_info=$eventinfo"

                val outputStream = urlConnection.outputStream
                outputStream.write(postData.toByteArray())
                outputStream.flush()

                val responseCode = urlConnection.responseCode
                return if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val response = StringBuffer()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    response.toString()
                } else {
//                    Toast.makeText(context, "There has been an issue. Please try again later", Toast.LENGTH_LONG).show()
                    "HTTP Error: $responseCode"
                }
            } finally {
                urlConnection.disconnect()
            }
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            if (result == "Event updated successfully") {
//                Toast.makeText(context, "Event Updated Successfully", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(context, "There has been an issue. Please try again later", Toast.LENGTH_LONG).show()
            }
        }
    }
}