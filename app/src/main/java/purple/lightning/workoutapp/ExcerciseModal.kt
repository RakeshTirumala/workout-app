package purple.lightning.workoutapp

class ExcerciseModal(
    private var id: Int,
    private var name:String,
    private var image:Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
){
    fun getId():Int{
        return id
    }

    fun setId(id: Int){
        this.id = id
    }
    fun getName():String{
        return name
    }
    fun setName(name: String){
        this.name = name
    }
    fun getImage():Int{
        return image
    }

    fun setImage(image: Int){
        this.image = image
    }

    fun getIsCompleted():Boolean{
        return isCompleted
    }
    fun setName(isCompleted: Boolean){
        this.isCompleted = isCompleted
    }

    fun getIsSelected():Boolean{
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean){
        this.isSelected = isSelected
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }
}