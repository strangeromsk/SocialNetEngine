package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.PersonToDialog;

import java.util.List;

@Repository
public interface PersonToDialogRepository extends JpaRepository<PersonToDialog, Integer>
{
    PersonToDialog findByPersonIdAndDialogId (int personId, int dialogId);
    List<PersonToDialog> findByDialogId (int id);
}
