package io.helidon.examples.book.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import io.helidon.examples.book.entity.Book;

@ApplicationScoped
public class BookRepository {

    private final DataSource dataSource;
    private final int defaultOffset = 0;
    private final int defaultLimit = 20;

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
        queryBuffer.append("SELECT p.*, ROW_NUMBER() OVER(ORDER BY id DESC) AS RNUM FROM BOOK p ");
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
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setLink(rs.getString("link"));
                book.setImage(rs.getString("image"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getString("price"));
                book.setDiscount(rs.getString("discount"));
                book.setPublisher(rs.getString("publisher"));
                book.setPubdate(rs.getString("pubdate"));
                book.setDescription(rs.getString("description"));
                book.setRatings(rs.getInt("ratings"));
                book.setReviews(rs.getInt("reviews"));
                
                bookArray.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookArray;
    }

    public Book findBookByid(String id) {
        Book book = new Book();

        try {
            Connection conn = this.dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM BOOK WHERE ID = ?");

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                book.setId(rs.getInt("id"));
                book.setIsbn(rs.getString("isbn"));
                book.setTitle(rs.getString("title"));
                book.setLink(rs.getString("link"));
                book.setImage(rs.getString("image"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getString("price"));
                book.setDiscount(rs.getString("discount"));
                book.setPublisher(rs.getString("publisher"));
                book.setPubdate(rs.getString("pubdate"));
                book.setDescription(rs.getString("description"));
                book.setRatings(rs.getInt("ratings"));
                book.setReviews(rs.getInt("reviews"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }
}