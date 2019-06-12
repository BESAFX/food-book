package com.besafx.app.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/socket")
@CrossOrigin("*")
public class NotificationController {

    private final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping(value = "/notifyOne", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void notifyOne(@RequestBody Notification notification) {
        LOG.info("Calling Service");
        notificationService.notifyOne(Notification.builder()
                .code(notification.getCode())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .date(new Date())
                .type(notification.getType()).build(), notification.getReceiver());
    }

    @RequestMapping(value = "/notifyAll", method = RequestMethod.POST)
    @ResponseBody
    public void notifyAll(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "message") String message,
            @RequestParam(value = "type") NotificationType type) {
        notificationService.notifyAll(Notification.builder().code(code).title(title).message(message).date(new Date()).type(type).build());
    }

    @RequestMapping(value = "/notifyAllExceptMe", method = RequestMethod.POST)
    @ResponseBody
    public void notifyAllExceptMe(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "message") String message,
            @RequestParam(value = "type") NotificationType type) {
        notificationService.notifyAllExceptMe(Notification.builder().code(code).title(title).message(message).date(new Date()).type(type).build());
    }

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public String test(String message) throws Exception {
//        Thread.sleep(1000); // simulated delay
//        String string = new String("Message From Server: " + message);
//        LOG.info(string);
//        return string;
//    }

    @MessageMapping("/hello")
    public void test2(Notification notification) throws Exception {
        Thread.sleep(1000); // simulated delay
        String string = new String("Message From Server2: " + notification.getMessage());
        LOG.info(string);
        simpMessagingTemplate.convertAndSendToUser(notification.getReceiver(), "/queue/notify", string);
    }
}
