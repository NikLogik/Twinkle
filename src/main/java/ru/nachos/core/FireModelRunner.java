package ru.nachos.core;

import ru.nachos.web.models.lib.RequestData;
import ru.nachos.web.models.lib.ResponseData;

public interface FireModelRunner {
    void run(RequestData requestData);
}
