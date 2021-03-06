package com.heowc.post.service;

import com.heowc.post.domain.AccessDeniedException;
import com.heowc.post.domain.Post;
import com.heowc.post.domain.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SimpleRemovePostService implements RemovePostService {

    private final PostRepository repository;

    public SimpleRemovePostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void remove(Post post) {
        Post byId = repository.findById(post.getId()).orElseThrow(NoSuchElementException::new);

        if (byId.canNotAccess(post.getCreatedBy())) {
            throw new AccessDeniedException();
        }

        repository.delete(byId);
    }
}
