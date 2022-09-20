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
            "            where q.is_locked=1 and q.type='quote' and q.status='active' and s.broker_id=7504 and s.product_id=4992 and s.status='active' order by s.id desc;";
    public static final String GET_SUBMISSIONS_WITH_BINDER_DOCUMENT = "SELECT b.id,s.id FROM snapqa_10.quote q \n" +
            "            inner join snapqa_10.submission s\n" +
            "            on s.id=q.submission_id\n" +
            "            inner join auth.broker b on b.id=s.broker_id \n" +
            "            where q.is_locked=1 and q.type='quote' and q.status='Active' and s.broker_id=7504 and s.product_id=4992 and s.status='active' AND s.policy_id IS NOT null order by s.id desc;";

    public static final String GET_SUBMISSION_ID_WITH_QUOTE_ID = "select submission_id from "+ ConfigDataReader.getInstance().getProperty("dbSchema")+".quote where id=";
    public static final String UPDATE_IN_REVIEW_SUBMISSION_TO_ACTIVE = "update auth.submission set status='active' where id=";
}
