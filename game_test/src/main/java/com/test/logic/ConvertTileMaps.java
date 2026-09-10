package com.test.logic;

import com.googlecode.lanterna.TextColor;
import com.test.objects.Tilemap;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConvertTileMaps {

    public static Tilemap convertFile(String filename) throws IOException {

        InputStream input = ConvertTileMaps.class
                .getClassLoader()
                .getResourceAsStream(filename);

        if (input == null) {
            throw new IOException("Resource not found: " + filename);
        }

        List<String> lines = new String(input.readAllBytes(), StandardCharsets.UTF_8).lines().toList();

        List<String> spriteLines = new ArrayList<>();
        List<String> bgLines = new ArrayList<>();
        List<String> fgLines = new ArrayList<>();

        String currentSection = "";

        for (String line : lines) {

            if (line.equals("[SPRITE]")) {
                currentSection = "SPRITE";
                continue;
            }

            if (line.equals("[BG]")) {
                currentSection = "BG";
                continue;
            }

            if (line.equals("[FG]")) {
                currentSection = "FG";
                continue;
            }

            if (line.isBlank()) {
                continue;
            }

            switch (currentSection) {

                case "SPRITE" -> spriteLines.add(line);

                case "BG" -> bgLines.add(line);

                case "FG" -> fgLines.add(line);
            }
        }

        char[][] sprite = new char[spriteLines.size()][];

        for (int y = 0; y < spriteLines.size(); y++) {
            sprite[y] = spriteLines.get(y).toCharArray();
        }

        int[] expectedWidths = new int[sprite.length];
        for (int y = 0; y < sprite.length; y++) {
            expectedWidths[y] = sprite[y].length;
        }

        TextColor[][] background = parseColorMap(bgLines, expectedWidths);
        TextColor[][] foreground = parseColorMap(fgLines, expectedWidths);

        return new Tilemap(sprite, background, foreground);
    }


    private static TextColor[][] parseColorMap(List<String> lines, int[] expectedWidths) {
        TextColor[][] result = new TextColor[lines.size()][];

        for (int y = 0; y < lines.size(); y++) {
            List<TextColor> row = tokenizeColorRow(lines.get(y));
            int expectedWidth = y < expectedWidths.length ? expectedWidths[y] : 0;

            if (expectedWidth > 0 && row.size() != expectedWidth) {
                row = expandToWidth(row, expectedWidth);
            }

            result[y] = row.toArray(new TextColor[0]);
        }

        return result;
    }

    private static List<TextColor> tokenizeColorRow(String row) {
        List<TextColor> colors = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inRgb = false;

        for (int i = 0; i < row.length(); i++) {
            char ch = row.charAt(i);

            if (ch == '(') {
                inRgb = true;
                current.append(ch);
                continue;
            }

            if (ch == ')') {
                inRgb = false;
                current.append(ch);
                colors.add(parseColor(current.toString()));
                current.setLength(0);
                continue;
            }

            if (Character.isWhitespace(ch) && !inRgb) {
                if (current.length() > 0) {
                    colors.add(parseColor(current.toString()));
                    current.setLength(0);
                }
                continue;
            }

            current.append(ch);
        }

        if (current.length() > 0) {
            colors.add(parseColor(current.toString()));
        }

        return colors;
    }

    private static List<TextColor> expandToWidth(List<TextColor> colors, int expectedWidth) {
        if (colors.isEmpty()) {
            return new ArrayList<>();
        }

        List<TextColor> expanded = new ArrayList<>(expectedWidth);
        int repeatFactor = (int) Math.ceil((double) expectedWidth / colors.size());

        for (TextColor color : colors) {
            for (int i = 0; i < repeatFactor && expanded.size() < expectedWidth; i++) {
                expanded.add(color);
            }
        }

        return expanded.subList(0, expectedWidth);
    }


    private static TextColor parseColor(String color) {
        if (color.startsWith("(") && color.endsWith(")")) {
            String values = color.substring(1, color.length() - 1);
            String[] rgb = values.trim().replace(',', ' ').split("\\s+");

            if (rgb.length != 3) {
                throw new IllegalArgumentException("Invalid RGB color: " + color);
            }

            int r = Integer.parseInt(rgb[0]);
            int g = Integer.parseInt(rgb[1]);
            int b = Integer.parseInt(rgb[2]);

            return new TextColor.RGB(r, g, b);
        }

        String upper = color.trim();
        if (upper.isEmpty()) {
            throw new IllegalArgumentException("Empty color token");
        }

        try {
            return TextColor.ANSI.valueOf(upper);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported color token: " + color, e);
        }
    }
}