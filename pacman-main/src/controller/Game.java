package controller;

import misc.*;
import model.*;
import view.PacWindow;
import view.QuestionWindow;
import view.StartWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Pacman Game class. The constructor recieves JLabel scoreboard, MapData md, PacWindow pw, String playerName
 * Initialize the game with the start map, scoreboard with score=0, lives=3 and level=1
 * Keeps ths player's name to write it to history file at the end of the game.
 * @author Berko
 *
 */

@SuppressWarnings("serial")
public class Game extends JPanel {

    public Timer redrawTimer;
    public ActionListener redrawAL;

    public int[][] map;
    public Image[] mapSegments;

    public Image foodImage;
    public Image[] pfoodImage;

    public Image goImage;
    public Image vicImage;

    public Pacman pacman;
    public ArrayList<Food> foods;
    public ArrayList<Food> eatenFoods;
    public ArrayList<PowerUpFood> pufoods;
    public ArrayList<PowerUpFood> eatenPuFoods;
    public ArrayList<Ghost> ghosts;
    public ArrayList<TeleportTunnel> teleports;

    public boolean isCustom = false;
    public boolean isGameOver = false;
    public boolean isWin = false;
    public boolean drawScore = false;
    public boolean clearScore = false; 
    public boolean flag = true;
    public boolean flag1 = true;
    public boolean flag_level3=true;
    public boolean winner = false;
    public int scoreToAdd = 0;
    public int lives;
    public int bombPoints;
    public int level;
    public boolean pacmanBomb = false;
    public boolean pacwoman;

    public int score;
    public JLabel scoreboard;

    public LoopPlayer siren;
    public boolean mustReactivateSiren = false;
    public LoopPlayer pac6;

    public Point ghostBase;

    public int m_x;
    public int m_y;

    public MapData md_backup;
    public PacWindow windowParent;
    public boolean map2 = false;
    public String playerName;
    
    /**
     * 
     * @param scoreboard
     * @param md
     * @param pw
     * @param playerName
     * @param pacwoman
     * Define new game with a the given player name and pacman choice(pacman or pacwoman)
     */
	public Game(JLabel scoreboard, MapData md, PacWindow pw, String playerName, boolean pacwoman){
        this.playerName = playerName;
    	this.scoreboard = scoreboard;
        this.setDoubleBuffered(true);
        md_backup = md;
        windowParent = pw;
        this.lives = 3;
        this.pacwoman = pacwoman;

        m_x = md.getX();
        m_y = md.getY();
        this.map = md.getMap();

        this.isCustom = md.isCustom();
        this.ghostBase = md.getGhostBasePosition();

       

        pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this, this.pacwoman);
        addKeyListener(pacman);

        foods = new ArrayList<>();
        eatenFoods = new ArrayList<Food>();
        pufoods = new ArrayList<>();
        eatenPuFoods = new ArrayList<PowerUpFood>();
        ghosts = new ArrayList<>();
        teleports = new ArrayList<>();


