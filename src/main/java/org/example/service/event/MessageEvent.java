package org.example.service.event;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageEvent {

    Long idUser;
    String emailUser;
    StatusMessage statusMessage;

}
