package org.ehealthinnovation.bluetoothglucose

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.*
import org.junit.Rule

open class BaseTest {

    @get:Rule
    var watchman = object : TestWatcher() {
        override fun succeeded(d: Description?) {
            super.succeeded(d)
            println(ok())
        }
    }

    var OK = "ICAgICAgICAgICAgLi0tLS0tLS0tLl8KICAgICAgICAgICAoYC0tJyAgICAgICBgLS4KICAgICAgICAgICAgYC5fX19fX18g" +
           "ICAgICBgLgogICAgICAgICBfX19fX19fX19fX2BfXyAgICAgXAogICAgICAsLScgICAgICAgICAgIGAtLlwgICAgIHwKICAgI" +
           "CAvLyAgICAgICAgICAgICAgICBcfCAgICB8XAogICAgKGAgIC4nfn5+fn4tLS1cICAgICBcJyAgIHwgfAogICAgIGAtJyAgIC" +
           "AgICAgICAgKSAgICAgXCAgIHwgfAogICAgICAgICwtLS0tLS0tLS0nIC0gLS4gIGAgIC4gJwogICAgICAsJyAgICAgICAgICA" +
           "gICBgJWBcYCAgICAgfAogICAgIC8gICAgICAgICAgICAgICAgICAgICAgXCAgfAogICAgLyAgICAgXC0tLS0tLiAgICAgICAg" +
           "IFwgICAgYAogICAvfCAgLF8vICAgICAgJy0uXyAgICAgICAgICAgIHwKICAoLScgIC8gICAgICAgICAgIC8gICAgICAgICAgI" +
           "CBgCiAgLGAtLTwgICAgICAgICAgIHwgICAgICAgIFwgICAgIFwKICBcIHwgIFwgICAgICAgICAvJSUgICAgICAgICAgICAgYF" +
           "wKICAgfC8gICBcX19fXy0tLSctLWAlICAgICAgICBcICAgICBcCiAgIHwgICAgJyAgICAgICAgICAgYCAgICAgICAgICAgICA" +
           "gIFwKICAgfAogICAgYC0tLl9fCiAgICAgICAgICBgLS0tLl9fX19fX18KICAgICAgICAgICAgICAgICAgICAgIGAuCiAgICAg" +
           "ICAgICAgICAgICAgICAgICAgIFwgICAgICAgICAgICAg"

   fun ok(): String {
       return Base64.getDecoder().decode(OK).toString(Charsets.UTF_8)
   }

}