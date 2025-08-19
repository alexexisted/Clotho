package polako.cloud.clotho.domain.model

import androidx.annotation.DrawableRes

data class ActivityType(
    val name: String,
    @DrawableRes val icon: Int,
)