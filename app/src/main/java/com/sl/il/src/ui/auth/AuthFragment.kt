package com.sl.il.src.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sl.il.src.R
import com.sl.il.src.base.BaseFragment
import com.sl.il.src.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment() {
    private val vm by lazy {
        getViewModel(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO add validation on input (prevent sql injection etc...)
        btn_login.setOnClickListener {
            vm.login(et_username.editText?.text.toString(), et_password.editText?.text.toString())
        }

        //TODO change snackbar to toast (or toasty)
        //TODO show more informative msg to user
        btn_register.setOnClickListener {
            vm.register(
                et_username.editText?.text.toString(),
                et_password.editText?.text.toString()
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm.getRegisterEvent().observe(viewLifecycleOwner, Observer { registrationResult ->
            when (registrationResult.getContentIfNotHandled()) {
                AuthStatus.SUCCESS -> {
                    findNavController().navigate(
                        R.id.action_loginFragment_to_detailsFragment, bundleOf(
                            "username" to et_username.editText?.text.toString(),
                            "password" to et_password.editText?.text.toString()
                        )
                    )
                }
                AuthStatus.FAILED -> view?.showSnackbar(
                    getString(R.string.failed_registration_msg),
                    Snackbar.LENGTH_SHORT
                )
            }
        })

        vm.getLoginEvent().observe(viewLifecycleOwner, Observer { loginResult ->
            when (loginResult.getContentIfNotHandled()) {
                AuthStatus.SUCCESS -> {
                    findNavController().navigate(
                        R.id.action_loginFragment_to_detailsFragment, bundleOf(
                            "username" to et_username.editText?.text.toString(),
                            "password" to et_password.editText?.text.toString()
                        )
                    )
                }
                AuthStatus.FAILED -> view?.showSnackbar(
                    getString(R.string.failed_login_msg),
                    Snackbar.LENGTH_SHORT
                )
            }
        })
    }
}
