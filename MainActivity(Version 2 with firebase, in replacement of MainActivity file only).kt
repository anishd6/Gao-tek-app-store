package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter // "lateinit" is a promise to kotlin that the variable mentioned here will be defined later in the codes
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) { // onCreate function will be called when launch the app
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        todoAdapter = TodoAdapter(mutableListOf()) // assign TodoAdapter to todoAdapter, pass an empty list to TodoAdapter, because the list needs to be updated dynamically.

        rvTodoItems.adapter = todoAdapter //assign Adapter to RecyclerView, you must have an adapter for a recyclerView
        rvTodoItems.layoutManager = LinearLayoutManager(this) //define how these items are arranged, programmer chooses to use LinearLayoutManager (most common one) as its LayoutManager form, and this layoutManger is assigned to RecyclerView in this line

        btnAddTodo.setOnClickListener { //brnAddTodo is one of the buttons created in the layout, here is to define what should happen if user click on this button
            val todoTitle = etTodoTitle.text.toString()
            database = FirebaseDatabase.getInstance().getReference("Todo")
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                database.child(todoTitle).setValue(todo).addOnSuccessListener {
                    Toast.makeText(this,"Successfully Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,"Saving failed",Toast.LENGTH_SHORT).show()
                }
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear() // clear this added text
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }
}