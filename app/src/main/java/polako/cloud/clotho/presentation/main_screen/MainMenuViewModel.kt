package polako.cloud.clotho.presentation.main_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(MainMenuUiState())
        val uiState: StateFlow<MainMenuUiState> = _uiState.asStateFlow()

        var onFocusButtonClicked: () -> Unit = {}

        fun onFocusButtonClick() {
            onFocusButtonClicked()
        }
    }

data class MainMenuUiState(
    val isLoading: Boolean = false,
)
