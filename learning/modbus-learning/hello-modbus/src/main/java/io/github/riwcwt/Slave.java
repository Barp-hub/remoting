package io.github.riwcwt;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.slave.ModbusTcpSlave;
import com.digitalpetri.modbus.slave.ModbusTcpSlaveConfig;
import com.digitalpetri.modbus.slave.ServiceRequestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class Slave {

	private static final Logger logger = LoggerFactory.getLogger(Slave.class);

	private final ModbusTcpSlaveConfig config = new ModbusTcpSlaveConfig.Builder().build();
	private final ModbusTcpSlave slave = new ModbusTcpSlave(config);

	public void start() throws ExecutionException, InterruptedException {
		slave.setRequestHandler(new ServiceRequestHandler() {

			@Override
			public void onChannelActive(ChannelHandlerContext ctx) {
				logger.info("channel active : " + ctx.channel().toString());

				// ModbusTcpPayload payload = new ModbusTcpPayload((short) 1,
				// (short) 1, new ReadHoldingRegistersRequest(0, 10));
				//
				// ctx.writeAndFlush(payload).addListener(future -> {
				// future.get();
				// });
			}

			@Override
			public void onReadHoldingRegisters(ServiceRequest<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> service) {
				ReadHoldingRegistersRequest request = service.getRequest();

				ByteBuf registers = PooledByteBufAllocator.DEFAULT.buffer(request.getQuantity());

				for (int i = 0; i < request.getQuantity(); i++) {
					registers.writeShort((int) (Math.random() * 64));
				}

				service.sendResponse(new ReadHoldingRegistersResponse(registers));

				ReferenceCountUtil.release(request);
			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				logger.info("stop...");
				Slave.this.stop();
			}
		});

		slave.bind("0.0.0.0", 502).get();
	}

	public void stop() {
		slave.shutdown();
	}

}
