package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;


public class Player extends Entity {
   
    KeyHandler keyH;
    
    
    public final int screenX;
    public final int screenY;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxinventorySize = 20;
    
    public Player(GamePanel gp, KeyHandler keyH){
        
        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        //attackArea.width = 36;
        //attackArea.height = 36;
        
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues(){
        //player stats
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        //ammo = 10;
        level = 1;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack();
        defence = getDefence();
    }
    public void setDefaultPositions(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }
    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
    }
    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
        
    }
    public int getDefence(){
        return defence = dexterity * currentShield.defenceValue;
    }
    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i=0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i=0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getPlayerImage(){
        
        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage(){
        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);       
        }
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);       
        }
        
        
    }
    
    public void update(){
        
        if(attacking == true){
            attacking();
        }
        else if(keyH.enterPressed == true || keyH.upPressed == true || keyH.downPressed == true 
                || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed == true){
            direction = "up";
            
            }
            else if(keyH.downPressed == true){
                direction = "down";
            
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            
            }
            
            //check tile collision    
            collisionOn = false;
            gp.CDetecter.checkTile(this);
        
            //check object collision
            int objIndex = gp.CDetecter.checkObject(this, true);
            pickUpObject(objIndex);
        
            //check npc collision
            int npcIndex = gp.CDetecter.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
        
            //checck monster collision
            int monsterIndex = gp.CDetecter.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
        
            //check  event
            gp.eHandler.checkEvent();
        
            //if collision is false player can move
            if(collisionOn == false && keyH.enterPressed == false){
            
                switch(direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            if(keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }
            attackCanceled = false;
            gp.keyH.enterPressed = false;
            
            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        //fire projectile
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this) == true){
            projectile.set(worldX, worldY, direction, true, this);
            projectile.subtractResource(this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
            gp.playSE(9);
        }
        if(invincible == true){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        //fix bug projectile
        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
        //fix bug life and mana
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.music.stop();
            gp.playSE(10);
        }
}
    public void attacking(){
        
        spriteCounter++;
        if(spriteCounter <= 5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <= 25){
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            switch(direction){
                case "up":
                    worldY -= attackArea.height; 
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            int monsterIndex = gp.CDetecter.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);
            
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
            
    }
    public void pickUpObject(int i){
        
        if(i != 999){
            
            String text;
            
            if(inventory.size() != maxinventorySize){
                inventory.add(gp.obj[i]);
                gp.playSE(1);
                text = "Got a " + gp.obj[i].name + "!";
            }
            else{
                text = "Your inventory is full !";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }
    public void interactNPC(int i){
            if(i != 999){
                
                if(gp.keyH.enterPressed == true){
                    attackCanceled = true;
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
                }
            }    
        
    }
    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false && gp.monster[i].dying == false){
                gp.playSE(6);
                int damage = gp.monster[i].attack - defence;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
            
        }
    }
    public void damageMonster(int i, int attack){
        if(i != 999){
            if(gp.monster[i].invincible == false){
                gp.playSE(5);
                int damage = attack - gp.monster[i].defence;
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + "damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                
                if(gp.monster[i].life <= 0){
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Killed the" + gp.monster[i].name + "!");
                    gp.ui.addMessage("EXP + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    
                    checkLevelUp();
                }
            }
        }
    }
    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp * 3;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defence = getDefence();
            gp.playSE(7);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "Level up ! :\n Level = " + level +"\n"
                    +"Max life, strength, dexterity, attack and defense\n increased !";
        }
    }
    public void selectItem(){
        
        int itemIndex = gp.ui.getItemIndexForSlot();
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defence = getDefence();
            }
            if(selectedItem.type == type_consumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    
    public void draw(Graphics2D g2){
        
        
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        switch(direction){
        case "up":
            if(attacking == false){
                if(spriteNum == 1){
                    image = up1;   
                }
                if(spriteNum == 2){
                    image = up2;
                } 
            }
            if(attacking == true){
               tempScreenY = screenY - gp.tileSize;
               if(spriteNum == 1){
                    image = attackUp1;   
                }
                if(spriteNum == 2){
                    image = attackUp2;
                }  
            }
            break;
            
        case "down":
            if(attacking == false){
                if(spriteNum == 1){
                    image = down1;   
                }
                if(spriteNum == 2){
                    image = down2;
                } 
            }
            if(attacking == true){
               if(spriteNum == 1){
                    image = attackDown1;   
                }
                if(spriteNum == 2){
                    image = attackDown2;
                }  
            } 
            break;
        case "left":
            if(attacking == false){
                if(spriteNum == 1){
                    image = left1;   
                }
                if(spriteNum == 2){
                    image = left2;
                } 
            }
            if(attacking == true){
                tempScreenX = screenX - gp.tileSize;
               if(spriteNum == 1){
                    image = attackLeft1;   
                }
                if(spriteNum == 2){
                    image = attackLeft2;
                }  
            } 
            break;
        case "right":
            if(attacking == false){
                if(spriteNum == 1){
                    image = right1;   
                }
                if(spriteNum == 2){
                    image = right2;
                } 
            }
            if(attacking == true){
               if(spriteNum == 1){
                    image = attackRight1;   
                }
                if(spriteNum == 2){
                    image = attackRight2;
                }  
            } 
            break;    
        }
        if(invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
