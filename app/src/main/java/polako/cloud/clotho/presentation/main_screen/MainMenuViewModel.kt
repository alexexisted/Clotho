package polako.cloud.clotho.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import polako.cloud.clotho.data.repository.ActivityTypeRepository
import polako.cloud.clotho.utils.execute
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel
    @Inject
    constructor(
        private val activityTypeRepository: ActivityTypeRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MainMenuUiState())
        val uiState: StateFlow<MainMenuUiState> = _uiState.asStateFlow()

        init {
            setupDb()
        }

        private fun setupDb() {
            viewModelScope.execute(
                source = {
                    activityTypeRepository.getAllActivities()
                },
            )
        }
    }

data class MainMenuUiState(
    val isLoading: Boolean = false,
)
