package urdu.sabirdanish.utils

import org.junit.Assert
import org.junit.Test

internal class UtilsTest {

    @Test
    fun getCurrentPageNoShouldReturnCorrectInfoForPageNo2() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getCurrentPageNo(2)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(2, printedPageNumber)
        Assert.assertEquals(0, pdfPageNo)
    }

    @Test
    fun getCurrentPageNoShouldReturnCorrectInfoForPageNo3() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getCurrentPageNo(3)
        Assert.assertEquals(PageSection.RIGHT, section)
        Assert.assertEquals(3, printedPageNumber)
        Assert.assertEquals(1, pdfPageNo)
    }

    @Test
    fun getCurrentPageNoShouldReturnCorrectInfoForPageNo4() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getCurrentPageNo(4)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(4, printedPageNumber)
        Assert.assertEquals(1, pdfPageNo)
    }

    @Test
    fun getCurrentPageNoShouldReturnCorrectInfoForOddPage() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getCurrentPageNo(11)
        Assert.assertEquals(PageSection.RIGHT, section)
        Assert.assertEquals(11, printedPageNumber)
        Assert.assertEquals(5, pdfPageNo)
    }

    @Test
    fun getCurrentPageNoShouldReturnCorrectInfoForEvenPage() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getCurrentPageNo(12)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(12, printedPageNumber)
        Assert.assertEquals(5, pdfPageNo)
    }

    @Test
    fun getNextPageNoShouldReturnNextPageNumberForPageNo1() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getNextPageNo(1)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(2, printedPageNumber)
        Assert.assertEquals(0, pdfPageNo)
    }

    @Test
    fun getNextPageNoShouldReturnNextPageNumberForPageNo2() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getNextPageNo(2)
        Assert.assertEquals(PageSection.RIGHT, section)
        Assert.assertEquals(3, printedPageNumber)
        Assert.assertEquals(1, pdfPageNo)
    }

    @Test
    fun getNextPageNoShouldReturnNextPageNumberForEvenPage() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getNextPageNo(10)
        Assert.assertEquals(PageSection.RIGHT, section)
        Assert.assertEquals(11, printedPageNumber)
        Assert.assertEquals(5, pdfPageNo)
    }

    @Test
    fun getNextPageNoShouldReturnNextPageNumberForOddPage() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getNextPageNo(11)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(12, printedPageNumber)
        Assert.assertEquals(5, pdfPageNo)
    }

    @Test
    fun getPreviousPageNoShouldReturnPreviousPageNumberForPageNo1() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getPreviousPageNo(1)
        Assert.assertEquals(1, printedPageNumber)
        Assert.assertEquals(0, pdfPageNo)
        Assert.assertEquals(PageSection.RIGHT, section)
    }

    @Test
    fun getPreviousPageNoShouldReturnPreviousPageNumberForPageNo2() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getPreviousPageNo(2)
        Assert.assertEquals(1, printedPageNumber)
        Assert.assertEquals(0, pdfPageNo)
        Assert.assertEquals(PageSection.RIGHT, section)
    }

    @Test
    fun getPreviousPageNoShouldReturnPreviousPageNumberForPageNo3() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getPreviousPageNo(3)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(2, printedPageNumber)
        Assert.assertEquals(0, pdfPageNo)
    }

    @Test
    fun getPreviousPageNoShouldReturnPreviousPageNumberForOddPage() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getPreviousPageNo(7)
        Assert.assertEquals(PageSection.LEFT, section)
        Assert.assertEquals(6, printedPageNumber)
        Assert.assertEquals(2, pdfPageNo)
    }

    @Test
    fun getPreviousPageNoShouldReturnPreviousPageNumberForEvenPage() {
        val (pdfPageNo, printedPageNumber, section) = Utils.getPreviousPageNo(10)
        Assert.assertEquals(9, printedPageNumber)
        Assert.assertEquals(4, pdfPageNo)
        Assert.assertEquals(PageSection.RIGHT, section)
    }
}