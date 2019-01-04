package com.github.stonedch.socialnetwork.repository;

import com.github.stonedch.socialnetwork.domain.Story;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoryRepository extends CrudRepository<Story, Long> {

    List<String> findByTag(String tag);
}
