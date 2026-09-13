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
            case "day":
                return new DayArea(tiles);
            case "month":
                return new MonthArea(tiles);
            case "date":
                return new DateArea(tiles);
            case "time":
                return new TimeArea(tiles);
            case "location":
                return new LocationArea(tiles);
            default:
                throw new IllegalArgumentException("Unknown information area: " + name);
        }
    }
}
