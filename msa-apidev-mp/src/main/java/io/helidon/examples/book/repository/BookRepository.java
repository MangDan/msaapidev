package io.helidon.examples.book.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import io.helidon.examples.book.entity.Book;

@ApplicationScoped
public class BookRepository {

    private final DataSource dataSource;

    /**
     * Creates a new {@link TablesResource}.
     *
     * @param dataSource the {@link DataSource} to use to acquire database table
     *                   names; must not be {@code null}
     *
     * @exception NullPointerException if {@code dataSource} is {@code
     * null}
     */
    @Inject
    public BookRepository(@Named("oracle") final DataSource dataSource) {
        super();
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    public List<Book> listAllBook(String title, Integer offset, Integer limit) {
        List<Book> bookArray = new ArrayList<Book>();
        String clause = "";
        if(!title.equals(""))
        clause = "WHERE TITLE LIKE '%' || ? || '%'";
        
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("SELECT * FROM (");
        queryBuffer.append("SELECT p.*, ROW_NUMBER() OVER(ORDER BY id) AS RNUM FROM BOOK p ");
        queryBuffer.append(clause);
        queryBuffer.append(") WHERE RNUM BETWEEN "+((offset * limit)+1)+" AND "+((offset * limit)+limit));

        
        System.err.println(queryBuffer.toString());
        try {
            Connection conn = this.dataSource.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(queryBuffer.toString());

            if(!title.equals(""))
                ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setIsbn(Optional.ofNullable(rs.getString("isbn")).orElse(""));
                book.setTitle(Optional.ofNullable(rs.getString("title")).orElse(""));
                book.setLink(Optional.ofNullable(rs.getString("link")).orElse(""));
                book.setImage(Optional.ofNullable(rs.getString("image")).orElse(""));
                book.setAuthor(Optional.ofNullable(rs.getString("author")).orElse(""));
                book.setPrice(Optional.ofNullable(rs.getString("price")).orElse(""));
                book.setDiscount(Optional.ofNullable(rs.getString("discount")).orElse(""));
                book.setPublisher(Optional.ofNullable(rs.getString("publisher")).orElse(""));
                book.setPubdate(Optional.ofNullable(rs.getString("pubdate")).orElse(""));
                book.setDescription(Optional.ofNullable(rs.getString("description")).orElse(""));
                book.setRatings(Optional.ofNullable(rs.getInt("ratings")).orElse(0));
                book.setReviews(Optional.ofNullable(rs.getInt("reviews")).orElse(0));
                
                bookArray.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookArray;
    }

    public Book findBookByid(int id) {
        Book book = new Book();

        try {
            Connection conn = this.dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM BOOK WHERE ID = ?");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                book.setId(rs.getInt("id"));
                book.setIsbn(Optional.ofNullable(rs.getString("isbn")).orElse(""));
                book.setTitle(Optional.ofNullable(rs.getString("title")).orElse(""));
                book.setLink(Optional.ofNullable(rs.getString("link")).orElse(""));
                book.setImage(Optional.ofNullable(rs.getString("image")).orElse(""));
                book.setAuthor(Optional.ofNullable(rs.getString("author")).orElse(""));
                book.setPrice(Optional.ofNullable(rs.getString("price")).orElse(""));
                book.setDiscount(Optional.ofNullable(rs.getString("discount")).orElse(""));
                book.setPublisher(Optional.ofNullable(rs.getString("publisher")).orElse(""));
                book.setPubdate(Optional.ofNullable(rs.getString("pubdate")).orElse(""));
                book.setDescription(Optional.ofNullable(rs.getString("description")).orElse(""));
                book.setRatings(Optional.ofNullable(rs.getInt("ratings")).orElse(0));
                book.setReviews(Optional.ofNullable(rs.getInt("reviews")).orElse(0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }
}