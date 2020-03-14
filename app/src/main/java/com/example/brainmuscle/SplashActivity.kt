package com.example.brainmuscle

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class SplashActivity : AppCompatActivity() {

    /**
     * データベースヘルパーオブジェクト。
     */

    private val _helper = DatabaseHelper(this@SplashActivity)

    private val handler = Handler()
    private val runnable = Runnable {

        // MainActivityへ遷移させる
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)

        // ここでfinish()を呼ばないとMainActivityでAndroidの戻るボタンを押すとスプラッシュ画面に戻ってしまう
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //データの初期化処理
        Log.i("ohtani","splash on create")
        initialDB()
        // スプラッシュ表示1000ms(1秒)後にrunnableを呼んでMeinActivityへ遷移させる
        handler.postDelayed(runnable, 1000)
    }

    override fun onStop() {
        super.onStop()

        // スプラッシュ画面中にアプリを落とした時にはrunnableが呼ばれないようにする
        // これがないとアプリを消した後にまた表示される
        handler.removeCallbacks(runnable)
    }
    private fun isTableExist(db: SQLiteDatabase, tableName:String):Boolean {
        val sql = "SELECT COUNT(*) as table_count FROM sqlite_master where type='table' and name='$tableName';"
        //SQLの実行。
        val cursor = db.rawQuery(sql, null)
        val intTableCountId: Int
        val intTableCount: Int

        //Log.i("ohtani ","before" )
        if (cursor.moveToNext()) {
            intTableCountId = cursor.getColumnIndex("table_count")
            intTableCount = cursor.getString(intTableCountId).toInt()
            if (intTableCount == 0) {
                return false
            }
        }
        return true
    }
    private fun isRecordExist(db: SQLiteDatabase, tableName:String,isIntPrimaryCol:Boolean,primaryColName:String,primaryColValue:String):Boolean {
        var sql :String

        when {
            isIntPrimaryCol -> {
                sql ="SELECT COUNT(*) as record_count FROM $tableName where $primaryColName=$primaryColValue;"
            }
            else -> {
                sql ="SELECT COUNT(*) as record_count FROM $tableName where $primaryColName='$primaryColValue';"
            }
        }

        //SQLの実行。
        val cursor = db.rawQuery(sql, null)
        val intTableCountId: Int
        val intTableCount: Int

        //Log.i("ohtani ","before" )
        if (cursor.moveToNext()) {
            intTableCountId = cursor.getColumnIndex("record_count")
            intTableCount = cursor.getString(intTableCountId).toInt()
            if (intTableCount == 0) {
                return false
            }
        }
        return true
    }

    private fun initialDB()
    {
        val db = _helper.writableDatabase

        var sb = StringBuilder()
        //レベルマスタの作成
        //主キーによる検索SQL文字列の用意。
        var sql = ""
        //SQLの実行。
        if(!isTableExist(db,"level")) {
                sb.append("CREATE TABLE level (")
                sb.append("id INTEGER PRIMARY KEY,")
                sb.append("level_no TEXT,")
                sb.append("level_name TEXT,")
                sb.append("level_explain TEXT,")
                sb.append("number_of_block INTEGER,")
                sb.append("number_of_expressions INTEGER,")
                sb.append("level_of_difficulty INTEGER,")
                sb.append("created_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),")
                sb.append("updated_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))")
                sb.append(");")
                sql = sb.toString()
                db.execSQL(sql)
         } else{
                sql = "delete FROM level;"
                db.execSQL(sql)
        }
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(1,'1','1','','3','2','6');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(2,'2','2','','4','2','8');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(3,'3','3','','3','3','9');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(4,'4','4','','5','2','10');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(5,'5','5','','6','2','12');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(6,'6','6','','4','3','12');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(7,'7','7','','3','4','12');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(8,'8','8','','7','2','14');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(9,'9','9','','5','3','15');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(10,'10','10','','3','5','15');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(11,'11','11','','8','2','16');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(12,'12','12','','4','4','16');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(13,'13','13','','9','2','18');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(14,'14','14','','6','3','18');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(15,'15','15','','3','6','18');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(16,'16','16','','10','2','20');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(17,'17','17','','5','4','20');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(18,'18','18','','4','5','20');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(19,'19','19','','7','3','21');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(20,'20','20','','3','7','21');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(21,'21','21','','11','2','22');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(22,'22','22','','12','2','24');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(23,'23','23','','8','3','24');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(24,'24','24','','6','4','24');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(25,'25','25','','4','6','24');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(26,'26','26','','3','8','24');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(27,'27','27','','5','5','25');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(28,'28','28','','13','2','26');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(29,'29','29','','9','3','27');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(30,'30','30','','3','9','27');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(31,'31','31','','14','2','28');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(32,'32','32','','7','4','28');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(33,'33','33','','4','7','28');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(34,'34','34','','15','2','30');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(35,'35','35','','10','3','30');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(36,'36','36','','6','5','30');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(37,'37','37','','5','6','30');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(38,'38','38','','3','10','30');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(39,'39','39','','16','2','32');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(40,'40','40','','8','4','32');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(41,'41','41','','4','8','32');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(42,'42','42','','11','3','33');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(43,'43','43','','17','2','34');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(44,'44','44','','7','5','35');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(45,'45','45','','5','7','35');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(46,'46','46','','18','2','36');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(47,'47','47','','12','3','36');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(48,'48','48','','9','4','36');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(49,'49','49','','6','6','36');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(50,'50','50','','4','9','36');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(51,'51','51','','19','2','38');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(52,'52','52','','13','3','39');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(53,'53','53','','20','2','40');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(54,'54','54','','10','4','40');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(55,'55','55','','8','5','40');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(56,'56','56','','5','8','40');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(57,'57','57','','4','10','40');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(58,'58','58','','21','2','42');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(59,'59','59','','14','3','42');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(60,'60','60','','7','6','42');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(61,'61','61','','6','7','42');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(62,'62','62','','22','2','44');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(63,'63','63','','11','4','44');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(64,'64','64','','15','3','45');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(65,'65','65','','9','5','45');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(66,'66','66','','5','9','45');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(67,'67','67','','23','2','46');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(68,'68','68','','24','2','48');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(69,'69','69','','16','3','48');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(70,'70','70','','12','4','48');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(71,'71','71','','8','6','48');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(72,'72','72','','6','8','48');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(73,'73','73','','7','7','49');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(74,'74','74','','25','2','50');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(75,'75','75','','10','5','50');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(76,'76','76','','5','10','50');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(77,'77','77','','17','3','51');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(78,'78','78','','13','4','52');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(79,'79','79','','18','3','54');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(80,'80','80','','9','6','54');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(81,'81','81','','6','9','54');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(82,'82','82','','11','5','55');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(83,'83','83','','14','4','56');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(84,'84','84','','8','7','56');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(85,'85','85','','7','8','56');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(86,'86','86','','19','3','57');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(87,'87','87','','20','3','60');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(88,'88','88','','15','4','60');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(89,'89','89','','12','5','60');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(90,'90','90','','10','6','60');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(91,'91','91','','6','10','60');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(92,'92','92','','21','3','63');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(93,'93','93','','9','7','63');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(94,'94','94','','7','9','63');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(95,'95','95','','16','4','64');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(96,'96','96','','8','8','64');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(97,'97','97','','13','5','65');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(98,'98','98','','22','3','66');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(99,'99','99','','11','6','66');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(100,'100','100','','17','4','68');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(101,'101','101','','23','3','69');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(102,'102','102','','14','5','70');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(103,'103','103','','10','7','70');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(104,'104','104','','7','10','70');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(105,'105','105','','24','3','72');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(106,'106','106','','18','4','72');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(107,'107','107','','12','6','72');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(108,'108','108','','9','8','72');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(109,'109','109','','8','9','72');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(110,'110','110','','25','3','75');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(111,'111','111','','15','5','75');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(112,'112','112','','19','4','76');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(113,'113','113','','11','7','77');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(114,'114','114','','13','6','78');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(115,'115','115','','20','4','80');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(116,'116','116','','16','5','80');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(117,'117','117','','10','8','80');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(118,'118','118','','8','10','80');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(119,'119','119','','9','9','81');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(120,'120','120','','21','4','84');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(121,'121','121','','14','6','84');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(122,'122','122','','12','7','84');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(123,'123','123','','17','5','85');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(124,'124','124','','22','4','88');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(125,'125','125','','11','8','88');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(126,'126','126','','18','5','90');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(127,'127','127','','15','6','90');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(128,'128','128','','10','9','90');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(129,'129','129','','9','10','90');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(130,'130','130','','13','7','91');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(131,'131','131','','23','4','92');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(132,'132','132','','19','5','95');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(133,'133','133','','24','4','96');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(134,'134','134','','16','6','96');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(135,'135','135','','12','8','96');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(136,'136','136','','14','7','98');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(137,'137','137','','11','9','99');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(138,'138','138','','25','4','100');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(139,'139','139','','20','5','100');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(140,'140','140','','10','10','100');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(141,'141','141','','17','6','102');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(142,'142','142','','13','8','104');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(143,'143','143','','21','5','105');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(144,'144','144','','15','7','105');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(145,'145','145','','18','6','108');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(146,'146','146','','12','9','108');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(147,'147','147','','22','5','110');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(148,'148','148','','11','10','110');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(149,'149','149','','16','7','112');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(150,'150','150','','14','8','112');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(151,'151','151','','19','6','114');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(152,'152','152','','23','5','115');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(153,'153','153','','13','9','117');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(154,'154','154','','17','7','119');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(155,'155','155','','24','5','120');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(156,'156','156','','20','6','120');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(157,'157','157','','15','8','120');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(158,'158','158','','12','10','120');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(159,'159','159','','25','5','125');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(160,'160','160','','21','6','126');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(161,'161','161','','18','7','126');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(162,'162','162','','14','9','126');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(163,'163','163','','16','8','128');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(164,'164','164','','13','10','130');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(165,'165','165','','22','6','132');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(166,'166','166','','19','7','133');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(167,'167','167','','15','9','135');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(168,'168','168','','17','8','136');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(169,'169','169','','23','6','138');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(170,'170','170','','20','7','140');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(171,'171','171','','14','10','140');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(172,'172','172','','24','6','144');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(173,'173','173','','18','8','144');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(174,'174','174','','16','9','144');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(175,'175','175','','21','7','147');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(176,'176','176','','25','6','150');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(177,'177','177','','15','10','150');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(178,'178','178','','19','8','152');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(179,'179','179','','17','9','153');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(180,'180','180','','22','7','154');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(181,'181','181','','20','8','160');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(182,'182','182','','16','10','160');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(183,'183','183','','23','7','161');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(184,'184','184','','18','9','162');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(185,'185','185','','24','7','168');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(186,'186','186','','21','8','168');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(187,'187','187','','17','10','170');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(188,'188','188','','19','9','171');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(189,'189','189','','25','7','175');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(190,'190','190','','22','8','176');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(191,'191','191','','20','9','180');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(192,'192','192','','18','10','180');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(193,'193','193','','23','8','184');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(194,'194','194','','21','9','189');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(195,'195','195','','19','10','190');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(196,'196','196','','24','8','192');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(197,'197','197','','22','9','198');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(198,'198','198','','25','8','200');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(199,'199','199','','20','10','200');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(200,'200','200','','23','9','207');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(201,'201','201','','21','10','210');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(202,'202','202','','24','9','216');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(203,'203','203','','22','10','220');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(204,'204','204','','25','9','225');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(205,'205','205','','23','10','230');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(206,'206','206','','24','10','240');"
        db.execSQL(sql)
        sql = "insert into level(id,level_no,level_name,level_explain,number_of_block,number_of_expressions,level_of_difficulty) values(207,'207','207','','25','10','250');"
        db.execSQL(sql)
        //現行レベルの作成
        if(!isTableExist(db,"my_level")) {
            sb.append("CREATE TABLE my_level (")
            sb.append("type_of_game TEXT,")
            sb.append("level_id INTEGER,")
            sb.append("latest_hit_latio REAL,")
            sb.append("created_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),")
            sb.append("updated_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))")
            sb.append(");")
            sql = sb.toString()
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_level",false,"type_of_game","1")) {
            sql = "insert into my_level(type_of_game,level_id,latest_hit_latio) values('1',1,'0');"
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_level",false,"type_of_game","2")) {
            sql = "insert into my_level(type_of_game,level_id,latest_hit_latio) values('2',1,'0');"
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_level",false,"type_of_game","3")) {
            sql = "insert into my_level(type_of_game,level_id,latest_hit_latio) values('3',1,'0');"
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_level",false,"type_of_game","4")) {
            sql = "insert into my_level(type_of_game,level_id,latest_hit_latio) values('4',1,'0');"
            db.execSQL(sql)
        }

        //マイレコードの作成
        if(!isTableExist(db,"my_records")) {
            //マイレコードの作成
            sb.append("CREATE TABLE my_records (")
            sb.append("id INTEGER PRIMARY KEY,")
            sb.append("level_id INTEGER,")
            sb.append("type_of_game TEXT,")
            sb.append("hit_ratio REAL,")
            sb.append("created_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),")
            sb.append("updated_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))")
            sb.append(");")
            sql = sb.toString()
            db.execSQL(sql)
        }
        //マイベストの作成
        if(!isTableExist(db,"my_best")) {
            sb.append("CREATE TABLE my_best (")
            sb.append("type_of_game TEXT,")
            sb.append("level_id INTEGER,")
            sb.append("level_no TEXT,")
            sb.append("hit_ratio REAL,")
            sb.append("user_name TEXT,")
            sb.append("created_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),")
            sb.append("updated_date TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime'))")
            sb.append(");")
            sql = sb.toString()
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_best",false,"type_of_game","1")) {
            sql ="insert into my_best(type_of_game,level_id,level_no,hit_ratio,user_name) values('1',1,'1','0','your name');"
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_best",false,"type_of_game","2")) {
            sql =
                "insert into my_best(type_of_game,level_id,level_no,hit_ratio,user_name) values('2',1,'1','0','your name');"
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_best",false,"type_of_game","3")) {
            sql ="insert into my_best(type_of_game,level_id,level_no,hit_ratio,user_name) values('3',1,'1','0','your name');"
            db.execSQL(sql)
        }
        //レコードが存在していない場合はinsertする
        if(!isRecordExist(db,"my_best",false,"type_of_game","4")) {
            sql =
                "insert into my_best(type_of_game,level_id,level_no,hit_ratio,user_name) values('4',1,'1','0','your name');"
            db.execSQL(sql)
        }
    }

}