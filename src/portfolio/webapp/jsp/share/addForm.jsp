<!-- $Name:  $ -->
<!-- $Id: addForm.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<div class="">
<div class="addViewer">
<div class="instructions">Enter an e-mail address</div>
<div class="inputs"><input type="text" size="30" name="query"
	id="query" /></div>
<div class="buttons"><input type="submit" name="addUser"
	value="Add" id="addUserSubmit" /> or <a href="#">cancel</a></div>
</div>
<script type="text/javascript">
   $('addUserSubmit').observe('click', function(event) {
      if (!$F('query').blank()) {
        new Ajax.Request('/portfolio/viewer/add/'+$F('query'), {
          onSuccess: function(transport) {
            $('viewersDiv').update(transport.responseText);
          }
        });
      }
      event.stop();
      $('query').clear();
   });
  </script></div>
