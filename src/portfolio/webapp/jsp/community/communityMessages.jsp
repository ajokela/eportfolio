<!-- $Name:  $ -->
<!-- $Id: communityMessages.jsp,v 1.1 2010/11/23 14:29:11 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tags:communityPage community="${community}" jsModules="button,dhtmlcalendar" pageTitle="Messages : Manage" id="communityMessages" returnTo="Community">
	<div class="MainContent communityHome yui-skin-sam" id="communityInfo" style="width: 100%;">
       <script type="text/javascript">
       
           function validateForm(){
               if($('subject').value=="")
               {
                   new dialog.Alert({messageText:'Please enter a subject'}).show();
                   return false;
               }
               return true;
           }

           function reload_page() {
        	    window.location = "/community/messages/manage/${community.id}";   
           }
           
        </script>
		
		<div style="text-align: center; width: 50%; float: left;">
			<div style="text-align: left;">
				<!--form action="/community/message_send/${community.id}" method="post" onSubmit="return validateForm();" -->
	
				<input type="text" name="subject" id="subject" size="40" /><br />
				<span style="font-size: 10pt;">Subject</span><br /><br />
				
				<div style="display: block; width: 100%;">
					<div style="float: left; width: 50%;">
						<input type="text" id="dateStart" name="started_at" value="None" style="width:100px" readonly="readonly"/><br />
						<span style="text-size: 8pt;">Start Display</span>
						<img id="trigger1" src="/images/calendar.jpg" style="cursor: pointer;"/>
						<a href="#" onclick="$('dateStart').value='None';return false;">clear</a>
						<script type="text/javascript">
							document.observe('loader:success', function() {
								var today = new Date();
								Calendar.setup({
								    inputField: 'dateStart',
								    ifFormat: "%m/%d/%Y",
								    button: 'trigger1',
								    weekNumbers: false
								    
								  }
								);
							}); 
						</script>
					</div>
					<div style="float: right; width: 50%;">
						<input type="text" id="dateEnd" name="ended_at" value="None" style="width:100px" readonly="readonly"/><br />
						<span style="text-size: 8pt;">End Display</span>
						<img id="trigger2" src="/images/calendar.jpg" style="cursor: pointer;"/>
						<a href="#" onclick="$('dateEnd').value='None';return false;">clear</a>
						<script type="text/javascript">
							document.observe('loader:success', function() {
								var today = new Date();
								Calendar.setup({
								    inputField: 'dateEnd',
								    ifFormat: "%m/%d/%Y",
								    button: 'trigger2',
								    weekNumbers: false
								    
								  }
								);
							}); 
						</script>
					</div>
				</div><br />
				
				<span style="font-size: 10pt;">Message</span><br />
				
				
				<textarea id="message" name="message" cols="60" rows="25"></textarea><br />
				<input type="submit" name="submit" id="submit" value="Send" />
				<!--/form-->
				
				<script type="text/javascript">
			    $('submit').observe("click", function(event){
			    	event.stop();
			    	
			    	if(validateForm()) {
			    		
			    		$('submit').value = "Please Wait...";
			    		
	                      new Ajax.Request('/community/message_send/${community.id}', {
	                           parameters : {
	                               'message'    : $('message').value,
	                               'subject'    : $('subject').value,
	                               'started_at' : $('dateStart').value,
	                               'ended_at'   : $('dateEnd').value
	                           },
	                           onSuccess : function(transport) {
	                               var resp = transport.responseJSON;

	                               if(resp != null) {
	                                   if(resp.status == 'ok') {
	                                       
	                                	   var extra = "";
	                                	   
	                                	   if($('dateStart').value != 'None') {
	                                		   extra = " Your message will begin running in the community on " + $('dateStart').value;
	                                	   }
	                                	   
	                                	   new dialog.Alert({messageText:"Message Complete." + extra}).show();
	                                	   
	                                	   window.location = "/community/messages/manage/${community.id}";
	                                	   return true;;
	                                   }
	                                   else {
	                                	   new dialog.Alert({messageText:"There was an error sending the message."}).show();
	                                   }
	                               }
	                           },
	                           onFailure : function(error) {
	                        	   console.log(error);
	                           }
	                       });

			    	}
			    	
			    });
				</script>
				
			</div>
		</div>

		<div style="text-align: center; width: 50%; float: right;">

	      <div style="display: block; float: left; width: 10px; padding: 1px; margin: 1px 1px 1px 1px;">&nbsp;</div>
	      <div style="display: block; float: left; width: 5%; padding: 1px; margin: 1px 1px 1px 1px;">&nbsp;</div>
	      <div style="display: block; float: left; width: 15%; text-align: center; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Coordinator</div>
	      <div style="display: block; float: left; width: 30%; text-align: center; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Subject</div>
	      <div style="display: block; float: left; width: 12%; text-align: center; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Date Sent</div>
	      <div style="display: block; float: left; width: 12%; text-align: center; font-weight: bold; padding: 1px; margin: 1px 1px 1px 1px;">Expires</div>
	      
	      <div style="clear: both;"></div>
	      
	      <c:forEach var="message" items="${messages}" varStatus="rowCounter">
	          <c:choose>
	              <c:when test="${rowCounter.count % 2 == 0}">
	                  <c:set var="rowStyle" scope="page" value="#dddddd"/>
	              </c:when>
	              <c:otherwise>
	                  <c:set var="rowStyle" scope="page" value="#ffffff"/>
	              </c:otherwise>
	          </c:choose>
	          
	          <c:choose>
	              <c:when test="${message.read == true}">
	                  <c:set var="weight" scope="page" value="normal" />
	              </c:when>
	              <c:otherwise>
	                  <c:set var="weight" scope="page" value="bold" />
	              </c:otherwise>
	          </c:choose>
	          
	          <div id="row_${message.id}">
	          <div style="display: block; float: left; width: 10px; padding: 1px;">${rowCounter.count}</div>
	          <div id="msg_delete_${message.id}" style="display: block; float: left; width: 5%; padding: 1px; margin: 1px 1px 1px 1px;">
	           <a href="#" id="message_delete_${message.id}" title="Delete Message"><img src="/images/delete.gif" alt="Delete Message" style="border: 0px;" /></a>
	           
	           <script type="text/javascript">
	           
	           $('message_delete_${message.id}').observe('click', function(event) {
	        	   event.stop();
	           
	        	   if (confirm("Are you sure you want to delete this message?")) {
	        		
	                   new Ajax.Request('/community/message/delete/${community.id}', {
	                       parameters : {
	                           messageId : '${message.id}'
	                       },
	                       onSuccess : function(transport) {
	                    	   var resp = transport.responseJSON;

	                    	   if(resp != null) {
	                    		   if(resp.status == 'ok') {
	                                   
	                                   $('row_${message.id}').fade();
	                    			   
	                    		   }
	                    		   else {
	                    			   alert("There was an error deleting the message.");
	                    		   }
	                    	   }
                               
	                       }
	                   });
	        		   	        	   
	        	   }
	        	    	        	   
	           });

	           </script>
       
	           
	          </div>
	          
	          <div id="msg_coord_${message.id}" style="display: block; float: left; width: 15%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">${message.coordinator.displayName}</div>
	          <div id="msg_subject_${message.id}" style="display: block; float: left; width: 30%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">
	              <a href="#" class="message_${message.id}" id="message_subject_${message.id}">${fn:escapeXml(message.subject)}</a>
	          </div>
	          <div id="msg_started_at_${message.id}" style="display: block; float: left; width: 12%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">
	               <a href="#" class="message_${message.id}" id="message_startedat_${message.id}"><fmt:formatDate value="${message.startedAt}" pattern="MM/dd/yyyy"/></a>
	          </div>
	          <div id="msg_expires_${message.id}" style="display: block; float: left; width: 12%;  padding: 1px; margin: 1px 1px 1px 1px; background-color: ${rowStyle}; font-weight: ${weight};">
	              <a href="#" class="message_${message.id}" id="message_endedat_${message.id}">
	              <c:if test="${message.endedAt != null}">
	                  <fmt:formatDate value="${message.endedAt}" pattern="MM/dd/yyyy"/>
	              </c:if>
	              
	              <c:if test="${message.endedAt == null}">
	                  Never
	              </c:if>
	              </a>
	          </div>
	          
	          <div style="clear: both;"></div>
			  </div>
			  
			  <script type="text/javascript">

			  $$('.message_${message.id}').each(function(item) {
                  
                  item.observe('click', function(event) {
                      event.stop();
                      
                      var width =  640; // document.body.clientWidth * 0.80;
                      var height = 480; // document.body.clientHeight * 0.80;
                      
                      var win = new Window({url: '/community/message/edit/${community.id}/${message.id}', options: {method: 'get'}}, {className: "alphacube", title: '${fn:escapeXml(message.subject)}'});
                      
                      win.setSize(width, height);
                      win.setDestroyOnClose();
                      win.toFront();
                      
                      win.showCenter(true);
                      
                      $('msg_coord_${message.id}').setStyle({fontWeight: 'normal'});
                      $('msg_subject_${message.id}').setStyle({fontWeight: 'normal'});
                      $('msg_started_at_${message.id}').setStyle({fontWeight: 'normal'});
                      $('msg_expires_${message.id}').setStyle({fontWeight: 'normal'});
                      
                  });
                  
              });
			  
			  </script>
			  
		  </c:forEach>
		</div>

		<div style="clear:both;"></div>
			
	</div>
</tags:communityPage>