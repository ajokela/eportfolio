<!-- $Name:  $ -->
<!-- $Id: processingApasReport.jsp,v 1.9 2010/11/23 20:34:57 ajokela Exp $ -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>Portfolio : Education: APAS Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/prototype/1.6/prototype.js"></script>
</head>
<body>
<br />
<br />
<br />
<table border="0" width="50%" cellpadding="3" cellspacing="0"
	align="center" class="mainTable">
	<tr>
		<td colspan="2" class="headTitle" style="white-space: nowrap;"><br />
		<br />
		<blockquote>One moment . . . <br />
		<br />
		while we generate the report</blockquote>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
  (function(){
    new PeriodicalExecuter(function(pe) {
      new Ajax.Request('/viewApasReport.do?personId=${apasHelper.degreeApasPS.encryptedPersonId}&entryId=${apasHelper.degreeApasPS.entryId}',{
        onComplete: function(transport){
          if (transport.responseText) {
            $$('body')[0].update(transport.responseText);
            pe.stop();
          }
        }
      });
		}, 2);
  })();
</script>
</html>
