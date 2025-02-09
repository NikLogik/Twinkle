package git.niklogik.web.services;

import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.web.NotFoundException;
import git.niklogik.web.models.FireDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResponseDataService {

    private final FireDatabaseService fireService;

    @Autowired
    public ResponseDataService(FireDatabaseService fireService) {
        this.fireService = fireService;
    }

    public FireDataResponse findByFireIdAndIteration(long fireId, int iterNumber) {
        return Optional.ofNullable(fireService.findIterationData(fireId, iterNumber))
                .orElseThrow(() -> new NotFoundException("Fire entity not found. Id - " + fireId));
    }

    public void deleteByFireId(long fireId) {
        fireService.deleteById(fireId);
    }

    public FireDataResponse getFireModelFirstIteration(long fireId) {
        return fireService.findFirstIterationData(fireId);
    }
}
