package io.helidon.examples.book.entity;

/**
 * @author Dan
 */
public class Book{

    private int id;
    private String isbn;
    private String title;
    private String link;
    private String image;
    private String author;
    private String price;
    private String discount;
    private String publisher;
    private String pubdate;
    private String description;
    private int reviews;

    public int getReviews() {
        return this.reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public Book reviews(int reviews) {
        this.reviews = reviews;
        return this;
    }

    public int getRatings() {
        return this.ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public Book ratings(int ratings) {
        this.ratings = ratings;
        return this;
    }
    private int ratings;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Book link(String link) {
        this.link = link;
        return this;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Book image(String image) {
        this.image = image;
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Book price(String price) {
        this.price = price;
        return this;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Book discount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Book publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public String getPubdate() {
        return this.pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public Book pubdate(String pubdate) {
        this.pubdate = pubdate;
        return this;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Book isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Book description(String description) {
        this.description = description;
        return this;
    }
}
