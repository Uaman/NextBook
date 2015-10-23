package com.nextbook.services;

import com.nextbook.domain.entities.BookEntity;

import java.util.Set;

/**
 * Created by KutsykV on 23.10.2015.
 */
public interface ISearchService {
    Set<BookEntity> find(String query, int per_page, int page);
}
