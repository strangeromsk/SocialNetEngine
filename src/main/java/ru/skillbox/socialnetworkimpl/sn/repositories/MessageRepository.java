package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>
{
    @Query("SELECT COUNT(*) FROM Message m WHERE m.dialogId = ?1 AND m.isDeleted = ?2")
    int getTotalMessageInDialog(int id, boolean isDeleted);

    Message findById (int id);

    List<Message> findByDialogIdAndMessageTextContainingAndIsDeleted(int id, String messageText, boolean isDeleted, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.dialogId = ?1 AND m.isDeleted =?2 ORDER BY m.time DESC")
    List<Message> findByDialog(int id, boolean isDeleted);

    List<Message> findByDialogId (int id);
}
