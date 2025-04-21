package com.example.demo_spring_boot_mysql.modbus;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.InetAddress;

public class ModbusDeviceJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String ipAddress = context.getJobDetail().getJobDataMap().getString("ipAddress");
        int port = context.getJobDetail().getJobDataMap().getInt("port");
        int slaveId = context.getJobDetail().getJobDataMap().getInt("slaveId");

        TcpParameters tcpParameters = new TcpParameters();
        try {
            tcpParameters.setHost(InetAddress.getByName(ipAddress));
            tcpParameters.setPort(port);
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            master.connect();
            Modbus.setAutoIncrementTransactionId(true);


            int offset = 1;
            int quantity = 10;
            int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
            for (int value : registerValues) {
                System.out.println("Device " + ipAddress + " Register Value: " + value);
            }

            master.disconnect();
        } catch (ModbusIOException e) {
            System.err.println("ModbusIOException at device " + ipAddress + ": " + e.getCause().getMessage());
        } catch (Exception e) {
            System.err.println("Error at device " + ipAddress + ": " + e.getMessage());
        }
    }
}
