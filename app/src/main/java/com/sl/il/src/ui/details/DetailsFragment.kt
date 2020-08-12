package com.sl.il.src.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.sl.il.src.R
import com.sl.il.src.base.BaseFragment
import com.sl.il.src.ui.auth.AuthViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : BaseFragment() {
    private val vm by lazy {
        getViewModel(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            tv_username_data.text = getString("username")
            tv_password_data.text = getString("password")
        }

        tv_encrypted_jwt_data.text = vm.getEncryptedToken()
        tv_jwt_data.text = vm.getDecryptedToken()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.getSecuredCredentials().observe(viewLifecycleOwner, Observer {
            tv_encrypted_password_data.text = it.password
        })
    }
}
