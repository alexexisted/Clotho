package polako.cloud.clotho.utils

import polako.cloud.clotho.domain.model.ActivityType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Used to hold selected activity that user chooses
 */
@Singleton
class ActivityManager
    @Inject
    constructor() {
        var selectedActivity: ActivityType? = null
    }
