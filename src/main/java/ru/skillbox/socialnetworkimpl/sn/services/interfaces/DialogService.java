package ru.skillbox.socialnetworkimpl.sn.services.interfaces;

import org.springframework.http.ResponseEntity;
import ru.skillbox.socialnetworkimpl.sn.api.requests.DialogRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;

public interface DialogService {
    ResponseEntity<ResponsePlatformApi> getDialogs(String query, int offset, int itemPerPage);
    ResponseEntity<ResponsePlatformApi> createDialogs(DialogRequestBody users);
    ResponseEntity<ResponsePlatformApi> getUnreadMessagesInDialogs();
    ResponseEntity<ResponsePlatformApi> deleteDialog(int id);
    ResponseEntity<ResponsePlatformApi> addUsersToDialog(int id, DialogRequestBody users);
    ResponseEntity<ResponsePlatformApi> deleteUsersFromDialog(int id, DialogRequestBody users);
    ResponseEntity<ResponsePlatformApi> getInviteLink(int id);
    ResponseEntity<ResponsePlatformApi> joinToDialog(int id, DialogRequestBody link);
    ResponseEntity<ResponsePlatformApi> getMessagesInDialog(int id, String query, int offset, int itemPerPage);
    ResponseEntity<ResponsePlatformApi> sendMessages(int id, DialogRequestBody messageText);
    ResponseEntity<ResponsePlatformApi> deleteMessageFromDialog(int dialogId, int messageId);
    ResponseEntity<ResponsePlatformApi> editMessageInDialog(int dialogId, int messageId, DialogRequestBody messageText);
    ResponseEntity<ResponsePlatformApi> recoverMessage(int dialogId, int messageId);
    ResponseEntity<ResponsePlatformApi> markMessageAsRead(int dialogId, int messageId);
    ResponseEntity<ResponsePlatformApi> getLastActivityAndStatus(int dialogId, int userId);
    ResponseEntity<ResponsePlatformApi> changeUserStatusInDialog(int dialogId, int userId);
}
