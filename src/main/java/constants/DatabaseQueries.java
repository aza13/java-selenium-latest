package constants;

public class DatabaseQueries {

    private DatabaseQueries(){}

    public static final String GET_BROKERS_QUERY = "select * from broker LIMIT 10;";
    public static final String GET_QUOTE_FOR_HARD_DECLINE ="select * from submission WHERE broker_id = 20217 AND STATUS = 'active' AND  \n" +
            "expiration_date >= (CURDATE() + INTERVAL 90 DAY ) AND origin = 'broker_portal'\n" +
            " LIMIT 10";
    public static final String GET_SUBMISSIONS_WITH_CONFIRMED_QUOTES = "SELECT b.id,s.id FROM auth.quote q " +
            "inner join auth.submission s " +
            "on s.id=q.submission_id " +
            "inner join auth.broker b on b.id=s.broker_id " +
            "where q.is_locked=1 and q.type='quote' and q.status='active' and s.broker_id=8804 and s.product_id=4992 and s.status='active' order by s.id desc;";

    public static final String GET_SUBMISSION_ID_WITH_QUOTE_ID = "select submission_id from auth.quote where id=";
    public static final String UPDATE_IN_REVIEW_SUBMISSION_TO_ACTIVE = "update auth.submission set status='active' where id=";
}
