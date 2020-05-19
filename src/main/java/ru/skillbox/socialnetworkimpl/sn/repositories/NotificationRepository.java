package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification,Integer> {
}