package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Dialog;

import java.util.List;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Integer>
{
    @Query("SELECT COUNT(*) FROM Dialog d WHERE d.ownerId = ?1 AND d.isDeleted = ?2")
    int getTotalDialogsForUser(int id, boolean isDeleted);

    List<Dialog> findByOwnerIdAndIsDeleted(int id, boolean isDeleted);
    Dialog findById (int id);

    @Query("SELECT d, COUNT (m) AS messageCount FROM Dialog d LEFT JOIN Message m " +
            "ON m.dialogId = d.id " +
            "WHERE d.ownerId = ?1 AND m.isDeleted = false AND m.messageText LIKE %?2% " +
            "AND d.isDeleted = false GROUP BY d.id")
    List<Dialog> findAllDialogsByUserAndQuery(int ownerId, String query, Pageable pageable);
}