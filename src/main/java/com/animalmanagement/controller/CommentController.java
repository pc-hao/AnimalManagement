package com.animalmanagement.controller;

import com.animalmanagement.bean.BaseResponse;
import com.animalmanagement.bean.bo.CommentLikeBo;
import com.animalmanagement.bean.bo.UserAndCommentIdBo;
import com.animalmanagement.bean.bo.UserAndTweetIdBo;
import com.animalmanagement.enums.StatusEnum;
import com.animalmanagement.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/like")
    public BaseResponse commentLike(@RequestBody CommentLikeBo commentLikeBo) {
        boolean isLike = commentService.commentLike(commentLikeBo);
        Map<Object, Object> map = new HashMap<>();
        map.put("isLike", isLike);
        return BaseResponse.builder().code(StatusEnum.SUCCESS.getCode())
                .body(map).build();
    }

    @PostMapping("/delete")
    public BaseResponse delete(@RequestBody UserAndCommentIdBo userAndCommentIdBo) {
        commentService.deleteComment(userAndCommentIdBo.getUserId(), userAndCommentIdBo.getCommentId());
        return BaseResponse.builder()
                .code(StatusEnum.SUCCESS.getCode()).message("删除成功")
                .build();
    }
}
