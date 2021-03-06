package domain;

import infra.repository.BookmarkRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Lists;

/**
 * Application service used to demostrate DDD application service and Mockito usage.
 */
@Named
public class BookmarkServiceImpl implements BookmarkService {

    // private static Logger logger = LoggerFactory.getLogger(BookmarkServiceImpl.class);

    @Inject
    private BookmarkRepository bookmarkRepository;

    BookmarkServiceImpl() {
        // Empty constructor
    }

    BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Timed
    public Bookmark findOne(Long id) {
        return bookmarkRepository.findOne(id);
    }

    /**
     * Return all the bookmarks sorted by modification date / creation date DESC
     */
    @Timed
    public List<Bookmark> findAll() {
        final List<Order> orders = new ArrayList<>();
        orders.add(new Order(Direction.DESC, "modificationDate"));
        orders.add(new Order(Direction.DESC, "creationDate"));

        return Lists.newArrayList(bookmarkRepository.findAll(new Sort(orders)));
    }

    /**
     * Save a bookmark and return the persisted entity
     */
    @Timed
    public Bookmark save(Bookmark bookmark) {
        if (bookmark.getId() == null) {
            bookmark.setCreationDate(new DateTime());
        } else {
            bookmark.setModificationDate(new DateTime());
        }
        return bookmarkRepository.save(bookmark);
    }

    @Timed
    public void delete(Long id) {
        bookmarkRepository.delete(id);
    }

}
