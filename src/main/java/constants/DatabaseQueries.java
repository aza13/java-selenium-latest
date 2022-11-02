package constants;

import base.BaseTest;
import utils.fileReader.ConfigDataReader;

public class DatabaseQueries extends BaseTest {

    private DatabaseQueries(){}

    public static final String GET_BROKERS_QUERY = "select * from broker LIMIT 10;";
    public static final String GET_QUOTE_FOR_HARD_DECLINE ="select * from submission WHERE broker_id = 20217 AND STATUS = 'active' AND  \n" +
            "expiration_date >= (CURDATE() + INTERVAL 90 DAY ) AND origin = 'broker_portal'\n" +
            " LIMIT 10";
    public static final String GET_SUBMISSIONS_WITH_CONFIRMED_QUOTES = "SELECT b.id,s.id FROM snapqa_10.quote q \n" +
            "            inner join snapqa_10.submission s\n" +
            "            on s.id=q.submission_id\n" +
            "            inner join auth.broker b on b.id=s.broker_id \n" +
            "            where q.is_locked=1 and q.type='quote' and q.status='active' and s.broker_id=7504 and s.product_id=4992 and s.status='active' AND s.is_option_ordered =0 order by s.id desc;";
    public static final String GET_SUBMISSIONS_WITH_BINDER_DOCUMENT = "SELECT b.id,s.id FROM snapqa_10.quote q \n" +
            "            inner join snapqa_10.submission s\n" +
            "            on s.id=q.submission_id\n" +
            "            inner join auth.broker b on b.id=s.broker_id \n" +
            "            where q.is_locked=1 and q.type='quote' and q.status='Active' and s.broker_id=7504 and s.product_id=4992 and s.status='active' AND s.policy_id IS NOT null AND s.is_bound= 1 order by s.id desc;";

    public static final String GET_SUBMISSION_ID_WITH_QUOTE_ID = "select submission_id from "+ ConfigDataReader.getInstance().getProperty("dbSchema")+".quote where id=";
    public static final String UPDATE_IN_REVIEW_SUBMISSION_TO_ACTIVE = "update auth.submission set status='active' where id=";

    // update subjectivity status
    public static final String UPDATE_SUBJECTIVITY_STATUS = "UPDATE "+ConfigDataReader.getInstance().getProperty("dbSchema")+".quote_subjectivity SET STATUS='accepted' WHERE status!='deleted' AND is_due_before_binding=1 AND quote_id=";
    public static final String GET_QUOTE_ID_WITH_SUBMISSION_ID = "Select id from "+ConfigDataReader.getInstance().getProperty("dbSchema")+".quote where submission_id='";

    public static final String GET_INELIGIBLE_POLICIES ="SELECT  p.number\n" +
            "FROM snapqa_10.submission AS s \n" +
            "JOIN policy AS p ON p.id = s.policy_id\n" +
            "WHERE p.status = 'active' AND s.is_quoteit_ineligible =1 and s.broker_id=7504  and s.status='active'";
}
