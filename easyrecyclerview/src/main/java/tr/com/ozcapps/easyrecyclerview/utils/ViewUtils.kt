package tr.com.ozcapps.easyrecyclerview.utils

import android.view.View
import android.view.ViewGroup
import java.util.*

class ViewUtils {
    companion object {
        @JvmStatic fun getOnClickListenerViews(parent: ViewGroup) : ArrayList<View> {
            var viewList: ArrayList<View> = ArrayList()
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                if (child is ViewGroup) {
                    getOnClickListenerViews(child).forEach {
                        viewList.add(it)
                    }
                } else {
                    if (child != null && child.hasOnClickListeners()) {
                        viewList.add(child)
                    }
                }
            }
            return viewList
        }
    }
}
