package com.apfjuliano.savepass

import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.apfjuliano.savepass.database.PasswordDao
import com.apfjuliano.savepass.database.PwdDb
import com.apfjuliano.savepass.database.TbPassword
import com.google.android.material.textfield.TextInputLayout

class AddPasswordFragment : Fragment() {
    private lateinit var btnSave: Button
    private lateinit var txtAccountName: TextInputLayout
    private lateinit var txtUsername: TextInputLayout
    private lateinit var txtPassword: TextInputLayout
    private lateinit var passwordDao: PasswordDao
    private lateinit var password: TbPassword
    private lateinit var acc: String
    private lateinit var user: String
    private lateinit var pwd: String

    private fun String.encode(): String {
        return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.DEFAULT)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val context = requireContext()
        val view = inflater.inflate(R.layout.fragment_add_password, container, false)
        btnSave = view.findViewById(R.id.btnSaveNewPassword)

        txtAccountName = view.findViewById(R.id.txtName)
        txtUsername = view.findViewById(R.id.txtUsername)
        txtPassword = view.findViewById(R.id.txtPassword)

        btnSave.setOnClickListener {
            acc = txtAccountName.editText?.text.toString()
            user = txtUsername.editText?.text.toString()
            pwd = txtPassword.editText?.text.toString().encode()

            if (acc == "" || user == "" || pwd == "") {
                Toast.makeText(context,"Please, fill all fields",Toast.LENGTH_SHORT).show()
            } else {
                passwordDao = PwdDb.getInstance(context).passwordDao()
                password = TbPassword(acc, user, pwd)
                passwordDao.insertPassword(password)

                txtAccountName.editText?.setText("")
                txtUsername.editText?.setText("")
                txtPassword.editText?.setText("")
                Toast.makeText(context, "Adding Password Information Success", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_passFragment_to_homeFragment)
            }
        }

        return view
    }

}