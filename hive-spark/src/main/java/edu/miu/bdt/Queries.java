package edu.miu.bdt;

/**
 * @author Mustafizur Rahman Hilaly
 */
public class Queries {
    public static final String SELECT_ALL = "SELECT * FROM tweets";

    public static final String TOP_15_HASHTAG =
            " SELECT hashtag, COUNT(hashtag) AS cnt FROM " +
                    " tweets GROUP BY hashtag " +
                    " ORDER BY cnt DESC " +
                    " LIMIT 15";

    public static final String CREATE_TABLE_STAT = " CREATE TABLE IF NOT EXISTS stat ( total int )";

    public static final String TOTAL_USERS_STAT = " INSERT INTO stat SELECT COUNT(username) FROM tweets";
}
