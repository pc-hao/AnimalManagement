package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @Autowired
    CommentService commentService;

    @Autowired
    SearchLogService searchLogService;

    @PostMapping("/content")
    public BaseResponse getTweetContent(@RequestBody TweetContentBo tweetContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.getTweetContent(tweetContentBo))
                .build();
    }

    @PostMapping("/getComments")
    public BaseResponse getComments(@RequestBody UserGetCommentsBo getCommentsBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(commentService.getComments(getCommentsBo))
                .build();
    }


    @PostMapping("/addComment")
    public BaseResponse addComment(@RequestBody AddCommentBo addCommentBo) {
        commentService.addComment(addCommentBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/get")
    public BaseResponse getTweets(@RequestBody GetTweetsBo getTweetsBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.getTweets(getTweetsBo))
                .build();
    }

    @PostMapping("/like")
    public BaseResponse like(@RequestBody UserAndTweetIdBo userAndTweetIdBo) {
        boolean isLike = tweetService.tweetLike(userAndTweetIdBo.getUserId(), userAndTweetIdBo.getTweetId());
        Map<Object, Object> map = new HashMap<>();
        map.put("isLike", isLike);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(map)
                .build();
    }

    @PostMapping("/star")
    public BaseResponse star(@RequestBody UserAndTweetIdBo userAndTweetIdBo) {
        boolean isLike = tweetService.tweetStar(userAndTweetIdBo.getUserId(), userAndTweetIdBo.getTweetId());
        Map<Object, Object> map = new HashMap<>();
        map.put("isStar", isLike);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(map)
                .build();
    }

    @PostMapping("/create")
    public BaseResponse create(@RequestBody TweetCreateBo tweetCreateBo) {
        tweetService.tweetCreate(tweetCreateBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/delete")
    public BaseResponse delete(@RequestBody UserAndTweetIdBo userAndTweetIdBo) {
        tweetService.deleteTweet(userAndTweetIdBo.getUserId(), userAndTweetIdBo.getTweetId());
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode()).message("删除成功")
                .build();
    }

    @PostMapping("/hotSearch")
    public BaseResponse getHotSearch() {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(searchLogService.getHot(10, false)).build();
    }

    @PostMapping("/selfSearch")
    public BaseResponse getSelfSearch() {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(searchLogService.getSelf(false)).build();
    }
}
