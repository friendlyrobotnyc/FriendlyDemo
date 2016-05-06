package nyc.friendlyrobot.demo.data.local

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import nyc.friendlyrobot.demo.data.model.Post
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Database
@Inject
constructor(context: Application) : SQLiteOpenHelper(context, "db", null, Database.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Post.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //used for upgrading to new data model.
    }

    companion object {
        private val DATABASE_VERSION = 1
    }


}
