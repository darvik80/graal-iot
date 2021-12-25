package xyz.crearts.iot.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@Service
public class NettyService {
    @Builder
    @Data
    static class DwsReport {
        String barcodes;
    }
    static class IncDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            String str = (String)byteBuf.readCharSequence(byteBuf.readableBytes(), Charset.defaultCharset());
            list.add(DwsReport.builder().barcodes(str).build());
        }
    }

    static class OutEncoder extends MessageToByteEncoder<DwsReport> {
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, DwsReport dwsReport, ByteBuf byteBuf) throws Exception {
            byteBuf.writeCharSequence(dwsReport.barcodes, Charset.defaultCharset());
        }
    }

    static class Processor extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            log.info("Channel is active");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            log.info("Channel is inactive");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            if (msg instanceof DwsReport) {
                log.info("Got msg: {}", ((DwsReport) msg).getBarcodes());
                ctx.writeAndFlush(msg);
            }
        }
    }

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void postConstruct() throws InterruptedException {
        log.info("Run server");
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new LoggingHandler(),
                                new LineBasedFrameDecoder(1024),
                                new IncDecoder(),
                                new OutEncoder(),
                                new Processor()
                        );
                    }
                }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.bind(9000).sync();
    }

    @PreDestroy
    public void preConstruct() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("Shutdown server");
    }
}
