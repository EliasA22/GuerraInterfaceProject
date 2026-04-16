/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.guerrainterfaceprototype;

/**
 *
 * @author owenjohnson
 */
public class MainGame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainGame.class.getName());
    
    
    // ---------------- GAME VARIABLES ----------------
private boolean presentationMode = true;
private int playerHealth = 25;
private int aiHealth = 25;

private int selectedCardButton = -1;

// very simple test values for now
private int playerBooster = 0;
private boolean playerHasBooster = false;
private boolean playerBoosterArmed = false;
private boolean playerBoosterUsedThisRound = false;

// Deck Counters
private int sharedDeckCount = 0;
private java.util.ArrayList<Card> sharedDeck = new java.util.ArrayList<>();

private Card playerCard1;
private Card playerCard2;
private Card playerCard3;
private Card playerCard4;

private Card aiCard1;
private Card aiCard2;
private Card aiCard3;
private Card aiCard4;

private boolean playerNextCardMinusOne = false;
private boolean aiNextCardMinusOne = false;

private boolean playerNextWinMultiplier = false;
private boolean aiNextWinMultiplier = false;

private double aiDamageMultiplier = 1.0;
private double aiHealMultiplier = 1.0;

private int playerBoosterTurnsLeft = 0;
private int aiBoosterTurnsLeft = 0;

private int aiBooster = 0;
private boolean aiHasBooster = false;

private Card playerBonusCard;
private Card aiBonusCard;

private boolean playerHasBonusCard = false;
private boolean aiHasBonusCard = false;

private boolean playerOneRoundHandReduction = false;
private boolean aiOneRoundHandReduction = false;

private int blockedPlayerSlot = -1;
private int blockedAISlot = -1;
private int playerBlockedRoundsLeft = 0;
private int aiBlockedRoundsLeft = 0;

private int currentRoundAISlot = -1;
private int currentRoundPlayerSlot = -1;
private boolean currentRoundPlayerUsedBonus = false;

private String currentRoundLog = "";

private boolean warActive = false;

private Card warPlayerTieCard = null;
private Card warAITieCard = null;

private Card warPlayerFinalCard = null;
private Card warAIFinalCard = null;

private int warPlayerOriginalSelectedButton = -1;
private int warAISlot = -1;




    /**
     * Creates new form MainGame
     */
    public MainGame() {
        initComponents();
        loadDifficultySettings();
        startGame();
    }
    
    
    private void startGame() {
    playerHealth = 25;
    aiHealth = 25;
    selectedCardButton = -1;
    playerBooster = 0;
    playerHasBooster = false;
    playerBoosterArmed = false;
    playerBoosterUsedThisRound = false;
    playerBoosterTurnsLeft = 0;
    aiBoosterTurnsLeft = 0;
    
    aiBooster = 0;
    aiHasBooster = false;

    jProgressBar1.setMaximum(25);
    jProgressBar2.setMaximum(25);

    jProgressBar1.setValue(playerHealth);
    jProgressBar2.setValue(aiHealth);

    jProgressBar1.setStringPainted(true);
    jProgressBar2.setStringPainted(true);

    jProgressBar1.setString("Player Health: " + playerHealth);
    jProgressBar2.setString("AI Health: " + aiHealth);
    
    
    buildDeck(sharedDeck);
    setStartingPlayerHand();
    setStartingAIHand();

    sharedDeckCount = sharedDeck.size();
    
    
    
    
    currentRoundLog = "";
    
    jButton9.setIcon(null);
    jButton10.setIcon(null);
    
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);
    
    jButton11.setIcon(null);
    jButton11.setText("PLAY");
    
    jButton12.setIcon(null);
    jButton12.setText("AI");
    
    jButton13.setVisible(false);
    jButton14.setVisible(false);
    
    
    playerBonusCard = null;
    aiBonusCard = null;
    playerHasBonusCard = false;
    aiHasBonusCard = false;
    
    jButton13.setIcon(null);
    jButton13.setText("");
    jButton13.setEnabled(false);

    jButton14.setIcon(null);
    jButton14.setText("");
    jButton14.setEnabled(false);
    
    jButton15.setText("Use Booster");
    jButton15.setEnabled(false);

    jButton13.setVisible(false);
    jButton14.setVisible(false);
    
    setTitle("Guerra - Difficulty: " + GameSettings.difficulty + 
         " | Booster: " + playerBooster + 
         " | Turns Left: " + playerBoosterTurnsLeft);
    
    
    updateTitleBar();
    
    playerOneRoundHandReduction = false;
    aiOneRoundHandReduction = false;
    blockedPlayerSlot = -1;
    blockedAISlot = -1;
    playerBlockedRoundsLeft = 0;
    aiBlockedRoundsLeft = 0;
    
    currentRoundAISlot = -1;
    currentRoundPlayerSlot = -1;
    currentRoundPlayerUsedBonus = false;
    warActive = false;
warPlayerTieCard = null;
warAITieCard = null;
warPlayerFinalCard = null;
warAIFinalCard = null;
warPlayerOriginalSelectedButton = -1;
warAISlot = -1;

jButton16.setVisible(false);
jButton17.setVisible(false);
jButton18.setVisible(false);
jButton19.setVisible(false);
jButton20.setVisible(false);
jButton21.setVisible(false);
jButton22.setVisible(false);

jButton16.setIcon(null);
jButton17.setIcon(null);
jButton18.setIcon(null);
jButton19.setIcon(null);
jButton20.setIcon(null);
jButton21.setIcon(null);

jButton16.setText("");
jButton17.setText("");
jButton18.setText("");
jButton19.setText("");
jButton20.setText("");
jButton21.setText("");

