package com.aigeniusx.helpers

class DatabaseManager {

    object FeedReaderContract {
        // Table for user information
        object UserEntry : BaseColumns {
            const val TABLE_NAME = "userStorage"
            const val COLUMN_USER_ID = "userID"
            const val COLUMN_USER_NAME = "userName"
            const val COLUMN_USER_EMAIL = "userEmail"
            const val COLUMN_USER_PHONE = "userPhone"
            const val COLUMN_USER_PASSWORD = "userPassword"
            const val COLUMN_USER_COUNTRY = "userCountry"
        }

        // Table for user activity data
        object ActivityEntry : BaseColumns {
            const val TABLE_NAME = "activityStorage"
            const val COLUMN_ACTIVITY_ID = "activityID"
            const val COLUMN_USER_ID = "userID"  // This links to the UserEntry table's userID
            // Define other columns for activity data
        }

        // Table for temporary data storage
        object TemporaryDataEntry : BaseColumns {
            const val TABLE_NAME = "temporaryDataStorage"
            const val COLUMN_TEMP_DATA_ID = "tempDataID"
            const val COLUMN_USER_ID = "userID"  // This links to the UserEntry table's userID
            // Define other columns for temporary data
        }

        // Table for permanent data storage
        object PermanentDataEntry : BaseColumns {
            const val TABLE_NAME = "permanentDataStorage"
            const val COLUMN_PERM_DATA_ID = "permDataID"
            const val COLUMN_USER_ID = "userID"  // This links to the UserEntry table's userID
            // Define other columns for permanent data
        }
    }

    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${FeedEntry.COLUMN_NAME_TITLE} TEXT," +
                "${FeedEntry.COLUMN_NAME_SUBTITLE} TEXT)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"


    class FeedReaderDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }
        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }
        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "FeedReader.db"
        }
    }




}