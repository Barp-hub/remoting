package io.github.riwcwt.watcher;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DataWatcher implements CuratorWatcher {
	private static final Logger logger = LoggerFactory.getLogger(DataWatcher.class);

	@Override
	public void process(WatchedEvent event) throws Exception {
		logger.info(event.getPath());
	}

}
