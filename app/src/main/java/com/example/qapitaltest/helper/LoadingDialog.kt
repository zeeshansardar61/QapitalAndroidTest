package com.example.qapitaltest.helper

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


var loadingDialog: ProgressDialog? = null

fun Context.showLoader() {
    if (!loaderShowing()) {
        loadingDialog = ProgressDialog(this)
        loadingDialog?.setMessage("Loading...")
        loadingDialog?.show()
    }
}

fun cancelDialog() {
    loadingDialog?.dismiss()
}

fun loaderShowing(): Boolean {
    if (loadingDialog != null) {
        return loadingDialog!!.isShowing
    }
    return false
}


@BindingAdapter("html")
fun TextView.setHtml(html: String) {
    text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("price")
fun TextView.setPrice(price: Double) {
    text = "$" + price
}

@BindingAdapter("dateTime")
fun TextView.setDate(dateTime: String) {
    val date = dateTime.substring(0, 10)
    val dtf: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")
    val jodatime: DateTime = dtf.parseDateTime(date)
    val dtfOut: DateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")

    val today = DateTime.now().toLocalDate()
    val yesterday = DateTime.now().minusDays(1).toLocalDate()

    Log.d("date", "setDate: "+today)
    Log.d("date", "setDate: "+yesterday)

    val finalDate = if (dtfOut.print(jodatime).equals(dtfOut.print(today))) {
        "Today"
    } else if (dtfOut.print(jodatime).equals(dtfOut.print(yesterday))) {
        "Yesterday"
    } else {
        dtfOut.print(jodatime)
    }

    text = finalDate
}


@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(context).load(url).into(this)
}
