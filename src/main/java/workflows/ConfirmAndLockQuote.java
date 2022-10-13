package workflows;

import base.DriverManager;
import base.PageObjectManager;
import org.apache.log4j.Logger;
import pageActions.QuoteListPageActions;

import java.util.Objects;

public class ConfirmAndLockQuote {

    private static final Logger logger = Logger.getLogger(ConfirmAndLockQuote.class);

    private ConfirmAndLockQuote(){}

    public static boolean lockTheQuote() throws InterruptedException {
        /***
         * This method clicks on the confirm and lock button
         * on successful quote lock it returns true, otherwise false
         */
        logger.info("This method clicks on the confirm and lock button");
        QuoteListPageActions quoteListPageActions = PageObjectManager.getQuoteListPageActions();
        if (quoteListPageActions.clickConfirmAndLockButtonIfDisplayed(DriverManager.getDriver())) {
            if (quoteListPageActions.checkIfSubmitReviewDialogDisplayed(DriverManager.getDriver())) {
                quoteListPageActions.enterQuoteReviewText(DriverManager.getDriver());
                quoteListPageActions.clickSubmitForReview(DriverManager.getDriver());
            } else {
                quoteListPageActions.checkIfQuoteLockSuccessMessageDisplayed(DriverManager.getDriver());
                String status = quoteListPageActions.getQuoteStatus(DriverManager.getDriver());
                assert Objects.equals(status, "Ready to Place Order");
            }
            return true;
        }else{
            logger.error("locking the quote is failed");
            return false;
        }
    }
}
