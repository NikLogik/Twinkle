package git.niklogik.web.controller;

import git.niklogik.core.FireModelRunner;
import git.niklogik.web.NotFoundException;
import git.niklogik.web.models.lib.RequestData;
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
import git.niklogik.web.models.lib.ResponseData;

@RestController
public class IndexRestController {

    Logger logger = LoggerFactory.getLogger(IndexRestController.class);

    private final ResponseDataService responseService;
    private final RequestDataService requestService;
    private final FireModelRunner fireRunner;

    @Autowired
    public IndexRestController(ResponseDataService responseService, RequestDataService requestService, FireModelRunner runner) {
        this.responseService = responseService;
        this.requestService = requestService;
        this.fireRunner = runner;
    }

    @PostMapping(value = "/fires")
    public ResponseData createNewFire(@Valid @RequestBody RequestData requestData) {
        requestService.transformCoordinates(requestData.getFireCenter());
        logger.info("Get estimate data :" + requestData);
        fireRunner.run(requestData);
        ResponseData response = responseService.getFireModelFirstIteration(fireRunner.getModelId());
        logger.info("Send response with result of the first iteration");
        return response;
    }

    @GetMapping(value = "/fires/{fireId}")
    public ResponseData getIterationData(@PathVariable(name = "fireId") Long fireId,
                                         @RequestParam(name = "iterNum", defaultValue = "0") Integer iterNum) {
        ResponseData response = responseService.findByFireIdAndIteration(fireId, iterNum);
        if (response != null) {
            return response;
        } else {
            throw new NotFoundException("Not found");
        }
    }

    @DeleteMapping(value = "/fires/{fireId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFireModel(@PathVariable(name = "fireId") Long fireId) {
        responseService.deleteByFireId(fireId);
    }
}