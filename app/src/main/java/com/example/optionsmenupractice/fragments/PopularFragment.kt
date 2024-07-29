package com.example.optionsmenupractice.fragments

import Database.DBHelper
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import adapters.PopularRecyclerAdapter
import models.PopularEventModel
import com.example.optionsmenupractice.R

class PopularFragment : Fragment() {

    private lateinit var popularEventsList: ArrayList<PopularEventModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: DBHelper
    private lateinit var cursor: Cursor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_popular, container, false)

        // Initialize DBHelper
        dbHelper = DBHelper(requireContext())

        // Initialize other variables
        popularEventsList = ArrayList()


        recyclerView = view.findViewById(R.id.recyclerview)

//         Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        storeDataInArray()


        // Your code to populate recyclerView or any other initialization

        return view
    }

    private fun storeDataInArray() {
        // Clear the userList to avoid duplicating data
//        userList.clear()

        val query: String = "SELECT * FROM Events"
        // Read all data from the database
        cursor = dbHelper.readAlldata(query)

        if (cursor.count > 0) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                val popularEventModel = PopularEventModel()
                popularEventModel.setEventId(cursor.getInt(0))
                popularEventModel.setEventName(cursor.getString(2))
                popularEventModel.setEventType(cursor.getString(3))
                popularEventsList.add(popularEventModel)
            }
        } else {
            Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
        }



        recyclerView.adapter = PopularRecyclerAdapter(popularEventsList)
        cursor.close() // Close the cursor when done
    }
}
