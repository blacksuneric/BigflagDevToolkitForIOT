Bigflag Dev Toolkit - A well designed framework for IOT server development
===================================================
Overview
--------

First thing first, this framework is designed mainly for IOT domain. It can help developer to build up an IOT server in just a few steps. It can support TCP connection as the GPRS-like IOT device use and also support UDP connection as the NBIOT device use. Although the NBIOT device can also provide TCP connection to connect server, form my work experience, considering the NBIOT application scenarios, it usually require the NBIOT device work in a low power consumption, data delay tolerate situation so in most cases, NBIOT will adopt UDP as its connection protocol. So this framework also provides the base architecture to support UDP.

Besides the IOT connection architecture, the framework also provided Cache, Coordination, DB, RPC and Message Queue architectures. These are default implementation for these services, cache service is with Redis; coordination service is with Zookeeper; RPC is with apache HttpClient for restful type RPC, it use protobuf as the protocol to transmit bytes; message queue is with RabbitMQ; BD is ORM based and with c3p0 connection pool and the framework support SQL routine, so you can use it to easily adopt the divided-database-divided-table methodology. Developers can easily adopt their own implementation by implement the service interfaces since there is an IOC to choose the corresponding implementation with outside configuration.

The framework is still in implementation phase but current version should work as a base framework for setting up an IOT domain server.

IOT
---
#### TCP Service for GPRS-like device
To start TCP service for GPRS-like device to process data, you can follow below steps with demo code. 
##### 1. start tcp service listen

``` java
ISocketTCPService socketTcpService=ServiceFactory.getInstance().getDefaultSocketTCPService();
IIOTHandlerCenter iotHandlerCenter=ServiceFactory.getInstance().getDefaultIOTHandlerCenter();
socketTcpService.startToListenTCP(listenPort, (sessionID,data)->{
            // this is where you process the tcp incoming data
    		iotHandlerCenter.processIOTData(sessionID,(byte[])data);
		}, (socketSession)->{
            // this is where tcp session created
			logger.info("socket session create:"+socketSession.getSessionID()+" sessionCount:"+socketTcpService.getAllSocketSessions().size());
		}, (socketSession)->{
            // this is where tcp session closed
			logger.info("socket session close:"+socketSession.getSessionID()+"sessionCount:"+socketTcpService.getAllSocketSessions().size());
		});
```
##### 2. register IOT device handlers
Alternatively, you can do step 2 before step 1. The default IIOTHandlerCenter is thread safe.
```java
iotHandlerCenter.registerIOTProcessor(new AbstractIOTDeviceProcessor());
```
