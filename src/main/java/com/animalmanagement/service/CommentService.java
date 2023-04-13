package com.animalmanagement.service;

import com.animalmanagement.bean.bo.*;
import com.animalmanagement.bean.vo.CommentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.animalmanagement.entity.*;
import com.animalmanagement.mapper.*;
import com.animalmanagement.example.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final static Integer PAGE_SIZE = 10;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserService userService;

    public Map<String, Object> adminGetComments(AdminGetCommentsBo adminGetCommentsBo) {
        List<Comment> commentList = commentMapper.selectByExample(new CommentExample());

        Map<String, Object> map = new HashMap<>();
        map.put("sumNum", commentList.size());

        commentList.sort(Comparator.comparing(Comment::getTime));
        int start = (adminGetCommentsBo.getPage() - 1) * adminGetCommentsBo.getPageNum();
        if (start >= commentList.size()) {
            map.put("users", null);
        } else {
            int end = Math.min(start + adminGetCommentsBo.getPageNum(), commentList.size());
            map.put("users", commentList.subList(start, end));
        }
        return map;
    }

    public void adminCommentCensor(CommentCensorBo commentCensorBo) {
        Integer commentId = commentCensorBo.getCommentId();
        checkIdExists(commentId);
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (commentCensorBo.getOperate() == 0) {
            comment.setCensored(true);
        } else {
            comment.setDeleted(true);
        }
    }

    public void checkIdExists(Integer commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment == null) {
            throw new RuntimeException("CommentId Does Not Exist");
        }
    }

    public Map<String, Object> getComments(UserGetCommentsBo getCommentsBo) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTweetIdEqualTo(getCommentsBo.getTweetId());
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        commentList = commentList.stream().filter(this::checkCommentValid).collect(Collectors.toList());

        Map<Integer, UserInfo> userInfoMap = userService.getUserInfoByIdList(
                commentList.stream().map(Comment::getUserId).distinct().toList());

        List<CommentVo> commentVoList = commentList
                .stream()
                .map(e -> {
                    CommentVo commentVo = new CommentVo();
                    BeanUtils.copyProperties(e, commentVo);
                    UserInfo userInfo = userInfoMap.get(e.getUserId());
                    commentVo.setAvatar(userInfo.getAvatar());
                    commentVo.setUsername(userInfo.getUsername());
                    return commentVo;
                }).toList();
        commentVoList.sort(Comparator.comparing(CommentVo::getTime));

        Map<String, Object> resultMap = new HashMap<>();
        int start = getCommentsBo.getCommentPage() * PAGE_SIZE;
        if (start >= commentVoList.size()) {
            resultMap.put("comments", null);
        } else {
            int end = Math.min(start + PAGE_SIZE, commentVoList.size());
            resultMap.put("comments", commentVoList.subList(start, end));
        }
        return resultMap;
    }

    /**
     * 校验帖子是否过审、被删除
     */
    private boolean checkCommentValid(Comment comment) {
        return comment.getCensored() && !comment.getDeleted();
    }
}
