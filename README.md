# EasyRecyclerView
[![](https://jitpack.io/v/harunozceyhan/EasyRecyclerView.svg)](https://jitpack.io/#harunozceyhan/EasyRecyclerView)

EasyRecyclerView is a Kotlin Android library which makes easier to create RecyclerView.

# Installation
Add the code below to your **root build.gradle** file **(NOT your module build.gradle file)**.
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the code below to your **module's `build.gradle`** file:

```gradle
dependencies {
	implementation 'com.github.harunozceyhan:EasyRecyclerView:0.2.2'
}
```

# Usage
**1. Place `EasyRecyclerView` in your `layout.xml` like this:**


```XML
<!-- activity_main.xml -->
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
        
        ...
    
        <tr.com.harunozceyhan.easyrecyclerview.EasyRecyclerView
                android:id="@+id/easy_recyclerview"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:row_layout="@layout/recycler_view_row_layout" >
        </tr.com.harunozceyhan.easyrecyclerview.EasyRecyclerView>

</RelativeLayout>
```

+ **app:row_layout:** Set resource layout for RecyclerView's Adapter Holder View. 

**2. Create layout xml for `Holder View`.**

```XML
<!-- recycler_view_row_layout.xml -->
<androidx.cardview.widget.CardView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:card_view="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        card_view:cardCornerRadius="0dp"
        card_view:cardElevation="0dp"
        card_view:cardPreventCornerOverlap="false"
        card_view:cardBackgroundColor="#dddddd" >

    <LinearLayout
          android:layout_width="match_parent"
          android:layout_height="match_parent"
          android:layout_marginTop="1dp"
          android:layout_marginBottom="1dp"
          android:layout_marginLeft="2dp"
          android:layout_marginRight="2dp"
          android:orientation="horizontal"
          android:background="#dddddd">

        <ImageView
                android:id="@+id/imageview_template"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:layout_marginRight="1dp"
                android:scaleType="fitCenter"
                android:onClick="onItemClick"
                android:background="#ffffff"/>

        <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/textview_template"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="2"
                android:layout_marginLeft="1dp"
                android:layout_marginRight="1dp"
                android:gravity="center_vertical"
                android:background="#FFFFFF"
                android:textSize="24sp"/>

        <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/textview_template2"
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="2"
                android:gravity="center_vertical"
                android:background="#FFFFFF"
                android:layout_marginLeft="1dp"
                android:textSize="24sp"/>

    </LinearLayout>
</androidx.cardview.widget.CardView>
```

+ **For now**, `TextView` and `ImageView` is supported to set data. For `ImageView`, the image will be fetched automatically with the given url.

**3. Create a `Model Class` to set data for each row.**

```Kotlin
package tr.com.harunozceyhan.easyrecyclerviewexample.models

import tr.com.harunozceyhan.easyrecyclerview.annotations.ViewData

data class TestRecyclerViewModel (@ViewData("textview_template") var text1: String, @ViewData("textview_template2") var text2: String, @ViewData("imageview_template") var imageUrl: String) {
}
```
+ **@ViewData("view_id")** Annotation is used to set class property value to views. Example; value of *text1* will be set to *AppCompatTextView* with id **textview_template**.
+ **@ViewData("imageview_template")** enables to fetch image from given url and set image to *ImageView* with given **id(imageview_template)**.

**4. Create a `list` and set the list to your `recyclerview` in activity.**

```Kotlin
 private var list : MutableList<TestRecyclerViewModel> = mutableListOf()    // Any type of List

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create dump data. 
        for (i in 0 until 10){
            list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300", ""))
        }
        
        val easyRecyclerView : EasyRecyclerView = findViewById(R.id.easy_recyclerview)
        easyRecyclerView.setItemList(list)
        /* 
            If you're using kotlin-android-extensions, simply
            easy_recyclerview.setItemList(list)
        */
    }
```

**5. Set `listener` to `EasyRecyclerView`**

```Kotlin
    easyRecyclerView.onItemClick = { item, position, view ->
        if(view != null) {
            // Clicked a view that has onClick attribute
            Toast.makeText(this@MainActivity, "ImageView Clicked! Index: ${position}", Toast.LENGTH_LONG).show()
        } else {
            // Clicked a row.
            Toast.makeText(this@MainActivity, "${(item as TestRecyclerViewModel).text1} - ${(item).text2} - ${position}", Toast.LENGTH_LONG).show()
        }
    }
```
+ **item:** Clicked row data object.
+ **position:** Clicked row index.
+ **view:** When **android:onClick="onItemClick"** property is set to a view in row layout, view object will be set to this *view* parameter after clicking it. It will be *null* if **onClick** property is not set.
---
## Custom BindViewHolder

Custom view holders can be set to EasyRecyclerView to customize item views and set data.

```Kotlin
    easyRecyclerView.customBindViewHolder = { item, itemView ->
        itemView.textview_template.text = (item as TestRecyclerViewModel).text1
        itemView.textview_template2.text = (item as TestRecyclerViewModel).text2
    }
```
---
## Set data to EasyRecyclerView using Data Binding
Create `EasyRecyclerView` and set `app:item_list` property.
```XML
<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable name="vm" type="tr.com.harunozceyhan.easyrecyclerviewexample.model.MainActivityViewModel"/>
    </data>
    
    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
        
        <tr.com.harunozceyhan.easyrecyclerview.EasyRecyclerView
                android:id="@+id/easy_recyclerview"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:row_layout="@layout/recycler_view_row_layout"
                app:item_list="@{vm.items}">
        </tr.com.harunozceyhan.easyrecyclerview.EasyRecyclerView>

    </RelativeLayout>
</layout>
```
+ **app:item_list:** Set item list to recyclerview. This will set the item list to adapter automatically.

Create Activity `View Model` class to set binding object to `vm`.
```Kotlin
package tr.com.harunozceyhan.easyrecyclerviewexample.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import tr.com.harunozceyhan.easyrecyclerviewexample.BR

data class MainActivityViewModel (private var _items: MutableList<TestRecyclerViewModel>) : BaseObservable() {
    var items: MutableList<TestRecyclerViewModel>
        @Bindable get() = _items
        set(value) {    // To refresh binding data.
            _items = value
            notifyPropertyChanged(BR.items)
        }
}
```

Set item list of `mainActivityViewModel` object.
```Kotlin
private lateinit var binding: ActivityMainBinding
private var list : MutableList<TestRecyclerViewModel> = mutableListOf()

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout.activity_main)
        binding.setVariable(BR.vm, MainActivityViewModel(list))
        
        // Create dump data. 
        for (i in 0 until 10){
            list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300", ""))
        }
        
        // Refresh data
        binding.vm?.items = list // or binding.vm?.notifyPropertyChanged(BR.items)
}
```

# Contributing
The best way to submit a patch is to send us a [pull request](https://help.github.com/articles/about-pull-requests/).

If you want to add new functionality, please file a new proposal issue first to make sure that it is not in progress already. 

If you have any questions, feel free to create a question *issue*.

# License
```
MIT License

Copyright (c) 2019 harunozceyhan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
the  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sellcopies of the Software, 
and to permit persons to whom the Software isfurnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions 
of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE.
```
