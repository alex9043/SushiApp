package ru.alex9043.sushiapp.repository.blog;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.blog.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}