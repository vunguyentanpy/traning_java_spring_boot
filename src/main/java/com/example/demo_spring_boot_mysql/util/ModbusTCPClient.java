package com.example.demo_spring_boot_mysql.util;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;





import java.net.InetAddress;
import java.net.UnknownHostException;

public class ModbusTCPClient implements Runnable {
    private ModbusMaster master;
    private long timeout = 0;


    public static void main(String[] args)

	{
        ModbusTCPClient client = new ModbusTCPClient();

        try {

            client.start(10000);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
    private void start(long timeout) {
        this.timeout = timeout;
        Thread thread = new Thread(this);
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            TcpParameters tcpParameters = new TcpParameters();
            tcpParameters.setHost(InetAddress.getByName("192.168.80.100"));
            tcpParameters.setPort(1502);
            ModbusMaster master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            master.connect();
            Modbus.setAutoIncrementTransactionId(true);
            long time = System.currentTimeMillis();
            while ((System.currentTimeMillis() - time) < timeout) {
                try {
                    Thread.sleep(100);

                    int slaveId = 1;
                    int offset = 1;
                    int quantity = 10;
                    if (master != null) {
                        int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
                        for (int value : registerValues) {
                            System.out.println("Register Value: " + value);
                        }
                    }
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                master.disconnect();
            } catch (ModbusIOException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	}
