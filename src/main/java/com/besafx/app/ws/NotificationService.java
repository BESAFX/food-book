package com.besafx.app.ws;

import com.besafx.app.dao.PersonDao;
import com.besafx.app.model.Person;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    private final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private PersonDao personDao;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //Send to one destination
    public void notifyOne(Notification notification, String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        notification.setSender(auth == null ? "" : auth.getName());
        notification.setReceiver(username);
        messagingTemplate.convertAndSendToUser(username, "/queue/notify", notification);
        LOG.info("Send notification to " + notification.getReceiver() + ": " + notification);
    }

    //Send to multiple destination
    public void notifyAll(Notification notification) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional.ofNullable(auth).ifPresent(value -> notification.setSender(value.getName()));
        Lists.newArrayList(personDao.findAll())
                .stream()
                .forEach(person -> {
                    notification.setReceiver(person.getEmail());
                    messagingTemplate.convertAndSendToUser(person.getEmail(), "/queue/notify", notification);
                    LOG.info("Send notification to " + notification.getReceiver() + ": " + notification);
                });
    }

    //Send to multiple destination except me
    public void notifyAllExceptMe(Notification notification) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person me = personDao.findByEmail(auth == null ? "" : auth.getName()).get();
        Optional.ofNullable(auth).ifPresent(value -> notification.setSender(value.getName()));
        Lists.newArrayList(personDao.findAll())
                .stream()
                .filter(person -> !person.equals(me))
                .forEach(person -> {
                    notification.setReceiver(person.getEmail());
                    messagingTemplate.convertAndSendToUser(person.getEmail(), "/queue/notify", notification);
                    LOG.info("Send notification to " + notification.getReceiver() + ": " + notification);
                });
    }
}
