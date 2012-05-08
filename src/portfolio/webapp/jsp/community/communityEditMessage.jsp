<!-- $Name:  $ -->
<!-- $Id: communityShowMessage.jsp,v 1.1 2010/11/23 14:29:11 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
    <head>
        <title>${message.subject}</title>
        <link rel="shortcut icon" href="/favicon.ico" />
        <link rel="stylesheet" href="/js/yui/2.8.1/build/grids/grids-min.css" type="text/css" />
        <link rel="stylesheet" type="text/css" href="/css/modules/core.css" />
  
        <script type="text/javascript" src="/js/prototype.js"></script>
        <script type="text/javascript" src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls"></script>
        <script type="text/javascript" src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js"></script>
        <script type="text/javascript" src="/js/EPF.js"></script>  
        <script type="text/javascript" src="/js/modules/core.js"></script>
        <script type="text/javascript" src="/js/tiny_mce/tiny_mce.js"></script>
  
    <script type="text/javascript">
    
        function randomString(string_length) {
            var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
            
            var randomstring = '';
            
            for (var i=0; i<string_length; i++) {
                var rnum = Math.floor(Math.random() * chars.length);
                randomstring += chars.substring(rnum,rnum+1);
            }
            
            return randomstring;
        }
    
        EPF.use(["button","dhtmlcalendar"], randomString(32), function() {
          document.fire('loader:success');
        });
        
    </script>
  
  
    </head>
    <body>
        <div style="padding: 2px; margin: 2px; background-color: #cccccc;">
            <div style="padding: 1px; background-color: #ffffff; text-align: left;">
            
                <div style="text-align: left; width: 100%; font-size: 12pt;">
                    <span style="font-weight: bold;">Subject:&nbsp;</span><input type="text" id="subject" size="40" value="${fn:escapeXml(message.subject)}" />
                </div>
            
                <div style="width: 100%;">
                
                    <input type="text" id="dateStart" name="started_at" value="<fmt:formatDate value="${message.startedAt}" pattern="MM/dd/yyyy"/>" style="width:100px" readonly="readonly"/><br />
                    <span style="text-size: 8pt;">Sent Date</span>
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

                <div style="width: 100%;">
                
                    <input type="text" id="dateEnd" name="ended_at" value="<fmt:formatDate value="${message.endedAt}" pattern="MM/dd/yyyy"/>" style="width:100px" readonly="readonly"/><br />
                    <span style="text-size: 8pt;">End Date</span>
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
                                        
                <div>
                    <input type="text" size="60" style="border: 1px solid #000000;" value="${message.coordinator.displayName}" readonly="readonly" />
                </div>
                <div>
                    <span style="font-size: 8pt;">Coordinator</span><br />
                </div>
                
                <div>
                    <textarea id="message" rows="15" cols="64" style="border: 1px solid #000000; font-size: 10pt;">${fn:escapeXml(message.messageTxt)}</textarea>
                </div>
            </div>
            
            <input type="button" id="submit" value="Save" />
            
            <script type="text/javascript">
            
            $('submit').observe('click', function(event) {
                event.stop();
            
                new Ajax.Request('/community/message_send/${community.id}', {
                    parameters : {
                    	'messageId'  : '${message.id}',
                    	'subject'    : $('subject').value,
                    	'message'    : $('message').value,
                    	'started_at' : $('dateStart').value,
                    	'ended_at'   : $('dateEnd').value
                    },
                    onSuccess : function(transport) {
                        var resp = transport.responseJSON;

                        if(resp != null) {
                            if(resp.status == 'ok') {
                                
                            	parent.reload_page();
                                
                            }
                            else {
                                alert("There was an error saving the message.");
                            }
                        }
                        
                    }
                });
                                    
            });
            
            </script>
            
        </div>
    </body>
</html>