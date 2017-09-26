package com.bigflag.toolkit.tool.socket.impl.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class IOTMsgEncoder implements ProtocolEncoder {
    private Charset charset;

    public IOTMsgEncoder(Charset charset) {
        this.charset = charset;
    }
    
    public IOTMsgEncoder() {
    }

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {

        IoBuffer buffer = IoBuffer.allocate(100, false).setAutoExpand(true);

        //buffer.put((byte) 0x31);
//        buffer.putShort(lm.getStartBit());
//        buffer.put(lm.getPackageLen());
//        buffer.put(lm.getProtocolNo());
//
//        buffer.put(lm.getContent().getBytes(charset));
//
//        buffer.putShort(lm.getSerialNumber());
//        buffer.putShort(lm.getErrorCheck());
//        buffer.putShort(lm.getEndBit());
        buffer.put((byte[])message);
        buffer.flip();
        out.write(buffer);

    }
    @Override
    public void dispose(IoSession session) throws Exception {

    }

}