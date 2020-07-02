package ru.skillbox.socialnetworkimpl.sn.api.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TagResponse {
    int id;
    String tag;
}
