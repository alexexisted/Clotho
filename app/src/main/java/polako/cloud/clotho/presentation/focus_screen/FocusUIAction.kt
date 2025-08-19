package polako.cloud.clotho.presentation.focus_screen

sealed interface FocusUIAction {
    object Start : FocusUIAction

    object Stop : FocusUIAction

    object Pause : FocusUIAction
}