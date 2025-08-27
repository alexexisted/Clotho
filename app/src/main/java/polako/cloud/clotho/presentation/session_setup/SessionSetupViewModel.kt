package polako.cloud.clotho.presentation.session_setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.data.repository.ActivityTypeRepository
import polako.cloud.clotho.domain.ActivityManager
import polako.cloud.clotho.domain.model.ActivityType
import polako.cloud.clotho.utils.execute
import javax.inject.Inject

@HiltViewModel
class SessionSetupViewModel
    @Inject
    constructor(
        private val activityManager: ActivityManager,
        private val activityTypeRepository: ActivityTypeRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SessionSetupUiState())
        val uiState: StateFlow<SessionSetupUiState> = _uiState.asStateFlow()

        init {
            getAllActivities()
        }

        fun onActivityTypeSelected(activityType: ActivityType) {
            _uiState.update {
                it.copy(selectedActivity = activityType)
            }
            activityManager.selectedActivity = activityType
        }

        private fun getAllActivities() {
            viewModelScope.execute(
                source = { activityTypeRepository.getAllActivities() },
                onSuccess = { types ->
                    _uiState.update {
                        it.copy(
                            activityTypesLists = types,
                        )
                    }
                },
            )
        }
    }

data class SessionSetupUiState(
    val activityTypesLists: List<ActivityType> = emptyList(),
    val selectedActivity: ActivityType? = null,
)
