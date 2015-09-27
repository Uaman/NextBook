if(!NextBook)
    throw new Error('Please import global NextBook file');


var startSessionTime = new Date().getTime();
NextBook.Statistic = function(){};

/**
 * @desc method that init KeenIO client
 * @param string keenIoProjectId - required
 * @param string keenIoWriteKey - required if you want to send some data to cloud
 * @param keenIoReadKey - required if you want to get some data from cloud
 */
NextBook.Statistic.init = function(keenIoProjectId, keenIoWriteKey, keenIoReadKey){
    if(!keenIoProjectId) throw new Error('project id is required');
    if(!keenIoReadKey && !keenIoWriteKey) throw new Error('write key or read key is required');

    var properties = {};
    properties[_global.project_id] = keenIoProjectId;

    if(keenIoReadKey)
        properties[_global.read_key] = keenIoReadKey;
    if(keenIoWriteKey)
        properties[_global.write_key] = keenIoWriteKey;

    CommonWeb.Keen.Client = new Keen(properties);
    CommonWeb.Callback = CommonWeb.Keen.Callback;
};

/**
 * @desc add global properties to all data that will be sent. each page require at least property called 'page'
 * @param object properties - properties to be added
 */
NextBook.Statistic.addGlobalProperty = function(properties){
    CommonWeb.addGlobalProperties(properties);
};


var sent = false;
/**
 * @desc track session time on current page
 */
NextBook.Statistic.sessionTime = function(){
    if(!sent) {
        if (!_isPageDefined())
            throw new Error('Please specify page name for this page');
        var endSessionTime = new Date().getTime();
        CommonWeb.Keen.Client.addEvent("sessionTime", $.extend({time: (endSessionTime - startSessionTime)}, CommonWeb.options.globalProperties), function () {
        });
        sent = true;
    }
};

NextBook.Statistic.click = function(elements, properties){
    if(!_isPageDefined())
        throw new Error('Please specify page name for this page');
    CommonWeb.trackClicks(elements, properties);
};

NextBook.Statistic.pageViews = function(){
    if(!_isPageDefined())
        throw new Error('Please specify page name for this page');
    CommonWeb.trackPageview();
};

_isPageDefined = function(){
    return _global.properties.page_name in CommonWeb.options.globalProperties;
};

_global = {
    project_id: 'projectId',
    write_key: 'writeKey',
    read_key: 'readKey',
    properties: {
        page_name: 'page'
    }
};