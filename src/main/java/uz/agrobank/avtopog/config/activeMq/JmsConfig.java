package uz.agrobank.avtopog.config.activeMq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSException;

@Configuration
public class JmsConfig {

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        try {
            factory.setBrokerURL("tcp://localhost:61616");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        factory.setUser("admin");
        factory.setPassword("admin");
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(connectionFactory());
    }

    @Bean
    public DestinationResolver destinationResolver() {
        return new DynamicDestinationResolver();
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate(cachingConnectionFactory());
        template.setDestinationResolver(destinationResolver());
        return template;
    }
}





//package uz.agrobank.avtopog.config.activeMq;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
//import org.springframework.jms.support.converter.MessageConverter;
//import org.springframework.jms.support.converter.MessageType;
//
//@Configuration
//@EnableJms
//public class JmsConfig {
//
//    @Value("${jms.queues.uzcard-queue.name}")
//    public String mainQueue;
//
//    @Value("${jms.queues.humo-queue.name}")
//    public String chattingQueue;
//
//    // this one is customized
//    @Bean
//    public MessageConverter messageConverter(){
//        MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.TEXT);
//        converter.setTypeIdPropertyName("_type");
//        return converter;
//    }
//
//
//    //Initialize queues
////    @Bean(name="mainQueueDestination")
////    public Destination mainQueueDestination() {
////        return new ActiveMQQueue(mainQueue);
////    }
////    @Bean(name="chattingQueueDestination")
////    public Destination chattingQueueDestination() {
////        return new ActiveMQQueue(chattingQueue);
////    }
//}