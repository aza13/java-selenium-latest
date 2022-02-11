package constants;

public class DatabaseQueries {

    private DatabaseQueries(){}

    public static final String GET_BROKERS_QUERY = "select * from broker LIMIT 10;";
    public static final String GET_QUOTE_FOR_HARD_DECLINE ="select * from submission WHERE broker_id = 20217 AND STATUS = 'active' AND  \n" +
            "expiration_date >= (CURDATE() + INTERVAL 90 DAY ) AND origin = 'broker_portal'\n" +
            " LIMIT 10";

}
