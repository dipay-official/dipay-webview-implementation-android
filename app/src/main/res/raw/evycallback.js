javascript:(function() {
    function callback(e){
        /* Only allow origin from Evy WebView URL */
        if(e.origin !== 'https://wvs.evy.id'){
            return;
        }

        /* Stringify the json */
        var data = JSON.stringify(e.data.callback);

        /* Check the type of postMessage */
        if(e.data.type == 'ACTIVATION'){
            /* Call JavaScript Interface onActivation */

            EvyWebView.onActivation(data);
        } else if(e.data.type == 'PAYMENT'){
            /* Call JavaScript Interface onPayment */

            EvyWebView.onPayment(data);
        } else if(e.data.type == 'CLOSE_WEBVIEW'){
            /* Call JavaScript Interface onClose */

            EvyWebView.onClose();
        }
    }

    /* Add Event Listener for onMessage */
    window.addEventListener('message', callback, false);

})()