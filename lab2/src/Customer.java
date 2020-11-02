import java.util.Random;

public class Customer extends Thread{
    private int number;
    public Shop shop;

    public Customer(int number, Shop shop){
        this.number = number;
        this.shop = shop;
    }

    @Override
    public void run() {
        Random rand = new Random();
        shop.takeCart();
        System.out.println("Customer "+number+": I start my shopping");
        try {
            sleep(rand.nextInt(5000));
        } catch (InterruptedException e) { e.printStackTrace();}
        System.out.println("Customer "+number+": I finish my shopping");
        shop.returnCart();
    }
}
