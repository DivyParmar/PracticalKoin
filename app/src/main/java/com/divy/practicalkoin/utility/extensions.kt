package com.divy.practicalkoin.utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.divy.practicalkoin.BuildConfig
import com.divy.practicalkoin.R
import com.bumptech.glide.Glide
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun selectImageFromGallery(galleryResultLauncher: ActivityResultLauncher<PickVisualMediaRequest>) {
    galleryResultLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
}

fun Context.selectImageFromCamera(
    cameraResultLauncher: ActivityResultLauncher<Intent>,
    onPhotoPathFound: (path: File) -> Unit
) {
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    cameraIntent.resolveActivity(this.packageManager)?.let {
        // Create the File where the photo should go
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            // Handle the error
            null
        }

        // Continue only if the File was successfully created
        photoFile?.also {
            onPhotoPathFound.invoke(it)
            val authority = "${this.packageName}.provider"
            val photoURI = FileProvider.getUriForFile(
                this,
                authority,
                it
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            cameraResultLauncher.launch(cameraIntent)
        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp: String =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File? = this.getExternalFilesDir(null)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}


fun Context.cropImage(
    sourceUri: Uri,
    cropImageLauncher: ActivityResultLauncher<Intent>
) {
    val options = UCrop.Options().apply {
        // Set cropping window options
        setShowCropGrid(true)
//        setShowCropFrame(true)
//        setHideBottomControls(false)

        // Set compression options
        setCompressionFormat(Bitmap.CompressFormat.JPEG)
        setCompressionQuality(80) // Range 0-100

        // Set aspect ratio options
//        setAspectRatioOptions(1, UCropAspectRatioOptions( // Fixed set of aspect ratios
//            R.string.ucrop_label_original, UCrop.DEFAULT_ASPECT_RATIO,
//            R.string.ucrop_label_square, 1f,
//            R.string.ucrop_label_3_4, 3f / 4f,
//            R.string.ucrop_label_4_3, 4f / 3f,
//            R.string.ucrop_label_9_16, 9f / 16f,
//            R.string.ucrop_label_16_9, 16f / 9f
//        ))

        // Set maximum bitmap size
//        setMaxBitmapSize(maxWidth, maxHeight)

        // Set UI options
//        setToolbarColor(ContextCompat.getColor(this@cropImage, R.color.colorPrimary))
//        setStatusBarColor(ContextCompat.getColor(this@cropImage, R.color.colorPrimaryDark))
//        setActiveControlsWidgetColor(ContextCompat.getColor(this@cropImage, R.color.colorAccent))

        setFreeStyleCropEnabled(true)
//        setToolbarCropDrawable(R.drawable.ucrop_ic_done)
//        setToolbarCancelDrawable(R.drawable.ucrop_ic_cross)

        // Set other options if needed
    }


    Log.e("SourceUri", "launchUCropActivity: " + sourceUri)
    val uCrop = UCrop.of(sourceUri, Uri.fromFile(createImageFile()))
        .withOptions(options)
    cropImageLauncher.launch(uCrop.getIntent(this))
}


fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.setClickableSpannableString(
    textView: TextView,
    fullText: String,
    clickableText: String,
    clickableTextColor: Int,
    textBold: Boolean = true,
    textUnderline: Boolean = false,
    onClick: () -> Unit
) {
    try {

        //it will go in Catch exception and won't display any text if the clickableText is not present in full text
//                Eg: if you text is "Don't have an account? Sign up"
//                then clickableText should be "Sign up"
//                if you give clickable text as For eg:-"new" then it will go in catch and wont show anything in textview

        val signUpIndex = fullText.indexOf(clickableText)

        val spannableString = SpannableString(fullText)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = textUnderline
            }
        }

        spannableString.setSpan(
            clickableSpan,
            signUpIndex,
            signUpIndex + clickableText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    clickableTextColor
                )
            ),
            signUpIndex,
            signUpIndex + clickableText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        if (textBold) {
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                signUpIndex,
                signUpIndex + clickableText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        //to disable the highlighting clickable span on click
        textView.highlightColor = Color.parseColor("#00FFFFFF")

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    } catch (e: Exception) {
        Log.e("SpannableStringError", "Catch setClickableSpannableString: " + e.message)
    }
}




fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Activity.startActivityCustom(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != null) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}

fun getImageNameFromUrl(url: String): String {
    val uri = Uri.parse(url)
    val path = uri.path
    if (path != null) {
        // Split the path by '/' and get the last part, which is the image name
        val parts = path.split("/")
        if (parts.isNotEmpty()) {
            return parts.last()
        }
    }
    // Return a default name or handle cases where the URL doesn't contain a valid image path
    return "" // Change this to your default image name
}

