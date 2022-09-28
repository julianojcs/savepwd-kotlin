package com.apfjuliano.savepass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class PasswordAdapter(private var passwordList: ArrayList<Password>)
class PasswordAdapter(var clickedItem: ClickedItem)
    : RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder>() {

    private lateinit var passwordList: ArrayList<Password>

    fun setData(passwordList: ArrayList<Password>){
        this.passwordList = passwordList
        notifyDataSetChanged()

    }

    interface ClickedItem{
        fun clickedItem(password: Password)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.password_item,
            parent,
            false
        )
        return PasswordViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        var password = passwordList[position]
        holder.bind(password)

        holder.itemView.setOnClickListener {
            clickedItem.clickedItem(password)
        }
    }

    override fun getItemCount(): Int {
        return passwordList.count() // passwordList.size
    }

    inner class PasswordViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvUser: TextView = itemView.findViewById(R.id.tvUser)
        private val tvPassword: TextView = itemView.findViewById(R.id.tvPassword)

        fun bind(password: Password) {
            tvId.text = "${password.id}: "
            tvName.text = "${password.name}"
            tvUser.text = "User: ${password.user}"
            tvPassword.text = "pwd: ${password.password}"
        }
    }

}