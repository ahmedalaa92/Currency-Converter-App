package com.example.currencyconverterapp.ui

sealed class UIState

object LoadingState : UIState()
object ContentState : UIState()
class ErrorState(val message: String) : UIState()
