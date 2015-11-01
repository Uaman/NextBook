package com.nextbook.dao;

import com.nextbook.dao.base.objects.GetableById;

/**
 * Created by Eugene on 28.10.2015.
 */
public interface AttachingService {
    void attachWithUpdate(GetableById object);
    <T extends GetableById> T attachWithMerge(T object);
}
