import io.netty.channel.*
import java.net.InetSocketAddress
import java.net.SocketAddress

open class PseudoChannel(parent: Channel, id: ChannelId) : AbstractChannel(parent, id) {
    override fun metadata(): ChannelMetadata = ChannelMetadata(false)
    override fun config(): ChannelConfig = DefaultChannelConfig(this)
    override fun isCompatible(loop: EventLoop?) = true

    override fun isOpen(): Boolean {
        return true
    }

    override fun isActive(): Boolean {
        return true
    }

    override fun localAddress0(): SocketAddress {
        return InetSocketAddress.createUnresolved("1.2.3.4", 20001)
    }

    override fun remoteAddress0(): SocketAddress {
        return InetSocketAddress.createUnresolved("1.2.3.4", 20002)
    }

    override fun doBind(localAddress: SocketAddress?) {
        throw UnsupportedOperationException("ChildChannel doesn't support bind()")
    }

    override fun doDisconnect() {
        println("CLIENT: doDisconnect")
    }

    override fun doClose() {
        println("CLIENT: PseudoChannel")
    }

    override fun doBeginRead() {
        println("CLIENT: doBeginRead")
    }

    override fun doWrite(`in`: ChannelOutboundBuffer?) {
        println("CLIENT: doWrite")
    }

    override fun newUnsafe(): AbstractUnsafe = MUnsafe()

    private inner class MUnsafe : AbstractUnsafe() {
        override fun connect(remoteAddress: SocketAddress?, localAddress: SocketAddress?, promise: ChannelPromise?) {
            throw UnsupportedOperationException("ChildChannel doesn't support connect()")
        }
    }
}