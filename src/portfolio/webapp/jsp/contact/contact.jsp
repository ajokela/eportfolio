<!-- $Name:  $ -->
<!-- $Id: contact.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<tags:portfolioPage viewMode="enter" pageTitle="ePortfolio : Contact Us">
	<div class="MainContent" id="contactUs">
	<h1>Contact Us</h1>
	<p>Use this form to send us feedback or to report any problems that
	you are having.</p>
	<form action="/contact/send" method="post" id="contactForm">
	<fieldset>
	<ul>
		<li><label for="contactName">Name*</label> <input
			id="contactName" type="text" name="name"
			value="${Person ne null ? Person.displayName : ''}" /></li>
		<li><label for="contactEmail">E-mail*</label> <input
			id="contactEmail" type="text" name="email"
			value="${Person ne null ? Person.email : ''}" /></li>
		<li><label for="contactMessage">Message*</label> <textarea
			id="contactMessage" name="message" rows="4" cols="30"></textarea></li>
	</ul>
	</fieldset>
	<fieldset class="submit"><input type="submit" value="Send" />
	</fieldset>
	</form>
	<script type="text/javascript">
      (function(){
        $('contactForm').observe('submit', function(event){
          if ($F('contactName').blank()) {
            event.stop();
            new dialog.Alert('Please enter a name.').show();
            return;
          }
          if ($F('contactEmail').blank()) {
            event.stop();
            new dialog.Alert('Please enter an email.').show();
            return;
          }
          if ($F('contactMessage').blank()) {
            event.stop();
            new dialog.Alert('Please enter a message.').show();
            return;
          }
        });
      })();
    </script></div>
</tags:portfolioPage>
