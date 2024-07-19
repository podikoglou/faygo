package me.alex.faygo.utility;

import lombok.*;
import org.bukkit.Location;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loc {

    private int x, y, z;
    private float yaw;

    public static Loc fromLocation(Location location) {
        return new Loc(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getYaw()
        );
    }


}
