package adapters
import models.PopularEventModel
import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.optionsmenupractice.R

class PopularRecyclerAdapter(private val popularList: ArrayList<PopularEventModel>) : RecyclerView.Adapter<PopularRecyclerAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = popularList[position]
        holder.eventname.text = currentitem.getEventName()
        holder.eventid.text = currentitem.getEventId().toString()
        holder.eventtype.text = currentitem.getEventType()
//        holder.imageView.setImageResource(R.drawable.sharp_man_4_24)



    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventid: TextView = itemView.findViewById(R.id.eventid)
        val eventname: TextView = itemView.findViewById(R.id.eventname)
        val eventtype: TextView = itemView.findViewById(R.id.eventtype)
//        val lastnametxt: TextView = itemView.findViewById(R.id.lastname)
//        val imageView: ImageView = itemView.findViewById(R.id.imageView2)




    }



}