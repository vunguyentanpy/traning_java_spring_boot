package com.example.demo_spring_boot_mysql.modbus;
import com.example.demo_spring_boot_mysql.util.Lib;
import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.InetAddress;

public class ModbusDeviceJob implements Job {
    Lib libInstance = new Lib();
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
            int quantity = 5;
            int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
            String Datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            System.out.println("Time " + Datetime);
            for (int value : registerValues) {

               int valueOfAddress=  libInstance.toSigned16Bit(value);
                System.out.println("Device " + ipAddress + " Register Value: " + valueOfAddress);
            }
            System.out.println(libInstance.toFloat32BigEndian(registerValues));
//            master.writeSingleRegister(1, 1, 100);
            master.disconnect();
        } catch (ModbusIOException e) {
            System.err.println("ModbusIOException at device " + ipAddress + ": " + e.getCause().getMessage());
        } catch (Exception e) {
            System.err.println("Error at device " + ipAddress + ": " + e.getMessage());
        }
    }
}
