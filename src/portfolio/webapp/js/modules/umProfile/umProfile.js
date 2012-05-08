/* $Name:  $ */
/* $Id: umProfile.js,v 1.6 2010/12/06 14:26:05 ajokela Exp $ */
EPF.namespace('controller.umprofile');
EPF.controller.umprofile.Main = Class.create(
    /** @lends EPF.controller.umprofile.Main.prototype */
{
    /**
     * Main controller for UM Profile page.
     *
     * @constructs
     * @param {String} adviseeUsername
     */
    initialize : function(adviseeUsername) {
        this.adviseeUsername = adviseeUsername;

        $$('ul.elementsList .entryName a').each(function(e) {
            var entryKeyId = e.id.split('_')[1];
            e.observe('click', function(event) {
                event.stop();
                new EPF.widget.ElementWindow({
                    entryKeyId : entryKeyId
                });
            });
        });

        new Ajax.Request('/adviser/comment/view/' + this.adviseeUsername, {
            method: 'get',
            onComplete : this.onAdviserCommentsLoaded.bind(this)
        });

        if ($('addCommentForm')) {
        	
            $('addCommentForm').observe('submit', this.onAddCommentFormSubmit.bind(this));

            new EPF.widget.TextareaCounter({
                textareaEl: 'addCommentText',
                counterEl: 'commentCounter',
                max: 4000
            });
        }

        $('umProfile').show();
    },

    onAddCommentFormSubmit : function(event) {
        event.stop();
        if ($F('addCommentText').length > 4000) {
            alert('Comment can not exceed 4000 characters. You have: ' + $F('addCommentText').length);
            return;
        }
        new Ajax.Request('/adviser/comment/add', {
            method: 'post',
            parameters : {
                text : $F('addCommentText'),
                adviseeUsername : this.adviseeUsername
            },
            onComplete : this.onAdviserCommentsLoaded.bind(this)
        });
        $('addCommentText').value = '';
    },

    onAdviserCommentsLoaded : function(transport) {
        
    	var text = transport.responseText;
    	
    	text = text.replace(/\n\n/g, "<br /><br />");
    	text = text.replace(/\r\n\r\n/g, "<br /><br />");
    	text = text.replace(/\n\r\n\r/g, "<br /><br />");
    	
    	$('adviserComments').update(text);
        
        $$('#adviserComments .deleteLink').each(function(e) {
            e.observe('click', this.onDeleteClick.bindAsEventListener(this, e));
        }.bind(this));
        
    },

    onDeleteClick : function(event, link) {
        var id = link.id.split('_')[1];
        event.stop();
        new dialog.Confirm({
            messageText : 'Are you sure you want to delete this comment?',
            onConfirm : function() {
                new Ajax.Request('/adviser/comment/delete/' + id, {
                    method: 'post',
                    onSuccess : function(transport) {
                        link.up('li').fade({
                            afterFinish : function(effect) {
                                effect.element.remove();
                                if ($$('#adviserComments .commentsList li').size() == 0) {
                                    $('noComments').show();
                                }
                            }
                        });
                    }
                });
            }
        }).show();
    }
});
