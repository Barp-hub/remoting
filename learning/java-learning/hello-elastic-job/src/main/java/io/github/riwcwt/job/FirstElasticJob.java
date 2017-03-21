package io.github.riwcwt.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by michael on 2017-03-21.
 */
public class FirstElasticJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(FirstElasticJob.class);

    @Override
    public void execute(ShardingContext context) {
        logger.info("sharding item : " + context.getShardingItem());
        logger.info("task id : " + context.getTaskId());
    }
}
