package urdu.sabirdanish.utils

object Utils {
    fun getCurrentPageNo(currentPhysicalPageNos: Int): PageNumber {
        val correctedCurrentPhysicalPageNo =
            if (currentPhysicalPageNos < 1) 1 else currentPhysicalPageNos
        val currDigitalPdfPage =
            if (correctedCurrentPhysicalPageNo <= 2) 0 else (correctedCurrentPhysicalPageNo - 1) / 2
        val currSection =
            if (correctedCurrentPhysicalPageNo % 2 == 0) PageSection.LEFT else PageSection.RIGHT
        return PageNumber(currDigitalPdfPage, correctedCurrentPhysicalPageNo, currSection)
    }

    fun getNextPageNo(currentPageNo: Int): PageNumber {
        val nextPrintedPage = currentPageNo + 1
        val nextPdfPage = if (nextPrintedPage <= 2) 0 else (nextPrintedPage-1) / 2
        val nextSection = if (nextPrintedPage % 2 == 0) PageSection.LEFT else PageSection.RIGHT
        return PageNumber(nextPdfPage, nextPrintedPage, nextSection)
    }

    fun getPreviousPageNo(currentPageNo: Int): PageNumber {
        val prevPrintedPage = if (currentPageNo <= 1) 1 else currentPageNo - 1
        val prevPdfPage = if (prevPrintedPage <= 2) 0 else (prevPrintedPage - 1) / 2

        val prevSection = when {
            prevPrintedPage % 2 == 0 -> PageSection.LEFT
            else -> PageSection.RIGHT
        }
        return PageNumber(prevPdfPage, prevPrintedPage, prevSection)
    }
}

data class PageNumber(val pdfPageNo: Int, val printedPageNumber: Int, val section: PageSection)

enum class PageSection {
    LEFT, RIGHT
}