package uz.agrobank.avtopog.config.activeMq;

import javax.jms.*;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import lombok.RequiredArgsConstructor;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.management.ObjectNameBuilder;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Service
@RequiredArgsConstructor
public class QueueManager {
    private final JmsTemplate jmsTemplate;

    public int getMessageCount(String queueName) {
        return jmsTemplate.browse(queueName, (session, browser) -> {
            int count = 0;
            Enumeration<?> enumeration = browser.getEnumeration();
            while (enumeration.hasMoreElements()) {
                enumeration.nextElement();
                count++;
            }
            return count;
        });
    }
}
