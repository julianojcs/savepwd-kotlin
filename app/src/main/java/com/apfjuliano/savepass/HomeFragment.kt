package com.apfjuliano.savepass

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apfjuliano.savepass.database.PasswordDao
import com.apfjuliano.savepass.database.PwdDb
import com.apfjuliano.savepass.database.TbPassword
import com.apfjuliano.savepass.model.Password
import com.apfjuliano.savepass.view.PasswordAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(), PasswordAdapter.ClickedItem {
    private lateinit var recyclerView: RecyclerView
    private lateinit var passwordAdapter: PasswordAdapter
    private lateinit var passwordList: ArrayList<Password>
    private lateinit var lstPassword:  List<TbPassword>
    private lateinit var passwordDao: PasswordDao

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val button = view.findViewById<FloatingActionButton>(R.id.btnPlus)
        val edtSearch = view.findViewById<EditText>(R.id.edtSearch)
        val tvTotal = view.findViewById<TextView>(R.id.tvTotal)
        val context = requireContext()

        edtSearch.maxWidth = Int.MAX_VALUE

        button.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_passFragment)
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                Log.e("TAG", "====> BEF: s= ${s}, start ${start}, count ${count}, after ${after}")
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                passwordAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                "Total ${passwordAdapter.size} of ${passwordAdapter.originalSize}".also { tvTotal.text = it }
            }

        })

        //Data
        passwordList = ArrayList()

        passwordDao = PwdDb.getInstance(context).passwordDao()
        lstPassword = passwordDao.getPasswords()

        for (item in lstPassword) {
            passwordList.add(Password(item.id,item.name, item.userName, item.password))
        }

        //RecyclerView + Item
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        passwordAdapter = PasswordAdapter(this)
        passwordAdapter.setData(passwordList)
        recyclerView.adapter = passwordAdapter

        "Total ${passwordAdapter.size} of ${passwordAdapter.originalSize}".also { tvTotal.text = it }

        return view
    }


    override fun clickedItem(password: Password) {
        Log.e("TAG", "====> ${password.name}")
    }

}