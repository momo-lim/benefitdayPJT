package com.ssafy.recommend.repository.boards;

import com.ssafy.recommend.domain.boards.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion,Long> {
}
