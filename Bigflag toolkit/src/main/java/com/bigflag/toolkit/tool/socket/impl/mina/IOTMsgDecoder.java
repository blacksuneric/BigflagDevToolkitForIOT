package com.bigflag.toolkit.tool.socket.impl.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class IOTMsgDecoder extends CumulativeProtocolDecoder {    

  @Override
  protected boolean doDecode(IoSession session, IoBuffer buffer,ProtocolDecoderOutput out) throws Exception {
	  
	  byte[] content=new byte[buffer.remaining()];
	  buffer.get(content);
	  out.write(content);
	  return true;

  }

}