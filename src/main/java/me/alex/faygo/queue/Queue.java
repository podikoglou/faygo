package me.alex.faygo.queue;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.alex.faygo.ladder.Ladder;
import me.alex.faygo.profile.Profile;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Queue {

    @NonNull
    private Ladder ladder;

    @NonNull
    private QueueType type;

    private List<Profile> players = new ArrayList<>();

}
