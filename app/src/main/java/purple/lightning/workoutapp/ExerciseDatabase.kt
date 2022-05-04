package purple.lightning.workoutapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExerciseEntity::class], version=2, exportSchema = true)
abstract class ExerciseDatabase: RoomDatabase(){
    abstract fun exerciseDao():ExerciseDao

    companion object{
        @Volatile
        private var INSTANCE: ExerciseDatabase ?= null

        fun getInstance(context: Context):ExerciseDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "exercise_database"
                    ).allowMainThreadQueries()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}