package com.demo.friendship.controller;

import com.demo.friendship.controller.exception.ConnectionRejectException;
import com.demo.friendship.controller.message.BaseResp;
import com.demo.friendship.controller.message.CreateFriendshipConnectionReq;
import com.demo.friendship.controller.message.ErrorResp;
import com.demo.friendship.controller.message.GetFriendsResp;
import com.demo.friendship.service.FriendshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/friendship")
public class FriendshipController {
    private static final Logger log = LoggerFactory.getLogger(FriendshipController.class);

    @Autowired
    @Qualifier("SHARED_TASK_EXECUTOR")
    private TaskExecutor taskExecutor;

    @Autowired
    private FriendshipService friendshipService;

    @RequestMapping(value = "/connection", method = RequestMethod.POST, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<BaseResp> registerAccount(@RequestBody CreateFriendshipConnectionReq req) {
        req.validate();
        DeferredResult<BaseResp> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            try {
                friendshipService.createConnection(req);
                deferredResult.setResult(BaseResp.ok());
            } catch (ConnectionRejectException e) {
                log.error("FriendConnection connection request rejected", e);
                deferredResult.setResult(new ErrorResp(e.getMessage()));
            }
        }, taskExecutor);
        return deferredResult;
    }

    @RequestMapping(value = "/connection/all", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<BaseResp> getAllFriends(@NotBlank @RequestParam String user) {
        DeferredResult<BaseResp> deferredResult = new DeferredResult<>();
        CompletableFuture.runAsync(() -> {
            List<String> allConnections = friendshipService.getAllUserConnections(user);
            GetFriendsResp resp = new GetFriendsResp();
            resp.setSuccess(true);
            resp.setFriends(allConnections);
            resp.setCount(allConnections.size());
            deferredResult.setResult(resp);
        }, taskExecutor);
        return deferredResult;
    }

}