jButton22.setText("WAR");
    
    
    
}
    
    
private void updateTitleBar() {
    String boosterStatus = "OFF";

    if (playerHasBooster) {
        if (playerBoosterArmed) {
            boosterStatus = "ARMED";
        } else {
            boosterStatus = "READY";
        }
    }

    setTitle("Guerra - Difficulty: " + GameSettings.difficulty +
             " | Player Booster: " + playerBooster +
             " | Booster Turns: " + playerBoosterTurnsLeft +
             " | Booster Status: " + boosterStatus);

    if (playerHasBooster && !playerBoosterArmed) {
        jButton15.setEnabled(true);
        jButton15.setText("Use Booster");
    } else if (playerBoosterArmed) {
        jButton15.setEnabled(false);
        jButton15.setText("Booster Armed");
    } else {
        jButton15.setEnabled(false);
        jButton15.setText("Use Booster");
    }
}
    
    
    
    private void armPlayerBooster() {
    if (!playerHasBooster || playerBooster <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "You do not have a booster to use.");
        return;
    }

    if (playerBoosterArmed) {
        javax.swing.JOptionPane.showMessageDialog(this, "Your booster is already armed.");
        return;
    }

    playerBoosterArmed = true;
    updateTitleBar();
    javax.swing.JOptionPane.showMessageDialog(this, "Booster armed. Play your next card.");
}
    
    
    
    private void loadDifficultySettings() {
    if (GameSettings.difficulty.equals("Easy")) {
        aiDamageMultiplier = 0.8;
        aiHealMultiplier = 0.8;
    } else if (GameSettings.difficulty.equals("Hard")) {
        aiDamageMultiplier = 1.2;
        aiHealMultiplier = 1.2;
    } else {
        aiDamageMultiplier = 1.0;
        aiHealMultiplier = 1.0;
    }
}
    
    
    
    
private void buildDeck(java.util.ArrayList<Card> deck) {
    deck.clear();

    String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};

    for (String suit : suits) {
        for (int value = 2; value <= 14; value++) {
            String valueName = "";

            if (value >= 2 && value <= 10) {
                valueName = "" + value;
            } else if (value == 11) {
                valueName = "Jack";
            } else if (value == 12) {
                valueName = "Queen";
            } else if (value == 13) {
                valueName = "King";
            } else if (value == 14) {
                valueName = "Ace";
            }

            String imagePath = "/images/" + valueName + suit + ".png";
            deck.add(new Card(suit, value, imagePath));
        }
    }

    java.util.Collections.shuffle(deck);
}
    
    
private Card drawSharedDeckCard() {
    if (sharedDeck.isEmpty()) {
        return null;
    }
    return sharedDeck.remove(0);
}

    
    
    
    
    
    
    
    
    
    
    
    
    private class Card {
    private String suit;
    private int value;
    private String imagePath;

    public Card(String suit, int value, String imagePath) {
        this.suit = suit;
        this.value = value;
        this.imagePath = imagePath;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }
}
    
    private Card getSelectedPlayerCard() {
    if (selectedCardButton == 1) {
        return playerCard1;
    } else if (selectedCardButton == 2) {
        return playerCard2;
    } else if (selectedCardButton == 3) {
        return playerCard3;
    } else if (selectedCardButton == 4) {
        return playerCard4;
    } else if (selectedCardButton == 13) {
        return playerBonusCard;
    }
    return null;
}
    
    private int getSelectedPlayerSlot() {
    if (selectedCardButton == 1) {
        return 1;
    } else if (selectedCardButton == 2) {
        return 2;
    } else if (selectedCardButton == 3) {
        return 3;
    } else if (selectedCardButton == 4) {
        return 4;
    }
    return -1;
}
    
    
    
    
    
    
    
    
    
    private void selectBonusPlayerCard() {
    if (playerHasBonusCard && playerBonusCard != null) {
        selectedCardButton = 13;
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerBonusCard.getImagePath())));
        jButton11.setText("");
    }
}

private void givePlayerBonusCard() {
    Card newBonus = drawSharedDeckCard();

    if (newBonus == null) {
        addRoundLog("No more cards left in the deck for a player bonus card.");
        return;
    }

    playerBonusCard = newBonus;
    playerHasBonusCard = true;

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);

    jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerBonusCard.getImagePath())));
    jButton13.setText("");
    jButton13.setVisible(true);
    jButton13.setEnabled(true);
}