        if(!isCustom) {
            for (int i = 0; i < m_x; i++) {
                for (int j = 0; j < m_y; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Food(i, j));
                }
            }
        }else{
            foods = md.getFoodPositions();
        }

        pufoods = md.getPufoodPositions();

        ghosts = new ArrayList<>();
        for(GhostData gd : md.getGhostsData()){
            switch(gd.getType()) {
                case RED:
                    ghosts.add(new RedGhost(gd.getX(), gd.getY(), this));
                    break;
                case PINK:
                    ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                    break;
                case CYAN:
                    ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                    break;
            }
        }
        

        teleports = md.getTeleports();

        setLayout(null);
        setSize(20*m_x,20*m_y);
        setBackground(Color.black);

        mapSegments = new Image[28];
        mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/map segments/"+ms+".png"));
            }catch(Exception e){}
        }

        pfoodImage = new Image[4];
        for(int ms=0 ;ms<4;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("/resources/images/food/"+ms+".png"));
            }catch(Exception e){}
        }
        try{
            foodImage = ImageIO.read(this.getClass().getResource("/resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("/resources/images/gameover.png"));
            vicImage = ImageIO.read(this.getClass().getResource("/resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}


        redrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Draw Board
                repaint();
            }
        };
        redrawTimer = new Timer(16,redrawAL);
        redrawTimer .start();

        siren = new LoopPlayer("siren.wav");
        pac6 = new LoopPlayer("pac6.wav");
        siren.start();
        //Define space button as bomb
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke("SPACE"), "space");
        am.put("space", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                if(getBombPoints()>0) {
                	for(Ghost g: ghosts) {
                		if(isGhostNearby(g)) {
                			g.die();
                			setBombPoints(getBombPoints()-1);
                		}
                	}
                	
                }
            }
        });
       
    }
    
	/**
	 * This functions checks if there was a collision between pacman and a ghost. 
	 * If it was player's last live, the game ends and the score written to history file.
	 * else, lives = lives-1, pacman and the ghost goes back to base position and the game continues.
	 */
    public void collisionTest(){
    	Rectangle pr = new Rectangle(pacman.pixelPosition.x+13,pacman.pixelPosition.y+13,2,2);
        for(Ghost g : ghosts){
            Rectangle gr = new Rectangle(g.pixelPosition.x,g.pixelPosition.y,28,28);

            if(pr.intersects(gr)){
                if(!g.isDead()) {
                    if (!g.isWeak()) {
                    	if(this.lives<2){
	                        //Game Over
	                    	siren.stop();
	                        SoundPlayer.play("pacman_lose.wav");
	                        pacman.moveTimer.stop();
	                        pacman.animTimer.stop();
	                        g.moveTimer.stop();
	                        isGameOver = true;
	                        //Write player name and score to JSON file
	                        SysData.getInstance().readJson();
	                        SysData.getInstance().addPlayersToHistory(this.playerName, String.valueOf(score));
	                        break;
                    	} else {
                    		lives--;
                    		ghosts = new ArrayList<Ghost>();
                            for(GhostData gd : md_backup.getGhostsData()){
                                switch(gd.getType()) {
                                    case RED:
                                        ghosts.add(new RedGhost(gd.getX(), gd.getY(), this));
                                        break;
                                    case PINK:
                                        ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                                        break;
                                    case CYAN:
                                        ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                                        break;
                                }
                            }
                            pacman.activeMove = moveType.NONE;
                            pacman.pixelPosition.x = 28*md_backup.getPacmanPosition().x;
                            pacman.pixelPosition.y = 28*md_backup.getPacmanPosition().y;
                            pacman.logicalPosition.x = md_backup.getPacmanPosition().x;
                            pacman.logicalPosition.y = md_backup.getPacmanPosition().y;
                            
                            		
                            for(Ghost gs:ghosts) {
                            	gs.parentBoard = this;
                            	gs.bfs = new BFSFinder(this);
                            	if(this.level==4)
                            		gs.level=2;		
                            }
                          
                            scoreboard.setText("    Score : "+score + "   Lives : "+lives + "   Level : " + this.level);
                    	}
                    }
                }
            }
        }
    }
    
    
    /**
     * Update runs every time something changed on board. Updates the score, pacman bomb or question eating.
     */
    public void update(){
    	scoreToAdd=0;
        Food foodToEat = null;
        
        //Check normal food eating. Start food timer to restore it after 30 seconds
        for(Food f : foods){
            if(pacman.logicalPosition.x == f.position.x && pacman.logicalPosition.y == f.position.y) {
                foodToEat = f;   
            }
        }
        
        if(foodToEat!=null) {
            SoundPlayer.play("pacman_eat.wav");
            eatenFoods.add(foodToEat);
            foods.remove(foodToEat);
            foodToEat.restoreFood();
            score ++;
            scoreboard.setText("    Score : "+score + "   Lives : "+lives + "   Level : " + this.level);
        }
        
        for(Iterator<Food> fIterator = eatenFoods.iterator(); fIterator.hasNext();) {
        	Food checkFood = fIterator.next();
        	if(checkFood.isEaten) {
        		foods.add(new Food(checkFood.position.x, checkFood.position.y));
        		fIterator.remove();
        	}
        }
        
        //Game winner
        if(score>199){
        	this.score=200;
            siren.stop();
            pac6.stop();
            SoundPlayer.play("pacman_intermission.wav");
            isWin = true;
            pacman.moveTimer.stop();
            for(Ghost g : ghosts){
                g.moveTimer.stop();
            }
            SysData.getInstance().readJson();
            SysData.getInstance().addPlayersToHistory(playerName, "200");
        }
        
        
        if(score<0)
        	score=0;
        
        //Bomb or question item eaten.
        PowerUpFood puFoodToEat = null;
        //Check pu food eat
        for(PowerUpFood puf : pufoods){
            if(pacman.logicalPosition.x == puf.position.x && pacman.logicalPosition.y == puf.position.y)
                puFoodToEat = puf;
            
        }
        if(puFoodToEat!=null) {
            switch(puFoodToEat.type) {
                case 0:
                    //Bomb Point
                	bombPoints++;
                	eatenPuFoods.add(puFoodToEat);
                    pufoods.remove(puFoodToEat);
                    puFoodToEat.restoreFood();
                    break;
                default:
                	//Question Item. open question window and freeze the game until the player answers.
                    SoundPlayer.play("pacman_eatfruit.wav");
                    pufoods.remove(puFoodToEat);
                    int randomFood = ThreadLocalRandom.current().nextInt(foods.size()-1)+1;
                    Point f = foods.get(randomFood).position;
                    foods.remove(randomFood);
                    pufoods.add(new PowerUpFood(f.x, f.y, ThreadLocalRandom.current().nextInt(1, 4)));
                    freeze();
                    QuestionWindow qw = new QuestionWindow(puFoodToEat.type);
                    windowParent.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
                    unfreeze();
                    
                    //Check if the answer is correct and update scoreToAdd accordingly
                    if(qw.getCorrect()) {
                    	if(qw.getLevel() == 1)
                    		scoreToAdd=1;
                    	if(qw.getLevel() ==2)
                    		scoreToAdd=2;
                    	else
                    		scoreToAdd=3;
                    }else {
                    	if(qw.getLevel() == 1)
                    		scoreToAdd=-10;
                    	if(qw.getLevel() ==2)
                    		scoreToAdd=-20;
                    	else
                    		scoreToAdd=-30;
                    }
                    drawScore = true; // To show score animation
            }  
        }
        
        //Update game level
        if(score < 51) 
        	this.level = 1;
        else if(score>50 && score<101) 
        	this.level = 2;
        else if(score >100 && score <151)
        	this.level = 3;
        else if(score>151)
        	this.level =4;
        
        //Check if level changed and map, ghost or pacman speed needs to be updated.
         if(this.level == 2 && !map2 ) { // level 2 and need to open teleports
        	flag_level3=true;
            MapData map1 = getMapFromResource("/resources/maps/map1_c.txt");
            adjustMap(map1);
            this.map = map1.getMap();
            BFSFinder.map = map1.getMap();
            for(Ghost g: ghosts) {
            	g.parentBoard = this;
            	g.bfs = new BFSFinder(this);
            	if(g.level==2)
        			g.level=1;
            }
            map2 = true;
            pacman.parentBoard = this;
            if(pacman.level==2)
            	pacman.level=1;
        }
        else if(this.level !=2) {
        	if(map2) { // close teleports
                MapData map1 = getMapFromResource("/resources/maps/start_map.txt");
                adjustMap(map1);
                this.map = map1.getMap();
                BFSFinder.map = map1.getMap();
                for(Ghost g: ghosts) {
                	g.parentBoard = this;
                	g.bfs = new BFSFinder(this);
                	if(g.level==2)
            			g.level=1;
                }
                map2 = false;
                pacman.parentBoard = this;
        	}
            if(this.level==3 && flag_level3) {
            	flag_level3=false;
            	pacman.level = 2;
            	flag=true;
            	for(Ghost gh: ghosts) {
            		if(gh.level==2)
            			gh.level=1;
            		gh.parentBoard = this;
            		gh.bfs = new BFSFinder(this);
            	}
            }
            else if(this.level==4 && flag) {
            	flag=false;
            	flag_level3=true;
            	for(Ghost gh: ghosts) {
            		gh.level=2;
            		gh.parentBoard = this;
            		gh.bfs = new BFSFinder(this);
            		
            	}
            }  
        } 
        score+=scoreToAdd;
        scoreboard.setText("    Score : "+score + "   Lives : "+lives + "   Level : " + this.level);
        
        if(bombPoints>0 && !pacmanBomb) { 
        	pacman.setBombImage(this.pacwoman);
        	pacmanBomb = true;
        }
        
        else if(bombPoints<1 && pacmanBomb) {
        	pacman.setPacImage(this.pacwoman);
        	pacmanBomb = false;
        }

        //Check Ghost Undie
        for(Ghost g:ghosts){
            if(g.isDead() && g.logicalPosition.x == ghostBase.x && g.logicalPosition.y == ghostBase.y){
                g.undie();
            }
        }
        
        //Check Teleport
        for(TeleportTunnel tp : teleports) {
            if (pacman.logicalPosition.x == tp.getFrom().x && pacman.logicalPosition.y == tp.getFrom().y && pacman.activeMove == tp.getReqMove()) {
                //System.out.println("TELE !");
                pacman.logicalPosition = tp.getTo();
                pacman.pixelPosition.x = pacman.logicalPosition.x * 28;
                pacman.pixelPosition.y = pacman.logicalPosition.y * 28;
            }
        }

        //Check isSiren
        boolean isSiren = true;
        for(Ghost g:ghosts){
            if(g.isWeak()){
                isSiren = false;
            }
        }
        
        if(isSiren){
            pac6.stop();
            if(mustReactivateSiren){
                mustReactivateSiren = false;
                siren.start();
            }

        }
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Draw Walls
        g.setColor(Color.blue);
        for(int i=0;i<m_x;i++){
            for(int j=0;j<m_y;j++){
                if(map[i][j]>0){
                    //g.drawImage(10+i*28,10+j*28,28,28);
                    g.drawImage(mapSegments[map[i][j]],10+i*28,10+j*28,null);
                }
            }
        }

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Food f : foods){
            g.drawImage(foodImage,10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw PowerUpFoods
        g.setColor(new Color(204, 174, 168));
        for(PowerUpFood f : pufoods){
            g.drawImage(pfoodImage[f.type],10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw Pacman
        switch(pacman.activeMove){
            case NONE:
            case RIGHT:
                g.drawImage(pacman.getPacmanImage(),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case LEFT:
                g.drawImage(ImageHelper.flipHor(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case DOWN:
                g.drawImage(ImageHelper.rotate90(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case UP:
                g.drawImage(ImageHelper.flipVer(ImageHelper.rotate90(pacman.getPacmanImage())),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
        }

        //Draw Ghosts
        for(Ghost gh : ghosts){
            g.drawImage(gh.getGhostImage(), 10+gh.pixelPosition.x, 10+gh.pixelPosition.y,null);
        }

        if(clearScore){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore =false;
        }

        if(drawScore) {
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd;
            g.drawString(s.toString(), pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 50);
            //drawScore = false;
            score += s;
            scoreboard.setText("    Score : "+score + "   Lives : "+lives + "   Level : " + this.level);
            clearScore = true;

        }

        if(isGameOver){
            g.drawImage(goImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }

        if(isWin){
            g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }


    }


    @Override
    public void processEvent(AWTEvent ae){

        if(ae.getID()== Messages.UPDATE) {
            update();
        }else if(ae.getID()== Messages.COLTEST) {
            if (!isGameOver) {
                collisionTest();
            }
        }else if(ae.getID()== Messages.RESET){
            if(isGameOver)
                restart();
        }else if(ae.getID() == Messages.HOME) {
        	if(isWin || isGameOver) {
        		new StartWindow();
        		this.windowParent.dispose();
        	}	
        }
        		
        else {
            super.processEvent(ae);
        }
    }
    
    //freeze and unfreeze all moving object on board(pacman and ghosts)
    public void freeze() {
        pacman.moveTimer.stop();
        pacman.animTimer.stop();
        for(Ghost g: ghosts) {
        	g.moveTimer.stop();
        	g.animTimer.stop();
        }
    }
    public void unfreeze() {
        pacman.moveTimer.start();
        pacman.animTimer.start();
        for(Ghost g: ghosts) {
        	g.moveTimer.start();
        	g.animTimer.start();
        }
    }
    
    //Restart using same player name
    public void restart(){
        siren.stop();
        new PacWindow(this.playerName, this.pacwoman);
        windowParent.dispose();
    }
    
    public void setBombPoints(int num) {
    	if(num<0)
    		this.bombPoints=0;
    	else
    		this.bombPoints=num;
    }
    public int getBombPoints() {
    	return this.bombPoints;
    }
    
  
    //Map Methods
    public MapData getMapFromResource(String relPath) {
        String mapStr = "";
        try {
            Scanner scn = new Scanner(this.getClass().getResourceAsStream(relPath));
            StringBuilder sb = new StringBuilder();
            String line;
            while (scn.hasNextLine()) {
                line = scn.nextLine();
                sb.append(line).append('\n');
            }
            mapStr = sb.toString();
        } catch (Exception e) {
            System.err.println("Error Reading Map File !");
        }
        if ("".equals(mapStr)) {
            System.err.println("Map is Empty !");
        }
        return MapEditor.compileMap(mapStr);
    }

    //Dynamically Generate Map Segments
    public void adjustMap(MapData mapd) {
        int[][] map = mapd.getMap();
        int mx = mapd.getX();
        int my = mapd.getY();
        for (int y = 0; y < my; y++) {
            for (int x = 0; x < mx; x++) {
                boolean l = false;
                boolean r = false;
                boolean t = false;
                boolean b = false;
                boolean tl = false;
                boolean tr = false;
                boolean bl = false;
                boolean br = false;


                if (map[x][y] > 0 && map[x][y] < 26) {
                    int mustSet = 0;
                    //LEFT
                    if (x > 0 && map[x - 1][y] > 0 && map[x - 1][y] < 26) {
                        l = true;
                    }
                    //RIGHT
                    if (x < mx - 1 && map[x + 1][y] > 0 && map[x + 1][y] < 26) {
                        r = true;
                    }
                    //TOP
                    if (y > 0 && map[x][y - 1] > 0 && map[x][y - 1] < 26) {
                        t = true;
                    }
                    //Bottom
                    if (y < my - 1 && map[x][y + 1] > 0 && map[x][y + 1] < 26) {
                        b = true;
                    }
                    //TOP LEFT
                    if (x > 0 && y > 0 && map[x - 1][y - 1] > 0 && map[x - 1][y - 1] < 26) {
                        tl = true;
                    }
                    //TOP RIGHT
                    if (x < mx - 1 && y > 0 && map[x + 1][y - 1] > 0 && map[x + 1][y - 1] < 26) {
                        tr = true;
                    }
                    //Bottom LEFT
                    if (x > 0 && y < my - 1 && map[x - 1][y + 1] > 0 && map[x - 1][y + 1] < 26) {
                        bl = true;
                    }
                    //Bottom RIGHT
                    if (x < mx - 1 && y < my - 1 && map[x + 1][y + 1] > 0 && map[x + 1][y + 1] < 26) {
                        br = true;
                    }

                    //Decide Image to View
                    if (!r && !l && !t && !b) {
                        mustSet = 23;
                    }
                    if (r && !l && !t && !b) {
                        mustSet = 22;
                    }
                    if (!r && l && !t && !b) {
                        mustSet = 25;
                    }
                    if (!r && !l && t && !b) {
                        mustSet = 21;
                    }
                    if (!r && !l && !t && b) {
                        mustSet = 19;
                    }
                    if (r && l && !t && !b) {
                        mustSet = 24;
                    }
                    if (!r && !l && t && b) {
                        mustSet = 20;
                    }
                    if (r && !l && t && !b && !tr) {
                        mustSet = 11;
                    }
                    if (r && !l && t && !b && tr) {
                        mustSet = 2;
                    }
                    if (!r && l && t && !b && !tl) {
                        mustSet = 12;
                    }
                    if (!r && l && t && !b && tl) {
                        mustSet = 3;
                    }
                    if (r && !l && !t && b && br) {
                        mustSet = 1;
                    }
                    if (r && !l && !t && b && !br) {
                        mustSet = 10;
                    }
                    if (!r && l && !t && b && bl) {
                        mustSet = 4;
                    }
                    if (r && !l && t && b && !tr) {
                        mustSet = 15;
                    }
                    if (r && !l && t && b && tr) {
                        mustSet = 6;
                    }
                    if (!r && l && t && b && !tl) {
                        mustSet = 17;
                    }
                    if (!r && l && t && b && tl) {
                        mustSet = 8;
                    }
                    if (r && l && !t && b && !br) {
                        mustSet = 14;
                    }
                    if (r && l && !t && b && br) {
                        mustSet = 5;
                    }
                    if (r && l && t && !b && !tr) {
                        mustSet = 16;
                    }
                    if (r && l && t && !b && tr) {
                        mustSet = 7;
                    }
                    if (!r && l && !t && b && !bl) {
                        mustSet = 13;
                    }
                    if (r && l && t && b && br && tl) {
                        mustSet = 9;
                    }
                    if (r && l && t && b && !br && !tl) {
                        mustSet = 18;
                    }

                    //System.out.println("MAP SEGMENT : " + mustSet);
                    map[x][y] = mustSet;
                }
                mapd.setMap(map);
            }
        }
        System.out.println("Map Adjust OK !");
    }
    
    public boolean isGhostNearby(Ghost g) {
    	
    	Rectangle pr = new Rectangle(pacman.pixelPosition.x+13,pacman.pixelPosition.y+13,50,50);
    	Rectangle prMinus = new Rectangle(pacman.pixelPosition.x-13,pacman.pixelPosition.y-13,50,50);
        Rectangle gr = new Rectangle(g.pixelPosition.x,g.pixelPosition.y,28,28);
        return pr.intersects(gr) || prMinus.intersects(gr);
    			
    }

}
