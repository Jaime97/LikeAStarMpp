package com.jaa.library.domain.di

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    expect fun createDriver(): SqlDriver
}