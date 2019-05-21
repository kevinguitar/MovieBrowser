package com.kevingt.moviebrowser.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.kevingt.moviebrowser.R
import kotlinx.android.synthetic.main.custom_button.view.*

class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_button, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomButton, 0, 0)
            val buttonText = resources.getText(
                typedArray.getResourceId(
                    R.styleable.CustomButton_buttonText, R.string.custom_button_default_text
                )
            )

            tv_button_text.text = buttonText
            typedArray.recycle()
        }
    }

}