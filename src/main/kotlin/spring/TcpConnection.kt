package spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.ip.tcp.TcpInboundGateway
import org.springframework.integration.ip.tcp.TcpOutboundGateway
import org.springframework.integration.ip.tcp.connection.*
import org.springframework.integration.ip.tcp.serializer.ByteArrayCrLfSerializer
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler

@Value("\${spring.config.host}")
internal lateinit var host: String

@Bean
fun port() = 9998

// Create the tcp outgoing connection
@Bean
@ServiceActivator(inputChannel = "inbox")
fun tcpOutGate(connectionFactory: AbstractClientConnectionFactory): MessageHandler {
    val gate = TcpOutboundGateway()
    gate.setConnectionFactory(connectionFactory)
    gate.setOutputChannelName("resultToString")
    return gate
}

// Creates the incoming connection
@Bean
fun tcpInGate(connectionFactory: AbstractServerConnectionFactory): TcpInboundGateway {
    val inGate = TcpInboundGateway()
    inGate.setConnectionFactory(connectionFactory)
    inGate.setRequestChannel(tcpIn())
    return inGate
}

@Bean
fun tcpIn(): MessageChannel {
    return DirectChannel()
}

@Bean
fun getTcpConnectionInterceptorFactoryChain(@Autowired tcpConnectionInterceptorFactory: TcpConnectionInterceptorFactory): TcpConnectionInterceptorFactoryChain {
    val chain = TcpConnectionInterceptorFactoryChain()
    chain.setInterceptors(arrayOf(tcpConnectionInterceptorFactory))
    return chain
}

@Bean
fun clientClientFactory(@Autowired interceptorFactoryChain: TcpConnectionInterceptorFactoryChain): AbstractClientConnectionFactory {
    val tcpNetClientConnectionFactory = TcpNetClientConnectionFactory(host, port())
    tcpNetClientConnectionFactory.setInterceptorFactoryChain(interceptorFactoryChain)
    return tcpNetClientConnectionFactory
}

@Bean
fun serverClientFactory(@Autowired interceptorFactoryChain: TcpConnectionInterceptorFactoryChain): AbstractServerConnectionFactory {
    val tcpNetServerConnectionFactory = TcpNetServerConnectionFactory(port())
    tcpNetServerConnectionFactory.setInterceptorFactoryChain(interceptorFactoryChain)
    (tcpNetServerConnectionFactory.deserializer as ByteArrayCrLfSerializer).maxMessageSize = 100000
    return tcpNetServerConnectionFactory
}