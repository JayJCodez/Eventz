package com.example.optionsmenupractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R


class GetTickets : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_get_tickets, container, false)


        val promocode = view.findViewById<EditText>(R.id.promo_code)
        val user_name = view.findViewById<EditText>(R.id.edit_text_name)
        val email = view.findViewById<EditText>(R.id.edit_text_email)
        val phone = view.findViewById<EditText>(R.id.edit_text_phone)
        val quantity = view.findViewById<EditText>(R.id.num_tickets_editText)
        val submit = view.findViewById<Button>(R.id.button_confirm_transaction)
        val event_name = view.findViewById<TextView>(R.id.event_name)
        val event_type = view.findViewById<TextView>(R.id.event_date)

        val bundle = arguments
        val id = bundle?.getInt("event_id").toString()
        val user_id = bundle?.getInt("user_id").toString()
        val eventname = bundle?.getString("event_name").toString()
        val eventtype = bundle?.getString("event_type").toString()

        event_name.text = eventname
        event_type.text = eventtype

        submit.setOnClickListener{


            bookTickets(user_id, promocode.text.toString(), id.toString(), user_name.text.toString(), email.text.toString(), phone.text.toString(), quantity.text.toString() )

        }
        // Inflate the layout for this fragment
        return view
    }



    private fun bookTickets(userid : String, promoCode : String, eventID : String, name : String, email :String, phone: String, ticketno : String) {

        val url = "http://10.0.2.2:8888/GetTickets.php"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            Response.Listener { response ->
                // Handle successful response
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                // Handle error
                Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }) {

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["promo_code"] = promoCode
                params["event_id"] = eventID
                params["name"] = name
                params["email"] = email
                params["phone"] = phone
                params["ticket_no"] = ticketno
                params["user_id"] = userid
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(stringRequest)

    }


}