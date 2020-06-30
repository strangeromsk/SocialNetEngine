package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Friendship;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    @Query(value = "SELECT * FROM friendship f WHERE (f.src_person_id = :id OR f.dst_person_id = :id) AND f.code = 'FRIEND'", nativeQuery = true)
    List<Friendship> getFriendsForUserById(@Param("id") Integer id);
}
