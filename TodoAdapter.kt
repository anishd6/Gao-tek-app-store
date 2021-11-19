package com.example.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationBarItemView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    private val todos: MutableList<Todo> //variable declared here can not be accessed by other classes, "Todo" is another class defined in another file
) : RecyclerView.Adapter <TodoAdapter.TodoViewHolder>(){

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) // it inherits the ViewHolder inside the RecyclerView, the constructor is itemView. View-holder holds the all the text/image/checkbox/string created in the layout design

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder { //this function create view holder
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate( //inflate function convert xml layout into the pattern we see in UI
                R.layout.item_todo,
                parent,
                false // fixed coding for every type of RecyclerView app
            )
        )
    }

    private fun toggleStrikeThrough(tvTodoTitle:TextView, isChecked:Boolean){ //TextView and Boolean are data types
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG //if the item has been check, a horizontal line will be added in the middle of the text
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv() //inv() means revert this middle line
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) { //onBindViewHolder set the item from todos list to textview and checkbox
        val curTodo = todos[position] //position is an index to indicate the current item in the todo list
        holder.itemView.apply { //with "apply", holder.itemView does not need to be always prepended
            tvTodoTitle.text = curTodo.title //TextView
            cbDone.isChecked = curTodo.isChecked //Checkbox
            toggleStrikeThrough(tvTodoTitle,curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle,isChecked)
                curTodo.isChecked = !curTodo.isChecked
            } //when the checked status of cbDone is changed, the following line lamda function is going to be executed
        }
    }

    fun addTodo(todo: Todo) {
        todos.add(todo) //todos list will be updated, but recyclerView will not be automatically updated
        notifyItemInserted(todos.size - 1) //notify the adapter that something has been changed, todos.size is the total storage the app has contained strings in the list, each new item will consume 1 space, to pass the last string index in the list, it should be "list.size - 1"
    }

    fun deleteDoneTodos(){
        todos.removeAll { todo -> //todo is the item that is going to be removed
            todo.isChecked //todo.isChecked is a boolean, every "1" will be removed
        }
        notifyDataSetChanged() //notify the adapter that something has been changed
    }

    override fun getItemCount(): Int {
        return todos.size //so the recyclerView knows how many items should be displayed
    }

}