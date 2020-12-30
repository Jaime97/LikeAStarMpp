package com.jaa.library.domain.di

import android.content.Context
import com.jaa.library.domain.dataSource.storage.FilmSqlDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FilmSqlDatabase.Schema, context, "FilmSqlDatabase.db")
    }
}