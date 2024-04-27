package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddUserRequest {
    private String email;
    private String password;
    private Integer roleType;
}