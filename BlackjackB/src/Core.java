import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//TODO: split hit olayını çöz
//TODO: 2x bet yap

public class Core {
    static int money = 1000;
    static Deck deck1 = new Deck();
    static Random rnd = new Random();
    static ArrayList<Card> playerHand = new ArrayList<>();
    static ArrayList<Card> dealerHand = new ArrayList<>();
    static ArrayList<Card> sideDeck = new ArrayList<>();
    //static ArrayList<User> users = new ArrayList<>();
    static int betChoice = 0;
    static int bet = 0;
    static Scanner scan = new Scanner(System.in);
    static int bound = 208;

    public static void main(String[] args) {
        System.out.println("-----------------------------------");
        System.out.println("Welcome to the Blackjack Game");
        System.out.println("What would you like to do? ");
        System.out.println("1-) Login");
        System.out.println("2-) Register");
        System.out.println("3-) Continue As Guest");
        int choice = scan.nextInt();
        switch (choice){
            case 1:
            case 2:
                System.out.println("Enter username: ");
                String username = scan.nextLine();
                scan.nextLine();
                System.out.println("Enter password: ");
                String password = scan.nextLine();
                Register(username,password);
            case 3:
        }
        boolean isFinished = false;
        deck1.createDeck();
        deck1.shuffleDeck();

        while (!isFinished && money > 0) {
            System.out.println("You have " + money + " coins. How much would you like to bet?");
            System.out.println("1-) 500 coin");
            System.out.println("2-) 250 coin");
            System.out.println("3-) 100 coin");
            System.out.println("4-) All");
            betChoice = scan.nextInt();
            switch (betChoice) {
                case 1 -> {
                    if (money < 500) {
                        System.out.println("You don't have enough money to bet that much. ");
                        continue;
                    }
                    bet = 500;
                    money = money - 500;

                }
                case 2 -> {
                    if (money < 250) {
                        System.out.println("You don't have enough money to bet that much. ");
                        continue;
                    }
                    money = money - 250;
                    bet = 250;
                }
                case 3 -> {
                    if (money < 100) {
                        System.out.println("You don't have enough money to bet that much. ");
                        continue;
                    }
                    money = money - 100;
                    bet = 100;
                }
                case 4 -> {
                    money = 0;
                    bet = 1000;
                }
                default -> System.out.println("Please enter a valid number.");
            }

            playerHand.clear();
            dealerHand.clear();
            sideDeck.clear();

            fillHands(playerHand,dealerHand);
            System.out.println("Your hand: "+playerHand+" worth: "+ deck1.calculateValue(playerHand));

            if (isBlackjack(playerHand)) {
                calculateWinner(playerHand);
                continue;
            }
            System.out.println("Dealer's Hand: "+"["+dealerHand.get(0)+" [hidden]] "+dealerHand.get(1)+ " worth: " + deck1.calculateValue(dealerHand));

            if (isBlackjack(dealerHand)) {
                calculateWinner(playerHand);
                continue;
            }
            ArrayList<Card> tempHand = new ArrayList<>();
            boolean over = true;
            while(over) {
                int index = 3;
                System.out.println("1-) Hit");
                System.out.println("2-) Stand");
                //if((Value.getValue(playerHand.get(0).getValue()) == Value.getValue(playerHand.get(1).getValue())) && playerHand.size()==2 && sideDeck.isEmpty()){
                    System.out.println("3-) Split");
                //    index++;
                //}
                System.out.println("4-) Double x2");
                int choice2 = scan.nextInt();
                switch (choice2) {
                    case 1:
                        deck1.addCard(playerHand);
                        System.out.println(playerHand + " worth: " + deck1.calculateValue(playerHand));
                        if (deck1.calculateValue(playerHand) >= 21) {
                            if (sideDeck.size() == 1) {
                                deck1.addCard(sideDeck);
                                System.out.println("First hand is done. Now play with your second hand");
                                System.out.println("Your second deck: " + sideDeck);
                                if (isBlackjack(sideDeck)) {
                                    calculateWinner(sideDeck);
                                    calculateWinner(playerHand);
                                    break;
                                }
                                System.out.println("Dealer Hand: " + dealerHand.get(0) + " [hidden]" + dealerHand.get(1));
                                tempHand.addAll(playerHand);
                                playerHand.clear();
                                playerHand.addAll(sideDeck);
                                continue;
                            } else if (sideDeck.size() > 1) {
                                while (deck1.calculateValue(dealerHand) < 17) {
                                    deck1.addCard(dealerHand);
                                }
                                calculateWinner(tempHand);
                                calculateWinner(playerHand);
                                over = false;
                            } else {
                                calculateWinner(playerHand);
                                over = false;
                            }
                        } else {
                            System.out.println("Dealer Hand: " + dealerHand.get(0) + " [hidden]" + dealerHand.get(1));
                            continue;
                        }
                        break;
                    case 2:
                        if (sideDeck.size() > 1) {
                            while (deck1.calculateValue(dealerHand) < 17) {
                                deck1.addCard(dealerHand);
                            }
                            calculateWinner(tempHand);
                            calculateWinner(sideDeck);
                            break;
                        }
                        if (sideDeck.size() == 1) {
                            deck1.addCard(sideDeck);
                            tempHand.addAll(playerHand);
                            playerHand.clear();
                            playerHand.addAll(sideDeck);
                            System.out.println("Your second deck: " + playerHand);
                            if (isBlackjack(playerHand)) {
                                calculateWinner(playerHand);
                                break;
                            }
                            continue;
                        }
                        while (deck1.calculateValue(dealerHand) < 17) {
                            deck1.addCard(dealerHand);
                        }
                        calculateWinner(playerHand);
                        break;
                    case 3:
                        if (money >= bet * 2) {
                            if (Value.getValue(playerHand.get(0).getValue()) == Value.getValue(playerHand.get(1).getValue())) {
                                sideDeck.add(playerHand.get(1));
                                playerHand.remove(1);
                                deck1.addCard(playerHand);
                                System.out.println(playerHand + " worth: " + deck1.calculateValue(playerHand));
                                System.out.println("Dealer Hand: "+dealerHand+" worth: "+deck1.calculateValue(dealerHand));
                                money -= bet;
                                if (isBlackjack(playerHand)) {
                                    deck1.addCard(sideDeck);
                                    tempHand.addAll(playerHand);
                                    playerHand.clear();
                                    playerHand.addAll(sideDeck);
                                    System.out.println("First hand is over. Now you are playing with your second deck.");
                                    System.out.println("Your second deck: " + sideDeck);
                                }
                                continue;
                            }else{
                                System.out.println("You cannot split your deck. You need 2 same valued cards. ");
                                money = money+bet;
                            }
                            break;
                        } else {
                            System.out.println("You cannot split. You need at least twice as much money as you bet.");
                            money = money+bet;
                            break;
                        }
                    case 4:
                        money+=bet;
                        if (money >= bet * 2) {
                            money-=bet;
                            money = money + bet;
                            bet = bet * 2;
                            money -= bet;
                            if (sideDeck.size() > 1) {
                                while (deck1.calculateValue(dealerHand) < 17) {
                                    deck1.addCard(dealerHand);
                                }
                                calculateWinner(tempHand);
                                calculateWinner(sideDeck);
                                break;
                            }
                            if (sideDeck.size() == 1) {
                                deck1.addCard(sideDeck);
                                tempHand.addAll(playerHand);
                                playerHand.clear();
                                playerHand.addAll(sideDeck);
                                System.out.println("Your second deck: " + playerHand);
                                if (isBlackjack(playerHand)) {
                                    calculateWinner(playerHand);
                                    break;
                                }
                                continue;
                            }
                            deck1.addCard(playerHand);
                            while (deck1.calculateValue(dealerHand) < 17) {
                                deck1.addCard(dealerHand);
                            }
                            calculateWinner(playerHand);
                            break;
                        }
                        else{
                            System.out.println("You cannot bet this much. You need at least twice as much money.");
                            continue;
                        }
                }
                break;
            }
        }
    }

