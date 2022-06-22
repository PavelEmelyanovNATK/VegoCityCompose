package com.emelyanov.vegocity.modules.orderregistration.domain

import com.emelyanov.vegocity.modules.orderregistration.domain.usecases.PopBackUseCase
import com.emelyanov.vegocity.shared.domain.BaseStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OrderRegistrationViewModel
@Inject
constructor(
    private val popBack: PopBackUseCase
) : BaseStateViewModel<OrderRegistrationViewModel.ViewState>(
    initialState = ViewState.Loading
) {
    private val _fullNameField = MutableStateFlow("")
    private val _phoneField = MutableStateFlow("")
    private val _addressField = MutableStateFlow("")
    private val _commentField = MutableStateFlow("")

    init {
        updateState {
            ViewState.Loading
        }

        updateState {
            presentationState(19000)
        }
    }

    private fun onFullNameChange(value: String) { _fullNameField.value = value }
    private fun onPhoneChange(value: String) { _phoneField.value = value }
    private fun onAddressChange(value: String) { _addressField.value = value }
    private fun onCommentChange(value: String) { _commentField.value = value }
    private fun onRegisterClick() {}
    fun onBackClick() = popBack()

    private fun presentationState(
        totalCost: Int
    ) = ViewState.Presentation(
        fullNameField = _fullNameField,
        phoneField = _phoneField,
        addressField = _addressField,
        commentField = _commentField,
        onFullNameChange = ::onFullNameChange,
        onPhoneChange = ::onPhoneChange,
        onAddressChange = ::onAddressChange,
        onCommentChange = ::onCommentChange,
        totalCost = totalCost,
        onRegister = ::onRegisterClick
    )

    sealed interface ViewState {
        object Loading : ViewState
        data class Presentation(
            val fullNameField: StateFlow<String>,
            val phoneField: StateFlow<String>,
            val addressField: StateFlow<String>,
            val commentField: StateFlow<String>,
            val onFullNameChange: (String) -> Unit,
            val onPhoneChange: (String) -> Unit,
            val onAddressChange: (String) -> Unit,
            val onCommentChange: (String) -> Unit,
            val totalCost: Int,
            val onRegister: () -> Unit
        ) : ViewState
        data class Error(val message: String) : ViewState
    }
}