fun getImageNameFromPath(imagePath: String): String {
    val file = File(imagePath)
    return file.name
}

fun Fragment.startActivityCustom(intent: Intent, requestCode: Int? = 0) {
    if (requestCode != null) {
        this.startActivityForResult(intent, requestCode)
    } else {
        this.startActivity(intent)
    }
}

fun Int.pxToDp(): Float {
    return this / Resources.getSystem().displayMetrics.density
}

fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun AppCompatImageView.loadImage(
    image: Any,
    placeholder: Int? = R.drawable.ic_gallery
) {
    Glide.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .into(this)
}

fun AppCompatImageView.loadImageRound(
    image: Any,
    placeholder: Int? = R.drawable.ic_gallery
) {
    Glide.with(this.context)
        .load(image)
        .placeholder(placeholder!!)
        .circleCrop()
        .into(this)
}



fun isInvalidEmail(email: String): Boolean {
    if (isTopLevelDomainValid(email)) return true
    val pattern = Patterns.EMAIL_ADDRESS
    return !(pattern.matcher(email).matches())
}

fun isTopLevelDomainValid(source: String): Boolean {
    return source.split(".").last().length == 1
}

fun Context.isValidPhone(text: String): Boolean {
    return !TextUtils.isEmpty(text)
            && Patterns.PHONE.matcher(text).matches()
            && (text.length >= 10)
            && (text.length <= 10)
}

fun Context.isValidPassword(text: String): Boolean {
    return !TextUtils.isEmpty(text)
            && (text.length >= 6)
            && (text.length <= 10)
}

fun Context.isPasswordAndConfirmPasswordMatch(password: String, confirmPass: String): Boolean {
    return !TextUtils.isEmpty(password)
            && !TextUtils.isEmpty(confirmPass)
            && password.contentEquals(confirmPass)
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }

                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
}

fun Context.getDeviceTimeZone(): String {
    return Calendar.getInstance().timeZone.id
}

fun Activity.openPermissionSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", this.packageName, null)
    intent.data = uri
    startActivityForResult(intent, AppConstant.INTENT_SETTINGS)
}

fun Context.getAndroidID(): String {
    return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID)
}