private void giveAIBonusCard() {
    Card newBonus = drawSharedDeckCard();

    if (newBonus == null) {
        addRoundLog("No more cards left in the deck for an AI bonus card.");
        return;
    }

    aiBonusCard = newBonus;
    aiHasBonusCard = true;

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);

    jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png")));
    jButton14.setText("");
    jButton14.setVisible(true);
    jButton14.setEnabled(false);
}

    
    
    

    
    
    
    
    
    private int getRandomAISlot() {
    int slot;

    do {
        slot = (int)(Math.random() * 4) + 1;
    } while (aiOneRoundHandReduction && slot == blockedAISlot);

    return slot;
}

    
private void restoreBlockedSlots() {
    if (playerOneRoundHandReduction) {
        playerBlockedRoundsLeft--;

        if (playerBlockedRoundsLeft <= 0) {
            if (blockedPlayerSlot == 1) {
                updatePlayerButtonIcon(1);
            } else if (blockedPlayerSlot == 2) {
                updatePlayerButtonIcon(2);
            } else if (blockedPlayerSlot == 3) {
                updatePlayerButtonIcon(3);
            } else if (blockedPlayerSlot == 4) {
                updatePlayerButtonIcon(4);
            }

            playerOneRoundHandReduction = false;
            blockedPlayerSlot = -1;
            playerBlockedRoundsLeft = 0;
        }
    }

    if (aiOneRoundHandReduction) {
        aiBlockedRoundsLeft--;

        if (aiBlockedRoundsLeft <= 0) {
            if (blockedAISlot == 1) {
                updateAIButtonBack(1);
            } else if (blockedAISlot == 2) {
                updateAIButtonBack(2);
            } else if (blockedAISlot == 3) {
                updateAIButtonBack(3);
            } else if (blockedAISlot == 4) {
                updateAIButtonBack(4);
            }

            aiOneRoundHandReduction = false;
            blockedAISlot = -1;
            aiBlockedRoundsLeft = 0;
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
private Card getAICardBySlot(int slot) {
    if (slot == 1) {
        return aiCard1;
    } else if (slot == 2) {
        return aiCard2;
    } else if (slot == 3) {
        return aiCard3;
    } else if (slot == 4) {
        return aiCard4;
    }
    return null;
}
    


    private void setStartingPlayerHand() {
    playerCard1 = drawSharedDeckCard();
    playerCard2 = drawSharedDeckCard();
    playerCard3 = drawSharedDeckCard();
    playerCard4 = drawSharedDeckCard();

    updatePlayerButtonIcon(1);
    updatePlayerButtonIcon(2);
    updatePlayerButtonIcon(3);
    updatePlayerButtonIcon(4);
}
    
    
    
    
private void setStartingAIHand() {
    aiCard1 = drawSharedDeckCard();
    aiCard2 = drawSharedDeckCard();
    aiCard3 = drawSharedDeckCard();
    aiCard4 = drawSharedDeckCard();

    updateAIButtonBack(1);
    updateAIButtonBack(2);
    updateAIButtonBack(3);
    updateAIButtonBack(4);
}
    
    
    
    
    
    
    private void resetPlayerCardButtons() {
    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2Clubs.png")));
    jButton1.setText("");
    jButton1.setEnabled(true);

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/7Diamonds.png")));
    jButton2.setText("");
    jButton2.setEnabled(true);

    jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JackHearts.png")));
    jButton3.setText("");
    jButton3.setEnabled(true);

    jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/8Spades.png")));
    jButton4.setText("");
    jButton4.setEnabled(true);
}
    
    

private void selectPlayerCard(int buttonNumber) {
    if (warActive) {
        return;
    }

    if (playerOneRoundHandReduction && buttonNumber == blockedPlayerSlot) {
        return;
    }

    selectedCardButton = buttonNumber;
    showSelectedCardInMiddle();
}







private void showWarPile() {
    javax.swing.ImageIcon backIcon =
            new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"));

    jButton16.setIcon(backIcon);
    jButton17.setIcon(backIcon);
    jButton18.setIcon(backIcon);

    jButton19.setIcon(backIcon);
    jButton20.setIcon(backIcon);
    jButton21.setIcon(backIcon);

    jButton16.setText("");
    jButton17.setText("");
    jButton18.setText("");
    jButton19.setText("");
    jButton20.setText("");
    jButton21.setText("");

    jButton16.setVisible(true);
    jButton17.setVisible(true);
    jButton18.setVisible(true);
    jButton19.setVisible(true);
    jButton20.setVisible(true);
    jButton21.setVisible(true);

    jButton22.setVisible(true);
    jButton22.setText("WAR");
}

private void hideWarPile() {
    jButton16.setIcon(null);
    jButton17.setIcon(null);
    jButton18.setIcon(null);
    jButton19.setIcon(null);
    jButton20.setIcon(null);
    jButton21.setIcon(null);

    jButton16.setText("");
    jButton17.setText("");
    jButton18.setText("");
    jButton19.setText("");
    jButton20.setText("");
    jButton21.setText("");

    jButton16.setVisible(false);
    jButton17.setVisible(false);
    jButton18.setVisible(false);
    jButton19.setVisible(false);
    jButton20.setVisible(false);
    jButton21.setVisible(false);

    jButton22.setVisible(false);
}



private void startWar(Card playerCard, Card aiCard, int aiSlot) {
    warActive = true;

    warPlayerTieCard = playerCard;
    warAITieCard = aiCard;

    warPlayerOriginalSelectedButton = selectedCardButton;
    warAISlot = aiSlot;

    addRoundLog("War started.");
    addRoundLog("Both players place 3 cards face down.");

if (sharedDeck.size() >= 6) {
    for (int i = 0; i < 6; i++) {
        drawSharedDeckCard();
    }
} else {
    sharedDeck.clear();
}

sharedDeckCount = sharedDeck.size();
jButton9.setText("" + sharedDeckCount);
jButton10.setText("" + sharedDeckCount);

    showWarPile();
    showRoundLog();
}



private void resolveWarRound() {
    if (!warActive) {
        return;
    }

    clearRoundLog();
    playerBoosterUsedThisRound = false;
    addRoundLog("War button pressed.");

    if (sharedDeck.size() < 2) {
    addRoundLog("A player ran out of cards during war.");

    hideWarPile();

    warActive = false;
    warPlayerTieCard = null;
    warAITieCard = null;
    warPlayerFinalCard = null;
    warAIFinalCard = null;
    warPlayerOriginalSelectedButton = -1;
    warAISlot = -1;

    showRoundLog();
    return;
}

warPlayerFinalCard = drawSharedDeckCard();
warAIFinalCard = drawSharedDeckCard();

sharedDeckCount = sharedDeck.size();
jButton9.setText("" + sharedDeckCount);
jButton10.setText("" + sharedDeckCount);

    jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource(warPlayerFinalCard.getImagePath())));
    jButton11.setText("");

    jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource(warAIFinalCard.getImagePath())));
    jButton12.setText("");

    addRoundLog("Your war card: " + cardToText(warPlayerFinalCard));
    addRoundLog("AI war card: " + cardToText(warAIFinalCard));

    int playerValue = warPlayerFinalCard.getValue();
    int aiValue = warAIFinalCard.getValue();

    addRoundLog("War values -> You: " + playerValue + " | AI: " + aiValue);

    if (playerValue > aiValue) {
        addRoundLog("You won the war.");
        applyWinningSuitEffect(warPlayerFinalCard, true);
        applyLosingCardEffect(warAIFinalCard, false);
    } else if (aiValue > playerValue) {
        addRoundLog("AI won the war.");
        applyWinningSuitEffect(warAIFinalCard, false);
        applyLosingCardEffect(warPlayerFinalCard, true);
    } else {
        addRoundLog("War tied again. No extra effect for now.");
    }

    selectedCardButton = warPlayerOriginalSelectedButton;
    removeUsedPlayerCard();
    replaceUsedPlayerCard();
    replaceUsedAICard(warAISlot);

finishPlayerBoosterUse();
updateBoosterTimers();
restoreBlockedSlots();
updateTitleBar();

    hideWarPile();

    warActive = false;
    warPlayerTieCard = null;
    warAITieCard = null;
    warPlayerFinalCard = null;
    warAIFinalCard = null;
    warPlayerOriginalSelectedButton = -1;
    warAISlot = -1;

    selectedCardButton = -1;
    currentRoundAISlot = -1;
    currentRoundPlayerSlot = -1;
    currentRoundPlayerUsedBonus = false;

    showRoundLog();
    checkWinner();
}







private void updateHealthBars() {
    if (playerHealth < 0) {
        playerHealth = 0;
    }
    if (aiHealth < 0) {
        aiHealth = 0;
    }
    if (playerHealth > 25) {
        playerHealth = 25;
    }
    if (aiHealth > 25) {
        aiHealth = 25;
    }

    jProgressBar1.setValue(playerHealth);
    jProgressBar2.setValue(aiHealth);

    jProgressBar1.setString("Player Health: " + playerHealth);
    jProgressBar2.setString("AI Health: " + aiHealth);
}




