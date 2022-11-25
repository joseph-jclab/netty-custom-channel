import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler

class TempInitializer : ChannelInitializer<PseudoChannel>() {
    override fun initChannel(ch: PseudoChannel) {
        println("initChannel: " + ch)
    }
}

fun main(args: Array<String>) {
    val bossGroup = NioEventLoopGroup(1)
    val workerGroup = NioEventLoopGroup()

    val channel = ServerBootstrap()
        .group(bossGroup, workerGroup)
        .channel(PseudoServerChannel::class.java)
        .handler(LoggingHandler(LogLevel.INFO))
        .childHandler(TempInitializer())
        .bind(PseudoSocketAddress())
        .sync()
        .channel()

    println("channel: " + channel)
}