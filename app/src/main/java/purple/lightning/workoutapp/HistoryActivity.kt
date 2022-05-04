package purple.lightning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import purple.lightning.workoutapp.databinding.ActivityHistoryBinding
import purple.lightning.workoutapp.databinding.ActivityMainBinding

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener{
            val intent = Intent(this@HistoryActivity,MainActivity::class.java)
            startActivity(intent)
        }

        val exerciseDao = (application as ExerciseApp).db.exerciseDao()
        getHistory(exerciseDao)
    }

    private fun getHistory(exerciseDao: ExerciseDao){
        lifecycleScope.launch {
            exerciseDao.fetchExerciseHistory().collect { allCompletedDates ->
                if(allCompletedDates.isNotEmpty()){
                    binding?.nohistoryTv?.visibility = View.GONE
                    binding?.historyRv?.visibility = View.VISIBLE

                    binding?.historyRv?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = ArrayList<String>()
                    for(date in allCompletedDates){
                        dates.add(date.date)
                    }

                    val historyAdapter = ExerciseAdapter(dates)
                    binding?.historyRv?.adapter = historyAdapter


                }else{
                    binding?.nohistoryTv?.visibility = View.VISIBLE
                    binding?.historyRv?.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}