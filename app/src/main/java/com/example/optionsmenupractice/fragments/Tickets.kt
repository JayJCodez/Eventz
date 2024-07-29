package com.example.optionsmenupractice.fragments

import adapters.MyTicketsAdapter
import models.MyTicketsModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.optionsmenupractice.R


class Tickets : Fragment() {

    private lateinit var user_id : String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyTicketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tickets, container, false)

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        // Initialize transactionList here
        val transactionList = ArrayList<MyTicketsModel>()

        val bundle = arguments
        user_id = bundle?.getInt("user_id").toString()

        storeDataInArray(transactionList)

        // Inflate the layout for this fragment
        return view
    }

    private fun storeDataInArray(transactionList: ArrayList<MyTicketsModel>) {
        val url = "http://10.0.2.2:8888/MyTickets.php?user_id=$user_id"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    transactionList.clear()
                    for (i in 0 until response.length()) {
                        val jsonObject = response.getJSONObject(i)
                        val eventID = jsonObject.getString("event_id")
                        val eventName = jsonObject.getString("event_name")
                        val eventType = jsonObject.getString("event_type")
                        val eventStart = jsonObject.getString("event_start")
                        val eventFinish = jsonObject.getString("event_finish")
                        val eventInfo = jsonObject.getString("event_info")
                        val ticketno = jsonObject.getString("ticket_quantity")


                        val transaction = MyTicketsModel().apply {
                            setEventId(eventID.toInt())
                            setTicketNo(ticketno.toInt())
                            setEventName(eventName)
                            setEventType(eventType)
                            setEventStartTime(eventStart)
                            setEventFinishTime(eventFinish)
                            setEventDescription(eventInfo)
                        }
                        transactionList.add(transaction)
                    }

                    adapter = MyTicketsAdapter(transactionList)
                    recyclerView.adapter = adapter

                } catch (e: Exception) {
                    Toast.makeText(
                        requireContext(),
                        "Error parsing JSON: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

}