// NewEvent.kt
package fragments

import Database.DBHelper
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.optionsmenupractice.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Calendar

class NewEvent : Fragment(R.layout.fragment_new_event) {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var dbHelper: DBHelper
    private lateinit var button: Button
    private lateinit var confirm_img_selection: TextView
    private lateinit var imgUri: Uri
    private var startselectedHour: Int = 0
    private var startselectedMinute: Int = 0
    private var endselectedHour: Int = 0
    private var endselectedMinute: Int = 0
    var startTime: String = ""
    var endTime: String = ""
    private lateinit var setStart : TextView
    private lateinit var setEnd : TextView

    var insertEventTask: InsertEventTask = InsertEventTask()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_event, container, false)
        button = view.findViewById(R.id.createneweventBTN)
        val datebtn = view.findViewById<Button>(R.id.choose_date)
        dbHelper = DBHelper(requireContext())

        val spinner: Spinner = view.findViewById(R.id.event_type)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val eventname: EditText = view.findViewById(R.id.editTextText)
        val startTime : Button = view.findViewById(R.id.choose_start_time)
        val endTime : Button = view.findViewById(R.id.choose_finish_time)
        setStart = view.findViewById(R.id.set_start_time)
        setEnd  = view.findViewById(R.id.set_end_time)
//        val selectImage: FloatingActionButton = view.findViewById(R.id.selectImg)
//        confirm_img_selection = view.findViewById(R.id.confirm_img_select)
        val event_info = view.findViewById<EditText>(R.id.eventinformation)
        val event_date = view.findViewById<TextView>(R.id.date_text)

        startTime.setOnClickListener {
            showTimePickerDialog(setStart)
        }

        endTime.setOnClickListener {
            showTimePickerDialog(setEnd)
        }

        datebtn.setOnClickListener {
            showDatePicker(event_date)
        }

        button.setOnClickListener {
            val event_name: String = eventname.text.toString()
            val event_type: String = spinner.selectedItem.toString()
            val event_date : String = event_date.text.toString()
            val event_start = setStart.text.toString()
            val event_finish = setEnd.text.toString()
            val info : String = event_info.text.toString()
            val bundle = arguments
            val user_id = bundle?.getString("user_id")

            insertEventTask = InsertEventTask()
            insertEventTask.execute(event_name, event_type, event_date, event_start, event_finish, info, user_id)
        }

//        selectImage.setOnClickListener {
//            selectImage()
//        }

        return view
    }

    private fun showTimePickerDialog(textView: TextView): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                startselectedHour = hourOfDay
                startselectedMinute = minute
                textView.text = String.format("%02d:%02d", startselectedHour, startselectedMinute)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
        return startTime
    }

    fun showDatePicker(textView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, i, i1, i2 ->
            val selectedDate = "$year-${month + 1}-$dayOfMonth"
            textView.text = selectedDate
        }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    override fun onResume() {
        super.onResume()
    }

    fun selectImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null){
                imgUri = selectedImageUri
                val text = "An image has been selected"
                confirm_img_selection.text = text
            }
        }
    }

    inner class InsertEventTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {
            val eventName = params[0]
            val eventtype = params[1]
            val eventdate = params[2]
            val eventstart = params[3]
            val eventfinish = params[4]
            val eventinfo = params[5]
            val user_id = params[6]

            val url = URL("http://10.0.2.2:8888/NewEvent.php")
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                urlConnection.doOutput = true
                urlConnection.requestMethod = "POST"

                val postData = "event_name=$eventName&event_type=$eventtype&event_date=$eventdate&event_start=$eventstart&event_finish=$eventfinish&event_info=$eventinfo&user_id=$user_id"

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
                    Toast.makeText(requireContext(), "There has been an issue. Please try again later", Toast.LENGTH_LONG).show()
                    "HTTP Error: $responseCode"
                }
            } finally {
                urlConnection.disconnect()
            }
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            if (result == "New record created successfully") {
                Toast.makeText(requireContext(), "Event Created Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "There has been an issue. Please try again later", Toast.LENGTH_LONG).show()
            }
        }
    }
}

