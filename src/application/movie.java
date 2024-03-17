package application;


public class movie {
    public  String name;
    public  String genre;
    public  String duration;
    public  String date;
    public float price;

    /**
     * 
     */
    public movie(String name, String genre, String duration, String date, float price){
        this.name=name;
        this.genre=genre;
        this.duration=duration;
        this.date=date;
        this.price=price;
    }
    
    public String get_name(){
        return name;
    }
    public String get_genre(){
        return genre;
    }
    public String get_duration(){
        return duration;
    }
    public String get_date(){
        return date;
    }
    public float get_price(){
        return price;
    }
}
