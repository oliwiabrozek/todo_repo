package com.example.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_form.*
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment(), View.OnClickListener {

    lateinit var dbHelper: DBHelper

    companion object {
        fun newInstacne() = FormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val i = inflater.inflate(R.layout.fragment_form, container, false)

        val btn: Button = i.findViewById(R.id.buttonCancel)
        val btn2: Button = i.findViewById(R.id.buttonAdd)
        val editTxt: EditText = i.findViewById(R.id.editTextTask)

        dbHelper = DBHelper(context as MainActivity)

        btn.setOnClickListener(this)
        btn2.setOnClickListener(this)
        editTxt.setOnClickListener(this)



        return i
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonCancel -> {
                Toast.makeText(this.context, "Anulowałeś dodanie zadania", LENGTH_SHORT).show()
                exit(v)
            }
            R.id.buttonAdd -> {

                val currentTime = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy.MM.dd")
                val dateString = formatter.format(currentTime)

                val task = Task(
                    null, editTextTask.text.toString(),
                    priority_spinner.selectedItem.toString(),
                    status_spinner.selectedItem.toString(),
                    dateString
                )

                dbHelper.addTask(task)

                Toast.makeText(this.context, "Dodałeś nowe zadanie", LENGTH_SHORT).show()

                val c = context as MainActivity
                c.refresh()
                exit(v)
            }
            R.id.editTextTask -> {
                clear(v)

            }
            else -> {
            }
        }
    }

    fun exit(view: View) {
        val sfm = (context as MainActivity).supportFragmentManager
        sfm.beginTransaction().remove(this).commit()
    }

    fun clear(view: View) {
        editTextTask.text.clear()
    }

}