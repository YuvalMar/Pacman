package misc;
/**
 * Class to define map data.
 * Defines each character from map's string.
 */

import model.Food;
import model.PowerUpFood;
import model.TeleportTunnel;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class MapEditor {

    public MapEditor(){
    }

    //Resolve Map
    public static MapData compileMap(String input){
        int mx = input.indexOf('\n');
        int my = StringHelper.countLines(input);
        System.out.println("Making Map "+mx+"x"+my);

        MapData customMap = new MapData(mx,my);
        customMap.setCustom(true);
        int[][] map = new int[mx][my];

        //Pass Map As Argument
        int i=0;
        int j=0;
        for(char c : input.toCharArray()){
            if(c == '1'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.RED));
            }
            if(c == '2'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.PINK));
            }
            if(c == '3'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.CYAN));
            }
            if(c == 'P'){
                map[i][j] = 0;
                customMap.setPacmanPosition(new Point(i,j));
            }
            if(c == 'X'){
                map[i][j] = 23;
            }
            if(c == 'Y'){
                map[i][j] = 26;
            }
            if(c == '_'){
                map[i][j] = 0;
                customMap.getFoodPositions().add(new Food(i,j));
            }
            if(c == '='){
                map[i][j] = 0;
            }
            if(c == 'O'){
                map[i][j] = 0;
                customMap.getPufoodPositions().add(new PowerUpFood(i,j,0));
            }
            if(c == 'F'){
                map[i][j] = 0;
                customMap.getPufoodPositions().add(new PowerUpFood(i,j, ThreadLocalRandom.current().nextInt(1, 4)));
            }
            if(c == 'B'){
                map[i][j] = 0;
                customMap.setGhostBasePosition(new Point(i,j));
            }
            if(c == 'T'){
                map[i][j] = 0;
                customMap.getTeleports().add(new TeleportTunnel(0, j, 26, j, moveType.LEFT ));
                customMap.getTeleports().add(new TeleportTunnel(26,j, 0, j, moveType.RIGHT ));
            }
            if(c == 'G'){
                map[i][j] = 0;
                customMap.getTeleports().add(new TeleportTunnel(i, 0, i, 28, moveType.UP ));
                customMap.getTeleports().add(new TeleportTunnel(i,28, i, 0, moveType.DOWN ));
            }
            i++;
            if(c == '\n'){
                j++;
                i=0;
            }
        }

        customMap.setMap(map);
        customMap.setCustom(true);
        System.out.println("Map Read OK !");
        return customMap;
    }

}
