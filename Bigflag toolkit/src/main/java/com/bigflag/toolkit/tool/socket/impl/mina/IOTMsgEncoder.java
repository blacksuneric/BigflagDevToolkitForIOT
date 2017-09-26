package com.bigflag.toolkit.tool.socket.impl.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class IOTMsgEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception {

        IoBuffer buffer = IoBuffer.allocate(100, false).setAutoExpand(true);

        buffer.put((byte[])message);
        buffer.flip();
        out.write(buffer);

    }
    @Override
    public void dispose(IoSession session) throws Exception {

    }

}