package git.niklogik.web.models.lib;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import git.niklogik.web.models.CoordinateJson;
import git.niklogik.web.models.ResponseDataImpl;

@JsonDeserialize(as = ResponseDataImpl.class)
@JsonSerialize(as = ResponseDataImpl.class)
public interface ResponseData {
    long getFireId();
    int getIterNum();
    int getCurrentIterNum();
    CoordinateJson[] getData();
}
