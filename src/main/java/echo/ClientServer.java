package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class ClientServer {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    });
            Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
            while (true) {
                channel.writeAndFlush("hello world!");
                System.out.println("send message");
                Thread.sleep(2000);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
