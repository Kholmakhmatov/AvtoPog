package uz.agrobank.avtopog.config.activeMq;

import javax.jms.*;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.management.ObjectNameBuilder;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.agrobank.avtopog.dto.statistic.StatisticMQ;
import uz.agrobank.avtopog.exceptions.UniversalException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueManager {
    private final JmsTemplate jmsTemplate;
    @Value("${jms.queues.uzcard-queue.name}")
    public String uzcard;

    @Value("${jms.queues.humo-queue.name}")
    public String humo;
    private long getMessageCount(String queueName) {
        return jmsTemplate.browse(queueName, (session, browser) -> {
            long count = 0;
            Enumeration<?> enumeration = browser.getEnumeration();
            while (enumeration.hasMoreElements()) {
                enumeration.nextElement();
                count++;
            }
            return count;
        });
    }
    public String mqList(){
        List<StatisticMQ>list=new ArrayList<>();
        long messageCount = getMessageCount(uzcard);
        StatisticMQ uzcard=new StatisticMQ();
        uzcard.setAmount(messageCount);
        uzcard.setLabel("UzCard");
        list.add(uzcard);
        long messageCountHumo = getMessageCount(humo);
        StatisticMQ humo=new StatisticMQ();
        humo.setAmount((long) messageCountHumo);
        humo.setLabel("Humo");
        list.add(humo);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String s = null;
        try {
            s = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new UniversalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  s;
    }
    public long getMessagesUzCard(){
        return getMessageCount(uzcard);
    }
    public long getMessagesHumo(){
        return getMessageCount(humo);
    }
}