private void damageAI(int amount) {
    aiHealth = aiHealth - amount;
    updateHealthBars();
    checkWinner();
}




private void healPlayer(int amount) {
    playerHealth = playerHealth + amount;
    updateHealthBars();
    checkWinner();
}




private void damagePlayer(int amount) {
    playerHealth = playerHealth - amount;
    updateHealthBars();
    checkWinner();
}




private void checkWinner() {
    if (aiHealth <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "You win!");
    } else if (playerHealth <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "AI wins!");
    }
}



private void aiTurn() {
    int aiChoice = (int)(Math.random() * 4) + 1;
    int aiValue = (int)(Math.random() * 13) + 2;

    showAICardInMiddle(aiChoice);

    if (aiChoice == 1) {
        damagePlayer(aiValue);
    } else if (aiChoice == 2) {
        healAI(aiValue);
    } else if (aiChoice == 3) {
        //sharedDeckCount = sharedDeckCount + 1;
        jButton10.setText("" + sharedDeckCount);
    } else if (aiChoice == 4) {
        // AI club booster placeholder for later
    }
}


private void showAICardInMiddle(int aiChoice) {
    if (aiChoice == 1) {
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/7Diamonds.png")));
        jButton12.setText("");
    } else if (aiChoice == 2) {
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JackHearts.png")));
        jButton12.setText("");
    } else if (aiChoice == 3) {
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/8Spades.png")));
        jButton12.setText("");
    } else if (aiChoice == 4) {
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2Clubs.png")));
        jButton12.setText("");
    }
}



private void showRealAICardInMiddle(Card aiCard) {
    if (aiCard != null) {
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource(aiCard.getImagePath())));
        jButton12.setText("");
    }
}



private void applyLosingCardEffect(Card losingCard, boolean playerLost) {
    if (losingCard == null) {
        return;
    }

    int value = losingCard.getValue();

    if (value == 2) {
        if (playerLost) {
            blockRandomAIHandSlot();
            addRoundLog("Losing card effect 2 activated: one hand slot is blocked next round.");
        } else {
        blockRandomPlayerHandSlot();
    }

    } else if (value == 3) {
    if (playerLost) {
        revealChosenAICard();
    } else {
        revealChosenPlayerCardByAI();
    }

    } else if (value == 4) {
    if (playerLost) {
        givePlayerBonusCard();
        addRoundLog("You gained a bonus card.");
    } else {
        giveAIBonusCard();
        addRoundLog("AI gained a bonus card.");
    }

    } else if (value == 5) {
        if (playerLost) {
            aiNextCardMinusOne = true;
        } else {
            playerNextCardMinusOne = true;
            addRoundLog("Losing card effect 5 activated: next card gets -1.");
        }

    } else if (value == 6) {
        if (playerLost) {
            playerNextWinMultiplier = true;
            addRoundLog("Losing card effect 6 activated: next win gets 1.5x effect.");
        } else {
            aiNextWinMultiplier = true;
        }
    }
}




private void blockRandomPlayerHandSlot() {
    blockedPlayerSlot = getRandomPlayerSlot();
    playerOneRoundHandReduction = true;
    playerBlockedRoundsLeft = 2;

    if (blockedPlayerSlot == 1) {
        jButton1.setIcon(null);
        jButton1.setText("BLOCKED");
        jButton1.setEnabled(false);
    } else if (blockedPlayerSlot == 2) {
        jButton2.setIcon(null);
        jButton2.setText("BLOCKED");
        jButton2.setEnabled(false);
    } else if (blockedPlayerSlot == 3) {
        jButton3.setIcon(null);
        jButton3.setText("BLOCKED");
        jButton3.setEnabled(false);
    } else if (blockedPlayerSlot == 4) {
        jButton4.setIcon(null);
        jButton4.setText("BLOCKED");
        jButton4.setEnabled(false);
    }
}







private void blockRandomAIHandSlot() {
    blockedAISlot = getRandomAISlot();
    aiOneRoundHandReduction = true;
    aiBlockedRoundsLeft = 2;
    
    if (blockedAISlot == 1) {
        jButton5.setIcon(null);
        jButton5.setText("BLOCKED");
        jButton5.setEnabled(false);
    } else if (blockedAISlot == 2) {
        jButton6.setIcon(null);
        jButton6.setText("BLOCKED");
        jButton6.setEnabled(false);
    } else if (blockedAISlot == 3) {
        jButton7.setIcon(null);
        jButton7.setText("BLOCKED");
        jButton7.setEnabled(false);
    } else if (blockedAISlot == 4) {
        jButton8.setIcon(null);
        jButton8.setText("BLOCKED");
        jButton8.setEnabled(false);
    }
}








private void setPlayerCardBySlot(int slot, Card card) {
    if (slot == 1) {
        playerCard1 = card;
    } else if (slot == 2) {
        playerCard2 = card;
    } else if (slot == 3) {
        playerCard3 = card;
    } else if (slot == 4) {
        playerCard4 = card;
    }
}

private int getRandomPlayerSlot() {
    int slot;

    do {
        slot = (int)(Math.random() * 4) + 1;
    } while (playerOneRoundHandReduction && slot == blockedPlayerSlot);

    return slot;
}




private void updatePlayerButtonIcon(int slot) {
    if (slot == 1 && playerCard1 != null) {
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard1.getImagePath())));
        jButton1.setText("");
        jButton1.setEnabled(true);
    } else if (slot == 2 && playerCard2 != null) {
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard2.getImagePath())));
        jButton2.setText("");
        jButton2.setEnabled(true);
    } else if (slot == 3 && playerCard3 != null) {
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard3.getImagePath())));
        jButton3.setText("");
        jButton3.setEnabled(true);
    } else if (slot == 4 && playerCard4 != null) {
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard4.getImagePath())));
        jButton4.setText("");
        jButton4.setEnabled(true);
    }
}




private void updateAIButtonBack(int slot) {
    if (slot == 1) {
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png")));
        jButton5.setText("");
        jButton5.setEnabled(true);
    } else if (slot == 2) {
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png")));
        jButton6.setText("");
        jButton6.setEnabled(true);
    } else if (slot == 3) {
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png")));
        jButton7.setText("");
        jButton7.setEnabled(true);
    } else if (slot == 4) {
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png")));
        jButton8.setText("");
        jButton8.setEnabled(true);
    }
}

