package ru.skillbox.socialnetworkimpl.sn.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StorageResponse {
    private String id;
    private int ownerId;
    private String fileName;
    private String relativeFilePath;
    private String rawFileURL;
    private String fileFormat;
    private byte[] bytes;
    private String fileType;
    private Date createdAt;
}
