package org.monodev.scriptedSMP.bots;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.embedded.EmbeddedChannel;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.ProtocolInfo;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import org.jspecify.annotations.Nullable;

public class FakeConnection extends Connection {
    public boolean reportedConnectionStatus = true;

    public FakeConnection(PacketFlow receiving) {
        super(receiving);
        this.channel = new EmbeddedChannel();
    }

    @Override
    public <T extends PacketListener> void setupInboundProtocol(ProtocolInfo<T> protocol, T packetListener) {

    }

    @Override
    public void setupOutboundProtocol(ProtocolInfo<?> protocol) {

    }

    @Override
    public void setupCompression(int threshold, boolean validateDecompressed) {

    }

    @Override
    public void send(Packet<?> packet, @Nullable ChannelFutureListener listener, boolean flush) {

    }

    @Override
    public boolean isConnected() {
        return reportedConnectionStatus;
    }
}
