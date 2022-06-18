import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.syed.myapplication.R
import com.syed.myapplication.data.BaseResponseModel


class MyEventsAdapter(private var context: Context, var list: ArrayList<BaseResponseModel>?, var currentDate: String) : RecyclerView.Adapter<MyEventsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEventName: TextView = itemView.findViewById(R.id.tv_event_name_my)
        val tvEventPrice: TextView = itemView.findViewById(R.id.tv_event_price_my)
        val ll_ticket: LinearLayout = itemView.findViewById(R.id.ll_ticket)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_my_events, parent, false)
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvEventName.text = list?.get(position)?.title
        holder.tvEventPrice.text = list?.get(position)?.body
    }
    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}