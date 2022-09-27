package edu.miu.bdt;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

/**
 * @author Mustafizur Rahman Hilaly
 */
public class HiveSparkApplication {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local");
        sparkConf.set("hive.metastore.uris", "thrift://localhost:9083");
        sparkConf.set("hive.exec.scratchdir", "/tmp/myhive");

        SparkSession sparkSession = SparkSession.builder()
                .appName("SparkSQL-Hive")
                .config(sparkConf)
                .enableHiveSupport()
                .getOrCreate();

        // use Twitter database
        sparkSession.sqlContext().sql("USE twitter");

        // top Fifteen hashTag
        Dataset<Row> top15HashTag = sparkSession.sql(Queries.TOP_15_HASHTAG);
        top15HashTag.write().csv(args[0] + "/TopFifteenTags");

        // distinct user
        Dataset<Row> all = sparkSession.sql(Queries.SELECT_ALL);
        all.select(col("username")).distinct().write().csv(args[0] + "/DistinctUsers");

        // statistics table creation and put user count
        sparkSession.sql(Queries.CREATE_TABLE_STAT);
        sparkSession.sql(Queries.TOTAL_USERS_STAT);
    }
}