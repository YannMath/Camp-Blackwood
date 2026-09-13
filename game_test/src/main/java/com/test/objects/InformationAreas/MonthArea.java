package com.test.objects.InformationAreas;

import com.test.objects.*;

public class MonthArea extends InformationArea {
    int currentMonth;
    
    public MonthArea(Tile[] tiles) {
        super(tiles);
    }

    @Override
    public void update(Object object) {
        if (!(object instanceof GameState)) return;
        GameState gs = (GameState) object;
        currentMonth = gs.getMonth();
        int filledTiles = 2;
        if (filledTiles > tiles.length) {System.out.println("Not enough tiles to display month"); return;}

        if (tiles.length == 3) {
            String month = convertMonthToName(currentMonth);
            tiles[0].setChar(month.charAt(0));
            tiles[1].setChar(month.charAt(1));
            tiles[2].setChar(month.charAt(2));
        }
        else if (tiles.length >= 1) {
            String month = String.valueOf(currentMonth);

            if (currentMonth > 9) {
                tiles[0].setChar(month.charAt(0));
                tiles[1].setChar(month.charAt(1));
            }
            else {
                tiles[0].setChar('0');
                tiles[1].setChar(month.charAt(0));
            }
        }
    }

    private String convertMonthToName(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "Couldn't convert from month " + month + " to String";
        }
    }
}
