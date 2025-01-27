package com.myblog.controllers;

import com.myblog.dtos.CommentRequest;
import com.myblog.facades.CommentFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts/{post_id}/comments")
public class CommentController {

    private final CommentFacade commentFacade;

    public CommentController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @PostMapping
    public String addComment(@PathVariable("post_id") Long postId,
                             @ModelAttribute CommentRequest request) {
        commentFacade.addComment(postId, request);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("post_id") Long postId,
                               @PathVariable("id") Long id) {
        return "redirect:/posts/" + postId + "?editCommentId=" + id;
    }

    @PostMapping("/{id}/edit")
    public String editComment(@PathVariable("post_id") Long postId,
                              @PathVariable("id") Long id,
                              @ModelAttribute CommentRequest request) {
        commentFacade.editComment(postId, id, request);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/{id}/delete")
    public String deleteComment(@PathVariable("post_id") Long postId,
                                @PathVariable("id") Long id) {
        commentFacade.deleteComment(postId, id);
        return "redirect:/posts/" + postId;
    }
}