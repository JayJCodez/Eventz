package Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri


class DBHelper(context: Context) : SQLiteOpenHelper(context, "Userdata", null, 4) {

    private lateinit var cursor: Cursor

    // Define your table creation SQL statements
    private val CREATE_TABLE_USERDATA = """
            CREATE TABLE Userdata (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT,
                firstname TEXT,
                lastname TEXT,
                password TEXT
            )
        """

    private val  CREATE_TABLE_EVENTS = """
            CREATE TABLE Events (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                event_id TEXT,
                event_name TEXT,
                event_type TEXT,
                event_start TEXT,
                event_finish TEXT,
                event_info TEXT,
                image_uri TEXT NOT NULL
            )
        """


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_EVENTS)
        db?.execSQL(CREATE_TABLE_USERDATA)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Userdata")
        db?.execSQL("DROP TABLE IF EXISTS Events")
        onCreate(db)
    }

    fun inserdata(username: String, firstname : String, lastname : String, password : String): Boolean {

         val p0 = this.writableDatabase
         val cv = ContentValues()
        cv.put("username", username)
        cv.put("firstname", firstname)
        cv.put("lastname", lastname)
        cv.put("password", password)
        val result = p0.insert("Userdata", null, cv)


        if (result.toInt() == -1){
            return false
        }else {
            return true
        }
        }

    fun insert_event(eventname: String, eventtype: String, eventstart: String, event_info : String ,eventfinish: String, imageUri: Uri): Boolean{
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("event_name", eventname)
        cv.put("event_type", eventtype)
        cv.put("event_start", eventstart)
        cv.put("event_finish", eventfinish)
        cv.put("event_info", event_info)
        cv.put("image_uri", imageUri.toString())

        val result = p0.insert("Events", null, cv)
        p0.close()

        if (result.toInt() == -1){
            return false
        }else {
            return true
        }

    }

    fun checkuserpass(username: String, password: String): Boolean {
        val p0 = this.writableDatabase
        val query = "SELECT * FROM Userdata WHERE username=? AND password=?"
        val cursor = p0.rawQuery(query, arrayOf(username, password))


//        val userexists = cursor.moveToFirst()
//
//        if (userexists == true){
//            cursor.close()
//            return true
//        }



        if (cursor.count > 0){
            cursor.close()
            return true

        }else {
            cursor.close()
            return false
        }
    }

    fun getUserData(username: String): Cursor{
        val p0 = this.readableDatabase
        val query = "SELECT * FROM Userdata WHERE username = '$username'"


        if (p0 != null){
            cursor = p0.rawQuery(query, null)
        }
        return cursor
    }


    fun readAlldata(query: String): Cursor {
        val p0 = this.readableDatabase


        if (p0 != null){
            cursor = p0.rawQuery(query, null)
        }
        return cursor

    }

//    private fun insertImageUriIntoDatabase(imageUri: Uri) {
//        val contentValues = ContentValues().apply {
//            put("image_uri", imageUri.toString()) // Store URI as string
//            // You can add more columns if needed
//        }
//        val db = this.writableDatabase
//        val newRowId = db.insert("images_table", null, contentValues)
//        db.close()
//
//        if (newRowId != -1L) {
//            // Successfully inserted
//        } else {
//            // Insertion failed
//        }
}