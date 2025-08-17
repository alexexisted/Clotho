package polako.cloud.clotho.presentation.session_setup

import polako.cloud.clotho.R
import android.graphics.drawable.Drawable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.domain.ActivityType
import javax.inject.Inject

@HiltViewModel
class SessionSetupViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SessionSetupUiState())
    val uiState: StateFlow<SessionSetupUiState> = _uiState.asStateFlow()


    fun onActivityTypeSelected(activityTypesList: ActivityType) {
        _uiState.update { currentState ->
            currentState.copy(selectedActivity = activityTypesList)
        }
    }

    fun onStartSessionClicked() {
        // Will be implemented when navigation is set up
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