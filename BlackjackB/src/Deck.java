import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Deck(){
        this.cards = new ArrayList<Card>();
    }
    public void createDeck(){
        for(Type type: Type.values()){
            for(Value value : Value.values()){
                this.cards.add(new Card(type,value));
                this.cards.add(new Card(type,value));
                this.cards.add(new Card(type,value));
                this.cards.add(new Card(type,value));
            }
        }
    }
    public void shuffleDeck(){
        Collections.shuffle(cards);
    }
    public Card getCard(int a){
        return cards.get(a);
    }
    public void removeCard(int a){
        cards.remove(a);
    }
    public Card addCard(ArrayList<Card> hand){
        Random rnd = new Random();
        int random = rnd.nextInt(Core.bound);
        Card newCard = this.getCard(random);
        hand.add(newCard);
        this.removeCard(random);
        Core.bound--;
        return newCard;
    }
    public int calculateValue(ArrayList<Card> cards){
        int totalValue = 0;
        int aceCount = 0;
        //        int index = 0;
//        for(Card a : cards){
//            if(Value.getValue(a.getValue())==1){        //ace seven ace seven   11 7 11 7= 36
//                totalValue += 11;                       // 11 4 11 4 = 30
//                index = cards.indexOf(a);               // 11 11 11 11 = 44
//            }
//            else {
//                totalValue += Value.getValue(a.getValue());
//            }
//        }
//        if(Value.getValue(cards.get(index).getValue())==1 && totalValue>21){
//            totalValue = totalValue-10;
//        }
        for(Card x: cards){
            totalValue += Value.getValue(x.getValue());
            if(Value.getValue(x.getValue())==11)
                aceCount++;
        }
        while(true){
            if(totalValue<=21)
                break;
            else{
                if(aceCount==0)
                    break;
                if(aceCount>0){
                    aceCount--;
                    totalValue -= 10;
                }
            }
        }
        return totalValue;
    }
    public String toString(){
        return cards.toString();
    }
}
