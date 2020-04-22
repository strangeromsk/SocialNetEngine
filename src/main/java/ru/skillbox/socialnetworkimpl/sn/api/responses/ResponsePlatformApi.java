package ru.skillbox.socialnetworkimpl.sn.api.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor

public class ResponsePlatformApi<T> {
    private String error;
    private long timestamp;
    private int total;
    private int offset;
    private int perPage;
    private T data;

    public ResponsePlatformApi (String error, int total, int offset, int perPage, T data) {
        this.error = error;
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
        this.data = data;
        timestamp = new Date().getTime();
    }
}
