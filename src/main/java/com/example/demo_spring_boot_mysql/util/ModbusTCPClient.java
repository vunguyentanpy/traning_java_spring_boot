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
    private static final int RECONNECT_DELAY_MS = 5000;

    public static void main(String[] args) {
        ModbusTCPClient client = new ModbusTCPClient();
        try {
            client.start(10000);
        } catch (RuntimeException e) {
            System.err.println("RuntimeException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private void start(long timeout) {
        this.timeout = timeout;
        Thread thread = new Thread(this);
        thread.start();
        try {
            thread.join(10000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        TcpParameters tcpParameters = new TcpParameters();
        try {
            tcpParameters.setHost(InetAddress.getByName("192.168.80.100"));
            tcpParameters.setPort(1502);
        } catch (Exception e) {
            System.err.println("Failed to set TCP parameters: " + e.getMessage());
            return;
        }

        Modbus.setAutoIncrementTransactionId(true);

        while (true) {
            try {
                connect(tcpParameters);

                long time = System.currentTimeMillis();
                while ((System.currentTimeMillis() - time) < timeout) {
                    try {
                        Thread.sleep(100);

                        int slaveId = 1;
                        int offset = 1;
                        int quantity = 10;
                        int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);
                        for (int value : registerValues) {
                            System.out.println("Register Value: " + value);
                        }
                    } catch (ModbusIOException e) {
                        System.err.println("ModbusIOException: " + e.getCause().getMessage());
                        break;
                    } catch (Exception e) {
                        System.err.println("Error reading registers: " + e.getMessage());
                    }
                }
                master.disconnect();
            } catch (Exception e) {
                System.err.println("Error during connection: " + e.getMessage());
            } finally {
                try {
                    Thread.sleep(RECONNECT_DELAY_MS);
                } catch (InterruptedException e) {
                    System.err.println("Interrupted during reconnect delay: " + e.getMessage());
                }
            }
        }
    }

    private void connect(TcpParameters tcpParameters) throws Exception {
        master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
        master.connect();
        System.out.println("Connected to Modbus device");
    }
}