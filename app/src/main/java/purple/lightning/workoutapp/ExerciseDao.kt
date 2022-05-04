package purple.lightning.workoutapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    fun insert(exerciseEntity: ExerciseEntity)

    @Query("SELECT * from `exercisetable`")
    fun fetchExerciseHistory():Flow<List<ExerciseEntity>>
}