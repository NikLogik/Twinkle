package git.niklogik.web.controller;

import git.niklogik.core.FireModelRunner;
import git.niklogik.web.models.CreateFireRequest;
import git.niklogik.web.models.FireDataResponse;
import git.niklogik.web.services.RequestDataService;
import git.niklogik.web.services.ResponseDataService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FiresController {

    private final Logger logger = LoggerFactory.getLogger(FiresController.class);

    private final ResponseDataService responseService;
    private final RequestDataService requestService;
    private final FireModelRunner fireRunner;

    @Autowired
    public FiresController(ResponseDataService responseService, RequestDataService requestService, FireModelRunner runner) {
        this.responseService = responseService;
        this.requestService = requestService;
        this.fireRunner = runner;
    }

    @PostMapping(value = "/fires")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createNewFire(@Valid @RequestBody CreateFireRequest requestData) {
        requestService.transformCoordinates(requestData.fireCenter());
        logger.info("Get estimated data :{}", requestData);
        fireRunner.run(requestData);
        logger.info("Send response with result of the first iteration");
    }

    @GetMapping(value = "/fires/{fireId}")
    public FireDataResponse getIterationData(@PathVariable(name = "fireId") Long fireId,
                                             @RequestParam(name = "iterNum", defaultValue = "0") Integer iterNum) {
        return responseService.findByFireIdAndIteration(fireId, iterNum);
    }

    @DeleteMapping(value = "/fires/{fireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFireModel(@PathVariable(name = "fireId") Long fireId) {
        responseService.deleteByFireId(fireId);
    }
}