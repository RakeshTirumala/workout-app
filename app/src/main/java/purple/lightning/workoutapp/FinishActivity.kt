package purple.lightning.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import purple.lightning.workoutapp.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        binding?.FinishBtn?.setOnClickListener{
            finish()
        }

        val exerciseDao = (application as ExerciseApp).db.exerciseDao()
        addDateToDatabase(exerciseDao)

    }

    private fun addDateToDatabase(exerciseDao: ExerciseDao){
        val c = Calendar.getInstance()
        val dateTime = c.time

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())

        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            exerciseDao.insert(ExerciseEntity(date))
        }
    }

}