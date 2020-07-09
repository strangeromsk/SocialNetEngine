package ru.skillbox.socialnetworkimpl.sn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.socialnetworkimpl.sn.api.requests.DialogRequestBody;
import ru.skillbox.socialnetworkimpl.sn.api.responses.DialogResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.MessageResponse;
import ru.skillbox.socialnetworkimpl.sn.api.responses.ResponsePlatformApi;
import ru.skillbox.socialnetworkimpl.sn.domain.Dialog;
import ru.skillbox.socialnetworkimpl.sn.domain.Message;
import ru.skillbox.socialnetworkimpl.sn.domain.Person;
import ru.skillbox.socialnetworkimpl.sn.domain.PersonToDialog;
import ru.skillbox.socialnetworkimpl.sn.domain.enums.MessageStatus;
import ru.skillbox.socialnetworkimpl.sn.repositories.DialogRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.MessageRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonRepository;
import ru.skillbox.socialnetworkimpl.sn.repositories.PersonToDialogRepository;
import ru.skillbox.socialnetworkimpl.sn.services.interfaces.DialogService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DialogServiceImpl implements DialogService {

    /** В данном коде не учтена вообще отправка нотификаций и настройка получения нотификаций конкретным пользователем
     * Кроме того не учтён момент, не оговоренный в ТЗ - возможно ли добавить в диалог кого-то (начать с ним диалог),
     * кто запретил получение сообщений, кроме как от друзей!
     *
     * Кроме этого не определено, как должны работать два метода.
     *
     * Скорее всего коду понадобятся доработки*/

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private PersonToDialogRepository personToDialogRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public ResponseEntity<ResponsePlatformApi> getDialogs(String query, int offset, int itemPerPage) {

        int currentUserId = getIdFromCurrentUser();
        int total = dialogRepository.getTotalDialogsForUser(currentUserId, false);
        List<Dialog> dialogs = dialogRepository.findAllDialogsByUserAndQuery(currentUserId, query, setPageable(offset, itemPerPage));
        ArrayList<DialogResponse> data = new ArrayList<>();

        for (Dialog dialog : dialogs) {
            List<Message> messages = messageRepository.findByDialog(dialog.getId(), false);
            Message message = messages.get(0);

            data.add(DialogResponse.builder()
                    .id(dialog.getId())
                    .unreadCount(dialog.getUnreadCount())
                    .lastMessage(createMessageResponse(message))
                    .build());
        }
        return ResponseEntity.ok(
                ResponsePlatformApi.builder()
                        .error("List of dialogs is download")
                        .timestamp(System.currentTimeMillis())
                        .total(total)
                        .offset(offset)
                        .perPage(dialogs.size())
                        .data(data)
                        .build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> createDialogs(DialogRequestBody users) {

        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = Dialog.builder()
                .isDeleted(false)
                .unreadCount(0)
                .ownerId(currentUserId)
                .build();
        int dialogId = dialogRepository.save(dialog).getId();
        personToDialogRepository.save(PersonToDialog.builder()
                .dialogId(dialogId)
                .personId(currentUserId).build());

        for (int userId : users.getUserIds()) {
            personToDialogRepository.save(PersonToDialog.builder()
                    .dialogId(dialogId)
                    .personId(userId).build());
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("The dialog was creation successfully!")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder().id(dialogId).build()).build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getUnreadMessagesInDialogs() {

        int currentUserId = getIdFromCurrentUser();
        int count = 0;
        List<Dialog> dialogs = dialogRepository.findByOwnerIdAndIsDeleted(currentUserId, false);
        for (Dialog dialog : dialogs) {
            count += dialog.getUnreadCount();
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("Unread count was download")
                .timestamp(System.currentTimeMillis()).data(DialogResponse.builder().count(count).build()).build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> deleteDialog(int id) {

        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = dialogRepository.findById(id);

        if (isDialogCanBeChanged(dialog, currentUserId)) {
            dialog.setDeleted(true);
            dialog.setUnreadCount(0);
            dialogRepository.save(dialog);

            List<Message> messages = messageRepository.findByDialogId(id);
            for (Message message : messages) {
                message.setDeleted(true);
                messageRepository.save(message);
            }

            List<PersonToDialog> personToDialogs = personToDialogRepository.findByDialogId(id);
            for (PersonToDialog personToDialog : personToDialogs) {
                personToDialogRepository.delete(personToDialog);
            }
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder()
                .error("Dialog was successfully deleted")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder()
                        .id(id)
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> addUsersToDialog(int id, DialogRequestBody users) {

        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = dialogRepository.findById(id);

        if (isDialogCanBeChanged(dialog, currentUserId))
        {
            for (int userId : users.getUserIds()) {
                Person person = personRepository.findByUserId(userId);
                if (!person.isDeleted() && !person.isApproved() && !person.isBlocked()) {
                    personToDialogRepository.save(PersonToDialog.builder().dialogId(id).personId(userId).build());
                }
            }
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder()
                .error("User was added")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder()
                        .userIds(users.getUserIds())
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> deleteUsersFromDialog(int id, DialogRequestBody users) {

        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = dialogRepository.findById(id);

        if (isDialogCanBeChanged(dialog, currentUserId)) {
            for (int userId : users.getUserIds()) {
                PersonToDialog personToDialog = personToDialogRepository.findByPersonIdAndDialogId(userId, id);
                personToDialogRepository.delete(personToDialog);
            }
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("User was deleted")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder().userIds(users.getUserIds()).build()).build());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> getInviteLink(int id) {

        //пока так, до тех пор, пока не поймём как генерится ссылка

        Dialog dialog = dialogRepository.findById(id);
        String link = "This is invite link";
        dialog.setInviteCode(link);
        dialogRepository.save(dialog);

        return ResponseEntity.ok(ResponsePlatformApi.builder()
                .error("Invite link was generated")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder()
                        .link(link)
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> joinToDialog(int id, DialogRequestBody link) {

        int currentUserId = getIdFromCurrentUser();
        ArrayList<Integer> usersIds = new ArrayList<>();
        usersIds.add(currentUserId);

        Dialog dialog = dialogRepository.findById(id);

        if (dialog.getInviteCode().equals(link.getLink()) && !dialog.isDeleted()) {
            personToDialogRepository.save(PersonToDialog.builder().personId(currentUserId).dialogId(id).build());
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder()
                .error("User was added")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder()
                        .userIds(usersIds)
                        .build())
                .build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getMessagesInDialog(int id, String query, int offset, int itemPerPage) {

        int currentUserId = getIdFromCurrentUser();

        if (!isPersonInDialog(currentUserId, id))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else
        {
            List<Message> messages = messageRepository.findByDialogIdAndMessageTextContainingAndIsDeleted(id, query,
                    false, setPageable(offset, itemPerPage));
            int count = messageRepository.getTotalMessageInDialog(id, false);
            ArrayList<MessageResponse> data = new ArrayList<>();

            for (Message message : messages)
                data.add(createMessageResponse(message));

            return ResponseEntity.ok(
                    ResponsePlatformApi.builder()
                            .error("Messages in dialog was download")
                            .timestamp(System.currentTimeMillis())
                            .total(count)
                            .offset(offset)
                            .perPage(messages.size())
                            .data(data)
                            .build());
        }
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> sendMessages(int id, DialogRequestBody messageText) {

        int currentUserId = getIdFromCurrentUser();

        if (!isPersonInDialog(currentUserId, id))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        else {
            Dialog dialog = dialogRepository.findById(id);
            dialog.setUnreadCount(dialog.getUnreadCount() + 1);

            Message message = messageRepository.save(Message.builder().time(LocalDateTime.now())
                    .authorId(currentUserId)
                    .recipientId(currentUserId)
                    .messageText(messageText.getMessageText())
                    .readStatus(MessageStatus.SENT)
                    .dialogId(id)
                    .isDeleted(false).build());

            return ResponseEntity.ok(ResponsePlatformApi.builder().error("Message was sent").timestamp(System.currentTimeMillis())
                    .data(createMessageResponse(message)).build());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> deleteMessageFromDialog(int dialogId, int messageId) {

        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = dialogRepository.findById(dialogId);
        Message message = messageRepository.findById(messageId);

        if (isMessageCanBeChanged(currentUserId, dialogId, dialog, message) && !message.isDeleted()) {
            message.setDeleted(true);
            messageRepository.save(message);

            if (message.getReadStatus().equals(MessageStatus.SENT)) {
                dialog.setUnreadCount(dialog.getUnreadCount() - 1);
                dialogRepository.save(dialog);
            }
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("Message was deleted")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder().messageId(messageId).build()).build());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> editMessageInDialog(int dialogId, int messageId,
                                                                   DialogRequestBody messageText) {
        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = dialogRepository.findById(dialogId);
        Message message = messageRepository.findById(messageId);

        if (isMessageCanBeChanged(currentUserId, dialogId, dialog, message) && !message.isDeleted()) {
            message.setMessageText(messageText.getMessageText());
            messageRepository.save(message);
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("Message was edited")
                .timestamp(System.currentTimeMillis())
                .data(createMessageResponse(message)).build());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> recoverMessage(int dialogId, int messageId) {

        int currentUserId = getIdFromCurrentUser();
        Dialog dialog = dialogRepository.findById(dialogId);
        Message message = messageRepository.findById(messageId);

        if (isMessageCanBeChanged(currentUserId, dialogId, dialog, message)) {
            message.setDeleted(false);
            messageRepository.save(message);

            if (message.getReadStatus().equals(MessageStatus.SENT)) {
                dialog.setUnreadCount(dialog.getUnreadCount() + 1);
                dialogRepository.save(dialog);
            }
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("Message was recovered")
                .timestamp(System.currentTimeMillis())
                .data(createMessageResponse(message)).build());
    }

    @Override
    @Transactional
    public ResponseEntity<ResponsePlatformApi> markMessageAsRead(int dialogId, int messageId)
    {
        int currentUserId = getIdFromCurrentUser();
        Message message = messageRepository.findById(messageId);
        Dialog dialog = dialogRepository.findById(dialogId);

        if (isPersonInDialog(currentUserId, dialogId) && isMessageInDialog(message, dialogId) && !message.isDeleted()) {
            message.setReadStatus(MessageStatus.READ);
            messageRepository.save(message);

            dialog.setUnreadCount(dialog.getUnreadCount() - 1);
            dialogRepository.save(dialog);
        }
        return ResponseEntity.ok(ResponsePlatformApi.builder().error("Message was marked as read")
                .timestamp(System.currentTimeMillis())
                .data(createMessageResponse(message)).build());
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> getLastActivityAndStatus(int dialogId, int userId) {

        Person person = personRepository.findByUserId(userId);

        if (isPersonInDialog(userId, dialogId))
            return ResponseEntity.ok(ResponsePlatformApi.builder()
                .error("Last activity and status was got")
                .timestamp(System.currentTimeMillis())
                .data(DialogResponse.builder()
                        .online(person.isOnline())
                        .lastActivity(person.getLastOnlineTime()).build())
                .build());

        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<ResponsePlatformApi> changeUserStatusInDialog(int dialogId, int userId) {

        //заглушка до теста на фронте. Непонятно, что должен делать метод
        return null;
    }

    private int getIdFromCurrentUser() {
        return accountService.getCurrentUser().getId();
    }

    private MessageResponse createMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .time(message.getTime())
                .authorId(message.getAuthorId())
                .recipientId(message.getRecipientId())
                .messageText(message.getMessageText())
                .readStatus(message.getReadStatus().toString())
                .build();
    }

    private boolean isPersonInDialog(int personId, int dialogId) {
        PersonToDialog personToDialog = personToDialogRepository.findByPersonIdAndDialogId(personId, dialogId);
        return personToDialog != null;
    }

    private boolean isMessageInDialog (Message message, int dialogId) {
        return message.getDialogId() == dialogId;
    }

    private boolean isDialogCanBeChanged (Dialog dialog, int currentUserId) {
         return dialog.getOwnerId() == currentUserId && !dialog.isDeleted();
    }

    private boolean isMessageCanBeChanged(int currentUserId, int dialogId, Dialog dialog, Message message)
    {
        return isPersonInDialog(currentUserId, dialogId)
                && (dialog.getOwnerId() == currentUserId || message.getAuthorId() == currentUserId)
                && isMessageInDialog(message, dialogId);
    }

    private Pageable setPageable(int offset, int itemPerPage) {
        int page = offset/itemPerPage;
        return PageRequest.of(page, itemPerPage);
    }
}
