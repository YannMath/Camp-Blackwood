package com.test.logic;

import com.test.objects.Interface;
import com.test.objects.Tilemap;
import com.test.objects.Tile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertUI {
    private static final Pattern INFORMATION_PATTERN =
            Pattern.compile("^\\s*\\(([^;]+);(.*)\\)\\s*$");
    private static final Pattern COORDINATE_PATTERN =
            Pattern.compile("\\((-?\\d+)\\s*,\\s*(-?\\d+)\\)");

    public static Interface convertUI(String filename) throws IOException{
        filename = filename.contains("/") || filename.endsWith(".ui")
            ? filename
            : "ui/" + filename + ".ui";
        InputStream input = ConvertTileMaps.class
            .getClassLoader()
            .getResourceAsStream(filename);

        if (input == null) {
            throw new IOException("Resource not found: " + filename);
        }

        List<String> lines = new String(input.readAllBytes(), StandardCharsets.UTF_8).lines().toList();

        String spriteLine = null;
        List<String> infoAreaLines = new ArrayList<>();
        String offsetLine = null;

        String currentSection = "";

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("[SPRITE]")) {
                currentSection = "SPRITE";
                continue;
            }

            if (line.equals("[INFORMATION]")) {
                currentSection = "INFORMATION";
                continue;
            }

            if (line.equals("[OFFSET]")) {
                currentSection = "OFFSET";
                continue;
            }

            if (line.isBlank()) {
                continue;
            }

            switch (currentSection) {
                case "SPRITE" -> spriteLine = line;
                case "INFORMATION" -> infoAreaLines.add(line);
                case "OFFSET" -> offsetLine = line;
            }
        }

        if (spriteLine == null || spriteLine.isBlank()) {
            throw new IOException("UI file does not define a sprite: " + filename);
        }

        Tilemap sprite = ConvertTileMaps.convertFile(resolveSpriteResource(spriteLine));

        int[] offsets = parseOffset(offsetLine);
        Interface newInterface = new Interface(
                sprite.getSprite().length,
                sprite.getSprite()[0].length,
                offsets[1],
                offsets[0],
                sprite);

        Map<String, List<Tile>> informationAreas = new LinkedHashMap<>();
        for (String infoAreaLine : infoAreaLines) {
            Matcher informationMatcher = INFORMATION_PATTERN.matcher(infoAreaLine);
            if (!informationMatcher.matches()) {
                throw new IllegalArgumentException("Invalid information area: " + infoAreaLine);
            }

            String name = informationMatcher.group(1).trim();
            Matcher coordinateMatcher = COORDINATE_PATTERN.matcher(informationMatcher.group(2));
            List<Tile> tiles = informationAreas.computeIfAbsent(name, ignored -> new ArrayList<>());
            while (coordinateMatcher.find()) {
                int x = Integer.parseInt(coordinateMatcher.group(1));
                int y = Integer.parseInt(coordinateMatcher.group(2));
                if (x < 0 || x >= newInterface.width() || y < 0 || y >= newInterface.height()) {
                    throw new IllegalArgumentException("Information area coordinate outside UI: " + x + ", " + y);
                }
                tiles.add(newInterface.getTile(x, y));
            }

            if (tiles.isEmpty()) {
                throw new IllegalArgumentException("Information area has no coordinates: " + name);
            }
        }

        for (Map.Entry<String, List<Tile>> informationArea : informationAreas.entrySet()) {
            newInterface.setInformationArea(
                    informationArea.getKey(),
                    informationArea.getValue().toArray(new Tile[0]));
        }

        return newInterface;
    }

    private static int[] parseOffset(String offsetLine) {
        if (offsetLine == null || offsetLine.isBlank()) {
            return new int[]{0, 0};
        }

        String[] values = offsetLine.split("\\s*,\\s*");
        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid UI offset: " + offsetLine);
        }

        return new int[]{Integer.parseInt(values[0].trim()), Integer.parseInt(values[1].trim())};
    }

    private static String resolveSpriteResource(String spriteName) {
        if (spriteName.contains("/") || spriteName.endsWith(".txt")) {
            return spriteName;
        }
        return "tilemaps/" + spriteName + ".txt";
    }
}
