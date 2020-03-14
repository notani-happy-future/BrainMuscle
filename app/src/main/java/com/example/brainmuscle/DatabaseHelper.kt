package com.example.brainmuscle

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * 『Androidアプリ開発の教科書』
 * 第10章
 * データベースサンプル
 *
 * データベースヘルパークラス。
 *
 * @author Shinzo SAITO
 */
class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    //クラス内のpirvate定数を宣言するためにcompanion objectブロックとする。
    companion object {
        /**
         * データベースファイル名の定数フィールド。
         */
        private const val DATABASE_NAME = "brainmuscle.db"
        /**
         * バージョン情報の定数フィールド。
         */
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}
