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

//TODO login without credentials in-case you already have JWT

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

    @ExperimentalStdlibApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_login.setOnClickListener {
            if (et_username.editText?.text?.isNotBlank() == true) {
                vm.login(
                    et_username.editText?.text.toString(),
                    et_password.editText?.text.toString()
                )
            }
        }

        btn_register.setOnClickListener {
            if (et_username.editText?.text?.isNotBlank() == true) {
                vm.register(
                    et_username.editText?.text.toString(),
                    et_password.editText?.text.toString()
                ).let {
                    if (!it) {
                        view.showSnackbar(
                            """Password must be:
At least 8 chars

Contains at least one digit

Contains at least one lower alpha char and one upper alpha char

Contains at least one char within a set of special chars (@#%$^ etc.)

Does not contain space, tab, etc. """,
                            Snackbar.LENGTH_SHORT
                        )
                    }
                }
            }
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
