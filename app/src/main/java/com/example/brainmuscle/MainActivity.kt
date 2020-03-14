package com.example.brainmuscle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlin.math.*
import android.widget.AdapterView
import android.widget.Toast
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import androidx.core.os.postDelayed
import kotlin.random.Random
import android.R.attr.button
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.id
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.button
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.bottom
import android.R.attr.right
import android.R.attr.top
import android.R.attr.left
import android.database.sqlite.SQLiteDatabase
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlin.random.nextInt


class MainActivity : AppCompatActivity() {

    private val _helper = DatabaseHelper(this@MainActivity)

    private var arrayBlockIdList =arrayListOf<Int>()
    private var arrayBlockTextList =arrayListOf<String>()
    private var arrayBlockBgcList =arrayListOf<Int>()

    private val readyTime = 1000


    private var _strTypeOfGame=""
    private var _strLevelId=""
    private var _intCountOfTry =0
    private fun countOfTryPlus(){
        _intCountOfTry+=1
    }
    private var _intTotalBlockCount=0
    private var _intTotalHitCount =0

    private var _intNumberOfBlock=0
    private var _intNumberOfExpressions=0
    private var _strLevelOfDifficulty=""

    private fun arraySetting(id:Int,text:String,color:Int){
        arrayBlockIdList.add(id)
        arrayBlockTextList.add(text)
        arrayBlockBgcList.add(color)
    }
    private fun arrayInit() {
        arrayBlockIdList.clear()
        arrayBlockTextList.clear()
        arrayBlockBgcList.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

Log.i("ohtani ","0000" )

        //パタメータの取得
        _strTypeOfGame = intent.getStringExtra("type_of_game")
        _strLevelId = intent.getStringExtra("level_id")
        _intCountOfTry = intent.getStringExtra("count_of_try").toInt()

        //初期処理
        initGame()

        val tvSelectable = findViewById<TextView>(R.id.tv_selectable0)
        tvSelectable.setOnClickListener(TvSelectableClickListener())

        val tvSelectable1 = findViewById<TextView>(R.id.tv_selectable1)
        tvSelectable1.setOnClickListener(TvSelectableClickListener())

        val tvSelectable2 = findViewById<TextView>(R.id.tv_selectable2)
        tvSelectable2.setOnClickListener(TvSelectableClickListener())

        val tvSelectable3 = findViewById<TextView>(R.id.tv_selectable3)
        tvSelectable3.setOnClickListener(TvSelectableClickListener())

        val tvSelectable4 = findViewById<TextView>(R.id.tv_selectable4)
        tvSelectable4.setOnClickListener(TvSelectableClickListener())

        val tvSelectable5 = findViewById<TextView>(R.id.tv_selectable5)
        tvSelectable5.setOnClickListener(TvSelectableClickListener())

        val tvSelectable6 = findViewById<TextView>(R.id.tv_selectable6)
        tvSelectable6.setOnClickListener(TvSelectableClickListener())

        val tvSelectable7 = findViewById<TextView>(R.id.tv_selectable7)
        tvSelectable7.setOnClickListener(TvSelectableClickListener())

        val tvSelectable8 = findViewById<TextView>(R.id.tv_selectable8)
        tvSelectable8.setOnClickListener(TvSelectableClickListener())

        val tvSelectable9 = findViewById<TextView>(R.id.tv_selectable9)
        tvSelectable9.setOnClickListener(TvSelectableClickListener())

        val btGo = findViewById<Button>(R.id.bt_go)
        btGo.setOnClickListener(BtOnClickListener())

        val btCheck = findViewById<Button>(R.id.bt_check)
        btCheck.setOnClickListener(BtCheckOnClickListener())

        val btReview = findViewById<Button>(R.id.bt_review)
        btReview.setOnClickListener(BtReviewOnClickListener())
        Log.i("ohtani ","0200" )

    }
    private fun initGame()
    {
        _intTotalHitCount = 0
        _intTotalBlockCount = 0
        val tvHitRatio = findViewById<TextView>(R.id.tv_hit_ratio)
        tvHitRatio.setText("0")

        val db = _helper.writableDatabase
        // ブロック数の取得
        val mapLevelInformation: MutableMap<String, String> = getLevelInfomation(db,_strLevelId)
        val strNumberOfBlock = mapLevelInformation.get("number_of_block")
        if(strNumberOfBlock!=null){
            _intNumberOfBlock=strNumberOfBlock.toInt()
        }
        // 表現数の取得
        val strNumberOfExpressions=mapLevelInformation.get("number_of_expressions")
        if(strNumberOfExpressions!=null){
            _intNumberOfExpressions=strNumberOfExpressions.toInt()
        }
        //難易度の取得
        _strLevelOfDifficulty=mapLevelInformation.get("level_of_difficulty").toString()

        //Levelの初期表示
        val tvLevelId = findViewById<TextView>(R.id.tv_level_id);
        tvLevelId.setText(_strLevelId)

        setSelectableContentsArray()
        //spinnerの初期表示と選択時の処理
        layoutBlock()

        //ボタンの制御処理
        buttonClickableDefault()
    }
    /*
     defaultとreviewボタンクリック後
     */
    private fun buttonClickableDefault(){
        val btGo = findViewById<Button>(R.id.bt_go)
        btGo.setEnabled(true)
        val btCheck = findViewById<Button>(R.id.bt_check)
        btCheck.setEnabled(false)
        val btReview = findViewById<Button>(R.id.bt_review)
        btReview.setEnabled(false)
    }
    /*
     goボタンクリック後
     */
    private fun buttonClickableGoAfter(){
        val btGo = findViewById<Button>(R.id.bt_go)
        btGo.setEnabled(false)
        val btCheck = findViewById<Button>(R.id.bt_check)
        btCheck.setEnabled(true)
        val btReview = findViewById<Button>(R.id.bt_review)
        btReview.setEnabled(false)
    }
    /*
     checkボタンクリック後
     */
    private fun buttonClickableCheckAfter() {
        val btGo = findViewById<Button>(R.id.bt_go)
        btGo.setEnabled(true)
        val btCheck = findViewById<Button>(R.id.bt_check)
        btCheck.setEnabled(false)
        val btReview = findViewById<Button>(R.id.bt_review)
        btReview.setEnabled(true)
    }
    private fun updateMyLevel(db: SQLiteDatabase, strLevelId:String){
        val sql ="Update my_level set level_id = $strLevelId where type_of_game='$_strTypeOfGame'"
        db.execSQL(sql)
    }
    private fun getLevelInfomation(db: SQLiteDatabase, strLevelId:String):MutableMap<String,String> {
        val sql = "SELECT number_of_block,number_of_expressions,level_of_difficulty,level_no FROM level  where id=$strLevelId;"
        //SQLの実行。
        val cursor = db.rawQuery(sql, null)
        var intRecordIdx: Int
        var mapMyLevel : MutableMap<String, String> = mutableMapOf()

        if (cursor.moveToNext()) {
            intRecordIdx = cursor.getColumnIndex("number_of_block")
            mapMyLevel=mutableMapOf( "number_of_block" to cursor.getString(intRecordIdx).toString() )
            intRecordIdx = cursor.getColumnIndex("number_of_expressions")
            mapMyLevel.put("number_of_expressions" , cursor.getString(intRecordIdx).toString()   )
            intRecordIdx = cursor.getColumnIndex("level_of_difficulty")
            mapMyLevel.put("level_of_difficulty" , cursor.getString(intRecordIdx).toString()   )
            intRecordIdx = cursor.getColumnIndex("level_no")
            mapMyLevel.put("level_no" , cursor.getString(intRecordIdx).toString()   )
        }
        return mapMyLevel
    }

