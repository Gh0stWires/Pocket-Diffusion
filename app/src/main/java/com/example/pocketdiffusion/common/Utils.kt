package com.example.pocketdiffusion.common

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun Context.shareImage(title: String, image: Bitmap, filename: String) {
    val file = try {
        val outputFile = File(cacheDir, "$filename.png")
        val outPutStream = FileOutputStream(outputFile)
        image.compress(CompressFormat.PNG, 100, outPutStream)
        outPutStream.flush()
        outPutStream.close()
        outputFile
    } catch (e: Throwable) {
        return toast(e)
    }
    val uri = file.toUriCompat(this)
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
    }
    startActivity(Intent.createChooser(shareIntent, title))
}

fun File.toUriCompat(context: Context): Uri {
    return FileProvider.getUriForFile(context, context.packageName + ".provider", this)
}

fun Context.toast(throwable: Throwable) =
    throwable.message?.let { toast(it) }
        ?: toast("Unknown")

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
