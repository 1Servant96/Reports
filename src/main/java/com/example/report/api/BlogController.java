package com.example.report.api;

import com.example.report.dto.SimpleResponse;
import com.example.report.entities.Post;
import com.example.report.entities.Status;
import com.example.report.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog/api")
public class BlogController {

    private final PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }


    // Логика лайков и диз



    @PostMapping("/blog/{id}/like")
    public String blogPostLike(@PathVariable(value = "id") long id, Status status) {
        Post post = postRepository.findById(id).orElseThrow();
        if (status.isStatus() == false) {
            for (int i = 0; i < 1; i++) {
                int count = post.getViews();
                count = count + 1;
                post.setViews(count);
            }
//            status.setStatus(true);
            if (status.isStatus() == true) {
                return "redirect:/blog/{id}";
            }

            postRepository.save(post);
            return "redirect:/blog/{id}";
        } else {
            return "redirect:/blog";
        }

    }


    @PostMapping("/blog/{id}/dislike")
    public SimpleResponse blogPostDislike(@PathVariable long id, Status status) {
        Post post = postRepository.findById(id).orElseThrow();
        if (!status.isStatus()) {
            int count = post.getViews();
            count = count - 1;
            post.setViews(count);
            status.setStatus(true);
            if (post.getViews() < 0) {
                return new SimpleResponse("hello");
            }
        } else {
            return new SimpleResponse("hello");
        }
        postRepository.save(post);

        return new SimpleResponse("hello");
    }

}
