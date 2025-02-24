package git.niklogik.web.controller;

import git.niklogik.core.FireModelRunner;
import git.niklogik.db.services.FireDatabaseService;
import git.niklogik.error.exception.FireModelNotFoundException;
import git.niklogik.web.models.CreateFireRequest;
import git.niklogik.web.models.FireDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class FiresController {

    private final Logger logger = LoggerFactory.getLogger(FiresController.class);

    private final FireModelRunner fireRunner;
    private final FireDatabaseService fireDatabaseService;

    @PostMapping(value = "/fires")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createNewFire(@Valid @RequestBody CreateFireRequest requestData) {
        logger.info("Get estimated data :{}", requestData);
        fireRunner.run(requestData);
    }

    @GetMapping(value = "/fires/{fireId}")
    public FireDataResponse getIterationData(@PathVariable(name = "fireId") Long fireId,
                                             @RequestParam(name = "iterNum", defaultValue = "0") Integer iterNum) {
        return Optional.ofNullable(fireDatabaseService.findIterationData(fireId, iterNum))
                       .orElseThrow(() -> new FireModelNotFoundException(fireId));
    }

    @DeleteMapping(value = "/fires/{fireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFireModel(@PathVariable(name = "fireId") Long fireId) {
        fireDatabaseService.deleteById(fireId);
    }
}