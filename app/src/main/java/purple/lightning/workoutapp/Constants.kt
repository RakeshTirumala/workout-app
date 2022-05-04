package purple.lightning.workoutapp

object Constants {
    fun defaultExerciseList():ArrayList<ExcerciseModal>{
        val exerciseList = ArrayList<ExcerciseModal>()
        val SitUps = ExcerciseModal(
            1,
            "Sit ups",
            R.drawable.sit_ups,
            false,
            false
        )
        exerciseList.add(SitUps)

        val Crunches = ExcerciseModal(
            2,
            "Crunches",
            R.drawable.crunches_,
            false,
            false
        )
        exerciseList.add(Crunches)

        val Bicycles = ExcerciseModal(
            3,
            "Bicycles",
            R.drawable.bicycles_,
            false,
            false
        )
        exerciseList.add(Bicycles)
        val BulgarianSquats = ExcerciseModal(
            4,
            "BulgarianSquats",
            R.drawable.bulgarian_squat,
            false,
            false
        )
        exerciseList.add(BulgarianSquats)

        val Burpees = ExcerciseModal(
            5,
            "Burpees",
            R.drawable.burpees_,
            false,
            false
        )
        exerciseList.add(Burpees)

        val deadLift = ExcerciseModal(
            6,
            "DeadLift",
            R.drawable.dead_lift,
            false,
            false
        )
        exerciseList.add(deadLift)

        val Plank = ExcerciseModal(
            7,
            "plank",
            R.drawable.plank_,
            false,
            false
        )

        return exerciseList
    }
}