    private inner class BtReviewOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            var res = resources
            //typeスピナの状態によって以下へそれぞれのメッセージを表示する
            //tv_ok
            //tv_ng
            val tvOk = findViewById<TextView>(R.id.tv_ok)
            val tvNg = findViewById<TextView>(R.id.tv_ng)
/*
            val spContentsType = findViewById<Spinner>(R.id.sp_contents_type)
            val spContentsTypeIndex  =spContentsType.selectedItemPosition
*/
            val spContentsTypeIndex  =_strTypeOfGame.toInt()
            var colorId:Int
            var viewColor : ColorDrawable

            //arrayBlockIdListでループしながら
            //textと背景色を取得する
            //取得したtextと背景色がそれぞれ以下と合致しているかどうかみる
            //arrayBlockTextList
            //arrayBlockBgcList
            var tvBlock :TextView
            var index02 :Int
            index02 = 0

            for(index01 in arrayBlockIdList) {
                tvBlock = findViewById(index01)
                when(spContentsTypeIndex.toInt()) {
                    //数字,アルファベット,記号
                    getString(R.string.type_of_game_number).toInt(), getString(R.string.type_of_game_alphabet).toInt(), getString(R.string.type_of_game_symbol).toInt() -> tvBlock.text = arrayBlockTextList.get(index02)
                    //色
                    getString(R.string.type_of_game_color).toInt() -> tvBlock.setBackgroundColor(arrayBlockBgcList.get(index02))
                }
                index02 = index02 + 1
            }
            buttonClickableDefault()
        }
    }

    private inner class BtCheckOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            var res = resources
            //typeスピナの状態によって以下へそれぞれのメッセージを表示する
            //tv_ok
            //tv_ng
            val tvOk = findViewById<TextView>(R.id.tv_ok)
            val tvNg = findViewById<TextView>(R.id.tv_ng)
