package ru.nachos.web.services.lib;

import org.springframework.stereotype.Service;
import ru.nachos.web.models.lib.RequestData;

@Service
public interface RequestDataService {
    boolean validateData(RequestData requestData);

    ResponseDataService getResponseService();

    void start(RequestData requestData);
}
