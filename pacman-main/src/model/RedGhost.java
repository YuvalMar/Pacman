package model;

import controller.Game;
import misc.BFSFinder;
import misc.ImageHelper;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class RedGhost extends Ghost {

    BFSFinder bfs;

    public RedGhost(int x, int y, Game pb){
        super(x,y,pb,3);
    }

    @Override
    public void loadImages(){
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
            ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png"));
            ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png"));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png")));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png")));
            ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/4.png"));
            ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/5.png"));
            ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/6.png"));
            ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/7.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }
    }
}
