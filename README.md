Bigflag Dev Toolkit - A well designed framework for IOT server development
===================================================
Overview
--------

First thing first, this framework is designed mainly for IOT domain. It can help developer to build up an IOT server in just a few steps. It can support TCP connection as the GPRS-like IOT device use and also support UDP connection as the NBIOT device use. Although the NBIOT device can also provide TCP connection to connect server, form my work experience, considering the NBIOT application scenarios, it usually require the NBIOT device work in a low power consumption, data delay tolerate situation so in most cases, NBIOT will adopt UDP as its connection protocol. So this framework also provides the base architecture to support UDP.

Besides the IOT connection architecture, the framework also provided Cache, Coordination, DB, RPC and Message Queue architectures. These are default implementation for these services, cache service is with Redis; coordination service is with Zookeeper; RPC is with apache HttpClient for restful type RPC, it use protobuf as the protocol to transmit bytes;
message queue is with RabbitMQ; DB is ORM based and with c3p0 connection pool and the framework support SQL routine, so you can use it to easily adopt the divided-database-divided-table methodology. Developers can easily adopt their own implementation by implement the service interfaces since there is an IOC to choose the corresponding implementation with outside configuration.

The framework is still in implementation phase but current version should work as a base framework for setting up an IOT domain server.

IOT Service
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
For AbstractIOTDeviceProcessor class, it contains IIOTDeviceIdentifier and IIOTDeviceProcessor. The IIOTDeviceIdentifier will check if data from device tells it is the right device the processor wants, if it is, the IIOTDeviceIdentifier will pass the data to IIOTDeviceProcessor for further process, is not then drop the data. for example.
``` java
// the Locker device identifier, if the data begins with 0xEE, then it is the Locker device
public class IOTLockerDeviceIdentifier implements IIOTDeviceIdentifier {

	public boolean isThisDevice(byte[] data) {
		return data.length>2&&data[0]==(byte) 0xEE;
	}
}

// the Lockert device processor, if the IIOTDeviceIdentifier recogize the device, then will continue process the data
public class IOTLockerDeviceProcessor implements IIOTDeviceProcessor {

    // process data for TCP connection
	public boolean processIOTData(long sessionID,byte[] data) {
		System.out.println("handled locker:"+Arrays.toString(data));
		return true;
	}
    // process data for UPD connection
	@Override
	public boolean processIOTUDPData(ISocketSession socketSession, byte[] data) {
        // for UDP connection, the best time to send data is the time when you receivce data from it
        // especially for NBIOT device in China
        socketSession.sendData(new byte[]{11,22});
		return true;
	}
}

public class IOTLockerProcessor extends AbstractIOTDeviceProcessor {
    
	public IOTLockerProcessor() {
        // to construct device processor with above two components
		super(new IOTLockerDeviceIdentifier(),new IOTLockerDeviceProcessor());
	}
}
```
`That is it, just two steps to connect your TCP IOT devices`
#### UDP Service for NBIOT-like device
To start UPD service for NBIOT-like device is almost the same with TCP, see below codes.
``` java
ISocketUDPService socketUdpService=ServiceFactory.getInstance().getDefaultSocketUDPService();
IIOTHandlerCenter iotHandlerCenter=ServiceFactory.getInstance().getDefaultIOTHandlerCenter();
// changed from startToListenTCP to startToListenUDP
socketUdpService.startToListenUDP(listenPort, (sessionID,data)->{
            // this is where you process the tcp incoming data
            // changed from processIOTData to processIOTUDPData
        	iotHandlerCenter.processIOTUDPData(sessionID,(byte[])data);
		}, (socketSession)->{
            // this is where tcp session created
			logger.info("socket session create:"+socketSession.getSessionID()+" sessionCount:"+socketUdpService.getAllSocketSessions().size());
		}, (socketSession)->{
            // this is where tcp session closed
			logger.info("socket session close:"+socketSession.getSessionID()+"sessionCount:"+socketUdpService.getAllSocketSessions().size());
		});
```
Just changed two parts for UPD connection and for IIOTDeviceProcessor, it will invoke processIOTUDPData(ISocketSession socketSession, byte[] data) 

To send data to a NBIOT device throug UDP, you need to cache the data first then let NBIOT device adopt PULL from server method to send
``` java
ServiceFactory.getInstance().getDefaultNBIOTService().cacheNBIOTData("00,00", new byte[]{11,22,33,44});
```

Cache Service
---
To use cache service, only to init it with connection informations, afte that, you can use the data save and retrieve method from ICacheToolService.
Current default cache service is for redis, so if you want to use the default one, you will need a redis server to connect.
```java
ICacheToolService cacheService=ServiceFactory.getInstance().getDefaultCacheToolService();
cacheService.initCacheService(BaseCacheConfigBean configBean);
cacheService.saveValueByKey(String key,String value);
cacheService.findValueByKey(String key);
```

Message Queue Service
---
For distributed system, in IOT domain, usually the IOT data will pass to many systems, so the MQ can be used as the IOT data center to distribute to other systems.

``` java
// get the default MQ service which is with RabbitMQ
IMQToolService mqService=ServiceFactory.getInstance().getDefaultMQService();
// connect server
mqService.connectServer(BaseMQConfigBean configBean);
// register the interesting subject, when the subject has message, OnReceiveMsg will receive
mqService.registerInterestingSubject(String subject,String routingKey,OnReceiveMsg onReceiveMsg);
// to send the message to queue, now can only broadcast msg to client who are interested
mqService.sendMsg(String subject,String routingKey,byte[] msg, MQSendType mqSendType);

```
RPC Service
---
The RPC adopt restful methodology. It transmits bytes with protobuf protocol through Apache HttpClient. I did not use Thrift or RMI as the base framework is because that I want it to be used as widely as possible. If you are a fan of thrift or RMI, you can still have your solution by implement the RPC interfaces.

The HttpToolService can compress and encrypt the byte data using builder.
``` java
IHttpToolService httpToolService=ServiceFactory.getInstance().getDefaultHttpPostToolImpl();
httpToolService.startToPostBytes(new byte[]{11,22,33}).compressBytes(IHttpCompressor,IHttpDecompressor).encryptBytes(IHttpEncrypter, IHttpDecrypter).doPostBytes("http://test.com");
```
Cooridation Service
---
WIP

DB Service
---
WIP

IOC Service
---
WIP

Utils For IOT
---
There are functions for IOT domain
``` java
// this function will return 11,22,33 it convert the bytes to a readable String
BigflagTools.byteToHexString(new Byte[]{0x11,0x22,0x33});
// this function will return byte[]{0x11,0x22,0x33} it convert the readable String to bytes
BigflagTools.hexStringToBytes("11,22,33");
// this function will return the xor result of the input hexStr in above format
BigflagTools.getXorString(String hexStr) {
```
