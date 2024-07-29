package adapters

import models.MyEventsModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.optionsmenupractice.R

class MyEventsAdapter(
    private var myEventsList: ArrayList<MyEventsModel>,
    private val onItemClick: (MyEventsModel) -> Unit,
    private val onEditClick: (MyEventsModel) -> Unit,
    private val onViewClick: (MyEventsModel) -> Unit
) : RecyclerView.Adapter<MyEventsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.new_card_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = myEventsList[position]
        holder.bind(currentItem)

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load("https://cdn.pixabay.com/photo/2013/02/01/18/14/url-77169_1280.jpg")
            .error(R.drawable.baseline_error_24)
            .placeholder(R.drawable.baseline_downloading_24)
            .into(holder.imageView)

        // Set onClickListener for the edit button
        holder.editButton.setOnClickListener {
            onEditClick(currentItem)
        }

        holder.viewButton.setOnClickListener{
            onViewClick(currentItem)
        }


    }

    fun updateData(newEvents: ArrayList<MyEventsModel>) {
        myEventsList = newEvents
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return myEventsList.size
    }

    // ViewHolder class to hold item views
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var currentItem: MyEventsModel
//        val eventid: TextView = itemView.findViewById(R.id.eventid)
        val eventname: TextView = itemView.findViewById(R.id.eventname)
        val eventtype: TextView = itemView.findViewById(R.id.eventtype)
        val imageView: ImageView = itemView.findViewById(R.id.imgView)
//        val editButton: FloatingActionButton = itemView.findViewById(R.id.floatingActionButton2)
//        val viewButton: FloatingActionButton = itemView.findViewById(R.id.floatingActionButton3)

        val editButton: Button = itemView.findViewById(R.id.button4)
        val viewButton: Button = itemView.findViewById(R.id.button3)


        init {
            itemView.setOnClickListener {
                onItemClick.invoke(currentItem)
            }
        }

        // Bind item data to views
        fun bind(item: MyEventsModel) {
            currentItem = item
//            eventid.text = item.getEventId().toString()
            eventname.text = item.getEventName()
            eventtype.text = item.getEventType()
        }
    }
}
