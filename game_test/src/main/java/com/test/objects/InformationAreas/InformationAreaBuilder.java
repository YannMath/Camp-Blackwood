package com.test.objects.InformationAreas;

import com.test.objects.InformationArea;
import com.test.objects.Tile;

public final class InformationAreaBuilder {
    private InformationAreaBuilder() {
    }

    public static InformationArea build(String name, Tile[] tiles) {
        switch (name) {
            case "health":
                return new HealthArea(tiles);
            case "name":
                return new NameArea(tiles);
            default:
                throw new IllegalArgumentException("Unknown information area: " + name);
        }
    }
}
