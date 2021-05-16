
import javafx.beans.property.SimpleStringProperty;

public class Infoclient {
    private  SimpleStringProperty shop;
    private  SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty date;


    public Infoclient(String id, String name, String  phone, String date, String shop) {
        this.shop = new SimpleStringProperty(shop);
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone= new SimpleStringProperty(phone);
        this.date = new SimpleStringProperty(date);


    }
    public Infoclient(){

    }
    public SimpleStringProperty getShop(){return shop;}
    public void setShop(String shop){this.shop=new SimpleStringProperty(shop);}

    public SimpleStringProperty getid(){return id;}
    public void setid(String id){this.id=new SimpleStringProperty(id);}

    public SimpleStringProperty getname(){return name;}
    public void setName(String name){this.name=new SimpleStringProperty(name);}

    public SimpleStringProperty getPhone(){return phone;}
    public void setPhone(String phone){this.phone=new SimpleStringProperty(phone);}

    public SimpleStringProperty getDate(){return date;}
    public void setDate(String date){this.date=new SimpleStringProperty(date);}


}