import javafx.beans.property.SimpleStringProperty;

public class Allzakaz {
    private SimpleStringProperty shop;
    private  SimpleStringProperty ssilka;
    private  SimpleStringProperty id;
    private  SimpleStringProperty price;



    public Allzakaz(String ssilka, String shop, String id, String price) {
        this.shop = new SimpleStringProperty(shop);
        this.ssilka = new SimpleStringProperty(ssilka);
        this.id = new SimpleStringProperty(id);
        this.price = new SimpleStringProperty(price);



    }
    public Allzakaz(){

    }
    public SimpleStringProperty getShopp(){return shop;}
    public void setShopp(String shop){this.shop=new SimpleStringProperty(shop);}

    public SimpleStringProperty getSsilka(){return ssilka;}
    public void setSsilka(String id){this.ssilka=new SimpleStringProperty(id);}

    public SimpleStringProperty getId(){return id;}
    public void setId(String shop){this.id=new SimpleStringProperty(shop);}

    public SimpleStringProperty getPrice(){return price;}
    public void setPrice(String shop){this.price=new SimpleStringProperty(shop);}



}
