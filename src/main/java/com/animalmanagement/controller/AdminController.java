package com.animalmanagement.controller;


import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.*;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    TweetService tweetService;

    @Autowired
    CommentService commentService;

    @Autowired
    AnimalService animalService;

    @Autowired
    HelpService helpService;

    @Autowired
    AdoptionService adoptionService;

    @RequestMapping("/user/get")
    public BaseResponse getUsers(@RequestBody AdminGetUserBo adminGetUserBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(userService.adminGetUsers(adminGetUserBo))
                .message("success")
                .build();
    }

    @RequestMapping("user/black")
    public BaseResponse changeUserStatus(@RequestBody ChangeUserStatusBo changeUserStatusBo) {
        userService.changeUserStatus(changeUserStatusBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("success").build();
    }

    @RequestMapping("/user/modify")
    public BaseResponse modifyUser(@RequestBody ModifyUserInfoBo modifyUserInfoBo) {
        userService.modifyUserInfo(modifyUserInfoBo);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode()).message("success").build();
    }

    @PostMapping("/tweet/get")
    public BaseResponse tweetGet(@RequestBody AdminTweetGetBo adminTweetGetBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.adminTweetGet(adminTweetGetBo))
                .message("success")
                .build();
    }

    @PostMapping("/tweet/content")
    public BaseResponse tweetContent(@RequestBody AdminTweetContentBo adminTweetContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(tweetService.adminTweetGetContent(adminTweetContentBo))
                .message("success")
                .build();
    }

    @PostMapping("/tweet/censor")
    public BaseResponse tweetCensor(@RequestBody TweetCensorBo tweetCensorBo) {
        tweetService.adminTweetCensor(tweetCensorBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/comment/get")
    public BaseResponse getComments(@RequestBody AdminGetCommentsBo adminGetCommentsBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(commentService.adminGetComments(adminGetCommentsBo))
                .message("success")
                .build();
    }

    @PostMapping("/comment/censor")
    public BaseResponse commentCensor(@RequestBody CommentCensorBo commentCensorBo) {
        commentService.adminCommentCensor(commentCensorBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/animal/get")
    public BaseResponse animalGet(@RequestBody AdminAnimalGetBo adminAnimalGetBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(animalService.adminAnimalGet(adminAnimalGetBo))
                .message("success")
                .build();
    }

    @PostMapping("/animal/content")
    public BaseResponse animalContent(@RequestBody AdminAnimalContentBo adminAnimalContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(animalService.adminAnimalContent(adminAnimalContentBo))
                .message("success")
                .build();
    }

    @PostMapping("/animal/modify")
    public BaseResponse animalModify(@RequestBody AdminAnimalModifyBo adminAnimalModifyBo) {
        animalService.adminAnimalModify(adminAnimalModifyBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/animal/delete")
    public BaseResponse animalDelete(@RequestBody AdminAnimalDeleteBo adminAnimalDeleteBo) {
        animalService.adminAnimalDelete(adminAnimalDeleteBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/animal/addrecord")
    public BaseResponse animalAddRecord(@RequestBody AdminAnimalAddBo adminAnimalAddBo) {
        animalService.adminAnimalAdd(adminAnimalAddBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/help/get")
    public BaseResponse helpGet(@RequestBody AdminHelpGetBo adminHelpGetBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(helpService.adminHelpGet(adminHelpGetBo))
                .message("success")
                .build();
    }

    @PostMapping("/help/content")
    public BaseResponse helpContent(@RequestBody AdminHelpContentBo adminHelpContentBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(helpService.adminHelpContent(adminHelpContentBo))
                .message("success")
                .build();
    }

    @PostMapping("/help/reply")
    public BaseResponse helpReply(@RequestBody AdminHelpReplyBo adminHelpReplyBo) {
        Integer userId = Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        AddCommentBo addCommentBo = AddCommentBo.builder().userId(userId).tweetId(adminHelpReplyBo.getHelpId()).comment(adminHelpReplyBo.getContent()).build();
        commentService.addComment(addCommentBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/adoption/get")
    public BaseResponse adoptionGet(@RequestBody AdminAdoptionGetBo adminAdoptionGetBo) {
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(adoptionService.adminAdoptionGet(adminAdoptionGetBo))
                .message("success")
                .build();
    }

    @PostMapping("/adoption/censor")
    public BaseResponse adoptionCensor(@RequestBody AdminAdoptionCensorBo adminAdoptionCensorBo) {
        adoptionService.adminAdoptionCensor(adminAdoptionCensorBo);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .message("success")
                .build();
    }

    @PostMapping("/help/changeStatus")
    public BaseResponse changeHelpStatus(@RequestBody TweetIdBo tweetIdBo) {
        boolean isLike = helpService.changeStatusByAdmin(tweetIdBo.getTweetId());
        Map<Object, Object> map = new HashMap<>();
        map.put("solved", isLike);
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode())
                .body(map)
                .message("success")
                .build();
    }
}
