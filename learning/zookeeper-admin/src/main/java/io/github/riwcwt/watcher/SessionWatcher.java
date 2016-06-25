package io.github.riwcwt.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SessionWatcher implements ConnectionStateListener {
	private static final Logger logger = LoggerFactory.getLogger(SessionWatcher.class);

	@Override
	public void stateChanged(CuratorFramework client, ConnectionState newState) {
		logger.info(newState.toString());
	}

}
