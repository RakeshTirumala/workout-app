package purple.lightning.workoutapp

import android.app.Application

class ExerciseApp:Application() {
    val db by lazy{
        ExerciseDatabase.getInstance(this)
    }
}