public class Buffer {
    private String message="";
    private boolean empty=true;

    public void put(String message){
        while(!empty) {
            try{
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        this.message=message;
        empty=false;
        notifyAll();
    }

    public String take(){
        while(empty) {
            try{
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        String tmpMessage = message;
        this.message="";
        empty = true;
        notifyAll();
        return tmpMessage;
    }
}
