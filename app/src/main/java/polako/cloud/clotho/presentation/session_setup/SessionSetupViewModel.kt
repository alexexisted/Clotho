package polako.cloud.clotho.presentation.session_setup

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.R
import polako.cloud.clotho.domain.ActivityManager
import polako.cloud.clotho.domain.model.ActivityType
import javax.inject.Inject

@HiltViewModel
class SessionSetupViewModel @Inject constructor(
    private val activityManager: ActivityManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionSetupUiState())
    val uiState: StateFlow<SessionSetupUiState> = _uiState.asStateFlow()


    fun onActivityTypeSelected(activityTypesList: ActivityType) {
        _uiState.update {
            it.copy(selectedActivity = activityTypesList)
        }
        activityManager.selectedActivity = activityTypesList
    }
}

data class SessionSetupUiState(
    val activityTypesLists: List<ActivityType> = listOf(
        ActivityType("Work", R.drawable.icon_work),
        ActivityType("Study", R.drawable.icon_school),
        ActivityType("Reading", R.drawable.icon_book),
        ActivityType("Meditation", R.drawable.icon_mind),
        ActivityType("Sport", R.drawable.icon_barbell),
        ActivityType("Other", R.drawable.icon_other),
    ),
    val selectedActivity: ActivityType? = null
)