/* private void sendRandomAICardBackToDeck() {
    int slot = getRandomAISlot();
    Card newCard = createRandomCard();

    if (slot == 1) {
        aiCard1 = newCard;
    } else if (slot == 2) {
        aiCard2 = newCard;
    } else if (slot == 3) {
        aiCard3 = newCard;
    } else if (slot == 4) {
        aiCard4 = newCard;
    }

    sharedDeckCount++;
    jButton10.setText("" + sharedDeckCount);
    updateAIButtonBack(slot);
}

private void sendRandomPlayerCardBackToDeck() {
    int slot = getRandomPlayerSlot();
    Card newCard = createRandomCard();

    setPlayerCardBySlot(slot, newCard);
    sharedDeckCount++;
    jButton9.setText("" + sharedDeckCount);
    updatePlayerButtonIcon(slot);
} */

private void revealRandomAICard() {
    int slot = getRandomAISlot();
    Card revealed = getAICardBySlot(slot);

    if (revealed != null) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Revealed AI card: " + revealed.getSuit() + " " + revealed.getValue());
    }
}

private void revealRandomPlayerCard() {
    int slot = getRandomPlayerSlot();
    Card revealed = null;

    if (slot == 1) {
        revealed = playerCard1;
    } else if (slot == 2) {
        revealed = playerCard2;
    } else if (slot == 3) {
        revealed = playerCard3;
    } else if (slot == 4) {
        revealed = playerCard4;
    }

    if (revealed != null) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "AI revealed your card: " + revealed.getSuit() + " " + revealed.getValue());
    }
}

private void drawExtraPlayerCard() {
    sharedDeckCount++;
    jButton9.setText("" + sharedDeckCount);
}

private void drawExtraAICard() {
    sharedDeckCount++;
    jButton10.setText("" + sharedDeckCount);
}








private void applyWinningSuitEffect(Card winningCard, boolean playerWon) {
    if (winningCard == null) {
        return;
    }

    int effectValue = getCardEffectValue(winningCard);

    if (playerWon && playerNextWinMultiplier) {
        effectValue = (int)(effectValue * 1.5);
        playerNextWinMultiplier = false;
    }

    if (!playerWon && aiNextWinMultiplier) {
        effectValue = (int)(effectValue * 1.5);
        aiNextWinMultiplier = false;
    }

    if (winningCard.getSuit().equals("Diamonds")) {
        if (playerWon) {
            if (playerBoosterArmed && playerHasBooster) {
    effectValue += playerBooster;
    playerBoosterUsedThisRound = true;
    addRoundLog("Your booster added +" + playerBooster + " to this card.");
}
            addRoundLog("You dealt " + effectValue + " damage.");
            damageAI(effectValue);
        } else {
            if (aiHasBooster) {
                effectValue += aiBooster;
                aiBooster = 0;
                aiHasBooster = false;
                aiBoosterTurnsLeft = 0;
            }
            int totalDamage = (int)(effectValue * aiDamageMultiplier);
            addRoundLog("AI dealt " + totalDamage + " damage.");
            damagePlayer(totalDamage);    
            //damagePlayer((int)(effectValue * aiDamageMultiplier));
        }   

    } else if (winningCard.getSuit().equals("Hearts")) {
        if (playerWon) {
            if (playerBoosterArmed && playerHasBooster) {
    effectValue += playerBooster;
    playerBoosterUsedThisRound = true;
    addRoundLog("Your booster added +" + playerBooster + " to this card.");
}
            addRoundLog("You healed " + effectValue + " health.");
            healPlayer(effectValue);
        } else {
            if (aiHasBooster) {
                effectValue += aiBooster;
                aiBooster = 0;
                aiHasBooster = false;
                aiBoosterTurnsLeft = 0;
}
            int totalHeal = (int)(effectValue * aiHealMultiplier);
            addRoundLog("AI healed " + totalHeal + " health.");
            healAI(totalHeal);
        }

    } else if (winningCard.getSuit().equals("Spades")) {
    if (playerWon) {
        int stealAmount = 1;

        if (playerBoosterArmed && playerHasBooster) {
    stealAmount += 1;
    playerBoosterUsedThisRound = true;
    addRoundLog("Your booster increased your steal amount by 1.");
}
        addRoundLog("You activated Spades and can steal " + stealAmount + " card(s).");
        for (int i = 0; i < stealAmount; i++) {
    stealChosenAICardToPlayer();
}

    } else {
        int aiStealAmount = 1;

        if (aiHasBooster) {
            aiStealAmount += 1;
            aiBooster = 0;
            aiHasBooster = false;
            aiBoosterTurnsLeft = 0;
        }
addRoundLog("AI activated Spades and steals " + aiStealAmount + " card(s).");
for (int i = 0; i < aiStealAmount; i++) {
    stealChosenPlayerCardToAI();
}
    }
} else if (winningCard.getSuit().equals("Clubs")) {
    if (playerWon) {
        playerBooster = effectValue;
        playerHasBooster = true;
        playerBoosterArmed = false;
        playerBoosterTurnsLeft = 3;
        playerBoosterUsedThisRound = false;
        addRoundLog("You gained a booster of " + effectValue + " for 3 turns.");
    } else {
        aiBooster = effectValue;
        aiHasBooster = true;
        aiBoosterTurnsLeft = 3;
        addRoundLog("AI gained a booster of " + effectValue + " for 3 turns.");
    }
}
}




private void finishPlayerBoosterUse() {
    if (playerBoosterUsedThisRound) {
        playerBooster = 0;
        playerHasBooster = false;
        playerBoosterArmed = false;
        playerBoosterTurnsLeft = 0;
        playerBoosterUsedThisRound = false;
        addRoundLog("Your booster was used and is now gone.");
    }
}










private void updateBoosterTimers() {
    if (playerHasBooster && !playerBoosterArmed) {
        playerBoosterTurnsLeft--;

        if (playerBoosterTurnsLeft <= 0) {
            playerBooster = 0;
            playerHasBooster = false;
            playerBoosterArmed = false;
            playerBoosterTurnsLeft = 0;
            addRoundLog("Your booster expired before you used it.");
        }
    }

    if (aiHasBooster) {
        aiBoosterTurnsLeft--;

        if (aiBoosterTurnsLeft <= 0) {
            aiBooster = 0;
            aiHasBooster = false;
            aiBoosterTurnsLeft = 0;
            addRoundLog("AI booster expired.");
        }
    }
}







