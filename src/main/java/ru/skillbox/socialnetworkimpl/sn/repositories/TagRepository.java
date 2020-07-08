package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Page<Tag> findAllByTagContainingIgnoreCase(String tag, Pageable pageable);
}
