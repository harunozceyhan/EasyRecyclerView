package tr.com.harunozceyhan.easyrecyclerview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import tr.com.harunozceyhan.easyrecyclerview.R.styleable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tr.com.harunozceyhan.easyrecyclerview.annotations.ViewData
import tr.com.harunozceyhan.easyrecyclerview.utils.ViewUtils
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

/*
    Creates a RecyclerView and RecyclerViewAdapter with given configurations.
*/

class EasyRecyclerView : RecyclerView {

    private lateinit var itemList : List<Any>
    private lateinit var ctx: Context
    private var rowLayoutResourceId: Int = 0
    var onItemClick: ((Any, Int, View?) -> Unit)? = null

    companion object {
        /*
        *   app:item_list property binding method.
        *   Refresh the item list, when binding value changed.
        * */
        @BindingAdapter("item_list")
        @JvmStatic fun setItemList(recyclerView: EasyRecyclerView, items: List<Any>) {
            recyclerView.setItemList(items)
        }
    }

    constructor(ctx: Context) : this(ctx, null)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyle: Int) : super(ctx, attrs, defStyle) {
        if (attrs != null) {
            this.ctx = ctx
            val a = ctx.obtainStyledAttributes(attrs, styleable.EasyRecyclerView, defStyle, 0)

            rowLayoutResourceId = a.getResourceId(styleable.EasyRecyclerView_row_layout, 0)
            if(rowLayoutResourceId == 0) {
                throw Resources.NotFoundException("Row Layout Not Found! Set row_layout of RecyclerView!")
            }
            layoutManager = LinearLayoutManager(ctx)
            a.recycle()
        }
    }

    fun refreshData() {
        adapter?.notifyDataSetChanged()
    }

    fun setItemList(items: List<Any>) {
        itemList = items
        initUpdateAdapter()
    }

    /*
    *   Creates new Adapter if doesn't exist.
    *   If adapter exists, refresh the item list.
    * */
    private fun initUpdateAdapter() {
        if (adapter == null)
            adapter = EasyAdapter(ctx, itemList, rowLayoutResourceId)
        else
            refreshData()
    }

    /*
        RecyclerView Adapter class.
        Creates ViewHolder with given layoutResourceId and set data to views from given list.
    */
    inner class EasyAdapter(ctx: Context, private val itemList: List<Any>, private val rowLayoutResourceId: Int) : RecyclerView.Adapter<EasyAdapter.EasyViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(ctx)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EasyViewHolder {
            return EasyViewHolder(inflater.inflate(rowLayoutResourceId, parent, false))
        }

        override fun onBindViewHolder(holder: EasyViewHolder, position: Int) {
            holder.bindItems(itemList[position])
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        inner class EasyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private var listenerAttachedViewList: ArrayList<View> = ViewUtils.getOnClickListenerViews((itemView as ViewGroup))

            /*
            * Set listeners to RecyclerView and other views that have onClick property.
            * */
            init {
                itemView.setOnClickListener {
                    onItemClick?.invoke(itemList[adapterPosition], adapterPosition, null)
                }

                listenerAttachedViewList.forEach {
                    it.setOnClickListener { view ->
                        onItemClick?.invoke(itemList[adapterPosition], adapterPosition, view)
                    }
                }
            }
            /*
            *   Set data to views for each row.
            *   Get annotated object values, set value to views that have Id indicated by ViewData annotation.
            * */
            @Suppress("UNCHECKED_CAST")
            fun bindItems(item: Any) {
                item::class.members.forEach { prop ->
                    val viewData = prop.findAnnotation<ViewData>()
                    if(viewData != null) {
                        val value = (prop as KProperty1<Any, *>).get(item) as String
                        when (val view = itemView.findViewById<View>(resources.getIdentifier(viewData.viewId, "id", ctx.packageName))) {
                            is TextView -> view.text = value
                            is ImageView -> Glide.with(ctx).load(value).into(view)
                        }
                    }
                }
            }

        }
    }
}