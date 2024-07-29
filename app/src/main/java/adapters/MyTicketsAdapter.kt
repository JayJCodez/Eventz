package adapters

import models.MyTicketsModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.optionsmenupractice.R

class MyTicketsAdapter(
    private val myTicketList: ArrayList<MyTicketsModel>
) : RecyclerView.Adapter<MyTicketsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_tickets_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyTicketsAdapter.MyViewHolder, position: Int) {
        val currentItem = myTicketList[position]
        holder.bind(currentItem)

        // Load image using Glide
//        Glide.with(holder.itemView.context)
//            .load("https://cdn.pixabay.com/photo/2013/02/01/18/14/url-77169_1280.jpg")
//            .error(R.drawable.baseline_error_24)
//            .placeholder(R.drawable.baseline_downloading_24)
//            .into(holder.imageView)

    }


    override fun getItemCount(): Int {
        return myTicketList.size
    }

    // ViewHolder class to hold item views
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var currentItem: MyTicketsModel
        //        val eventid: TextView = itemView.findViewById(R.id.eventid)
        val eventname: TextView = itemView.findViewById(R.id.eventname)
        val eventtype: TextView = itemView.findViewById(R.id.eventtype)
//        val imageView: ImageView = itemView.findViewById(R.id.imgView)
        val event_info: TextView = itemView.findViewById(R.id.event_info)
        val num_of_tickets: TextView = itemView.findViewById(R.id.num_of_tickes)
//        val editButton: FloatingActionButton = itemView.findViewById(R.id.floatingActionButton2)
//        val viewButton: FloatingActionButton = itemView.findViewById(R.id.floatingActionButton3)
//
//        val editButton: Button = itemView.findViewById(R.id.button4)
//        val viewButton: Button = itemView.findViewById(R.id.button3)



        // Bind item data to views
        fun bind(item: MyTicketsModel) {
            currentItem = item
//            eventid.text = item.getEventId().toString()
            eventname.text = item.getEventName()
            eventtype.text = item.getEventType()
            event_info.text = item.getEventDescription()
            val quantitytxt = "Ticket Quantity: ${item.getTicketNo()}"
            num_of_tickets.text = quantitytxt
        }
    }
}
