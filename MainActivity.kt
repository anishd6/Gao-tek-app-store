package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter // "lateinit" is a promise to kotlin that the variable mentioned here will be defined later in the codes

    override fun onCreate(savedInstanceState: Bundle?) { // onCreate function will be called when launch the app
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter = TodoAdapter(mutableListOf()) // assign TodoAdapter to todoAdapter, pass an empty list to TodoAdapter, because the list needs to be updated dynamically.

        rvTodoItems.adapter = todoAdapter //assign Adapter to RecyclerView, you must have an adapter for a recyclerView
        rvTodoItems.layoutManager = LinearLayoutManager(this) //define how these items are arranged, programmer chooses to use LinearLayoutManager (most common one) as its LayoutManager form, and this layoutManger is assigned to RecyclerView in this line

        btnAddTodo.setOnClickListener { //brnAddTodo is one of the buttons created in the layout, here is to define what should happen if user click on this button
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear() // clear this added text
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}