package urdu.sabirdanish

import android.content.res.Resources
import android.graphics.*
import android.graphics.pdf.PdfRenderer
import android.media.Image
import android.os.Build
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


object PdfResourceReader {


    // Copies the resource PDF file locally so that PdfRenderer can handle the file
    @Throws(IOException::class)
    private fun copyToLocalCache(outputFile: File, @RawRes pdfResource: Int, resources: Resources) {
        if (!outputFile.exists()) {
            val input: InputStream = resources.openRawResource(pdfResource)
            val output: FileOutputStream
            output = FileOutputStream(outputFile)
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
        imageView: ImageView,
        resources: Resources,
        cacheDir: File,
        pageNumber: Int = CurrentOpenBook.getPageNo(),
        pdfResource: Int = CurrentOpenBook.getBookResource(),
    ) {
        // Copy sample.pdf from 'res/raw' folder into local cache so PdfRenderer can handle it
        val fileCopy = File(cacheDir, "FILE_NAME")

        copyToLocalCache(fileCopy, pdfResource, resources)

        // We will get a page from the PDF file by calling openPage
        val fileDescriptor = ParcelFileDescriptor.open(
            fileCopy,
            ParcelFileDescriptor.MODE_READ_ONLY
        )
        val mPdfRenderer1: PdfRenderer = PdfRenderer(fileDescriptor)

        val mPdfPage = mPdfRenderer1.openPage(pageNumber)
        // Create a new bitmap and render the page contents on to it

        println("${imageView.width}   : is the imageView Width")
        println("${imageView.height} : is the imageView Height")
        println("/////////////////////////////////////////////")
        val screenWidth = CurrentOpenBook.screenWidth
        val screenHeight = CurrentOpenBook.screenHeight
        println("The Width of screen is $screenWidth")
        println("The Height of screen is $screenHeight")
        println("/////////////////////////////////////////////")

        val imageWidth = mPdfPage.getWidth()
        val imageHeight = mPdfPage.getHeight()
        val bitmap = Bitmap.createBitmap(
//            (screenWidth + screenWidth / 4.1).toInt(),
            (imageWidth + imageWidth / 2.2).toInt(),
            screenHeight,
            Bitmap.Config.ARGB_8888
        )
        println("${bitmap.width}   : is the Width")
        println("${bitmap.height} : is the Height")

        val canvas = Canvas(bitmap)

        canvas.drawColor(Color.WHITE)
        canvas.clipOutRect(30,30,300,300)
//        canvas.scale((imageWidth).toFloat(), screenHeight.toFloat())
        canvas.drawBitmap(bitmap, 10f, 10f, Paint())

        val matrix = Matrix();
        val r1 = RectF(0f, 0f, screenWidth.toFloat(), screenHeight.toFloat())

        val src = RectF(0f, 0f, imageWidth.toFloat(), imageHeight.toFloat())
        matrix.postScale((imageView.scaleX * 2.5).toFloat(), imageView.scaleY)
        val secondPage = -1450f
        val firstPage = -130f

        val pageToDisplay = if (pageNumber % 2 == 0) secondPage else firstPage

        matrix.postTranslate(pageToDisplay, 0f)

        matrix.mapRect(r1)
        matrix.setRectToRect(src, r1, Matrix.ScaleToFit.FILL)

        val clip = null//Rect(0, 0, width, height)
        mPdfPage.render(bitmap, clip, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        // Set the bitmap in the ImageView so we can view it
        imageView.setImageBitmap(bitmap)

        mPdfPage?.close()
        mPdfRenderer1?.close()
    }
}