private void healAI(int amount) {
    aiHealth = aiHealth + amount;
    updateHealthBars();
    checkWinner();
}


private int chooseAISlotFromDialog(String message) {
    java.util.ArrayList<String> validOptions = new java.util.ArrayList<>();

    for (int slot = 1; slot <= 4; slot++) {
        if (aiOneRoundHandReduction && slot == blockedAISlot) {
            continue;
        }
        validOptions.add(String.valueOf(slot));
    }

    if (validOptions.isEmpty()) {
        return -1;
    }

    String choice = (String) javax.swing.JOptionPane.showInputDialog(
            this,
            message,
            "Choose AI Card",
            javax.swing.JOptionPane.PLAIN_MESSAGE,
            null,
            validOptions.toArray(),
            validOptions.get(0)
    );

    if (choice == null) {
        return -1;
    }

    return Integer.parseInt(choice);
}




private int choosePlayerSlotFromDialog(String message) {
    java.util.ArrayList<String> validOptions = new java.util.ArrayList<>();

    for (int slot = 1; slot <= 4; slot++) {
        if (playerOneRoundHandReduction && slot == blockedPlayerSlot) {
            continue;
        }
        validOptions.add(String.valueOf(slot));
    }

    if (validOptions.isEmpty()) {
        return -1;
    }

    String choice = (String) javax.swing.JOptionPane.showInputDialog(
            this,
            message,
            "Choose Your Card Slot",
            javax.swing.JOptionPane.PLAIN_MESSAGE,
            null,
            validOptions.toArray(),
            validOptions.get(0)
    );

    if (choice == null) {
        return -1;
    }

    return Integer.parseInt(choice);
}










private int choosePlayerSlotForAI() {
    return getRandomPlayerSlot();
}




private void revealChosenAICard() {
    int slot = chooseAISlotFromDialog("Choose which AI card to reveal:");

    if (slot == -1) {
        return;
    }

    Card revealed = getAICardBySlot(slot);

    if (revealed != null) {
        addRoundLog("Revealed AI card " + slot + ": " + cardToText(revealed));
    }
}



private void revealChosenPlayerCardByAI() {
    int slot = choosePlayerSlotForAI();
    Card revealed = getPlayerCardBySlot(slot);

    if (revealed != null) {
        addRoundLog("AI revealed your card " + slot + ": " + cardToText(revealed));
    }
}





private void stealChosenAICardToPlayer() {
    int aiSlot = chooseAISlotFromDialog("Choose which AI card to steal:");

    if (aiSlot == -1) {
        addRoundLog("You did not choose an AI card to steal.");
        return;
    }

    if (aiSlot == currentRoundAISlot) {
        addRoundLog("You cannot steal the AI card that is already being played this round.");
        return;
    }

    Card stolenCard = getAICardBySlot(aiSlot);

    if (stolenCard == null) {
        addRoundLog("That AI slot has no card to steal.");
        return;
    }

    int playerSlot = choosePlayerSlotFromDialog("Choose which of your card slots to replace:");

    if (playerSlot == -1) {
        addRoundLog("You did not choose one of your slots to replace.");
        return;
    }

    setPlayerCardBySlot(playerSlot, stolenCard);
    updatePlayerButtonIcon(playerSlot);

    Card replacementCard = drawSharedDeckCard();
    if (replacementCard == null) {
        setAICardBySlot(aiSlot, null);
        if (aiSlot == 1) {
            jButton5.setIcon(null);
            jButton5.setText("EMPTY");
            jButton5.setEnabled(false);
        } else if (aiSlot == 2) {
            jButton6.setIcon(null);
            jButton6.setText("EMPTY");
            jButton6.setEnabled(false);
        } else if (aiSlot == 3) {
            jButton7.setIcon(null);
            jButton7.setText("EMPTY");
            jButton7.setEnabled(false);
        } else if (aiSlot == 4) {
            jButton8.setIcon(null);
            jButton8.setText("EMPTY");
            jButton8.setEnabled(false);
        }
        addRoundLog("No replacement card was available for AI slot " + aiSlot + ".");
    } else {
        setAICardBySlot(aiSlot, replacementCard);
        updateAIButtonBack(aiSlot);
    }

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);

    addRoundLog("You stole AI card " + aiSlot + " (" + cardToText(stolenCard)
            + ") and placed it into your slot " + playerSlot + ".");
}








private void stealChosenPlayerCardToAI() {
    int playerSlot = choosePlayerSlotForAI();

    if (playerSlot == currentRoundPlayerSlot && !currentRoundPlayerUsedBonus) {
        for (int slot = 1; slot <= 4; slot++) {
            if (slot != currentRoundPlayerSlot &&
                !(playerOneRoundHandReduction && slot == blockedPlayerSlot)) {
                playerSlot = slot;
                break;
            }
        }
    }

    Card stolenCard = getPlayerCardBySlot(playerSlot);

    if (stolenCard == null) {
        return;
    }

    int aiSlot = getRandomAISlot();

    if (aiSlot == currentRoundAISlot) {
        for (int slot = 1; slot <= 4; slot++) {
            if (slot != currentRoundAISlot &&
                !(aiOneRoundHandReduction && slot == blockedAISlot)) {
                aiSlot = slot;
                break;
            }
        }
    }

    setAICardBySlot(aiSlot, stolenCard);
    updateAIButtonBack(aiSlot);

    Card replacementCard = drawSharedDeckCard();
    if (replacementCard == null) {
        setPlayerCardBySlot(playerSlot, null);
        if (playerSlot == 1) {
            jButton1.setIcon(null);
            jButton1.setText("EMPTY");
            jButton1.setEnabled(false);
        } else if (playerSlot == 2) {
            jButton2.setIcon(null);
            jButton2.setText("EMPTY");
            jButton2.setEnabled(false);
        } else if (playerSlot == 3) {
            jButton3.setIcon(null);
            jButton3.setText("EMPTY");
            jButton3.setEnabled(false);
        } else if (playerSlot == 4) {
            jButton4.setIcon(null);
            jButton4.setText("EMPTY");
            jButton4.setEnabled(false);
        }
        addRoundLog("No replacement card was available for player slot " + playerSlot + ".");
    } else {
        setPlayerCardBySlot(playerSlot, replacementCard);
        updatePlayerButtonIcon(playerSlot);
    }

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);

    addRoundLog("AI stole your card from slot " + playerSlot + ".");
}








