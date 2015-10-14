<%--
  Created by IntelliJ IDEA.
  User: Stacy
  Date: 9/28/15
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="google" content="notranslate">
    <title>${bookName}</title>


    <link rel="stylesheet" href="/resources/css/viewer.css"/>
    <script src="/resources/js/pdf.js/compatibility.js"></script>
    <link rel="resource" type="application/l10n" href="/resources/locale/locale.properties"/>
    <script src="/resources/js/pdf.js/l10n.js"></script>
    <script>
        PDFJS.imageResourcesPath = '<c:url value="../../../resources/css/images" />';
        PDFJS.workerSrc = '<c:url value="/resources/js/pdf.js/pdf.worker.js" />';
        PDFJS.cMapUrl = '<c:url value="../../../resources/cmaps" />';
        PDFJS.cMapPacked = true;
        var url = '${urlToFile}';
        var PASSWORD = '${pass}';
    </script>
    <script src="<c:url value='/resources/js/pdf.js/pdf.js' />"></script>
    <script src="<c:url value='/resources/js/pdf.js/debugger.js' />"></script>
    <script src="<c:url value='/resources/js/pdf.js/viewer.js' />"></script>
</head>
  <script src="/resources/js/jquery-2.1.3.min.js"></script>
<script src="/resources/js/jquery.form.min.js"></script>
  <script src="/resources/js/main/sign.in.js"></script>
<script type="text/javascript" src="/resources/js/galleria/galleria-1.4.2.min.js"></script>
<jsp:include page="/resources/js/main/bookFavoriteButton.js.jsp"/>

<script>
    $(document).ready(function(){
        $(function() {
            $('a[href*=#]:not([href=#])').click(function() {
                if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
                    var target = $(this.hash);
                    target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
                    if (target.length) {
                        $('html,body').animate({
                            scrollTop: target.offset().top
                        }, 1000);
                        return false;
                    }
                }
            });
        });

        formImages(${numberOfPhotos});
        Galleria.loadTheme('/resources/js/galleria/theme/galleria.classic.min.js');
        Galleria.run('.galleria');

        $('#add-comment').click(function(){
            $.ajax({
                url: '/bookInfo/addComment',
                data: JSON.stringify({text: $('#comment-text').val(), bookId: ${book.id}}),
                type: 'POST',
                dataType: 'json',
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                success: function(response){
                    if(response == -1){
                        $('#sign-in-form').show();
                    } else if (response == 0){
                        $('#comment-fail').show();
                    } else {
                        $('#comments').append(formNotActiveComment(response, $('#comment-text').val()));
                        $('#comment-success').show();
                    }
                    $('.shadow').show();
                },
                error: function(e){
                    console.log(e);
                }
            }).done(function(){
                $('#comment-text').val('');
            });
        })
        $('body').on('click', '.delete-comment', function(){
            var commentId = $(this).val();
            $.ajax({
                url: '/bookInfo/deleteComment/'+commentId,
                type: 'POST',
                success: function(response){
                    if(response){
                        $('#comment-'+commentId).remove();
                    }
                }
            })
        });
    });


    function formImages(numberOfPhotos){
        if(numberOfPhotos < 1){
            $('#container_galleria').hide();
        } else {
            $('#container_galleria').show();
            $('.galleria').html('');
            var html = '';
            var htmlBig = '<ul>';
            for (var i = 0; i < numberOfPhotos; ++i) {
                htmlBig += '<li><img src="/book/getGalleryPhoto/${book.id}/' + i + '"/></li>';
            }
            htmlBig += '</ul>';
            $('.galleria').html(htmlBig);
        }
    }

    function formNotActiveComment(commentId, commentText){
    var newCommentBlock = '<div class="not-active-comment" id="comment-'+commentId+'">'+
        'comment: '+commentText+'<br />'+
        '<button class="delete-comment" value="'+commentId+'">Delete Comment</button></div>';
        return newCommentBlock;
    }

