package polako.cloud.clotho.domain.model

import androidx.annotation.DrawableRes

data class ActivityType(
    val id: Long,
    val name: String,
    @DrawableRes val icon: Int,
    val color: Int,
)
