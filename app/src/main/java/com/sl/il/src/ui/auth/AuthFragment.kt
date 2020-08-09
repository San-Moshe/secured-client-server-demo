package com.sl.il.src.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sl.il.src.R
import com.sl.il.src.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login_register.*

class LoginRegisterFragment : BaseFragment() {
    private val vm by lazy {
        getViewModel(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO add validation on input (prevent sql injection etc...)
        btn_login.setOnClickListener {
            vm.login(et_username.editText?.text.toString(), et_password.editText?.text.toString())
        }

        btn_register.setOnClickListener {
            vm.register(
                et_username.editText?.text.toString(),
                et_password.editText?.text.toString()
            )
        }
    }
}
