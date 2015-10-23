package com.nextbook.services.impl;

import com.nextbook.domain.entities.BookEntity;
import com.nextbook.domain.pojo.Book;
import com.nextbook.domain.upload.Constants;
import com.nextbook.services.IBookProvider;
import com.nextbook.services.ISearchService;
import com.nextbook.utils.DozerMapperFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by KutsykV on 23.10.2015.
 */
@Service
public class SearchService implements ISearchService {

    private HttpSolrServer server = new HttpSolrServer(Constants.SOLR_SERVER);
    @Autowired
    private IBookProvider bookProvider;

    @Override
    public Set<BookEntity> find(String query, int per_page, int page) {
        Set<BookEntity> result = new HashSet<BookEntity>();
        findInContent(query, result, per_page, page);
        return result;
    }

    private void findInContent(String query, Set<BookEntity> result, int per_page, int page) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("content:" + query);
        solrQuery.setStart((page - 1) * 10);
        QueryResponse response = null;
        try {
            response = server.query(solrQuery);
            SolrDocumentList results = response.getResults();
            SolrDocument doc = null;
            ListIterator<SolrDocument> iter = results.listIterator();
            while (iter.hasNext()) {
                if(result.size()>per_page)
                    break;
                doc = iter.next();
                String id = doc.getFieldValue("book_id").toString();
                Book book = bookProvider.getBookById(Integer.parseInt(id));
                result.add(DozerMapperFactory.getDozerBeanMapper().map(book, BookEntity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
