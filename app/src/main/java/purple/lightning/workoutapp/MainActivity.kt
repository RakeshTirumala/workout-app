package purple.lightning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import purple.lightning.workoutapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.flStart?.setOnClickListener {
            val intent = Intent(this@MainActivity, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.bmiBtn?.setOnClickListener {
            val intent = Intent(this@MainActivity, BmiActivity::class.java)
            startActivity(intent)
        }

        binding?.btnHistory?.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}