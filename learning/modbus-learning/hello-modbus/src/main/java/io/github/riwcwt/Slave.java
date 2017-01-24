package io.github.riwcwt;

import java.util.concurrent.ExecutionException;

import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.slave.ModbusTcpSlave;
import com.digitalpetri.modbus.slave.ModbusTcpSlaveConfig;
import com.digitalpetri.modbus.slave.ServiceRequestHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ReferenceCountUtil;

public class Slave {
	private final ModbusTcpSlaveConfig config = new ModbusTcpSlaveConfig.Builder().build();
	private final ModbusTcpSlave slave = new ModbusTcpSlave(config);

	public void start() throws ExecutionException, InterruptedException {
		slave.setRequestHandler(new ServiceRequestHandler() {

			@Override
			public void onReadHoldingRegisters(ServiceRequest<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> service) {
				ReadHoldingRegistersRequest request = service.getRequest();

				ByteBuf registers = PooledByteBufAllocator.DEFAULT.buffer(request.getQuantity());

				for (int i = 0; i < request.getQuantity(); i++) {
					registers.writeShort(i);
				}

				service.sendResponse(new ReadHoldingRegistersResponse(registers));

				ReferenceCountUtil.release(request);
			}
		});

		slave.bind("localhost", 502).get();
	}

	public void stop() {
		slave.shutdown();
	}

}
