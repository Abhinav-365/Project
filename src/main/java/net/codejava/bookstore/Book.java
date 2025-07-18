package net.codejava.bookstore;

public class Book {
    private int id;
    private String author;
    private String customerName;
    private float price;
    private String bookName;

    public Book() {}

    public Book(int id, String author, String customerName, float price, String bookName) {
        this(author, customerName, price, bookName);
        this.bookName=bookName;
        this.id = id;
    }

    public Book(String author, String customerName, float price ,String bookName) {
        this.author = author;
        this.customerName = customerName;
        this.price = price;
        this.bookName=bookName;
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

    @Override
    public String toString() {
        return String.format("Book{id=%d, author='%s', customer='%s', price=%.2f,bookName='%s'}",
                id, author, customerName, price, bookName);
    }
}