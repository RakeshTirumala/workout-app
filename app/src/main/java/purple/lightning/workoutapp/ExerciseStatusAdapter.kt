package purple.lightning.workoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import purple.lightning.workoutapp.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items:ArrayList<ExcerciseModal>):
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

        class ViewHolder(binding: ItemExerciseStatusBinding):
            RecyclerView.ViewHolder(binding.root){
            val tvItem = binding.tvItem
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val model: ExcerciseModal = items[position]
            holder.tvItem.text = model.getId().toString()

            when{
                model.getIsSelected()->{
                    holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_thin_color_accent_border)
                    holder.tvItem.setTextColor(Color.parseColor("#212121"))
                }
                model.getIsCompleted()->{
                    holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_completed)
                    holder.tvItem.setTextColor(Color.parseColor("#212121"))
                }
                else ->{
                    holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_gray_background)
                    holder.tvItem.setTextColor(Color.parseColor("#212121"))
                }
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }
}