    public static void Register(String username, String password) {
        while (true) {
            User user1 = new User();
            for (User x : user1.users) {
                if (x.getUserName().equals(username)) {
                    System.out.println("Username already taken.");
                    break;
                }
            }
            User user = new User(username,password);
            user.getUsers().add(user);
            System.out.println("user "+user+" users "+user.getUsers());
            break;
        }
    }

    public static void fillHands(ArrayList<Card>playerHand,ArrayList<Card>dealerHand){
//        for (int i = 0; i < 4; i++) {
//            int rndIndex = rnd.nextInt(bound);
//            if (i == 0)
//                playerHand.add(deck1.getCard(rndIndex));
//            else if (i == 1)
//                playerHand.add(deck1.getCard(rndIndex));
//            else if (i == 2)
//                dealerHand.add(deck1.getCard(rndIndex));
//            else
//                dealerHand.add(deck1.getCard(rndIndex));
//
//                deck1.removeCard(rndIndex);
//                bound--;
//            }
        Card c = new Card(Type.CLUB, Value.ACE);
        Card d = new Card(Type.CLUB, Value.ACE);
        Card e = new Card(Type.CLUB, Value.FIVE);
        Card f = new Card(Type.CLUB, Value.FIVE);
        playerHand.add(c);
        playerHand.add(d);
        dealerHand.add(e);
        dealerHand.add(f);
    }

