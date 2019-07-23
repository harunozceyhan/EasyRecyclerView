package tr.com.harunozceyhan.easyrecyclerview.utils

import android.view.View
import android.view.ViewGroup
import java.util.*

/*
    This class contains static methods that return Android Views.
*/

class ViewUtils {
    companion object {
        /*
            Returns a list of views that has android:onClick property.
        */
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
