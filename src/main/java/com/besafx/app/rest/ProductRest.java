package com.besafx.app.rest;

import com.besafx.app.dao.ProductDao;
import com.besafx.app.model.Product;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.besafx.app.ws.NotificationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.util.SquigglyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/product")
public class ProductRest {

    private final String FILTER_TABLE = "**";

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ProductDao productDao;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_PRODUCT_CREATE')")
    @ResponseBody
    public String create(@RequestBody Product product) {
        product = productDao.save(product);
        notificationService.notifyAll(Notification
                .builder()
                .title("Product")
                .message("Product Created Successfully")
                .type(NotificationType.information)
                .icon("add")
                .build()
        );
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), product);
    }

    @GetMapping("/findAll")
    @ResponseBody
    public String findAll() {
        return SquigglyUtils.stringify(Squiggly.init(new ObjectMapper(), FILTER_TABLE), productDao.findAll());
    }
}