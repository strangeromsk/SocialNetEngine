package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.NotificationType;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Integer> {
    NotificationType findByName(String name);

}
