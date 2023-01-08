package urdu.sabirdanish

import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import urdu.sabirdanish.utils.PageSection
import urdu.sabirdanish.utils.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


object PdfResourceReader {

//Try this: https://pspdfkit.com/guides/android/features/rendering-pdf-pages/
//And https://github.com/TomRoush/PdfBox-Android/blob/master/library/src/main/java/com/tom_roush/pdfbox/rendering/PDFRenderer.java

    // Copies the resource PDF file locally so that PdfRenderer can handle the file
    @Throws(IOException::class)
    private fun copyToLocalCache(outputFile: File, @RawRes pdfResource: Int, resources: Resources) {
        if (!outputFile.exists()) {
            val input: InputStream = resources.openRawResource(pdfResource)
            val output = FileOutputStream(outputFile)
            val buffer = ByteArray(1024)
            var size: Int
            // Just copy the entire contents of the file
            while (input.read(buffer).also { size = it } != -1) {
                output.write(buffer, 0, size)
            }
            input.close()
            output.close()
        }
    }


    // Display a page from the PDF on an ImageView
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(IOException::class)
    fun openPdfWithAndroidSDK(
        viewHeight: Int,
        viewWidth: Int,
        scaleX: Float,
        scaleY: Float,
        resources: Resources,
        cacheDir: File,
        pageNumber: Int = CurrentOpenBook.getPageNo(),
        pdfResource: Int = CurrentOpenBook.getBookResource(),
    ): Bitmap {
        // Copy sample.pdf from 'res/raw' folder into local cache so PdfRenderer can handle it
        val fileCopy = File(cacheDir, "FILE_NAME")
        val (digitalPageNoX, printedPageNumberX, sectionX) = Utils.getCurrentPageNo(pageNumber)
//        Try this : https://stackoverflow.com/questions/45237866/pdf-renderer-in-android-converted-image-is-transparent-background

        copyToLocalCache(fileCopy, pdfResource, resources)

        // We will get a page from the PDF file by calling openPage
        val fileDescriptor = ParcelFileDescriptor.open(
            fileCopy,
            ParcelFileDescriptor.MODE_READ_ONLY
        )
        val mPdfRenderer1 = PdfRenderer(fileDescriptor)
        val mPdfPage = mPdfRenderer1.openPage(digitalPageNoX)
        // Create a new bitmap and render the page contents on to it

        println("$viewWidth   : is the imageView Width")
        println("$viewHeight : is the imageView Height")
        println("/////////////////////////////////////////////")
        val screenWidth = CurrentOpenBook.screenWidth
        val screenHeight = CurrentOpenBook.screenHeight
        println("The Width of screen is $screenWidth")
        println("The Height of screen is $screenHeight")
        println("/////////////////////////////////////////////")

        val imageWidth = mPdfPage.width
        val imageHeight = mPdfPage.height
        val bitmap = Bitmap.createBitmap(
//            (screenWidth + screenWidth / 4.1).toInt(),
            (imageWidth + imageWidth / 2.2).toInt(),
            screenHeight + 500,
            Bitmap.Config.ARGB_8888
        )
        println("${bitmap.width}   : is the Width")
        println("${bitmap.height} : is the Height")

        val canvas = Canvas(bitmap)

        canvas.drawColor(Color.WHITE)
//        canvas.clipOutRect(30,30,300,300)
//        canvas.scale((imageWidth).toFloat(), screenHeight.toFloat())
        canvas.drawBitmap(bitmap, 50f, 0f, Paint())

        val matrix = Matrix()
        val r1 = RectF(70f, 0f, screenWidth.toFloat(), screenHeight.toFloat())

        val src = RectF(70f, 0f, imageWidth.toFloat(), imageHeight.toFloat())
        matrix.postScale((scaleX * 2.5).toFloat(), scaleY)
        val secondPage = -130f
        val firstPage = -1450f

        val pageSectionToDisplay = when (sectionX) {
            PageSection.RIGHT -> firstPage
            PageSection.LEFT -> secondPage
        }
        matrix.postTranslate(pageSectionToDisplay, 0f)

        matrix.mapRect(r1)
        matrix.setRectToRect(src, r1, Matrix.ScaleToFit.FILL)

        val clip = null//Rect(0, 0, width, height)
        mPdfPage.render(bitmap, clip, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        // Set the bitmap in the ImageView so we can view it
//        imageView.setImageBitmap(bitmap)

        mPdfPage?.close()
        mPdfRenderer1.close()
        return bitmap
    }
}

