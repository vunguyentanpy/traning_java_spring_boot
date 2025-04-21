package com.example.demo_spring_boot_mysql.modbus;



import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class ModbusScheduler {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        int numberOfDevices = 100;

        for (int i = 0; i < numberOfDevices; i++) {
            String ipAddress = "192.168.80." + (100 + i);
            int port = 1502;
            int slaveId = 1;

            JobDetail jobDetail = JobBuilder.newJob(ModbusDeviceJob.class)
                    .withIdentity("deviceJob" + i, "group1")
                    .usingJobData("ipAddress", ipAddress)
                    .usingJobData("port", port)
                    .usingJobData("slaveId", slaveId)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("deviceTrigger" + i, "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }

        scheduler.start();
    }
}

