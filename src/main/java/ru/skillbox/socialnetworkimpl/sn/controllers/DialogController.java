package ru.skillbox.socialnetworkimpl.sn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetworkimpl.sn.api.requests.DialogRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.DialogService;

@RestController
@RequestMapping("/api/v1/dialogs/")
public class DialogController
{
    @Autowired
    DialogService dialogService;

    @GetMapping()
    public ResponseEntity<ResponsePlatformApi> getDialogs(String query, int offset, int itemPerPage)
    {
        return dialogService.getDialogs(query, offset, itemPerPage);
    }

    @PostMapping()
    public ResponseEntity<ResponsePlatformApi> createDialogs(@RequestBody DialogRequestBody users)
    {
        return dialogService.createDialogs(users);
    }

    @GetMapping("unreaded")
    public ResponseEntity<ResponsePlatformApi> getUnreadMessagesInDialogs()
    {
        return dialogService.getUnreadMessagesInDialogs();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponsePlatformApi> deleteDialog(@PathVariable int id) {
        return dialogService.deleteDialog(id);
    }

    @PutMapping("{id}/users")
    public ResponseEntity<ResponsePlatformApi> addUsersToDialog(@PathVariable int id,
                                                                @RequestBody DialogRequestBody users) {
        return dialogService.addUsersToDialog(id, users);
    }

    @DeleteMapping("{id}/users")
    public ResponseEntity<ResponsePlatformApi> deleteUsersFromDialog(@PathVariable int id,
                                                                     @RequestBody DialogRequestBody users) {
        return dialogService.deleteUsersFromDialog(id, users);
    }

    @GetMapping("{id}/users/invite")
    public ResponseEntity<ResponsePlatformApi> getInviteLink(@PathVariable int id) {
        return dialogService.getInviteLink(id);
    }

    @PutMapping("{id}/users/join")
    public ResponseEntity<ResponsePlatformApi> joinToDialog(@PathVariable int id,
                                                            @RequestBody DialogRequestBody requestBody) {
        return dialogService.joinToDialog(id, requestBody);
    }

    @GetMapping("{id}/messages")
    public ResponseEntity<ResponsePlatformApi> getMessagesInDialog(@PathVariable int id, String query, int offset,
                                                                   int itemPerPage) {
        return dialogService.getMessagesInDialog(id, query, offset, itemPerPage);
    }

    @PostMapping("{id}/messages")
    public ResponseEntity<ResponsePlatformApi> sendMessages(@PathVariable int id,
                                                            @RequestBody DialogRequestBody message) {
        return dialogService.sendMessages(id, message);
    }

    @DeleteMapping("{dialogId}/messages/{messageId}")
    public ResponseEntity<ResponsePlatformApi> deleteMessageFromDialog(@PathVariable int dialogId,
                                                                       @PathVariable int messageId) {
        return dialogService.deleteMessageFromDialog(dialogId, messageId);
    }

    @PutMapping("{dialogId}/messages/{messageId}")
    public ResponseEntity<ResponsePlatformApi> editMessageInDialog(@PathVariable int dialogId,
                                                                   @PathVariable int messageId,
                                                                   @RequestBody DialogRequestBody message) {
        return dialogService.editMessageInDialog(dialogId, messageId, message);
    }

    @PutMapping("{dialogId}/messages/{messageId}/recover")
    public ResponseEntity<ResponsePlatformApi> recoverMessage(@PathVariable int dialogId,
                                                              @PathVariable int messageId) {
        return dialogService.recoverMessage(dialogId, messageId);
    }

    @PutMapping("{dialogId}/messages/{messageId}/read")
    public ResponseEntity<ResponsePlatformApi> markMessageAsRead(@PathVariable int dialogId,
                                                                 @PathVariable int messageId) {
        return dialogService.markMessageAsRead(dialogId, messageId);
    }

    @GetMapping("{id}/activity/{userId}")
    public ResponseEntity<ResponsePlatformApi> getLastActivityAndStatus(@PathVariable int dialogId,
                                                                        @PathVariable int userId) {
        return dialogService.getLastActivityAndStatus(dialogId, userId);
    }

    @PostMapping("{id}/activity/{userId}") //заглушка, потому что непонятно, что должен делать метод
    public ResponseEntity<ResponsePlatformApi> changeUserStatusInDialog(@PathVariable int dialogId,
                                                                        @PathVariable int userId) {
        return dialogService.changeUserStatusInDialog(dialogId, userId);
    }
}
