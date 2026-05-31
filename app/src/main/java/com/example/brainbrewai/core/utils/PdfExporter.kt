package com.example.brainbrewai.core.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

object PdfExporter {

    fun exportToPdf(
        context: Context,
        title: String,
        content: String
    ): String {

        val document = PdfDocument()

        val pageInfo =
            PdfDocument.PageInfo.Builder(
                1080,
                1920,
                1
            ).create()

        val page =
            document.startPage(pageInfo)

        val canvas =
            page.canvas

        val titlePaint =
            Paint().apply {
                textSize = 36f
                isFakeBoldText = true
            }

        val bodyPaint =
            Paint().apply {
                textSize = 24f
            }

        canvas.drawText(
            title,
            60f,
            100f,
            titlePaint
        )

        var y = 170f

        content
            .split("\n")
            .forEach { line ->

                canvas.drawText(
                    line,
                    60f,
                    y,
                    bodyPaint
                )

                y += 40f
            }

        document.finishPage(page)

        val fileName =
            "BrainBrewAI_${System.currentTimeMillis()}.pdf"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val resolver =
                context.contentResolver

            val values =
                ContentValues().apply {
                    put(
                        MediaStore.MediaColumns.DISPLAY_NAME,
                        fileName
                    )
                    put(
                        MediaStore.MediaColumns.MIME_TYPE,
                        "application/pdf"
                    )
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DOWNLOADS
                    )
                }

            val uri =
                resolver.insert(
                    MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                    values
                )

            uri?.let {

                resolver.openOutputStream(it)?.use { output ->
                    document.writeTo(output)
                }
            }

        } else {

            val file =
                File(
                    Environment
                        .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        ),
                    fileName
                )

            FileOutputStream(file).use {
                document.writeTo(it)
            }
        }

        document.close()

        return fileName
    }
}