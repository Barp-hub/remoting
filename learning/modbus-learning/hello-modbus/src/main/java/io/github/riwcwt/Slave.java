package io.github.riwcwt;

import java.util.concurrent.ExecutionException;

import com.digitalpetri.modbus.requests.MaskWriteRegisterRequest;
import com.digitalpetri.modbus.requests.ReadCoilsRequest;
import com.digitalpetri.modbus.requests.ReadDiscreteInputsRequest;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.requests.ReadInputRegistersRequest;
import com.digitalpetri.modbus.requests.WriteMultipleCoilsRequest;
import com.digitalpetri.modbus.requests.WriteMultipleRegistersRequest;
import com.digitalpetri.modbus.requests.WriteSingleCoilRequest;
import com.digitalpetri.modbus.requests.WriteSingleRegisterRequest;
import com.digitalpetri.modbus.responses.MaskWriteRegisterResponse;
import com.digitalpetri.modbus.responses.ReadCoilsResponse;
import com.digitalpetri.modbus.responses.ReadDiscreteInputsResponse;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.responses.ReadInputRegistersResponse;
import com.digitalpetri.modbus.responses.WriteMultipleCoilsResponse;
import com.digitalpetri.modbus.responses.WriteMultipleRegistersResponse;
import com.digitalpetri.modbus.responses.WriteSingleCoilResponse;
import com.digitalpetri.modbus.responses.WriteSingleRegisterResponse;
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
			public void onReadInputRegisters(ServiceRequest<ReadInputRegistersRequest, ReadInputRegistersResponse> service) {
				ServiceRequestHandler.super.onReadInputRegisters(service);
			}

			@Override
			public void onReadCoils(ServiceRequest<ReadCoilsRequest, ReadCoilsResponse> service) {
				ServiceRequestHandler.super.onReadCoils(service);
			}

			@Override
			public void onReadDiscreteInputs(ServiceRequest<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> service) {
				ServiceRequestHandler.super.onReadDiscreteInputs(service);
			}

			@Override
			public void onWriteSingleRegister(ServiceRequest<WriteSingleRegisterRequest, WriteSingleRegisterResponse> service) {
				ServiceRequestHandler.super.onWriteSingleRegister(service);
			}

			@Override
			public void onWriteMultipleCoils(ServiceRequest<WriteMultipleCoilsRequest, WriteMultipleCoilsResponse> service) {
				ServiceRequestHandler.super.onWriteMultipleCoils(service);
			}

			@Override
			public void onWriteMultipleRegisters(ServiceRequest<WriteMultipleRegistersRequest, WriteMultipleRegistersResponse> service) {
				ServiceRequestHandler.super.onWriteMultipleRegisters(service);
			}

			@Override
			public void onMaskWriteRegister(ServiceRequest<MaskWriteRegisterRequest, MaskWriteRegisterResponse> service) {
				ServiceRequestHandler.super.onMaskWriteRegister(service);
			}

			@Override
			public void onWriteSingleCoil(ServiceRequest<WriteSingleCoilRequest, WriteSingleCoilResponse> service) {
				WriteSingleCoilRequest request = service.getRequest();

				service.sendResponse(new WriteSingleCoilResponse(request.getAddress(), request.getValue()));

				ReferenceCountUtil.release(request);
			}

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
