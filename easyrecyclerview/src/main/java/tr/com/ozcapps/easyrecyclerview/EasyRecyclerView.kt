package tr.com.ozcapps.easyrecyclerview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import tr.com.ozcapps.easyrecyclerview.R.styleable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tr.com.ozcapps.easyrecyclerview.annotations.ViewData
import tr.com.ozcapps.easyrecyclerview.utils.ViewUtils
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

class EasyRecyclerView : RecyclerView {

    private lateinit var itemList : List<Any>
    private lateinit var ctx: Context
    private var rowLayoutResourceId: Int = 0
    var onItemClick: ((Any, Int, View?) -> Unit)? = null

    companion object {
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

    private fun initUpdateAdapter() {
        if (adapter == null)
            adapter = XAdapter(ctx, itemList, rowLayoutResourceId)
        else
            refreshData()
    }

    inner class XAdapter(ctx: Context, private val itemList: List<Any>, private val rowLayoutResourceId: Int) : RecyclerView.Adapter<XAdapter.XViewHolder>() {

        private val inflater: LayoutInflater = LayoutInflater.from(ctx)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XViewHolder {
            return XViewHolder(inflater.inflate(rowLayoutResourceId, parent, false))
        }

        override fun onBindViewHolder(holder: XViewHolder, position: Int) {
            holder.bindItems(itemList[position])
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        inner class XViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private var listenerAttachedViewList: ArrayList<View> = ViewUtils.getOnClickListenerViews((itemView as ViewGroup))

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