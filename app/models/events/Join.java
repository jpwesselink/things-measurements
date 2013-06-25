package models.events;


public  class Join extends Event {
    
    final public String user;
    
    public Join(String user) {
        super("join");
        this.user = user;
    }
    
}