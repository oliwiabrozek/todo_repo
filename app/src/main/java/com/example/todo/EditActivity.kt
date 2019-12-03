package com.example.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*


class EditActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        dbHelper = DBHelper(this)
    }

    fun admitEdit(view: View) {

        val id = intent.getStringExtra("id")
        //Toast.makeText(this, id, LENGTH_LONG).show()
        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        val dateString = formatter.format(currentTime)

        val task = Task(
            null, editTextTask.text.toString(),
            priority_spinner.selectedItem.toString(),
            status_spinner.selectedItem.toString(),
            dateString
        )

        if (id != null) {
            dbHelper.editTask(id, task)
        }

        Toast.makeText(this, "Edytowałeś zadanie", LENGTH_SHORT).show()

        finish()

    }

    fun cancelEdit(view: View) {
        Toast.makeText(this, "Anulowałeś edycję zadania", LENGTH_SHORT).show()
        finish()
    }

    fun clear(view: View) {
        editTextTask.text.clear()
    }
}