/*
            val spContentsType = findViewById<Spinner>(R.id.sp_contents_type)
            val spContentsTypeIndex  =spContentsType.selectedItemPosition
*/
            val spContentsTypeIndex  =_strTypeOfGame
            var colorId:Int
            var viewColor : ColorDrawable

            var intHitCount = 0

            when(spContentsTypeIndex.toInt()){
                //数字,アルファベット,記号
                getString(R.string.type_of_game_number).toInt(),getString(R.string.type_of_game_alphabet).toInt(),getString(R.string.type_of_game_symbol).toInt() ->{
                    //tvOk
                    tvOk.text=getString(R.string.ok)
                    tvOk.setBackgroundColor(Color.parseColor(getString(R.string.ok_color_back)))
                    //tvNg
                    tvNg.text=getString(R.string.ng)
                    tvNg.setBackgroundColor(Color.parseColor(getString(R.string.ng_color_back)))
                }
                //色
                getString(R.string.type_of_game_color).toInt() ->{
                    //tvOk
                    /*
                    tvOk.text=getString(R.string.ok)
                    tvOk.setBackgroundColor(Color.parseColor(getString(R.string.back_color)))
                    //tvNg
                    tvNg.text=getString(R.string.ng)
                    tvNg.setBackgroundColor(Color.parseColor(getString(R.string.back_color)))
                     */
                }
            }

            //arrayBlockIdListでループしながら
            //textと背景色を取得する
            //取得したtextと背景色がそれぞれ以下と合致しているかどうかみる
            //arrayBlockTextList
            //arrayBlockBgcList
            var tvBlock :TextView
            var index02 :Int
            index02 = 0

            for(index01 in arrayBlockIdList) {
                tvBlock = findViewById(index01)
                when(spContentsTypeIndex.toInt()) {
                    //数字,アルファベット,記号
                    getString(R.string.type_of_game_number).toInt(), getString(R.string.type_of_game_alphabet).toInt(), getString(R.string.type_of_game_symbol).toInt() -> {
                        //tvOk
                        if((arrayBlockTextList.get(index02))==tvBlock.text){
                            intHitCount +=1
                            tvBlock.setBackgroundColor(Color.parseColor(getString(R.string.ok_color_back)))
                        }else{
                            tvBlock.setBackgroundColor(Color.parseColor(getString(R.string.ng_color_back)))
                        }
                        //tvBlock.setBackgroundResource(R.drawable.text_view_style)
                    }
                    //色
                    getString(R.string.type_of_game_color).toInt() -> {
                        viewColor = tvBlock.background as ColorDrawable
                        colorId = viewColor.color
                        if(arrayBlockBgcList.get(index02)==colorId){
                            tvBlock.text = getString(R.string.ok)
                            intHitCount +=1
                            //tvBlock.setBackgroundColor(Color.parseColor(getString(R.string.ok_color_back)))
                        }
                        else{
                            tvBlock.text = getString(R.string.ng)
                            //tvBlock.setBackgroundColor(Color.parseColor(getString(R.string.ng_color_back)))
                        }
                    }
                }
                index02 = index02 + 1
            }

            //正解率を表示する
            _intTotalHitCount = _intTotalHitCount + intHitCount
            val tvHitRatio = findViewById<TextView>(R.id.tv_hit_ratio)
            Log.i("ohtani 001",_intTotalHitCount.toString())
            Log.i("ohtani 002",_intTotalBlockCount.toString())
            val dblRatio = _intTotalHitCount*100 / _intTotalBlockCount*100
            val dblRatio100 =dblRatio / 100
            tvHitRatio.setText(dblRatio100.toString())

            //level判定を行う
            if(_intCountOfTry==getString(R.string.tries_of_one_game).toInt())
            {
                _intCountOfTry = 1
                var intLevelId = _strLevelId.toInt()
                if(dblRatio100>=80){
                    //レベルアップ
                    intLevelId = intLevelId + 1
                    val ts = Toast.makeText(applicationContext, "レベルが"+intLevelId.toString()+"へ上がりました", Toast.LENGTH_SHORT)
                    ts.show()

                } else if(dblRatio100<40) {
                    //レベルダウン
                    if(intLevelId > 1)
                        intLevelId = intLevelId -1
                        val ts = Toast.makeText(applicationContext, "レベルが"+intLevelId.toString()+"へ上がりました", Toast.LENGTH_SHORT)
                        ts.show()
                } else{
                    val ts = Toast.makeText(applicationContext, "レベルは"+intLevelId.toString()+"を維持されました", Toast.LENGTH_SHORT)
                    ts.show()
                }
                _strLevelId = intLevelId.toString()
                _intTotalHitCount = 0
                _intTotalBlockCount = 0

                //Levelデータの更新
                val db = _helper.writableDatabase
                updateMyLevel(db,_strLevelId)
            } else {
                //試行回数をアップデートする
                countOfTryPlus()
            }
            //ボタン制御する
            buttonClickableCheckAfter()
        }
    }
    private inner class BtOnClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            //Level表示の更新
            val tvLevelId = findViewById<TextView>(R.id.tv_level_id);
            tvLevelId.setText(_strLevelId)
            val tvTries = findViewById<TextView>(R.id.tv_tries)
            tvTries.setText(_intCountOfTry.toString())

            if(_intCountOfTry == 1)
            {
                initGame()
            }

            val res = resources
            //val ts = Toast.makeText(applicationContext, "タップされました。", Toast.LENGTH_SHORT)
            //ts.show()
            // tv_okとtv_ngを初期化する
            val tvOk = findViewById<TextView>(R.id.tv_ok)
            val tvNg = findViewById<TextView>(R.id.tv_ng)
            tvOk.text=""
            tvOk.setBackgroundColor(Color.parseColor(getString(R.string.back_white)))
            //tvNg
            tvNg.text=""
            tvNg.setBackgroundColor(Color.parseColor(getString(R.string.back_white)))

