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
	implementation 'com.github.harunozceyhan:EasyRecyclerView:0.1.3'
}
```

## Usage
Place EasyRecyclerView in your layout.xml like this:

activity_main.xml
```XML
<RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
        
        ...
    
        <tr.com.ozcapps.easyrecyclerview.EasyRecyclerView
                android:id="@+id/easy_recyclerview"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_alignParentBottom="true"
                app:row_layout="@layout/recycler_view_row_layout" >
        </tr.com.ozcapps.easyrecyclerview.EasyRecyclerView>

</RelativeLayout>
```

+ **app:row_layout:** Set resource layout for RecyclerView's Adapter Holder View. 

Create layout xml for Holder View.

recycler_view_row_layout.xml
```XML
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

+ For now, **TextView** and **ImageView** is supported to set data. For ImageView, the image will be fetched automatically with the given url.

Create a Model Class to set data for each row. 
```Kotlin
package tr.com.ozcapps.easyrecyclerviewexample.models

import tr.com.ozcapps.easyrecyclerview.annotations.ViewData

data class TestRecyclerViewModel (@ViewData("textview_template") var text1: String, @ViewData("textview_template2") var text2: String, @ViewData("imageview_template") var imageUrl: String) {
}
```
+ **@ViewData("view_id")** Annotation is used to set class property value to views. Example; value of *text1* will be set to *AppCompatTextView* with id **textview_template**.
+ **@ViewData("imageview_template")** enables to fetch image from given url and set image to *ImageView* with given **id(imageview_template)**.

Create a list and set the list to your recyclerview in activity.

```Kotlin
 // Any type of List
 private var list : MutableList<TestRecyclerViewModel> = mutableListOf()

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create dump data. 
        list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300"))
        list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300"))
        list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300"))
        list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300"))
        list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300"))
        list.add(TestRecyclerViewModel("Text ${(list.size + 1).toString()}", "Text ${(list.size + 2).toString()}", "https://picsum.photos/id/${(list.size + 1).toString()}/200/300"))
        
        val easyRecyclerView : EasyRecyclerView = findViewById(R.id.easy_recyclerview)
        easyRecyclerView.setItemList(list)
        /* 
            If you're using kotlin-android-extensions, simply
            easy_recyclerview.setItemList(list)
        */
    }

```

