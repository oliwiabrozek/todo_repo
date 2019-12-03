package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_task.view.*


class MainActivity : AppCompatActivity(), AdapterOnClick {

    lateinit var dbHelper: DBHelper
    val fragment = FormFragment.newInstacne()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)
        refresh()

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                val taskid = viewHolder.itemView.task_id.text.toString()
                dbHelper.deleteTask(taskid.toLong())
                Toast.makeText(this@MainActivity, "Usunąłeś zadanie", LENGTH_SHORT).show()
                refresh()
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(tasks_list)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.fragment_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.fragment_menu -> {
                if (!fragment.isAdded()) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.add(R.id.container, fragment)
                    ft.commit()
                    true
                } else {
                    true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(item: TextView) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("id", item.text.toString())
        startActivity(intent)
    }

    override fun onRestart() {
        super.onRestart()
        refresh()
    }


    fun refresh() {
        tasks_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RecyclerViewAdapter(dbHelper.readAllTasks(), this@MainActivity)
        }
    }

}
