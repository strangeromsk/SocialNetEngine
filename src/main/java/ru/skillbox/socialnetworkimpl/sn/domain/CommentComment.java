package ru.skillbox.socialnetworkimpl.sn.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "COMMENT_COMMENT")
public class CommentComment extends PostComment {
}
