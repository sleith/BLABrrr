package com.fatdino.blabrrr.utils

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


fun withBgColor(@ColorRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("View with color same as color with id $id")
    }

    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val bgColor = (view.background as ColorDrawable).color
        val expectedColor: Int? = context?.getColor(id)

        return bgColor == expectedColor
    }
}

fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("ImageView or ViewGroup with drawable same as drawable with id $id")
    }

    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val expectedBitmap = context.getDrawable(id)?.toBitmap()

        if (view is ImageView) {
            return view.drawable.toBitmap().sameAs(expectedBitmap)
        } else if (view is ViewGroup) {
            return view.background.toBitmap().sameAs(expectedBitmap)
        }

        return false
    }
}

fun withTextColor(@ColorRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("TextView or EditText with drawable same as drawable with id $id")
    }

    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val expectedColor: Int? = context?.getColor(id)

        if (view is TextView) {
            return view.currentTextColor == expectedColor
        }

        return false
    }
}

fun withTextHint(@StringRes id: Int) = object : TypeSafeMatcher<View>() {
    override fun describeTo(description: Description) {
        description.appendText("TextView or EditText with drawable same as drawable with id $id")
    }

    override fun matchesSafely(view: View): Boolean {
        val context = view.context
        val expectedString: String? = context?.getString(id)

        if (view is EditText) {
            return view.hint == expectedString
        } else if (view is TextInputLayout) {
            return view.hint == expectedString
        }

        return false
    }
}