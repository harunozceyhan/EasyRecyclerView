package tr.com.ozcapps.easyrecyclerviewexample.models

import tr.com.ozcapps.easyrecyclerview.annotations.ViewData

data class TestRecyclerViewModel (@ViewData("textview_template") var text1: String, @ViewData("textview_template2") var text2: String, @ViewData("imageview_template") var imageUrl: String, var text3: String) {
}