</script>
    <style>
        .comment{
            width: 100%;
            min-height: 50px;
            border: 1px solid #000000;
        }
        .not-active-comment{
            width: 100%;
            min-height: 50px;
            border: 1px solid #000000;
            background-color: #ccc;
            opacity: 0.4;
        }
    </style>
  <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=latin-ext,cyrillic-ext' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
  <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>

<script type="text/javascript" src="//vk.com/js/api/openapi.js?117"></script>
<script type="text/javascript" src="http://vk.com/js/api/share.js?92" charset="windows-1251"></script>
<script type="text/javascript">
    VK.init({apiId: 5098749, onlyWidgets: true});
</script>
<script src="https://apis.google.com/js/platform.js" async defer></script>

<body tabindex="1" class="loadingInProgress">
<div id="fb-root"></div>
<script>
    (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5&appId=407822279407021";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
  <div class="wrapper">
    <div class="page">
      <jsp:include page="../../template/default/headerContent.jsp"/>
    </div>
  </div>
<div id="comment-success" class="popup-default" style="display: none;">
    <div class="block-content">
        You comment was created. Admin or Publisher of this book will check it first.
        <button class="but-close but-gray close" title='<spring:message code="button.close"/>'>x</button>
    </div>
</div>
<div id="comment-fail" class="popup-default" style="display: none;">
    <div class="block-content">
        Ooops. failed to create comment
        <button class="but-close but-gray close" title='<spring:message code="button.close"/>'>x</button>
    </div>
</div>
  <jsp:include page="../auth/signinPopup.jsp"/>
  <img src="/book/getCover/${book.id}/1" width="80" height="100" onerror="this.src='/resources/images/no-cover.png'" align="left"/>
  <spring:message code="book.title" />: ${book.name}
  <c:if test="${book.eighteenPlus}">
    <spring:message code="book.adult"/>
  </c:if><br />
  <spring:message code="book.author" />:
  <c:forEach var="author" items="${book.authors}">
    ${author.name}<br />
  </c:forEach>
  <spring:message code="book.type" />: ${type} <br />
  <spring:message code="book.pages" />: ${book.pages} <br />
  <spring:message code="book.year" />: ${book.yearOfPublication} <br />
  <spring:message code="book.description" />: ${book.description} <br />
  <spring:message code="book.publisher" />: ${book.publisher.name} <br />
  ${category}:${book.subCategory}</br>
  <c:forEach var="keyword" items="${keywords}">
    ${keyword.keyword}
  </c:forEach>

<br/>
<c:choose>
    <c:when test="${book.favorite}"> <button class="deleteFavorite" id="favorite/${book.id}"><spring:message code="book.favorites.deletefromfavorites" /></button></c:when>
    <c:otherwise> <button class="addFavorite" id="favorite/${book.id}"><spring:message code="book.favorites.addtofavorites" /></button></c:otherwise>
</c:choose>
<br/>

  <a href="#read-book">MOVE TO BOOK</a>
<div class="fb-like" data-href="${shareLink}" data-layout="button" data-action="like" data-show-faces="true" data-share="true"></div>
<div id="vk_like"></div>
<script type="text/javascript">
    VK.Widgets.Like("vk_like", {type: "mini", height: 24});
</script>
<script type="text/javascript">
document.write(VK.Share.button('${shareLink}',{type: "round", text: "Share", eng: 1}));
</script>
<div class="g-plusone" data-size="small" data-annotation="inline" data-width="300" data-href="${shareLink}"></div>

<div id="container_galleria">
    <div class="galleria" style="height: 600px;">
    </div>
</div>


<div id="comments">
    Comments:<br />
    <c:if test="${book.comments ne null}">
        <jsp:useBean id="dateValue" class="java.util.Date"/>
        <c:forEach var="comment" items="${book.comments}">
            <c:choose>
                <c:when test="${userId ne null && comment.user.id eq userId}">
                    <c:choose>
                        <c:when test="${comment.status eq 'NEW'}">
                            <div class="not-active-comment" id="comment-${comment.id}">
                                user name: ${comment.user.name}<br />
                                comment: ${comment.comment}<br />
                                <jsp:setProperty name="dateValue" property="time" value="${comment.time}"/>
                                date created:<fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/><br />
                                <button class="delete-comment" value="${comment.id}">Delete Comment</button>
                            </div>
                        </c:when>
                        <c:when test="${comment.status eq 'ACTIVE'}">
                            <div class="comment" id="comment-${comment.id}">
                                user name: ${comment.user.name}<br />
                                comment: ${comment.comment}<br />
                                <jsp:setProperty name="dateValue" property="time" value="${comment.time}"/>
                                date created:<fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/><br />
                                <button class="delete-comment" value="${comment.id}">Delete Comment</button>
                            </div>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:when test="${comment.status eq 'ACTIVE'}">
                    <div class="comment">
                        user name: ${comment.user.name}<br />
                        comment: ${comment.comment}<br />
                        <jsp:setProperty name="dateValue" property="time" value="${comment.time}"/>
                        date created:<fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/><br />
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
    </c:if>
</div>

<div style="margin-top: 200px"></div>
<div>
    New comment:<br />
    <input type="text" id="comment-text" placeholder="please enter your comment"/>
    <button id="add-comment">Add Comment</button>
</div>

<div style="margin-top: 500px;"></div>
  <a name="read-book"></a>
  <div id="outerContainer">

  <div id="sidebarContainer">
      <div id="toolbarSidebar">
          <div class="splitToolbarButton toggled">
              <button id="viewThumbnail" class="toolbarButton group toggled" title="Show Thumbnails" tabindex="2" data-l10n-id="thumbs">
                  <span data-l10n-id="thumbs_label">Thumbnails</span>
              </button>
              <button id="viewOutline" class="toolbarButton group" title="Show Document Outline" tabindex="3" data-l10n-id="outline">
                  <span data-l10n-id="outline_label">Document Outline</span>
              </button>
              <button id="viewAttachments" class="toolbarButton group" title="Show Attachments" tabindex="4" data-l10n-id="attachments">
                  <span data-l10n-id="attachments_label">Attachments</span>
              </button>
          </div>
      </div>
      <div id="sidebarContent">
          <div id="thumbnailView">
          </div>
          <div id="outlineView" class="hidden">
          </div>
          <div id="attachmentsView" class="hidden">
          </div>
      </div>
  </div>  <!-- sidebarContainer -->

  <div id="mainContainer">
      <div class="findbar hidden doorHanger hiddenSmallView" id="findbar">
          <label for="findInput" class="toolbarLabel" data-l10n-id="find_label">Find:</label>
          <input id="findInput" class="toolbarField" tabindex="91">
          <div class="splitToolbarButton">
              <button class="toolbarButton findPrevious" title="" id="findPrevious" tabindex="92" data-l10n-id="find_previous">
                  <span data-l10n-id="find_previous_label">Previous</span>
              </button>
              <div class="splitToolbarButtonSeparator"></div>
              <button class="toolbarButton findNext" title="" id="findNext" tabindex="93" data-l10n-id="find_next">
                  <span data-l10n-id="find_next_label">Next</span>
              </button>
          </div>
          <input type="checkbox" id="findHighlightAll" class="toolbarField">
          <label for="findHighlightAll" class="toolbarLabel" tabindex="94" data-l10n-id="find_highlight">Highlight all</label>
          <input type="checkbox" id="findMatchCase" class="toolbarField">
          <label for="findMatchCase" class="toolbarLabel" tabindex="95" data-l10n-id="find_match_case_label">Match case</label>
          <span id="findMsg" class="toolbarLabel"></span>
      </div>  <!-- findbar -->

      <div id="secondaryToolbar" class="secondaryToolbar hidden doorHangerRight">
          <div id="secondaryToolbarButtonContainer">
              <button id="secondaryPresentationMode" class="secondaryToolbarButton presentationMode visibleLargeView" title="Switch to Presentation Mode" tabindex="51" data-l10n-id="presentation_mode">
                  <span data-l10n-id="presentation_mode_label">Presentation Mode</span>
              </button>

              <a href="#" id="secondaryViewBookmark" class="secondaryToolbarButton bookmark visibleSmallView" title="Current view (copy or open in new window)" tabindex="55" data-l10n-id="bookmark">
                  <span data-l10n-id="bookmark_label">Current View</span>
              </a>

              <div class="horizontalToolbarSeparator visibleLargeView"></div>

              <button id="firstPage" class="secondaryToolbarButton firstPage" title="Go to First Page" tabindex="56" data-l10n-id="first_page">
                  <span data-l10n-id="first_page_label">Go to First Page</span>
              </button>
              <button id="lastPage" class="secondaryToolbarButton lastPage" title="Go to Last Page" tabindex="57" data-l10n-id="last_page">
                  <span data-l10n-id="last_page_label">Go to Last Page</span>
              </button>

              <div class="horizontalToolbarSeparator"></div>

              <button id="pageRotateCw" class="secondaryToolbarButton rotateCw" title="Rotate Clockwise" tabindex="58" data-l10n-id="page_rotate_cw">
                  <span data-l10n-id="page_rotate_cw_label">Rotate Clockwise</span>
              </button>
              <button id="pageRotateCcw" class="secondaryToolbarButton rotateCcw" title="Rotate Counterclockwise" tabindex="59" data-l10n-id="page_rotate_ccw">
                  <span data-l10n-id="page_rotate_ccw_label">Rotate Counterclockwise</span>
              </button>

              <div class="horizontalToolbarSeparator"></div>

              <button id="toggleHandTool" class="secondaryToolbarButton handTool" title="Enable hand tool" tabindex="60" data-l10n-id="hand_tool_enable">
                  <span data-l10n-id="hand_tool_enable_label">Enable hand tool</span>
              </button>
          </div>
      </div>  <!-- secondaryToolbar -->

      <div class="toolbar">
          <div id="toolbarContainer">
              <div id="toolbarViewer">
                  <div id="toolbarViewerLeft">
                      <button id="sidebarToggle" class="toolbarButton" title="Toggle Sidebar" tabindex="11" data-l10n-id="toggle_sidebar">
                          <span data-l10n-id="toggle_sidebar_label">Toggle Sidebar</span>
                      </button>
                      <div class="toolbarButtonSpacer"></div>
                      <button id="viewFind" class="toolbarButton group hiddenSmallView" title="Find in Document" tabindex="12" data-l10n-id="findbar">
                          <span data-l10n-id="findbar_label">Find</span>
                      </button>
                      <div class="splitToolbarButton">
                          <button class="toolbarButton pageUp" title="Previous Page" id="previous" tabindex="13" data-l10n-id="previous">
                              <span data-l10n-id="previous_label">Previous</span>
                          </button>
                          <div class="splitToolbarButtonSeparator"></div>
                          <button class="toolbarButton pageDown" title="Next Page" id="next" tabindex="14" data-l10n-id="next">
                              <span data-l10n-id="next_label">Next</span>
                          </button>
                      </div>
                      <label id="pageNumberLabel" class="toolbarLabel" for="pageNumber" data-l10n-id="page_label">Page: </label>
                      <input type="number" id="pageNumber" class="toolbarField pageNumber" value="1" size="4" min="1" tabindex="15">
                      <span id="numPages" class="toolbarLabel"></span>
                  </div>
                  <div id="toolbarViewerRight">
                      <button id="presentationMode" class="toolbarButton presentationMode hiddenLargeView" title="Switch to Presentation Mode" tabindex="31" data-l10n-id="presentation_mode">
                          <span data-l10n-id="presentation_mode_label">Presentation Mode</span>
                      </button>

                      <a href="#" id="viewBookmark" class="toolbarButton bookmark hiddenSmallView" title="Current view (copy or open in new window)" tabindex="35" data-l10n-id="bookmark">
                          <span data-l10n-id="bookmark_label">Current View</span>
                      </a>

                      <div class="verticalToolbarSeparator hiddenSmallView"></div>

                      <button id="secondaryToolbarToggle" class="toolbarButton" title="Tools" tabindex="36" data-l10n-id="tools">
                          <span data-l10n-id="tools_label">Tools</span>
                      </button>
                  </div>
                  <div class="outerCenter">
                      <div class="innerCenter" id="toolbarViewerMiddle">
                          <div class="splitToolbarButton">
                              <button id="zoomOut" class="toolbarButton zoomOut" title="Zoom Out" tabindex="21" data-l10n-id="zoom_out">
                                  <span data-l10n-id="zoom_out_label">Zoom Out</span>
                              </button>
                              <div class="splitToolbarButtonSeparator"></div>
                              <button id="zoomIn" class="toolbarButton zoomIn" title="Zoom In" tabindex="22" data-l10n-id="zoom_in">
                                  <span data-l10n-id="zoom_in_label">Zoom In</span>
                              </button>
                          </div>
                  <span id="scaleSelectContainer" class="dropdownToolbarButton">
                     <select id="scaleSelect" title="Zoom" tabindex="23" data-l10n-id="zoom">
                         <option id="pageAutoOption" title="" value="auto" selected="selected" data-l10n-id="page_scale_auto">Automatic Zoom</option>
                         <option id="pageActualOption" title="" value="page-actual" data-l10n-id="page_scale_actual">Actual Size</option>
                         <option id="pageFitOption" title="" value="page-fit" data-l10n-id="page_scale_fit">Fit Page</option>
                         <option id="pageWidthOption" title="" value="page-width" data-l10n-id="page_scale_width">Full Width</option>
                         <option id="customScaleOption" title="" value="custom"></option>
                         <option title="" value="0.5" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 50 }'>50%</option>
                         <option title="" value="0.75" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 75 }'>75%</option>
                         <option title="" value="1" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 100 }'>100%</option>
                         <option title="" value="1.25" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 125 }'>125%</option>
                         <option title="" value="1.5" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 150 }'>150%</option>
                         <option title="" value="2" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 200 }'>200%</option>
                         <option title="" value="3" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 300 }'>300%</option>
                         <option title="" value="4" data-l10n-id="page_scale_percent" data-l10n-args='{ "scale": 400 }'>400%</option>
                     </select>
                  </span>
                      </div>
                  </div>
              </div>
              <div id="loadingBar">
                  <div class="progress">
                      <div class="glimmer">
                      </div>
                  </div>
              </div>
          </div>
      </div>

      <menu type="context" id="viewerContextMenu">
          <menuitem id="contextFirstPage" label="First Page"
                    data-l10n-id="first_page"></menuitem>
          <menuitem id="contextLastPage" label="Last Page"
                    data-l10n-id="last_page"></menuitem>
          <menuitem id="contextPageRotateCw" label="Rotate Clockwise"
                    data-l10n-id="page_rotate_cw"></menuitem>
          <menuitem id="contextPageRotateCcw" label="Rotate Counter-Clockwise"
                    data-l10n-id="page_rotate_ccw"></menuitem>
      </menu>

      <div id="viewerContainer" tabindex="0">
          <div id="viewer" class="pdfViewer"></div>
      </div>

      <div id="errorWrapper" hidden='true'>
          <div id="errorMessageLeft">
              <span id="errorMessage"></span>
              <button id="errorShowMore" data-l10n-id="error_more_info">
                  More Information
              </button>
              <button id="errorShowLess" data-l10n-id="error_less_info" hidden='true'>
                  Less Information
              </button>
          </div>
          <div id="errorMessageRight">
              <button id="errorClose" data-l10n-id="error_close">
                  Close
              </button>
          </div>
          <div class="clearBoth"></div>
          <textarea id="errorMoreInfo" hidden='true' readonly="readonly"></textarea>
      </div>
  </div> <!-- mainContainer -->

  <div id="overlayContainer" class="hidden">
      <div id="passwordOverlay" class="container hidden">
          <div class="dialog">
              <div class="row">
                  <p id="passwordText" data-l10n-id="password_label">Enter the password to open this PDF file:</p>
              </div>
              <div class="row">
                  <input type="password" id="password" class="toolbarField" />
              </div>
              <div class="buttonRow">
                  <button id="passwordCancel" class="overlayButton"><span data-l10n-id="password_cancel">Cancel</span></button>
                  <button id="passwordSubmit" class="overlayButton"><span data-l10n-id="password_ok">OK</span></button>
              </div>
          </div>
      </div>
      <div id="documentPropertiesOverlay" class="container hidden">
          <div class="dialog">
              <div class="row">
                  <span data-l10n-id="document_properties_file_name">File name:</span> <p id="fileNameField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_file_size">File size:</span> <p id="fileSizeField">-</p>
              </div>
              <div class="separator"></div>
              <div class="row">
                  <span data-l10n-id="document_properties_title">Title:</span> <p id="titleField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_author">Author:</span> <p id="authorField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_subject">Subject:</span> <p id="subjectField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_keywords">Keywords:</span> <p id="keywordsField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_creation_date">Creation Date:</span> <p id="creationDateField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_modification_date">Modification Date:</span> <p id="modificationDateField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_creator">Creator:</span> <p id="creatorField">-</p>
              </div>
              <div class="separator"></div>
              <div class="row">
                  <span data-l10n-id="document_properties_producer">PDF Producer:</span> <p id="producerField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_version">PDF Version:</span> <p id="versionField">-</p>
              </div>
              <div class="row">
                  <span data-l10n-id="document_properties_page_count">Page Count:</span> <p id="pageCountField">-</p>
              </div>
              <div class="buttonRow">
                  <button id="documentPropertiesClose" class="overlayButton"><span data-l10n-id="document_properties_close">Close</span></button>
              </div>
          </div>
      </div>
  </div>  <!-- overlayContainer -->

  </div> <!-- outerContainer -->
  <div id="mozPrintCallback-shim" hidden>
      <style>
          @media print {
              #printContainer div {
                  page-break-after: always;
                  page-break-inside: avoid;
              }
          }
      </style>
      <style scoped>
          #mozPrintCallback-shim {
              position: fixed;
              top: 0;
              left: 0;
              height: 100%;
              width: 100%;
              z-index: 9999999;

              display: block;
              text-align: center;
              background-color: rgba(0, 0, 0, 0.5);
          }
          #mozPrintCallback-shim[hidden] {
              display: none;
          }
          @media print {
              #mozPrintCallback-shim {
                  display: none;
              }
          }

          #mozPrintCallback-shim .mozPrintCallback-dialog-box {
              display: inline-block;
              margin: -50px auto 0;
              position: relative;
              top: 45%;
              left: 0;
              min-width: 220px;
              max-width: 400px;

              padding: 9px;

              border: 1px solid hsla(0, 0%, 0%, .5);
              border-radius: 2px;
              box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);

              background-color: #474747;

              color: hsl(0, 0%, 85%);
              font-size: 16px;
              line-height: 20px;
          }
          #mozPrintCallback-shim .progress-row {
              clear: both;
              padding: 1em 0;
          }
          #mozPrintCallback-shim progress {
              width: 100%;
          }
          #mozPrintCallback-shim .relative-progress {
              clear: both;
              float: right;
          }
          #mozPrintCallback-shim .progress-actions {
              clear: both;
          }
      </style>
      <div class="mozPrintCallback-dialog-box">
          <!-- TODO: Localise the following strings -->
          Preparing document for printing...
          <div class="progress-row">
              <progress value="0" max="100"></progress>
              <span class="relative-progress">0%</span>
          </div>
          <div class="progress-actions">
              <input type="button" value="Cancel" class="mozPrintCallback-cancel">
          </div>
      </div>
  </div>


</body>
</html>
