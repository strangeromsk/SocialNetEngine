package ru.skillbox.socialnetworkimpl.sn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.socialnetworkimpl.sn.domain.NotificationSettings;

@Repository
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Integer>
{
    NotificationSettings findByPersonIdAndNotificationTypeId(int personId, int notisicationTypeId);
}
