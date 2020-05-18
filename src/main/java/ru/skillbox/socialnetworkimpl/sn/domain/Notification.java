package ru.skillbox.socialnetworkimpl.sn.domain;

import lombok.Data;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "type_id", insertable = false, updatable = false, nullable = false)
    private NotificationType notificationType;

    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @Any(metaColumn = @Column(name = "type_id"))
    @AnyMetaDef(idType = "int",
            metaType = "int",
            metaValues = {
                    @MetaValue(targetEntity = Post.class, value = "1"),            //POST
                    @MetaValue(targetEntity = PostComment.class, value = "2"),     //POST_COMMENT
                    @MetaValue(targetEntity = CommentComment.class, value = "3"),     //COMMENT_COMMENT
                    @MetaValue(targetEntity = Friendship.class, value = "4"),      //FRIEND_REQUEST
                    @MetaValue(targetEntity = Message.class, value = "5")         //MESSAGE
            })
    @JoinColumn(name = "entity_id", nullable = false)
    private Object entity;

    @Column(name = "sent_time", columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp sentTime;

    @Column(name = "person_id", nullable = false)
    private Integer personId;

    @Column(name = "contact", nullable = false)
    private String contact;


    /**
     * Проверяет наличие родительского комментария, когда в сеттер передается объект класса PostComment.
     * Если класс сущности PostComment и parentId != null, то создает и устанавливает в поле entity класс-наследник
     * CommentComment для верного определения notificationType при записи сущности Notification в БД.
     */
    public void setEntity(Object entity) {
        if (entity instanceof PostComment) {
            PostComment pComment = (PostComment) entity;
            if (pComment.getParentId() != null) {
                //Создание подкласса и копирование значений полей
                CommentComment cComment = new CommentComment();
                cComment.setPostId(pComment.getPostId());
                cComment.setParentId(pComment.getParentId());
                cComment.setAuthorId(pComment.getAuthorId());
                cComment.setCommentText(pComment.getCommentText());
                cComment.setTime(pComment.getTime());
                cComment.setIsBlocked(pComment.getIsBlocked());
                this.entity = cComment;
                return;
            }
        }
        this.entity = entity;
    }
}

