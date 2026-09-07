package com.mink.projecttrip.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchUser {

    private long id;
    private String nickName;
    private String profileImg;

    private boolean friend;
    private boolean requestSent;

}
