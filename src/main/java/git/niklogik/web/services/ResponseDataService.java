package git.niklogik.web.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.web.models.lib.ResponseData;

@SessionScope
@Service
public class ResponseDataService {

    Logger logger = LoggerFactory.getLogger(ResponseDataService.class);
    private final FireDatabaseService fireService;

    @Autowired
    public ResponseDataService(FireDatabaseService fireService) {
        this.fireService = fireService;
    }

    public ResponseData findByFireIdAndIteration(long fireId, int iterNumber){
        return fireService.findIterationData(fireId, iterNumber);
    }

    public void deleteByFireId(long fireId){
        fireService.deleteById(fireId);
    }

    public ResponseData getFireModelFirstIteration(long fireId){
        return fireService.findFirstIterationData(fireId);
    }
}
