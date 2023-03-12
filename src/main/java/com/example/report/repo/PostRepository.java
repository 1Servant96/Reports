package com.example.report.repo;

import com.example.report.entities.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