    public static boolean isBlackjack(ArrayList<Card> hand){
        boolean isBlackjack = false;
        ArrayList<Value> cardValues = new ArrayList<>();
            for (Card a : hand) {
                cardValues.add(a.getValue());
            }
            if (cardValues.contains(Value.ACE) && (cardValues.contains(Value.JACK) ||
                cardValues.contains(Value.QUEEN) || cardValues.contains(Value.KING))) {
                isBlackjack = true;
            }
            return isBlackjack;
        }

    public static void calculateWinner(ArrayList<Card> playerHand){
        int playerValue = deck1.calculateValue(playerHand);     //15
        int dealerValue = deck1.calculateValue(dealerHand);     //17
        int diffPlayer = 21-playerValue;                        //21-15=6
        int diffDealer = 21-dealerValue;                        //21-17=4

        if(isBlackjack(playerHand) && !(isBlackjack(dealerHand)) && playerHand.size()==2){
            System.out.println("Blackjack!!");
            System.out.println("You win");
            System.out.println("Your hand: "+playerHand+ "worth: "+playerValue);
            System.out.println("Dealer hand: "+dealerHand+ "worth: "+dealerValue);
            money += bet*3;
            System.out.println("You now have: "+money+" coins!");
            System.out.println("------------------------------------------------");
            return;
        }
        if(isBlackjack(dealerHand) && !(isBlackjack(playerHand)) && dealerHand.size()==2){
            System.out.println("Dealer's Blackjack");
            System.out.println("Dealer Wins!");
            System.out.println("Your hand: "+playerHand+ "worth: "+playerValue);
            System.out.println("Dealer hand: "+dealerHand+ "worth: "+dealerValue);
            System.out.println("------------------------------------------------");
            return;
        }
        if(playerValue == dealerValue) {
            System.out.println("Tie");
            System.out.println("Your hand: "+playerHand+" worth: "+playerValue);
            System.out.println("Dealer had: "+dealerHand+" worth: "+dealerValue);
            money += bet;
            System.out.println("You still have "+money+" coins.");
            System.out.println("------------------------------------------------");

        }
        else if(playerValue > 21) {
            System.out.println(playerHand);
            System.out.println("Your hand worth: " + playerValue);
            System.out.println("You lost because your hand went above 21 limit. ");
            System.out.println("Dealer had: "+dealerHand+" worth: "+dealerValue);
            if(money==0){
                System.out.println("You lost all your money. SadKek");
            }
            else{
                System.out.println("You have "+money+" coins left.");
            }
            System.out.println("------------------------------------------------");
        }
        else if(dealerValue > 21 && (diffPlayer > diffDealer)){
            System.out.println("Dealer had: "+dealerHand+" worth: "+dealerValue);
            System.out.println("You had: "+playerHand+" worth: "+playerValue);
            System.out.println("You win");
            money += bet*2;
            System.out.println("You now have "+money+" coins.");
            System.out.println("------------------------------------------------");
        }
        else if((dealerValue > playerValue) && dealerValue <= 21){
            System.out.println("You had: "+playerHand+" worth: "+playerValue);
            System.out.println("Dealer had: "+dealerHand+" worth: "+dealerValue);
            System.out.println("Dealer Wins!");
            if(money==0){
                System.out.println("You lost all your money. SadKek");
            }
            else{
                System.out.println("You have "+money+" coins left.");
            }
            System.out.println("------------------------------------------------");
        }
        else if(playerValue > dealerValue && (diffDealer>diffPlayer)){
            System.out.println("You Win!!");
            System.out.println("Dealer had: "+dealerHand+" worth: "+dealerValue);
            System.out.println("Your hand: "+playerHand+" worth: "+playerValue);
            money += bet*2;
            System.out.println("sssYou now have "+money+" coins!");
            System.out.println("------------------------------------------------");

        }
        else {
            System.out.println("Eklemediğin condition");
            System.out.println("player "+playerHand);
            System.out.println("Dealer "+dealerHand);
        }
    }
}