private void clearRoundLog() {
    currentRoundLog = "";
}



private void addRoundLog(String text) {
    if (currentRoundLog.isEmpty()) {
        currentRoundLog = text;
    } else {
        currentRoundLog = currentRoundLog + "\n" + text;
    }
}




private void showRoundLog() {
    if (presentationMode) {
        System.out.println(currentRoundLog);
        currentRoundLog = "";
        return;
    }

    if (!currentRoundLog.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, currentRoundLog);
        currentRoundLog = "";
    }
}

private String cardToText(Card card) {
    if (card == null) {
        return "No card";
    }
    return card.getValue() + " of " + card.getSuit();
}






private void playSelectedCard() {
    if (selectedCardButton == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Select one of your cards first.");
        return;
    }

    if (warActive) {
        javax.swing.JOptionPane.showMessageDialog(this, "Finish the war first.");
        return;
    }
    
    clearRoundLog();
    playerBoosterUsedThisRound = false;

    Card selectedCard = getSelectedPlayerCard();
    currentRoundPlayerSlot = getSelectedPlayerSlot();
    currentRoundPlayerUsedBonus = (selectedCardButton == 13);

    if (selectedCard == null) {
        addRoundLog("No player card was selected.");
        showRoundLog();
        return;
    }

    int aiSlot = chooseAISmartSlot(selectedCard);
    currentRoundAISlot = aiSlot;

    Card aiCard = getAICardBySlot(aiSlot);

    if (aiCard == null) {
        addRoundLog("AI could not play a card.");
        showRoundLog();
        return;
    }

    addRoundLog("You played: " + cardToText(selectedCard));
    addRoundLog("AI played: " + cardToText(aiCard));

    showSelectedCardInMiddle();
    showRealAICardInMiddle(aiCard);

    int playerValue = selectedCard.getValue();
    int aiValue = aiCard.getValue();

    if (playerNextCardMinusOne) {
        playerValue = playerValue - 1;
        playerNextCardMinusOne = false;
        addRoundLog("Your card got -1 this round.");
    }

    if (aiNextCardMinusOne) {
        aiValue = aiValue - 1;
        aiNextCardMinusOne = false;
        addRoundLog("AI card got -1 this round.");
    }

    addRoundLog("Final values -> You: " + playerValue + " | AI: " + aiValue);

    if (playerValue > aiValue) {
        addRoundLog("You won the round.");
        applyWinningSuitEffect(selectedCard, true);
        applyLosingCardEffect(aiCard, false);
    } else if (aiValue > playerValue) {
        addRoundLog("AI won the round.");
        applyWinningSuitEffect(aiCard, false);
        applyLosingCardEffect(selectedCard, true);
    } else {
    addRoundLog("Tie round.");
    startWar(selectedCard, aiCard, aiSlot);
    return;
    }

removeUsedPlayerCard();
replaceUsedPlayerCard();
replaceUsedAICard(aiSlot);

finishPlayerBoosterUse();
updateBoosterTimers();
restoreBlockedSlots();

    updateTitleBar();

    showRoundLog();

    selectedCardButton = -1;
    currentRoundAISlot = -1;
    currentRoundPlayerSlot = -1;
    currentRoundPlayerUsedBonus = false;

    checkWinner();
}




private int chooseAISmartSlot(Card playerCard) {
    int bestSlot = -1;

    if (GameSettings.difficulty.equals("Easy")) {
        return getRandomAISlot();
    }

    if (GameSettings.difficulty.equals("Medium")) {
        if (Math.random() < 0.5) {
            return getRandomAISlot();
        }
    }

    int playerValue = playerCard.getValue();

    for (int slot = 1; slot <= 4; slot++) {
        if (aiOneRoundHandReduction && slot == blockedAISlot) {
            continue;
        }

        Card aiCard = getAICardBySlot(slot);
        if (aiCard == null) {
            continue;
        }

        if (aiCard.getValue() >= playerValue) {
            if (bestSlot == -1 || aiCard.getValue() < getAICardBySlot(bestSlot).getValue()) {
                bestSlot = slot;
            }
        }
    }

    if (bestSlot != -1) {
        return bestSlot;
    }

    for (int slot = 1; slot <= 4; slot++) {
        if (aiOneRoundHandReduction && slot == blockedAISlot) {
            continue;
        }

        Card aiCard = getAICardBySlot(slot);
        if (aiCard == null) {
            continue;
        }

        if (bestSlot == -1 || aiCard.getValue() > getAICardBySlot(bestSlot).getValue()) {
            bestSlot = slot;
        }
    }

    return bestSlot;
}







    

    private void replaceUsedAICard(int aiSlot) {
    if (sharedDeckCount <= 0) {
        return;
}

    Card newCard = drawSharedDeckCard();
    if (newCard == null) {
        return;
}

    if (aiSlot == 1) {
        aiCard1 = newCard;
    } else if (aiSlot == 2) {
        aiCard2 = newCard;
    } else if (aiSlot == 3) {
        aiCard3 = newCard;
    } else if (aiSlot == 4) {
        aiCard4 = newCard;
    }

    updateAIButtonBack(aiSlot);

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);
}







    private Card createRandomCard() {
    int suitNumber = (int)(Math.random() * 4) + 1;
    int value = (int)(Math.random() * 13) + 2;

    String suit = "";
    String imagePath = "";

    if (suitNumber == 1) {
        suit = "Clubs";
    } else if (suitNumber == 2) {
        suit = "Diamonds";
    } else if (suitNumber == 3) {
        suit = "Hearts";
    } else if (suitNumber == 4) {
        suit = "Spades";
    }

    String valueName = "";

    if (value >= 2 && value <= 10) {
        valueName = "" + value;
    } else if (value == 11) {
        valueName = "Jack";
    } else if (value == 12) {
        valueName = "Queen";
    } else if (value == 13) {
        valueName = "King";
    } else if (value == 14) {
        valueName = "Ace";
    }

    imagePath = "/images/" + valueName + suit + ".png";

    return new Card(suit, value, imagePath);
}








    private void removeUsedPlayerCard() {
    
    if (selectedCardButton == 13) {
    playerBonusCard = null;
    playerHasBonusCard = false;
    jButton13.setIcon(null);
    jButton13.setText("");
    jButton13.setVisible(false);
    return;
}
    if (selectedCardButton == 1) {
        jButton1.setIcon(null);
        jButton1.setText("USED");
        jButton1.setEnabled(false);
    } else if (selectedCardButton == 2) {
        jButton2.setIcon(null);
        jButton2.setText("USED");
        jButton2.setEnabled(false);
    } else if (selectedCardButton == 3) {
        jButton3.setIcon(null);
        jButton3.setText("USED");
        jButton3.setEnabled(false);
    } else if (selectedCardButton == 4) {
        jButton4.setIcon(null);
        jButton4.setText("USED");
        jButton4.setEnabled(false);
    }
}

    private void replaceUsedPlayerCard() {
    if (selectedCardButton == 13) {
    return;
}
    if (sharedDeckCount <= 0) {
    return;
}

    Card newCard = drawSharedDeckCard();
    if (newCard == null) {
        return;
}

    if (selectedCardButton == 1) {
        playerCard1 = newCard;
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard1.getImagePath())));
        jButton1.setText("");
        jButton1.setEnabled(true);

    } else if (selectedCardButton == 2) {
        playerCard2 = newCard;
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard2.getImagePath())));
        jButton2.setText("");
        jButton2.setEnabled(true);

    } else if (selectedCardButton == 3) {
        playerCard3 = newCard;
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard3.getImagePath())));
        jButton3.setText("");
        jButton3.setEnabled(true);

    } else if (selectedCardButton == 4) {
        playerCard4 = newCard;
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource(playerCard4.getImagePath())));
        jButton4.setText("");
        jButton4.setEnabled(true);
    }

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);
}
    
    private void showSelectedCardInMiddle() {
    Card selectedCard = getSelectedPlayerCard();

    if (selectedCard != null) {
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource(selectedCard.getImagePath())));
        jButton11.setText("");
    }
}
    
    
    
    private int getCardEffectValue(Card card) {
    if (card == null) {
        return 0;
    }

    return card.getValue();
}
    
    
    
    
    
    private Card getPlayerCardBySlot(int slot) {
    if (slot == 1) {
        return playerCard1;
    } else if (slot == 2) {
        return playerCard2;
    } else if (slot == 3) {
        return playerCard3;
    } else if (slot == 4) {
        return playerCard4;
    }
    return null;
}

