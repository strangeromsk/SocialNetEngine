package ru.skillbox.socialnetworkimpl.sn.api.requests.account;

import lombok.Data;

@Data
public class NotificationBody {
    private String notification_type;
    private boolean enable;
}
