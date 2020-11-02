
public class Shop {
    private CountingSemaphore numberOfCarts;

    public Shop(int numberOfCarts){
        this.numberOfCarts= new CountingSemaphore(numberOfCarts);
    }

    public void takeCart(){
        numberOfCarts.P();
    }

    public void returnCart() {
        numberOfCarts.V();
    }
}
