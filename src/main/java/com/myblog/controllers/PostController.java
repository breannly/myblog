package com.myblog.controllers;

import com.myblog.dtos.CreatePostRequest;
import com.myblog.dtos.PostResponse;
import com.myblog.dtos.UpdatePostRequest;
import com.myblog.facades.impl.PostFacadeImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostFacadeImpl postFacade;

    public PostController(PostFacadeImpl postFacade) {
        this.postFacade = postFacade;
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<PostResponse> posts = postFacade.findAll();
        model.addAttribute("posts", posts);
        return "posts/collection";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") Long id, Model model) {
        PostResponse post = postFacade.findById(id);
        model.addAttribute("post", post);
        return "posts/document";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("createPostRequest", new CreatePostRequest(null, null, null));
        return "posts/action/create";
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
        return "posts/action/edit";
    }

    @PostMapping("/{id}/likes/increment")
    public String incrementLikes(@PathVariable("id") Long id, HttpServletRequest request) {
        postFacade.incrementLikes(id);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
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