package purple.lightning.workoutapp
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import purple.lightning.workoutapp.databinding.ActivityExerciseBinding
import purple.lightning.workoutapp.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

//    binding makes it easy to access and make changes to the elements in xml
    private var binding : ActivityExerciseBinding?= null

//    Declaring global variables
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var ExcerciseTimer: CountDownTimer? = null
    private var ExcerciseProgress = 0

    private var exerciseList: ArrayList<ExcerciseModal>? = null
    private var currentExercisePosition = -1

    private var upComingExercise : Int? = null

    private var tts: TextToSpeech?= null
    private var text: String = "Get ready you first exercise"

    private var player: MediaPlayer? = null

    private  var exerciseAdapter: ExerciseStatusAdapter?= null


    //    ONCREATE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener{
            customDialogForBackButton()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.yesBtn.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.noBtn.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()

    }

    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    //  setting up rest view
    private fun setupRestView(){
    if(restTimer != null){
        restTimer?.cancel()
        restProgress = 0
    }

    try{
        val soundURI = Uri.parse(
            "android.resource://WorkoutApp/" +
                    R.raw.app_src_main_res_raw_press_start)
        player = MediaPlayer.create(applicationContext, soundURI)
        player?.isLooping = false
        player?.start()
    }catch (e: Exception){
        e.printStackTrace()
    }

    setRestProgressBar()
    }

    //    Rest progress Bar
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
        upComingExercise = currentExercisePosition + 1
        if(upComingExercise!! <= exerciseList!!.size){
            binding?.tvUpExerName?.text = exerciseList!![upComingExercise!!].getName()
        }

        if(currentExercisePosition <exerciseList!!.size){
            text = "Your upcoming exercise is ${binding?.tvUpExerName?.text.toString()}"
            speakOut(text)
        }

        restTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                restProgress += 1
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition += 1
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                binding?.tvUpComing?.visibility = View.INVISIBLE
                binding?.tvUpExerName?.visibility = View.INVISIBLE
                setUpExcerciseView()
            }
        }.start()
    }

    //    speak fun
    private fun speakOut(text:String){
    tts?.speak(text,
        TextToSpeech.QUEUE_FLUSH,
        null,
        ""
    )
    }

    //    setting up exercise view
    private fun setUpExcerciseView(){
        binding?.flProgress?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExcerciseView?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE

        if(ExcerciseTimer != null){
            ExcerciseTimer?.cancel()
            ExcerciseProgress = 0
        }

        binding?.ivExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setExcerciseProgressBar()

    }

    //    Exercise Progress Bar

    private fun setExcerciseProgressBar(){
        binding?.progressBarExcercise?.progress = ExcerciseProgress
        ExcerciseTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                ExcerciseProgress = ExcerciseProgress + 1
                binding?.progressBarExcercise?.progress = 10 - ExcerciseProgress
                binding?.tvTimerExcercise?.text = "${(10-ExcerciseProgress)}"
            }

            override fun onFinish() {
                binding?.flProgress?.visibility = View.VISIBLE
                binding?.tvTitle?.visibility = View.VISIBLE
                binding?.tvTitle?.text = "Relax For..."
                binding?.flExcerciseView?.visibility = View.INVISIBLE
                binding?.tvExerciseName?.visibility = View.INVISIBLE
                binding?.tvUpComing?.visibility = View.VISIBLE
                binding?.tvUpExerName?.visibility = View.VISIBLE
                binding?.ivExercise?.setImageResource(R.drawable.relaxing_)
                restProgress = 0


                if(currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)

                    exerciseAdapter!!.notifyDataSetChanged()
                    setRestProgressBar()

                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
                var newValue:String = exerciseList!![currentExercisePosition].toString()

            }
        }.start()
    }




    //    Init for TTS
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)

            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language specified is not supported")
            }else{
                Log.e("TTS", "Initialization failed!!")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        if(ExcerciseTimer != null){
            ExcerciseTimer?.cancel()
            ExcerciseProgress = 0
        }
        if(tts!= null){
            tts?.stop()
            tts?.shutdown()
        }

        if(player != null){
            player?.stop()
        }

        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)

        binding = null
    }

}