package ru.nachos.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.nachos.web.models.CoordinateJson;
import ru.nachos.web.models.ResponseDataImpl;

@JsonDeserialize(as = ResponseDataImpl.class)
@JsonSerialize(as = ResponseDataImpl.class)
public interface ResponseData {
    int getIterNum();
    int getCurrentIterNum();
    CoordinateJson[] getData();
}
