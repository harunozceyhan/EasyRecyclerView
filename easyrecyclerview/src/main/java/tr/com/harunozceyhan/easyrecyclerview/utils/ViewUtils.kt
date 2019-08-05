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
            Returns a list of views or Ids that has android:onClick property.
        */
        @JvmStatic fun getOnClickListenerViews(parent: ViewGroup, returnId: Boolean) : ArrayList<Any> {
            var viewList: ArrayList<Any> = ArrayList()
            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                if (child is ViewGroup) {
                    getOnClickListenerViews(child, returnId).forEach {
                        viewList.add(it)
                    }
                } else {
                    if (child != null && child.hasOnClickListeners()) {
                        viewList.add(if(returnId) child.id else child)
                    }
                }
            }
            return viewList
        }
    }
}
