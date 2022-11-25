import io.netty.channel.*
import java.net.InetSocketAddress
import java.net.SocketAddress

class PseudoServerChannel : AbstractServerChannel() {
    private val config = DefaultChannelConfig(this)

    private var closed: Boolean = false

    override fun config(): ChannelConfig {
        println("PseudoServerChannel: config")
        return config
    }

    override fun isOpen(): Boolean {
        println("PseudoServerChannel: isOpen")
        return !closed
    }

    override fun isActive(): Boolean {
        println("PseudoServerChannel: isActive")
        return this.isOpen
    }

    override fun isCompatible(loop: EventLoop): Boolean {
        println("PseudoServerChannel: doBeginRead")
        return true
    }

    override fun localAddress0(): SocketAddress? {
        println("PseudoServerChannel: doBeginRead")
        return InetSocketAddress.createUnresolved("1.2.3.4", 111)
    }

    override fun doBind(localAddress: SocketAddress) {
        println("PseudoServerChannel: doBind")
    }

    override fun doClose() {
        println("PseudoServerChannel: doClose")
        closed = true
    }

    override fun doBeginRead() {
        println("PseudoServerChannel: doBeginRead")

        Thread({
            this.eventLoop().register(PseudoChannel(this, DefaultChannelId.newInstance()))
        }).start()
    }
}