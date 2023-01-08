package urdu.sabirdanish

import java.util.concurrent.atomic.AtomicReference

data class Book(val pageNumber: Int, val bookUrl: Int, val maxPages: Int)

object CurrentOpenBook {
    var screenHeight: Int = 1050
    var screenWidth: Int = 1000
    private val currentBookStatus = AtomicReference(Book(1, R.raw.taskeen, 67))
    fun getBookInfo(): Book = currentBookStatus.get()
    fun getPageNo(): Int = currentBookStatus.get().pageNumber
    fun getBookResource(): Int = currentBookStatus.get().bookUrl

    fun openBook(bookResource: Int, maxPages: Int) =
        currentBookStatus.set(Book(1, bookResource, maxPages))

    fun getNextPageNumber(): Int {
        val maxPages = currentBookStatus.get().maxPages
        val nextNumber = currentBookStatus.get().pageNumber + 1
        val nextPage = if (nextNumber >= maxPages) maxPages else nextNumber
        currentBookStatus.set(
            Book(nextPage, currentBookStatus.get().bookUrl, maxPages)
        )
        return nextPage
    }

    fun getPreviousPageNumber(): Int {
        val maxPages = currentBookStatus.get().maxPages
        val previousNumber = currentBookStatus.get().pageNumber - 1
        val previousPage = if (previousNumber < 1) 1 else previousNumber
        currentBookStatus.set(
            Book(previousPage, currentBookStatus.get().bookUrl, maxPages)
        )
        return previousPage
    }

    fun goToPageNo(pageNo: Int) =
        currentBookStatus.set(
            Book(
                pageNo,
                currentBookStatus.get().bookUrl,
                currentBookStatus.get().maxPages
            )
        )

}