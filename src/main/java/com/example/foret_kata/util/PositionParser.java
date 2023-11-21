package com.example.foret_kata.util;
import com.example.foret_kata.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PositionParser {
    public static Position[] parse(String positionsString) {

            Pattern pattern = Pattern.compile("\\((\\d+),\\s*(\\d+)\\)");
            Matcher matcher = pattern.matcher(positionsString);

            List<Position> positionList = new ArrayList<>();

            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                Position position = new Position(x, y);
                positionList.add(position);
            }
            Position[] positions;
        positions = positionList.toArray(new Position[0]);

        return positions;
        }
}
