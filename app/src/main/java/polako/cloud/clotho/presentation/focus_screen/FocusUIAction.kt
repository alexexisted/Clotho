package polako.cloud.clotho.presentation.focus_screen

sealed interface FocusUIAction {
    object Start : FocusUIAction

    object Stop : FocusUIAction

    object Pause : FocusUIAction
    
    object ShowReflection : FocusUIAction
    
    data class SaveReflection(val score: Int, val tags: List<String>) : FocusUIAction
    
    object DismissReflection : FocusUIAction

    object OnSuccess: FocusUIAction
}