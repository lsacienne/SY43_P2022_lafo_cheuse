package net.yolopix.moneyz.model.entities

import androidx.room.*
import java.util.Date

@Entity
class Month(
    @PrimaryKey val id: Int,
    var prevision: Double,
    var payday: Date
)