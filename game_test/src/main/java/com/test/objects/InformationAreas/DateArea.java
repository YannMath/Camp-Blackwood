package com.test.objects.InformationAreas;

import com.test.objects.*;

public class DateArea extends InformationArea { 
    private DayArea da;
    private MonthArea ma;

    public DateArea(Tile[] tiles) {
        super(tiles);
        if (tiles.length > 5) {
            Tile[] dayTiles = {tiles[0], tiles[1]};
            Tile[] monthTiles = {tiles[3], tiles[4], tiles[5]};
            da = new DayArea(dayTiles);
            ma = new MonthArea(monthTiles);
        }
        else System.out.println("Not enough space for displaying the date");
    }

    @Override
    public void update(Object object) {
        da.update(object);
        tiles[2].setChar('/');
        ma.update(object);
    }
}
