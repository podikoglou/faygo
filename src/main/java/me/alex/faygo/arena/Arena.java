package me.alex.faygo.arena;

import lombok.*;
import me.alex.faygo.utility.Loc;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Arena {

    @NonNull
    private String name;

    private Loc loc1;
    private Loc loc2;

}
