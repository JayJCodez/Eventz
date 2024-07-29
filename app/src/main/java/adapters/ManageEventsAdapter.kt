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

class ManageEventsAdapter(
    private var eventList: ArrayList<MyEventsModel>,
    val onItemClick: (MyEventsModel) -> Unit // Callback for item click event
) : RecyclerView.Adapter<ManageEventsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.manage_events_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.bind(currentItem)

        Glide.with(holder.itemView.context)
            .load("https://cdn.pixabay.com/photo/2013/02/01/18/14/url-77169_1280.jpg")
            .error(R.drawable.baseline_error_24)
            .placeholder(R.drawable.baseline_downloading_24)
            .into(holder.imageView)

        holder.viewButton.setOnClickListener {
            onItemClick(currentItem) // Invoke onItemClick callback
        }
    }

    fun updateData(myEvents: ArrayList<MyEventsModel>) {
        eventList = myEvents
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var currentItem: MyEventsModel
        val eventname: TextView = itemView.findViewById(R.id.eventname)
        val eventtype: TextView = itemView.findViewById(R.id.eventtype)
        val imageView: ImageView = itemView.findViewById(R.id.imgView)
        val viewButton: Button = itemView.findViewById(R.id.button3)

        fun bind(item: MyEventsModel) {
            currentItem = item
            eventname.text = item.getEventName()
            eventtype.text = item.getEventType()
        }
    }
}
