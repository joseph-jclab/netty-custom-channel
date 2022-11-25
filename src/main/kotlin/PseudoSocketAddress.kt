import java.net.SocketAddress
import java.util.*

class PseudoSocketAddress : SocketAddress() {
    val uniqueId = UUID.randomUUID().toString()
}