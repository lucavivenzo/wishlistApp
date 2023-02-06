package com.sad.progetto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class InviteRequest {

    private List<Long> idFriend;
    private Long idEvent;

}
