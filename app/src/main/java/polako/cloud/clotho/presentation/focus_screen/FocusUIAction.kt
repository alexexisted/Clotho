package polako.cloud.clotho.presentation.focus_screen

sealed interface FocusUIAction {
    data class ShowReflection(
        val elapsedTimeMillis: Long,
    ) : FocusUIAction

    data class SaveReflection(
        val score: Int,
        val tags: List<String>,
    ) : FocusUIAction

    object DismissReflection : FocusUIAction

    object OnSuccess : FocusUIAction
}
