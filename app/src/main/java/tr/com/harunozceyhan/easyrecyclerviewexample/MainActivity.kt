package tr.com.harunozceyhan.easyrecyclerviewexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import tr.com.harunozceyhan.easyrecyclerviewexample.models.TestRecyclerViewModel

class MainActivity : AppCompatActivity() {

    private var list : MutableList<TestRecyclerViewModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        easy_recyclerview.onItemClick = { item, position, view ->
            if(view != null) {
                // Clicked a view that has onClick attribute
                Toast.makeText(this@MainActivity, "ImageView Clicked! Index: ${position}", Toast.LENGTH_LONG).show()
            } else {
                // Clicked a row.
                Toast.makeText(this@MainActivity, "${(item as TestRecyclerViewModel).text1} - ${(item).text2} - ${position}", Toast.LENGTH_LONG).show()
                addData()
            }
        }

        addData()
    }

    // Add 10 items to list and update recyclerview
    private fun addData() {
        for(i in 1..10) {   // Add 10 items to list
            list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300", ""))
        }
        easy_recyclerview.setItemList(list) // Update recyclerview
    }
}
