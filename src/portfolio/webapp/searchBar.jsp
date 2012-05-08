<!-- $Name:  $ -->
<!-- $Id: searchBar.jsp,v 1.14 2010/11/23 20:34:57 ajokela Exp $ -->
<div id="welcomeSearch">
<form id="searchForm"><input id="searchBox" type="text"
	name="searchText" /> <input id="searchLink" type="image"
	src="/images/fugue/icon_shadowless/magnifier.png" /></form>
<div id="searchTypesDiv" style="display: none;">
<div><input type="radio" id="searchType_collection"
	name="searchType" value="collection" checked="checked" /> <label
	for="searchType_collection"> collection </label></div>
<div><input type="radio" id="searchType_portfolios"
	name="searchType" value="portfolios" /> <label
	for="searchType_portfolios"> portfolios </label></div>
</div>
</div>

<script type="text/javascript">
document.observe("dom:loaded", function() {
    var hideSearchTypes = function(event){
        if (event.element().id != 'searchTypesDiv' && !event.element().descendantOf('searchTypesDiv') && event.element().id != 'searchBox') {
          $('searchTypesDiv').hide();
          document.stopObserving('click', hideSearchTypes);
        }
    };
    $('searchBox').observe('focus', function(event){
      $('searchTypesDiv').show();          
      var sbOffset = $('searchBox').positionedOffset();
      with ($('searchTypesDiv').style) {
        top = (sbOffset.top + $('searchBox').getHeight() + 2) + 'px';
        left = sbOffset.left + 'px';
      }
      document.observe('click', hideSearchTypes);
    });
    
    // set up search box
    new EPF.widget.InputTextInnerLabel('searchBox', 'Search your stuff');
    
    $$('input[type="radio"][name="searchType"]').invoke('observe', 'click', function(event){$('searchBox').focus()});
    
    $('searchForm').observe('submit', function(event){
      event.stop();
      var searchType = $$('input:checked[type="radio"][name="searchType"]').first().value;
      window.location='/search/' + searchType + '/' + $F('searchBox');
    });
});
</script>
