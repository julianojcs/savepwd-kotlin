package com.apfjuliano.savepass

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
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

    private fun String.encode(): String {
        return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    }

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

//        passwordDao.apply {
//            insertPasswords(
//                TbPassword("Gmail account", "apfjuliano@gmail.com", "galo12345".encode()),
//                TbPassword("LinkedIn", "apfjuliano@gmail.com", "l1nkp4rK".encode()),
//                TbPassword("Facebook", "julianocosta.usa@gmail.com", "F4c3b00k.".encode()),
//                TbPassword("WhatsApp", "27981330708", "12345678".encode()),
//                TbPassword("Instagram", "apfjuliano@gmail.com", "1n5t49876".encode()),
//                TbPassword("www.amazon.com.br", "apfjuliano@gmail.com", "963852".encode()),
//                TbPassword("Apple Id", "apfjuliano@gmail.com", "963852".encode()),
//                TbPassword("Azul Airlines", "apfjuliano@gmail.com", "963852".encode()),
//                TbPassword("Binance Wallet", "apfjuliano@gmail.com", "B1n4nc3".encode()),
//                TbPassword("Cartão de Crédito Ourocard", "", "1234".encode()),
//                TbPassword("www.mercadolivre.com.br", "julianocosta.usa@gmail.com", "m3345ki".encode())
//            )
//        }


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