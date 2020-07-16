package college.player.simple.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String QUEUE="queue_login";
    public static final String EXCHANGE="exchange_login";
    public static final String ROUTEKEY="routekey_login";
    //上传照片的队列
    public static final String QUEUE_uploadA="queue_uploadA";
    public static final String EXCHANGE_uploadA="exchange_uploadA";
    public static final String ROUTEKEY_uploadA="routekey_uploadA";
    public static final String QUEUE_uploadB="queue_uploadB";
    public static final String EXCHANGE_uploadB="exchange_uploadB";
    public static final String ROUTEKEY_uploadB="routekey_uploadB";


    //声明交换机
    @Bean(EXCHANGE)
    public Exchange ex_login(){
        return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
    }
    @Bean(EXCHANGE_uploadA)
    public Exchange ex_uploadA(){
        return ExchangeBuilder.directExchange(EXCHANGE_uploadA).durable(true).build();
    }
    @Bean(EXCHANGE_uploadB)
    public Exchange ex_uploadB(){
        return ExchangeBuilder.directExchange(EXCHANGE_uploadB).durable(true).build();
    }
    //声明队列
    @Bean(QUEUE)
    public Queue qu_login(){
        return new Queue(QUEUE);
    }
    @Bean(QUEUE_uploadA)
    public Queue qu_uploadA(){
        return new Queue(QUEUE_uploadA);
    }
    @Bean(QUEUE_uploadB)
    public Queue qu_uploadB(){
        return new Queue(QUEUE_uploadB);
    }

    //队列绑定交换机
    //queue_login队列,绑定交换机exchange_login
    @Bean
    public Binding bindqutoex_login(@Qualifier(QUEUE) Queue queue,
                              @Qualifier(EXCHANGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTEKEY).noargs();
    }
    //queue_upload队列,绑定到交换机exchange_upload
    @Bean
    public Binding binduploadA(@Qualifier(QUEUE_uploadA) Queue queue,
                                    @Qualifier(EXCHANGE_uploadA) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTEKEY_uploadA).noargs();
    }
    @Bean
    public Binding binduploadB(@Qualifier(QUEUE_uploadB) Queue queue,
                                    @Qualifier(EXCHANGE_uploadB) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTEKEY_uploadB).noargs();
    }

}