fun View.showKeyboard() {
    val inputMethodManager =
        ContextCompat.getSystemService(context, InputMethodManager::class.java)
    inputMethodManager?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


fun Context.hideKeyboardFrom(view: View) {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun callFromDialer(mContext: Context, number: String) {
    try {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$number")
        mContext.startActivity(callIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun String.formatTwoDecimal(): String {
    return try {
        val value = this.toDouble()
        String.format("%.2f", value)
    } catch (e: NumberFormatException) {
        // Handle the case where the string is not a valid number
        this
    }
}

fun String.formatOneDecimal(): String {
    try {
        val value = this.toDouble()
        return String.format("%.1f", value)
    } catch (e: NumberFormatException) {
        // Handle the case where the string is not a valid number
        return this
    }
}

fun sendGmail(mContext: Context, mail: String) {
    try {
        val email = Intent(Intent.ACTION_VIEW)
            .setType("message/rfc822")
            .setData(Uri.parse("mailto:${mail}"))
            // .setData(Uri.parse("mailto:your.email@gmail.com"))
            // .putExtra(Intent.EXTRA_EMAIL, "your.email@gmail.com")
            //   .putExtra(Intent.EXTRA_SUBJECT, "Subject")
            //  .putExtra(Intent.EXTRA_TEXT, "My Email message")
            .setPackage("com.google.android.gm")
        mContext.startActivity(email)
    } catch (e: Exception) {

    }
}


fun getTimeAgo(time1: Long): String {
    val secondMILLIS = 1000
    val minuteMILLIS = 60 * secondMILLIS
    val hourMILLIS = 60 * minuteMILLIS
    val dayMILLIS = 24 * hourMILLIS

    var time = time1
    if (time < 1000000000000L) {
        time *= 1000
    }

    val now = Calendar.getInstance().time.time
    if (time > now || time <= 0) {
        return "in the future"
    }

    val diff = now - time
    return when {
        diff < 48 * hourMILLIS -> "Yesterday"
        else -> "${diff / dayMILLIS} days ago"
    }
}

fun AppCompatTextView.setHtmlString(content: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(content)
    }
}

fun AppCompatEditText.getString(): String {
    return this.text.toString().trim()
}

fun AppCompatTextView.getString(): String {
    return this.text.toString().trim()
}


private fun openWhatsApp(mContext: Context, number: String) {
    val url = "https://api.whatsapp.com/send?phone=91$number&text="
    if (isAppInstalled(mContext, "com.whatsapp.w4b") || isAppInstalled(mContext, "com.whatsapp")) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        mContext.startActivity(i)
    } else {
        val url = "https://play.google.com/store/apps/details?id=com.whatsapp"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        mContext.startActivity(i)
    }
}

private fun isAppInstalled(mContext: Context, packageName: String): Boolean {
    val pm1 = mContext.packageManager
    val appInstalled = try {
        pm1?.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
    return appInstalled
}

fun Activity.getDeviceName(): String {
    return android.os.Build.MANUFACTURER + android.os.Build.MODEL
}

fun Activity.getAppVersion(): String {
    return "${BuildConfig.VERSION_NAME}"
}

fun Activity.getDeviceUniqueId(): String {
    return Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
}

fun Activity.getOSVersion(): String {
    return "Version ${Build.VERSION.SDK_INT}"
}

fun isValidUpi(text: String): Boolean {
    val matchPattern = "[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}"
    val passwordMatcher = Regex(matchPattern)
    return passwordMatcher.find(text) != null
}


fun Long.convertTwDigit(): String {
    if (this < 10) {
        return "0${this.toString()}"

    } else {
        return this.toString()
    }
}

fun String.capitalizeFirstWord(): String {
    if (this.isBlank() || this.equals("null", true)) {
        // Handle empty or blank strings if needed
        return this
    }
    // Capitalize the first letter and append the rest of the string
    return this.substring(0, 1).uppercase(Locale.ROOT) + this.substring(1)
}

fun String.timeDiffInMillisWithCurrent(withZoneCalculation: Boolean = true): Long {
    val INPUTFORMAT = "yyyy-MM-dd HH:mm:ss"
    val dateFormat = SimpleDateFormat(INPUTFORMAT)
//    val outFormat = SimpleDateFormat(INPUTFORMAT)

    if (withZoneCalculation) {
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        //     outFormat.timeZone = TimeZone.getDefault()
    }

    val futureDate = dateFormat.parse(this)
    return futureDate!!.time - Date().time
}

fun View.getHeightWithListener(onHeightReady: (Int) -> Unit) {
    if (height > 0) {
        // View already has a height, return it immediately
        onHeightReady(height)
        return
    }

    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)

            // Return the height to the callback function
            onHeightReady(height)
            return true
        }
    })
}

fun String?.replaceRupees(): Double {
    return if (this.isNullOrEmpty() || this.equals("null", true)) {
        0.0
    } else {
        this.replace("₹", "").replace(" ", "").toDouble()
    }
}

fun String?.addRupeesSymbol(): String {
    return if (this.isNullOrEmpty() || this.equals("null", true)) {
        "₹0"
    } else {
        "₹$this"
    }
}

fun String.formatDateFromDDMMYYYYToYYYYMMDD(): String {
    try {
        // Parse input date string
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = inputFormat.parse(this)

        // Format date to output format
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return outputFormat.format(date!!)
    } catch (e: Exception) {
        // Handle parsing/formatting errors
        e.printStackTrace()
        return this // Return original string in case of error
    }
}

fun Int.isOdd(): Boolean {
    return this % 2 != 0
}

fun String.toLocalTimeAgo(): String {
    // Parse the input string to Date
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    val date = dateFormat.parse(this)

    // Convert Date to time in milliseconds
    val timeInMillis = date?.time ?: 0

    // Calculate the time difference in minutes
    val minutesAgo = (System.currentTimeMillis() - timeInMillis) / (60 * 1000)

    return when {
        minutesAgo < 1 -> "Just now"
        minutesAgo < 60 -> "$minutesAgo mins ago"
        minutesAgo < 24 * 60 -> "${minutesAgo / 60} hours ago"
        minutesAgo < 48 * 60 -> "Yesterday"
        else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }
}
fun Context.getPlayStoreLink(): String {
    return "https://play.google.com/store/apps/details?id=$packageName"
}
fun String.convertTo12HourFormat(): String {
    try {
        val sdf24 = SimpleDateFormat("HH:mm", Locale.getDefault())
        val sdf12 = SimpleDateFormat("h:mm a", Locale.getDefault())
        val dateObj = sdf24.parse(this)
        return sdf12.format(dateObj)
    } catch (e: ParseException) {
        // Handle the exception if the input is not a valid time format
        e.printStackTrace()
        return "" // return the original string in case of an error
    }
}
