package de.syex.playground.extension

import java.time.LocalDate
import java.time.ZoneId
import java.util.*


fun Date.toLocalDate(): LocalDate = toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

fun LocalDate.toDate(): Date = Date.from(atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
