package purple.lightning.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import purple.lightning.workoutapp.databinding.ActivityBmiBinding

class BmiActivity : AppCompatActivity() {
    private var binding: ActivityBmiBinding ?= null
    private var height: Double?= null
    private var weight: Double?= null
    private var bmiValue: Double?= null
    private var feet: Int?= null
    private var inches: Double?= null
    private var case: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnCalculate?.setOnClickListener {
            CalculateBmi()
        }

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener{
            val intent = Intent(this@BmiActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding?.rbMetricUnits?.setOnClickListener {
            binding?.usQuesTv?.visibility = View.INVISIBLE
            binding?.usLL?.visibility = View.INVISIBLE
            binding?.bmiQues1Tv?.visibility = View.VISIBLE
            binding?.bmiAns1?.visibility = View.VISIBLE
            case = 0

        }
        binding?.rbUsUnits?.setOnClickListener {
            binding?.bmiQues1Tv?.visibility = View.INVISIBLE
            binding?.bmiAns1?.visibility = View.INVISIBLE
            binding?.usQuesTv?.visibility = View.VISIBLE
            binding?.usLL?.visibility = View.VISIBLE
            case = 1
        }
    }

    private fun CalculateBmi(){
        weight = binding?.bmiAns2?.text.toString().toDouble()
        if(case==0){
            height = binding?.bmiAns1?.text.toString().toDouble()
        }else{
            feet = binding?.feetEt?.text.toString().toInt()
            inches = binding?.inchesEt?.text.toString().toDouble()
            height = (feet!! + (inches!!/12))*0.3048
        }
        bmiValue = (weight!!)/(height!!*height!!)
        binding?.bmiValueTv?.text = bmiValue.toString()

        if(bmiValue!!>18 && bmiValue!!<25){
            binding?.feedbackTv?.text = "Your Normal! Maintain this"
        }else if(bmiValue!!>=25){
            binding?.feedbackTv?.text = "Your Overweight fatty! Cut some fat"
        }else{
            binding?.feedbackTv?.text = "Your Underweight skinny! Eat something"
        }
    }

}