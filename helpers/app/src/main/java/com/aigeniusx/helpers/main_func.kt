// UIHelper.kt

package com.aigeniusx.helpers

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

fun addTextPrompt(context: String, container: LinearLayout, text: String) {
    val textView = TextView(context)
    textView.text = text
    container.addView(textView)
}

fun addImage(context: Context, container: LinearLayout, imageUri: Uri) {
    val imageView = ImageView(context)
    imageView.setImageURI(imageUri)
    container.addView(imageView)
}