/*
            val spDisplayInterval = findViewById<Spinner>(R.id.sp_display_interval)
            val mmSec =spDisplayInterval.selectedItem
            val intMmSec : Int = Integer.parseInt(mmSec.toString())
*/
            val array:Array<String> = res.getStringArray(R.array.sp_display_interval)
            val intMmSec = array.get(_intCountOfTry-1 ).toInt()
            Log.i("ohtani _intCountOfTry",_intCountOfTry.toString())
            Log.i("ohtani intMmSec",intMmSec.toString())

            var tvBlock :TextView
            var intRandom  :Int

/*
            val spContentsType = findViewById<Spinner>(R.id.sp_contents_type)
            val spContentsTypeIndex  =spContentsType.selectedItemPosition

 */
            val spContentsTypeIndex =_strTypeOfGame

/*
            val spContentsCount = findViewById<Spinner>(R.id.sp_contents_count)
            val spContentsCountValue  =spContentsCount.selectedItem

 */
            val spContentsCountValue  =_intNumberOfExpressions.toString()

            val tv_selectable_name = "tv_selectable"
            var tv_selectable :TextView
            var viewId :Int
            var colorId:Int
            var viewColor : ColorDrawable
            var index02 : Int = 0


            for(index01 in arrayBlockIdList) {
                tvBlock = findViewById(index01)
                intRandom = Random.nextInt(spContentsCountValue.toString().toInt())

                viewId = res.getIdentifier(tv_selectable_name + intRandom.toString(), "id", packageName)
                tv_selectable = findViewById(viewId)
                viewColor = tv_selectable.background as ColorDrawable
                colorId = viewColor.color

                when(spContentsTypeIndex.toInt()){
                    //数字,アルファベット,記号
                    getString(R.string.type_of_game_number).toInt(),getString(R.string.type_of_game_alphabet).toInt(),getString(R.string.type_of_game_symbol).toInt() -> {
                        tvBlock.setBackgroundColor(Color.parseColor(getString(R.string.back_white)))
                        tvBlock.text = tv_selectable.text
                        tvBlock.setBackgroundResource(R.drawable.text_view_style)
                    }
                    getString(R.string.type_of_game_color).toInt() ->{
                            tvBlock.text = ""
                            tvBlock.setBackgroundColor(colorId)
                        }
                }
                arrayBlockTextList.set(index02,tvBlock.text.toString())
                arrayBlockBgcList.set(index02,colorId)
                index02 = index02 + 1
            }

            Handler().postDelayed(Runnable {
                // ここが指定時間後に呼ばれる
                //myControl.setEnabled(true)
                // val ts = Toast.makeText(applicationContext, "時間経過しました。", Toast.LENGTH_SHORT)
                // ts.show()
                for(index01 in arrayBlockIdList) {
                    tvBlock = findViewById(index01)
                    tvBlock.text  = ""
                    //tvBlock.setBackgroundColor(Color.parseColor(getString(R.string.back_color)))
                    //tvBlock.setBackgroundResource(R.drawable.frame_style)
                    tvBlock.setBackgroundResource(R.drawable.text_view_style)

                }
            }, intMmSec.toLong())  // 3000ms待機

            _intTotalBlockCount = _intTotalBlockCount + _intNumberOfBlock
            buttonClickableGoAfter()
        }
    }
    private inner class BlockOnClickListener : View.OnClickListener{
        override fun onClick(view:View) {

            val tvSelected = findViewById<TextView>(R.id.tv_selected)
            val viewColor = tvSelected.background as ColorDrawable
            val colorId = viewColor.color

            val tvBlock = findViewById<TextView>(view.id)
            tvBlock.setBackgroundColor(colorId)
            tvBlock.text = tvSelected.text
            // tvBlock.setBackgroundResource(R.drawable.frame_style)
        }
    }

    private inner class TvSelectableClickListener : View.OnClickListener{
        override fun onClick(view:View) {

            val tvSelectable = findViewById<TextView>(view.id)
            val viewColor = view.background as ColorDrawable
            val colorId = viewColor.color

            val tvSelected = findViewById<TextView>(R.id.tv_selected)
            tvSelected.setBackgroundColor(colorId)
            tvSelected.text = tvSelectable.text
            //tvSelected.setBackgroundResource(R.drawable.frame_style)
        }
    }
    private inner class SpinnerCntItemSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            layoutBlock()
        }
        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }
    private inner class SpinnerTypeItemSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            setSelectableContentsArray()
        }
        override fun onNothingSelected(parent: AdapterView<*>) {

        }
    }
    //contents_typeの選択状態から、要素を取得する
    private fun setSelectableContentsArray() {
        val res = resources
        /*
        val sp_contents_type = findViewById<Spinner>(R.id.sp_contents_type)
        val sp_contents_type_index  =sp_contents_type.selectedItemPosition
         */
        val sp_contents_type_index = _strTypeOfGame.toInt()
        var array:Array<String> = res.getStringArray(R.array.ary_contents_number)
        var tvSelected = findViewById<TextView>(R.id.tv_selected)

        Log.i("ohtani 049",sp_contents_type_index.toString())
        when(sp_contents_type_index){
            //数字
            getString(R.string.type_of_game_number).toInt() -> array = res.getStringArray(R.array.ary_contents_number)
            //アルファベット
            getString(R.string.type_of_game_alphabet).toInt() -> array = res.getStringArray(R.array.ary_contents_alphabet)
            //色
            getString(R.string.type_of_game_color).toInt() -> array = res.getStringArray(R.array.ary_contents_color)
            //記号
            getString(R.string.type_of_game_symbol).toInt() -> array = res.getStringArray(R.array.ary_contents_symbol)
        }

/*
        for(index02 in array){
            Log.i("index02",index02)
        }
*/
        val tv_selectable_name = "tv_selectable"
        var tv_selectable :TextView
        var viewId :Int
        var viewColor : ColorDrawable
        var colorId :Int

        for(index01 in array.indices){
            viewId = res.getIdentifier(tv_selectable_name+index01.toString(), "id", packageName)
            tv_selectable = findViewById<TextView>(viewId)

            //色の場合は背景色を設定する
            if(sp_contents_type_index == getString(R.string.type_of_game_color).toInt()) {
               tv_selectable.text= ""
               tv_selectable.setBackgroundColor(Color.parseColor(array[index01]))

            }
            else {
               tv_selectable.text= array[index01]
               //背景色を設定する
               tv_selectable.setBackgroundColor(Color.parseColor(getString(R.string.back_white)))
           }
            //tv_selectable.setBackgroundResource(R.drawable.text_view_style)
            //現在選択中のtextViewに値を設定する
            if(index01 == 0 ){
                tvSelected.text = tv_selectable.text
                if(sp_contents_type_index==getString(R.string.type_of_game_color).toInt()){
                    tvSelected.setBackgroundColor(Color.parseColor(array[index01]))
                }
                else{
                    tvSelected.setBackgroundColor(Color.parseColor(getString(R.string.back_white)))
                }
                //tvSelected.setBackgroundResource(R.drawable.text_view_style)
            }
        }
    }

    private fun getTextView( id: Int):TextView {
/*
        val spBlockCount = findViewById<Spinner>(R.id.sp_block_count)
        val blockCount =spBlockCount.selectedItem.toString()

 */
        val blockCount = _intNumberOfBlock.toString()


        val textView = TextView(this)
        //idを設定
        textView.id = id
        arraySetting(id,"",0)

        //marginを得るために入れてみたが意味がないコード
        val layoutParams = LinearLayout.LayoutParams(200, 200)
        layoutParams.setMargins(300, 300, 0, 0)
        textView.layoutParams = layoutParams
        //ここまで

        textView.gravity = Gravity.CENTER

        //何列にするか決める
        when(blockCount.toInt()) {
            in 3..16 ->{
                textView.setWidth(250)
                textView.setHeight(250)
                textView.setTextSize(1,35.toFloat())
            }
            in 17..25 ->{
                textView.setWidth(200)
                textView.setHeight(200)
                textView.setTextSize(1,30.toFloat())
            }
        }
//        textView.setBackgroundColor(Color.parseColor(getString(R.string.back_color)))
        textView.setBackgroundResource(R.drawable.text_view_style)

        //textView.background =


            textView.setOnClickListener(BlockOnClickListener())

        return textView
    }
    private  fun getLinearLayout():LinearLayout{

        val linearLayout = LinearLayout(this)
        // 垂直方向
        linearLayout.orientation = LinearLayout.HORIZONTAL
        // レイアウト中央寄せ
        linearLayout.gravity = Gravity.CENTER

        //linearLayout.transitionName = "lo_row_01"
        return linearLayout
    }
    private fun layoutBlock() {
        arrayInit()
        //親layoutの習得
        val loDisplayBlockDomain = findViewById<LinearLayout>(R.id.lo_display_block_domain)
/*
        //spinnerの取得
        val spBlockCount = findViewById<Spinner>(R.id.sp_block_count)
        //val sp_block_count_index  =sp_block_count.selectedItemPosition
        val blockCount =spBlockCount.selectedItem.toString()

 */

        val blockCount = _intNumberOfBlock.toString()
        Log.i("ohtani ",_intNumberOfBlock.toString() )
        //配列の設定
        var colCount = 0

        //最初にlayout内の子要素を全て削除する
        loDisplayBlockDomain.removeAllViewsInLayout()

        //何列にするか決める
        when(blockCount.toInt()) {
            in 3..6 ->  colCount = 2
            in 7..9 ->  colCount = 3
            in 10..16 -> colCount = 4
            in 17..25 -> colCount = 5
        }

        //列数が決まったら行数が決まる
        var rowCount = blockCount.toInt() / colCount
        //列数と行数が決まったら最終行に表示するブロックの数は余りとして算出できる
        val modCount = blockCount.toInt() % colCount

        if(modCount> 0){
            rowCount = rowCount + 1
        }

        var linearLayout:LinearLayout
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(5, 5, 0, 0)


        var textView : TextView
        var intTvCnt = 0

        //LinearLayoutの追加
        for(index01 in 1..rowCount) {
            //LinearLayoutの追加
            linearLayout =getLinearLayout()
            loDisplayBlockDomain.addView(linearLayout,
                LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )

            if(index01 == rowCount)
            {
                if(modCount == 0){
                    for(index02 in 1.. colCount) {
                        intTvCnt = intTvCnt + 1
                        //TextViewの追加
                        textView = getTextView(intTvCnt)
                        linearLayout.addView(
                            textView,
                            layoutParams
                        )
                    }
                }
                else {
                    for (index02 in 1..modCount) {
                        intTvCnt = intTvCnt + 1
                        //TextViewの追加
                        textView = getTextView(intTvCnt)
                        linearLayout.addView(
                            textView,
                            layoutParams
                        )
                    }
                }
            } else{
                for(index02 in 1.. colCount) {
                    intTvCnt = intTvCnt + 1
                    //TextViewの追加
                    textView = getTextView(intTvCnt)
                    linearLayout.addView(
                        textView,
                        layoutParams
                    )
                }
            }
        }
     }
}
