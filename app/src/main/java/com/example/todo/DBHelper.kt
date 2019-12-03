package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_TASKS" +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE TEXT, $COLUMN_PRIORITY TEXT, $COLUMN_STATUS TEXT, $COLUMN_DATE TEXT)"
        db!!.execSQL(CREATE_TASKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS)
        onCreate(db!!)
    }

    fun addTask(task: Task) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, task.title)
        values.put(COLUMN_PRIORITY, task.priority)
        values.put(COLUMN_STATUS, task.status)
        values.put(COLUMN_DATE, task.date)


        val db = this.writableDatabase
        val r = db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    fun editTask(taskid: String, editTask: Task) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, editTask.title)
        values.put(COLUMN_PRIORITY, editTask.priority)
        values.put(COLUMN_STATUS, editTask.status)
        values.put(COLUMN_DATE, editTask.date)


        val db = this.writableDatabase
        val r = db.update(TABLE_TASKS, values, "id=" + taskid.toLong(), null)
        //val r = db.insert(TABLE_TASKS, null, values)
        db.close()
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteTask(taskid: Long): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(taskid.toString())
        // Issue SQL statement.
        db.delete(TABLE_TASKS, selection, selectionArgs)

        return true
    }

    fun readAllTasks(): ArrayList<Task> {
        val tasks = ArrayList<Task>()
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_TASKS, null)
        } catch (e: SQLiteException) {
            return tasks
        }

        var taskid: Long
        var title: String
        var priority: String
        var status: String
        var date: String
        if (cursor!!.moveToFirst()) {
            do {
                taskid = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
                title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                priority = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY))
                status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))
                date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))

                tasks.add(Task(taskid, title, priority, status, date))
            } while (cursor.moveToNext())
        }
        return tasks
    }

    companion object {
        val DATABASE_NAME = "tasks.db"
        val DATABASE_VERSION = 1
        val COLUMN_ID = "id"
        val TABLE_TASKS = "tasks"
        val COLUMN_TITLE = "title"
        val COLUMN_PRIORITY = "priority"
        val COLUMN_STATUS = "status"
        val COLUMN_DATE = "date"
    }
}