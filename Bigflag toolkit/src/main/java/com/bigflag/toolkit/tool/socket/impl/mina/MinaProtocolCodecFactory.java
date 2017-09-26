package com.bigflag.toolkit.tool.socket.impl.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

//编解码器生成工产
public class MinaProtocolCodecFactory implements ProtocolCodecFactory {
  private ProtocolEncoder encoder;
  private ProtocolDecoder decoder;

  //public LoginMsgProtocolCodecFactory()
  //{
//      this(Charset.forName("UTF-8"));
  //}

//  public LoginMsgProtocolCodecFactory(Charset charset)
//  {
//      encoder = new LoginMsgEncoder(charset);
//      decoder = new LoginMsgDecoder(charset);
//  }
  
  public MinaProtocolCodecFactory()
  {
      encoder = new IOTMsgEncoder();
      decoder = new IOTMsgDecoder();
  }

  @Override
  public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
      return decoder;
  }

  @Override
  public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
      return encoder;
  }

}