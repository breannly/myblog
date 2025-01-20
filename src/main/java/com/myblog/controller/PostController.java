package com.myblog.controller;

import com.myblog.domain.dto.CreatePostRequest;
import com.myblog.domain.dto.PostResponse;
import com.myblog.domain.dto.UpdatePostRequest;
import com.myblog.facade.PostFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostFacade postFacade;

    public PostController(PostFacade postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<PostResponse> posts = postFacade.findAll();
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        PostResponse post = postFacade.findById(id);
        model.addAttribute("post", post);
        return "posts/detail";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("createPostRequest", new CreatePostRequest(null, null, null));
        return "posts/create";
    }

    @PostMapping
    public String createPost(@ModelAttribute CreatePostRequest request) {
        PostResponse created = postFacade.create(request);
        return "redirect:/posts/" + created.id();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        PostResponse post = postFacade.findById(id);
        UpdatePostRequest updateRequest = new UpdatePostRequest(
            post.title(),
            post.content(),
            post.imageUrl()
        );
        model.addAttribute("postId", id);
        model.addAttribute("updatePostRequest", updateRequest);
        return "posts/edit";
    }

    @PostMapping("/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute UpdatePostRequest request) {
        postFacade.update(id, request);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postFacade.deleteById(id);
        return "redirect:/posts";
    }
}