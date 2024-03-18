package application;


public class movie {
    public static String ddate="";
    public static int hall_n=0;
    public static String m_name="";
    public static String m_genre="";



    public  String name;
    public  String genre;
    public  String duration;
    public  String date;
    public float price;
    public int seats;
    public int hall_no;
    public String image;

    /**
     * 
     */
    public movie(String name, String genre, String duration, String date, float price, int seats, int hall_no, String img){
        this.name=name;
        this.genre=genre;
        this.duration=duration;
        this.date=date;
        this.price=price;
        this.seats=seats;
        this.hall_no=hall_no;
        this.image=img;
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
    public int get_seats(){
        return seats;
    }
    public int get_hall_no(){
        return hall_no;
    }

    public String get_image(){
        return image;
    }
}
