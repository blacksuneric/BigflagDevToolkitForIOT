package com.bigflag.toolkit.tool.socket.impl.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

//消息解码器
public class IOTMsgDecoder extends CumulativeProtocolDecoder {    

  private Charset charset;

  public IOTMsgDecoder(Charset charset) {
      this.charset = charset;
  }
  
  public IOTMsgDecoder() {
  }

  @Override
  protected boolean doDecode(IoSession session, IoBuffer buffer,ProtocolDecoderOutput out) throws Exception {
	  
	  System.out.println("hexdump:"+buffer.getHexDump());
	  byte[] content=new byte[buffer.remaining()];
	  buffer.get(content);
	  out.write(content);
	  return true;
//      while (buffer.remaining() > 3) {
//          //记录解码数据起始位置
//          buffer.mark();
//          // 读取包格式及大小
//          short s = buffer.getShort();
//          byte length = buffer.get();             
//          // 检查读取的包头是否正常，不正常的话清空buffer
//          if (length < 0) {
//              buffer.clear();         
//              break;
//          } else if (length > 3 && buffer.remaining() >= length+7) {
////              LoginMsg lm = new LoginMsg();
////
////              lm.setStartBit(s);
////              lm.setPackageLen(length);
////              lm.setProtocolNo(buffer.get());
////
////              byte[] content = new byte[lm.getPackageLen()];
////              buffer.get(content);
////              lm.setContent(new String(content,charset));
////
////              lm.setSerialNumber(buffer.getShort());
////              lm.setErrorCheck(buffer.getShort());
////              lm.setEndBit(buffer.getShort());
//        	  
//        	  byte[] content=new byte[1024];
//        	  for(byte b:content)
//        	  {
//        		  System.out.println(b);
//        	  }
//        	  buffer.get(content);
//              out.write(buffer);
//              return true;
//          } else {    
//              // 如果消息包不完整,将指针重新移动消息头的起始位置 
//              buffer.reset();
//              return false;
//          }           

//      }
//      return false;
  }

}