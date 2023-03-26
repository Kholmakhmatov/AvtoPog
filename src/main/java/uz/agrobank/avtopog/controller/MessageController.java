package uz.agrobank.avtopog.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import uz.agrobank.avtopog.config.activeMq.QueueManager;
import uz.agrobank.avtopog.dto.MyMessage;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final JmsTemplate jmsTemplate;
    @Value("${jms.queues.uzcard-queue.name}")
    public String uzcard;

    @Value("${jms.queues.humo-queue.name}")
    public String humo;
    private final QueueManager queueManager;

    @PostMapping("/send")
    public String sendMessage( @RequestBody MyMessage message) {
        jmsTemplate.convertAndSend(message.getQueue(), message.getContent());
       int messageCount = queueManager.getMessageCount(uzcard);
        System.out.println("messageCount = " + messageCount);
        //int humoCount = queueManager.getMessageCount(humo);
        return " uzcard = "  + messageCount +"\nMessage= "+message;
    }


}