package com.example.brainmuscle

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MenuActivity : AppCompatActivity() {

    private val _helper = DatabaseHelper(this@MenuActivity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(com.example.brainmuscle.R.layout.activity_menu)
        setContentView(R.layout.activity_menu)

        val db = _helper.writableDatabase
        val mapMyLevelOfNumber: MutableMap<String, String> = getMyLevel(db,getString(R.string.type_of_game_number).toString())
        val tvMyLevelOfNumber = findViewById<TextView>(R.id.tv_current_level_number)
        tvMyLevelOfNumber.setText(mapMyLevelOfNumber.get("level_no").toString())
        val tvMyLevelOfNumberId = findViewById<TextView>(R.id.tv_current_level_number_of_id)
        tvMyLevelOfNumberId.setText(mapMyLevelOfNumber.get("level_id").toString())
        Log.i("ohtani mapyLevelOf",mapMyLevelOfNumber.get("level_id").toString() )
        Log.i("ohtani tvMyLevelOf",tvMyLevelOfNumberId.text.toString() )

        val mapMyLevelOfAlphabet: MutableMap<String, String> = getMyLevel(db,getString(R.string.type_of_game_alphabet).toString())
        val tvMyLevelOfAlphabet = findViewById<TextView>(R.id.tv_current_level_alphabet)
        tvMyLevelOfAlphabet.setText(mapMyLevelOfAlphabet.get("level_no").toString())
        val tvMyLevelOfAlphabetId = findViewById<TextView>(R.id.tv_current_level_alphabet_of_id)
        tvMyLevelOfAlphabetId.setText(mapMyLevelOfAlphabet.get("level_id").toString())

        val mapMyLevelOfColor = getMyLevel(db,getString(R.string.type_of_game_color).toString())
        val tvMyLevelOfColor = findViewById<TextView>(R.id.tv_current_level_color)
        val tvMyLevelOfColorId = findViewById<TextView>(R.id.tv_current_level_color_of_id)
        tvMyLevelOfColor.setText(mapMyLevelOfColor.get("level_no").toString())
        tvMyLevelOfColorId.setText(mapMyLevelOfColor.get("level_id").toString())

        val mapMyLevelOfSymbol = getMyLevel(db,getString(R.string.type_of_game_symbol).toString())
        val tvMyLevelOfSymbol = findViewById<TextView>(R.id.tv_current_level_symbol)
        val tvMyLevelOfSymbolId = findViewById<TextView>(R.id.tv_current_level_symbol_of_id)
        tvMyLevelOfSymbol.setText(mapMyLevelOfSymbol.get("level_no").toString())
        tvMyLevelOfSymbolId.setText(mapMyLevelOfSymbol.get("level_id").toString())


        val btStartNumber = findViewById<ImageView>(R.id.iv_start_number)
        btStartNumber.setOnClickListener(BtStartNumberOnClickListener())

        val btStartAlphabet = findViewById<ImageView>(R.id.iv_start_alphabet)
        btStartAlphabet.setOnClickListener(BtStartAlphabetOnClickListener())

        val btStartColor = findViewById<ImageView>(R.id.iv_start_color)
        btStartColor.setOnClickListener(BtStartColorOnClickListener())

        val btStartSymbol = findViewById<ImageView>(R.id.iv_start_symbol)
        btStartSymbol.setOnClickListener(BtStartSymbolOnClickListener())

    }
    private inner class BtStartNumberOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.putExtra("type_of_game",getString(R.string.type_of_game_number))

            val tvMyLevelOfNumberId = findViewById<TextView>(R.id.tv_current_level_number_of_id)

            intent.putExtra("level_id",tvMyLevelOfNumberId.text.toString())

            intent.putExtra("count_of_try","1")

            startActivity(intent)
        }
    }

    private inner class BtStartAlphabetOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.putExtra("type_of_game",getString(R.string.type_of_game_alphabet))

            val tvMyLevelOfAlphabetId = findViewById<TextView>(R.id.tv_current_level_alphabet_of_id)
            intent.putExtra("level_id",tvMyLevelOfAlphabetId.text.toString())

            intent.putExtra("count_of_try","1")

            startActivity(intent)
        }
    }

    private inner class BtStartColorOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.putExtra("type_of_game",getString(R.string.type_of_game_color))

            val tvMyLevelOfColortId = findViewById<TextView>(R.id.tv_current_level_color_of_id)
            intent.putExtra("level_id",tvMyLevelOfColortId.text.toString())

            intent.putExtra("count_of_try","1")

            startActivity(intent)
        }
    }
    private inner class BtStartSymbolOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val intent = Intent(applicationContext,MainActivity::class.java)
            intent.putExtra("type_of_game",getString(R.string.type_of_game_symbol))

            val tvMyLevelOfSymbolId = findViewById<TextView>(R.id.tv_current_level_symbol_of_id)
            intent.putExtra("level_id",tvMyLevelOfSymbolId.text.toString())

            intent.putExtra("count_of_try","1")

            startActivity(intent)
        }
    }

    private fun getMyLevel(db: SQLiteDatabase, strTypeOfGame:String):MutableMap<String,String> {
        Log.i("ohtani strTypeOfGame",strTypeOfGame )
        val sql = "SELECT level.id as level_id,level.level_no FROM level inner join my_level on level.id = my_level.level_id where type_of_game='$strTypeOfGame';"
        //SQLの実行。
        val cursor = db.rawQuery(sql, null)
        var intRecordIdx: Int
        var mapMyLevel : MutableMap<String, String> = mutableMapOf()

        Log.i("ohtani getMyLevel","000" )
        if (cursor.moveToNext()) {
            Log.i("ohtani getMyLevel","001" )
            intRecordIdx = cursor.getColumnIndex("level_id")
            mapMyLevel=mutableMapOf( "level_id" to cursor.getString(intRecordIdx).toString() )
            Log.i("ohtani getMyLevel 002",mapMyLevel.get("level_id") )

            intRecordIdx = cursor.getColumnIndex("level_no")
            mapMyLevel.put("level_no" , cursor.getString(intRecordIdx).toString() )
        }
        return mapMyLevel
    }

}
