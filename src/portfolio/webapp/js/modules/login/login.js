/* $Name:  $ */
/* $Id: login.js,v 1.4 2011/03/04 17:44:10 rkavajec Exp $ */
EPF.namespace('controller.login');

EPF.controller.login.Main = Class.create(
/** @lends EPF.controller.login.Main.prototype */
{
    /**
     * Main controller Login page.
     * 
     * @constructs
     */
    initialize : function() {
      $('guestLoginLink').observe('click', this.onGuestLoginClick.bind(this));
    },
    
    onGuestLoginClick: function(event) {
        event.stop();
        var modal = new EPF.widget.modal.Modal({
            title: 'Login', 
            width: 350,
            content: EPF.template.login.guestLogin()
        });
        
        $('guestLoginForm').observe('submit', function(event){
            event.stop();
            if ($F('guestLoginEmail').blank()) {
                $('guestLoginError').update('Please enter an email address.').show();
                return;
              }
            if ($F('guestLoginPassword').blank()) {
                $('guestLoginError').update('Please enter a password.').show();
                return;
            }
            new Ajax.Request('/guestLogin',{
                method: 'post',
                parameters: $('guestLoginForm').serialize(true),
                onComplete: function(transport){
                    var resp = transport.responseJSON;
                    if (resp.stat == 'ok') {
                        window.location = '/';
                    } else {
                        $('guestLoginError').update(resp.error).show();
                    }
                }
            });
        });
    }
});
