package purple.lightning.workoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExerciseTable")
data class ExerciseEntity(
    @PrimaryKey
    val date:String
)
