package com.dipay.dipayintegration

import android.webkit.JavascriptInterface


/**
 * Class for receiving .postMessage from webview
 *
 * @param callback : An interface to handle the callback from JavascriptInterface function
 **/
class EvyJavascriptInterface(val callback: Callbacks) {

    /**
     * Function that handle JavascriptInterface for ACTIVATION type
     *
     * @param data = JsonStringify of payment callback
     * {
     *  code: String (00 = success, 99 = error)
     *  data: {
     *      secretKey: String
     *  }
     * }
     *
     * */
    @JavascriptInterface
    fun onActivation(data: String){
        callback.onActivation(data)
    }


    /**
     * Function that handle JavascriptInterface for PAYMENT type
     *
     * @param data = JsonStringify of payment callback
     * {
     *  code: String (00 = success, 99 = error)
     *  data: {
     *      amount: Double / Number
     *      transaction: String (Transaction Code / ID)
     *      payment: String (status)
     *  }
     * }
     *
     * */
    @JavascriptInterface
    fun onPayment(data: String) {
        callback.onPayment(data)
    }

    /**
     * Function that handle JavascriptInterface for CLOSE_WEBVIEW type
     *
     * You can close the webview when this function is called
     * */
    @JavascriptInterface
    fun onClose(){
        callback.onClose()
    }


}

interface Callbacks {
    fun onPayment(data: String)
    fun onClose()
    fun onActivation(data: String)
}