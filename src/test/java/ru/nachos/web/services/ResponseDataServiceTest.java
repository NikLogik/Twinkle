package ru.nachos.web.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nachos.db.model.fire.FireModel;
import ru.nachos.db.services.FireDatabaseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResponseDataServiceTest {

    @Autowired
    ResponseDataService service;
    @Autowired
    FireDatabaseService fireService;

    @Test
    public void deleteFireById(){
        service.deleteFireModelByFireId(136);
        FireModel fireByFireId = fireService.findFireByFireId(136);
        Assert.assertTrue(fireByFireId.getFireId()==null);
    }
}