private void setAICardBySlot(int slot, Card card) {
    if (slot == 1) {
        aiCard1 = card;
    } else if (slot == 2) {
        aiCard2 = card;
    } else if (slot == 3) {
        aiCard3 = card;
    } else if (slot == 4) {
        aiCard4 = card;
    }
}
    
    
private void stealRandomAICardToPlayer() {
    int aiSlot = getRandomAISlot();
    Card stolenCard = getAICardBySlot(aiSlot);

    if (stolenCard == null) {
        return;
    }

    int playerSlot = getRandomPlayerSlot();
    setPlayerCardBySlot(playerSlot, stolenCard);
    updatePlayerButtonIcon(playerSlot);

    Card replacementCard = drawSharedDeckCard();
    if (replacementCard == null) {
        setAICardBySlot(aiSlot, null);
    } else {
        setAICardBySlot(aiSlot, replacementCard);
        updateAIButtonBack(aiSlot);
    }

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);
}

private void stealRandomPlayerCardToAI() {
    int playerSlot = getRandomPlayerSlot();
    Card stolenCard = getPlayerCardBySlot(playerSlot);

    if (stolenCard == null) {
        return;
    }

    int aiSlot = getRandomAISlot();
    setAICardBySlot(aiSlot, stolenCard);
    updateAIButtonBack(aiSlot);

    Card replacementCard = drawSharedDeckCard();
    if (replacementCard == null) {
        setPlayerCardBySlot(playerSlot, null);
    } else {
        setPlayerCardBySlot(playerSlot, replacementCard);
        updatePlayerButtonIcon(playerSlot);
    }

    sharedDeckCount = sharedDeck.size();
    jButton9.setText("" + sharedDeckCount);
    jButton10.setText("" + sharedDeckCount);
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar2 = new javax.swing.JProgressBar();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2Clubs.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 600, 70, 100));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/7Diamonds.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 600, 70, 100));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/JackHearts.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 600, 70, 100));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/8Spades.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 600, 70, 100));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 70, 100));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 70, 100));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 60, 70, 100));

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 70, 100));

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 70, 100));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/b1fv.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 70, 100));

        jProgressBar1.setBackground(new java.awt.Color(255, 51, 51));
        jLayeredPane1.add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 730, 270, 30));

        jProgressBar2.setBackground(new java.awt.Color(255, 51, 51));
        jLayeredPane1.add(jProgressBar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 270, 30));

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/KingDiamonds.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 430, 70, 100));

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/QueenSpades.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 70, 100));

        jButton14.setText("jButton14");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 60, 70, 100));

        jButton13.setText("jButton13");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 600, 70, 100));

        jButton15.setText("jButton15");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 470, 180, -1));

        jButton16.setText("jButton16");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 70, 100));

        jButton17.setText("jButton17");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 70, 100));

        jButton18.setText("jButton18");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, 70, 100));

        jButton19.setText("jButton19");
        jLayeredPane1.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 70, 100));

        jButton20.setText("jButton20");
        jLayeredPane1.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 270, 70, 100));

        jButton21.setText("jButton21");
        jLayeredPane1.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 270, 70, 100));

        jButton22.setText("jButton22");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jButton22, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 390, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/MainGameBackground.png"))); // NOI18N
        jLayeredPane1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        selectPlayerCard(2);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "AI card 1");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "AI card 2");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "AI card 3");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "AI card 4");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "Player deck clicked.");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "AI deck clicked.");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        selectPlayerCard(3);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        playSelectedCard();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        //javax.swing.JOptionPane.showMessageDialog(this, "AI played card area.");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        selectPlayerCard(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        selectPlayerCard(4);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        // AI bonus card button - no click action needed right now
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        selectBonusPlayerCard();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        // TODO add your handling code here:
        resolveWarRound();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        armPlayerBooster();
    }//GEN-LAST:event_jButton15ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainGame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    // End of variables declaration//GEN-END:variables
}
