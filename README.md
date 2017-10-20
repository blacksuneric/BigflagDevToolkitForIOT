Bigflag Dev Toolkit - A well designed framework for IOT server development
===================================================
Overview
--------

First thing first, this framework is designed mainly for IOT domain. It can help developer to build up an IOT server in just a few steps. It can support TCP connection as the GPRS-like IOT device use and also support UDP connection as the NBIOT device use. Although the NBIOT device can also provide TCP connection to connect server, from my work experience, considering the NBIOT application scenarios, it usually require the NBIOT device work in a low power consumption, data delay tolerate situation so in most cases, NBIOT will adopt UDP as its connection protocol. So this framework also provides the base architecture to support UDP.

Besides the IOT connection architecture, the framework also provided Cache, Coordination, DB, RPC and Message Queue architectures. These are default implementation for these services, cache service is with Redis; Coordination service is with Zookeeper; RPC is with apache HttpClient for restful type RPC, it use protobuf as the protocol to transmit bytes;
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
For AbstractIOTDeviceProcessor class, it contains IIOTDeviceIdentifier and IIOTDeviceProcessor. The IIOTDeviceIdentifier will check if data from device tells it is the right device the processor wants, if it is, the IIOTDeviceIdentifier will pass the data to IIOTDeviceProcessor for further process, if not then drop the data. for example.
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
#### Encrypted Code Device
The encrypted code device is a type of IOT device which cannot connect to cloud. It sounds a little weird that an IOT device cannot connect to cloud, but it is true. I have a product which is a door lock. It is installed inside the door, powered by battery, so the power consumption is critical. It can only be BLE, NFC or other low power consumption method to connect to cloud. But for BLE or NFC, it requires user’s phone have such device as connection host. What can user do when user use an old fashion phone? So, the encrypted code device is the solution.

The encrypted code device has a number keyboard, user can obtain the password by any defined means, and input the password into the device, and then the device will verify the password and behaves. The problem is that the password should be changed over time; otherwise once user knows the password, the user can operate the device forever. And again, remember the device cannot connect to cloud, so the only way is that the device and cloud should generate the same password over time. The framework have provide the service for this situation.

``` java
String deviceCode="1234567";
// the method use current time and deviceCode to generate the dynamic password, user can use this password to operate device
// the device will check the input password inside it and caculate the password creation time, if it expires then the password is invalid
ServiceFactory.getInstance().getDefaultEncryptedCodeDeviceService().getEncryptedCodeForDevice(DateTime.now().toDate(), deviceCode);
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
Coordination Service
---
Currently, the default coordinate service is with zookeeper, again you can introduce your own easily as the framework is not using any zookeeper specific interfaces, in other words, the framework abstracted the zookeeper interfaces but still adopt the methodology of it.

####1 connect to coordinate server
```java
String connectURI="host:port";
int connectTimeout=30000;
ICoordinatorToolService coService = ServiceFactory.getInstance().getDefaultCoordinatorToolService();
coService.connectServer(new BaseCoordinatorConfigBean.Builder().connectUrl(connectURI).timeout(connectTimeout).build());
```
####2.1 create ephemeral path
This will create the ephemeral type path. If the coordinate client drop connection, the ephemeral path will be removed automatically. 
```java
String nodePath="/testNode";
byte[] nodeData="testData".getBytes();
boolean isSequential=false;
coService.createEphemeralPath(nodePath, nodeData, isSequential);
```

####2.2 create persistent path
This will create the persistent type path. The path will exist if the coordinate client drop connection.
```java
String nodePath="/testNode";
byte[] nodeData="testData".getBytes();
boolean isSequential=false;
coService.createPersistentPath(nodePath, nodeData, isSequential);
```

####3 get the node data
if the second parameter is true, then the node will be repeatly watched. Everytime the data of the node get changed,
then, the third lambda interface will be invoked.

```java
byte[] firstData=coService.getNodeData("/testOne/testData", true, (eventType,path,data)->{
    		System.out.println("test getData "+eventType+" "+path+" "+new String(data));
		});
```

####4 set the node data
```java
coService.setData("/testOne", DateTime.now().toLocalTime().toString().getBytes());
```

####5 get the children of node
if the second parameter is true, then the node children will be repeatly watched. Everytime the children of the node get changed,
added or removed, the third lambda interface will be invoked.
```java
List<String> childNodes=coService.getNodeChildren("/testOne", true, (eventType,path,nodes)->{
    		System.out.println("test childNodes "+eventType+" "+path+" "+Arrays.toString(nodes.toArray()));
		});
```

####6 remove node
```java
coService.removePath("/testOne");
```

####7 check if node exists
if the second parameter is true, then everytime to add or remove the node,
the third lambda interface will be invoked.
```java
boolean isExists=coService.existNode("/testOne", true, (eventType,path,data)->{
			System.out.println("test exist "+eventType+" "+path);
		});
```


ESB
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
