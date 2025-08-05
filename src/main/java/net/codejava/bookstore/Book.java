package net.codejava.bookstore;

public class Book {
    private int id;
    private String author;
    private String customerName;
    private float price;
    private String bookName;
    private String insertedDate;
    private String updatedDate;

    public Book() {}

    public Book(int id, String author, String customerName, float price, String bookName, String insertedDate ,String updatedDate){
        this.id = id;
        this.author = author;
        this.customerName = customerName;
        this.price = price;
        this.bookName = bookName;
        this.insertedDate = insertedDate;
        this.updatedDate=updatedDate;
    }

    public Book(String author, String customerName, float price ,String bookName,String insertedDate,String updatedDate){
        this.author = author;
        this.customerName = customerName;
        this.price = price;
        this.bookName=bookName;
        this.insertedDate=insertedDate;
        this.updatedDate=updatedDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    
    public String getBookName() {return bookName;}
    public void setBookName(String bookName) {this.bookName=bookName;}
    
    public String getIssuedDate() {return insertedDate;}
    public void setIssuedDate(String issuedDate) {this.insertedDate=issuedDate;}
    
    public String getUpdatedDate() {return updatedDate;}
    public void setupdatedDate(String updatedDate) {this.updatedDate=updatedDate;}
    

    @Override
    public String toString() {
        return String.format("Book{id=%d, author='%s', customer='%s', price=%.2f,bookName='%s',insertedDate='%s',updatedDate='%s'}",
                id, author, customerName, price, bookName,insertedDate,updatedDate);
    }
}