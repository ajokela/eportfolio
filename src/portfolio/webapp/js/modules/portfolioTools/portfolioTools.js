/* $Name:  $ */
/* $Id: portfolioTools.js,v 1.25 2011/02/24 21:49:16 ajokela Exp $ */
EPF.namespace('widget.portfolio');

EPF.widget.portfolio.Sharing = Class.create(/** @lends EPF.widget.portfolio.Sharing.prototype */
{
    /**
     *
     * @constructs
     * @param {String} portfolioId
     * @param options
     * @param {Function} [options.onShareChange] onChange callback
     */
    initialize : function(portfolioId, options) {
        this.portfolioId = portfolioId;
        this.options = options || {};
        new Ajax.Request('/portfolio/summary/' + portfolioId, {
            onSuccess: this._summarySuccessHandler.bind(this)
        });
    },

    _summarySuccessHandler: function(transport) {
        var resp = transport.responseJSON;
        this.portfolioJson = resp.data.portfolio;
        this._createSharingModal();
    },

    _createSharingModal: function() {
        var content = EPF.template.portfolio.portfolioTools.sharing({portfolio:this.portfolioJson});

        this.sharingModal = new EPF.widget.modal.Modal({
            title: 'Sharing',
            content: content
        });

        if (typeof this.options.onShareChange == 'function') {
            this.sharingModal.getModalElement().observe('epf:sharing.change', this.options.onShareChange);
        }

        this.noViewersEl = this.sharingModal.getContentParent().down('.noViewers');
        var viewersListLIs = this.sharingModal.getContentParent().select('.viewersList > li');
        if (viewersListLIs.size() > 0) {
            this.noViewersEl.hide();
            viewersListLIs.each(function(li) {
                this._processViewer(li);
            }.bind(this));
        }

        this.sharingModal.getContentParent().down('button.done').observe('click', function(event) {
            this.sharingModal.close();
        }.bind(this));
        this.sharingModal.getContentParent().down('button.addViewers').observe('click', this._addUsersClickHandler.bind(this));
        this.sharingModal.getContentParent().down('.changeVisibility').observe('click', this._changeVisibilityClickHandler.bind(this));
        this.sharingModal.getContentParent().down('.changeExpiration').observe('click', this._changeExpirationClickHandler.bind(this));

    },

    _fireSharingChangeEvent: function() {
        this.sharingModal.getModalElement().fire('epf:sharing.change', { portfolio: this.portfolioJson });
    },

    _processViewer: function(li) {
        var username = li.down('.username').innerHTML;
        li.down('.delete').observe('click', function(event) {
            event.stop();
            new Ajax.Request('/portfolio/sharing/removeViewer', {
                parameters: {
                    username: username,
                    portfolioId: this.portfolioId
                },
                onSuccess: function(transport) {
                    var resp = transport.responseJSON;
                    if (resp.stat == 'ok') {
                        this._removeViewer(li, username);
                    }
                }.bind(this)
            });
        }.bind(this));
    },

    _removeViewer: function(li, username) {
        li.fade({ afterFinish : function() {
            this.portfolioJson.viewers = this.portfolioJson.viewers.reject(function(e) {
                return e.username == username
            });
            this.portfolioJson.numViewers = this.portfolioJson.viewers.size();
            this._fireSharingChangeEvent();
            li.remove();

            var viewersListLIs = this.sharingModal.getContentParent().select('.viewersList > li');
            if (viewersListLIs.size() == 0) {
                this.noViewersEl.show();
            }
        }.bind(this) });
    },

    _changeVisibilityClickHandler: function(event) {
        event.stop();
        this.sharingModal.close();
        new EPF.widget.portfolio.ChangeVisibilityModal(this.portfolioJson, {
            onClose: this._createSharingModal.bind(this),
            onChange: this._fireSharingChangeEvent.bind(this)
        });
    },

    _changeExpirationClickHandler: function(event) {
        event.stop();
        this.sharingModal.close();
        new EPF.widget.portfolio.ChangeExpirationModal(this.portfolioJson, {
            onClose: this._createSharingModal.bind(this),
            onChange: this._fireSharingChangeEvent.bind(this)
        });
    },

    _addUsersClickHandler: function(event) {
        event.stop();
        this.sharingModal.close();

        new EPF.widget.portfolio.AddViewersModal(this.portfolioJson, {
            onClose: this._createSharingModal.bind(this),
            onChange: this._fireSharingChangeEvent.bind(this)
        });
    }
});

EPF.widget.portfolio.ChangeVisibilityModal = Class.create({
    initialize : function(portfolioJson, options) {
        this.portfolioJson = portfolioJson;
        this.portfolioId = portfolioJson.id;
        this.options = Object.extend({}, options);

        var content = EPF.template.portfolio.portfolioTools.visibility({portfolio:this.portfolioJson});

        this.modal = new EPF.widget.modal.Modal({
            title: 'Sharing - Visibility',
            content: content,
            onClose: this.options.onClose || Prototype.K
        });


        var saveButton = this.modal.getContentParent().down('.buttons .save');

        saveButton.observe('click', function(event) {

            this._onChangeVisibilitySubmit(event);

            this.modal.close();

        }.bind(this));

        var cancelButton = this.modal.getContentParent().down('.buttons .cancel');

        cancelButton.observe('click', function(event) {
            this.modal.close();
        }.bind(this));
    },

    _onChangeVisibilitySubmit: function(event) {

        var visibilityControl = this.modal.getContentParent().down('input[name=visibility]:checked');
        var visibility = visibilityControl.getValue();

        new Ajax.Request('/portfolio/sharing/updateVisibility', {
            parameters: {
                visibility: visibility,
                portfolioId: this.portfolioId
            },
            onSuccess: function(transport) {
                var resp = transport.responseJSON;

                if (resp.stat == 'ok') {

                    this.portfolioJson.viewers = resp.data.viewers;
                    this.portfolioJson.numViewers = this.portfolioJson.viewers.size();

                    var viewersIdField = document.getElementById("viewers_" + this.portfolioId);

                    var visibilityElement = document.getElementById("visibility_" + this.portfolioId);

                    if (visibility == 'public') {
                        if (visibilityElement != null) {
                            visibilityElement.innerHTML = '<span class="public">Public</span>';
                        }
                    }
                    else {
                        if (visibilityElement != null) {
                            visibilityElement.innerHTML = '<span class="private">Private</span>';
                        }
                    }

                    this.portfolioJson.publicView = (visibility == 'public');

                    if (typeof this.options.onChange == 'function') {
                        this.options.onChange();
                    }

                    if (this.portfolioJson.publicView) {
                        if (viewersIdField != null) {
                            viewersIdField.innerHTML = "Publicly Shared";
                        }
                    }
                    else {
                        if (this.portfolioJson.numViewers == 0) {
                            if (viewersIdField != null) {
                                viewersIdField.innerHTML = "Not Shared";
                            }
                        }
                        else {

                            var text = "";

                            for (var i = 0; i < this.portfolioJson.numViewers; ++i) {

                                var viewer = this.portfolioJson.viewers[i];

                                if (viewer.username != this.portfolioJson.viewers[this.portfolioJson.numViewers - 1].username) {
                                    text = text + viewer.displayName + ",";
                                } else {
                                    text = text + viewer.displayName;
                                }

                                text = text + "&nbsp;";
                            }

                            if (viewersIdField != null) {
                                viewersIdField.innerHTML = text;
                            }
                        }
                    }

                    this.modal.close();
                } else {
                    // TODO
                }
            }.bind(this)
        });
    }
});

EPF.widget.portfolio.ChangeExpirationModal = Class.create({
    initialize : function(portfolioJson, options) {
        this.portfolioJson = portfolioJson;
        this.portfolioId = portfolioJson.id;
        this.options = Object.extend({}, options);

        var content = EPF.template.portfolio.portfolioTools.expiration({portfolio:this.portfolioJson});

        this.modal = new EPF.widget.modal.Modal({
            title: 'Sharing - Expiration',
            content: content,
            onClose: this.options.onClose || Prototype.K
        });

        var calendarEl = this.modal.getContentParent().down('.calendar');
        this.calendar = new YAHOO.widget.Calendar("cal1", calendarEl)
        this.calendar.render();

        this.dateFieldEl = this.modal.getContentParent().down('input[name=expireDate]');

        this.calendar.selectEvent.subscribe(function(type, args, obj) {
            var selected = args[0];
            var selDate = this.calendar.toDate(selected[0]);

            var formatted = (selDate.getMonth() + 1) + '/' + selDate.getDate() + '/' + selDate.getFullYear();
            this.dateFieldEl.value = formatted;
        }.bind(this));

        var neverButton = this.modal.getContentParent().down('.never');
        neverButton.observe('click', function(event) {
            this.dateFieldEl.value = 'Never';
            this.calendar.clear();
        }.bind(this));

        var saveButton = this.modal.getContentParent().down('.buttons .save');

        saveButton.observe('click', function(event) {
            this._onSubmit(event);
            this.modal.close();
        }.bind(this));

        var cancelButton = this.modal.getContentParent().down('.buttons .cancel');
        cancelButton.observe('click', function(event) {
            this.modal.close();
        }.bind(this));

        this.modal.center();
    },

    _onSubmit: function(event) {
        var dateValue = $F(this.dateFieldEl) == 'Never' ? null : $F(this.dateFieldEl);

        new Ajax.Request('/portfolio/sharing/updateExpiration', {
            parameters: {
                date: dateValue == 'Never' ? '' : dateValue,
                portfolioId: this.portfolioId
            },
            onSuccess: function(transport) {

                var resp = transport.responseJSON;

                if (resp.stat == 'ok') {

                    var expireIdField = document.getElementById("expireDate_" + this.portfolioId);

                    var stateElement = document.getElementById("state_" + this.portfolioId);

                    if (expireIdField != null) {
                        expireIdField.innerHTML = dateValue ? dateValue : 'Never';
                    }

                    this.portfolioJson.expireDate = dateValue;

                    if (typeof this.options.onChange == 'function') {
                        this.options.onChange();
                    }

                    dateValue = dateValue ? dateValue : 'Never';

                    if (stateElement != null) {
                        stateElement.innerHTML = "<span>" + dateValue + "</span>";
                    }

                    // this.modal.close();

                } else {
                    // TODO.

                }

            }.bind(this)
        });
    }
});

EPF.widget.portfolio.AddViewersModal = Class.create({
    initialize : function(portfolioJson, options) {
        this.portfolioJson = portfolioJson;
        this.portfolioId = portfolioJson.id;
        this.options = Object.extend({}, options);

        var content = EPF.template.portfolio.portfolioTools.addViewers({portfolio:this.portfolioJson});

        this.modal = new EPF.widget.modal.Modal({
            title: 'Sharing - Add Viewers',
            content: content,
            onClose: this.options.onClose || Prototype.K
        });

        this.addViewerInput = this.modal.getContentParent().down('input[name=query]');
        new EPF.widget.InputTextInnerLabel(this.addViewerInput, 'E-mail address');

        var addViewerForm = this.modal.getContentParent().down('.addViewer form');
        var emailTextarea = this.modal.getContentParent().down('.emailForm textarea');

        new EPF.widget.InputTextInnerLabel(emailTextarea, 'Add a message');

        addViewerForm.observe('submit', function(event) {
            Event.stop(event);
            if (this.addViewerInput.value.strip() == '') {
                new dialog.Alert('Please enter an email address');
                return;
            }
            event.element().request({
                onSuccess: function(transport) {
                    this.addViewerInput.clear();
                    var resp = transport.responseJSON;
                    if (resp.stat == 'ok') {
                        try {
                            var usernames = this._getUsernamesToAdd();
                            if (usernames.indexOf(resp.data.viewer.username) != -1) {
                                new dialog.Alert('The user already exists in your list.');
                                return;
                            }


                            var content = EPF.template.portfolio.portfolioTools.viewer({viewer:resp.data.viewer});
                            var list = this.modal.getContentParent().down('.viewersList');
                            list.show().insert(content);
                            this.modal.center();
                            list.scrollTop = list.scrollHeight;
                            this._processAddUsersViewer(this.modal.getContentParent().down('.viewersList > li:last-of-type'));
                        } catch (e) {
                            EPF.log(e);
                        }
                    } else {
                        new dialog.Alert(resp.error);
                    }
                }.bind(this)
            });
        }.bind(this));

        var saveButton = this.modal.getContentParent().down('.buttons .save');
        saveButton.observe('click', this._onAddViewersSubmit.bind(this));
        var cancelButton = this.modal.getContentParent().down('.buttons .cancel');
        cancelButton.observe('click', function(event) {
            this.modal.close()
        }.bind(this));
    },

    _getUsernamesToAdd: function() {
        return this.modal.getContentParent().select('.viewersList > li > span.username').pluck('innerHTML');
    },

    _processAddUsersViewer: function(li) {
        var saveButton = this.modal.getContentParent().down('.buttons .save');

        var username = li.down('.username').innerHTML;
        li.down('.delete').observe('click', function(event) {
            event.stop();
            li.fade({ afterFinish : function() {
                li.remove();
                this.modal.center();
                var numViewersToAdd = this.modal.getContentParent().select('.viewersList > li').size();
                if (numViewersToAdd == 0) {
                    this.modal.getContentParent().down('.viewersList').hide();
                    saveButton.disabled = true;
                }
            }.bind(this) });
        }.bind(this));

        saveButton.disabled = false;
    },

    _onAddViewersSubmit: function(event) {
        var emailForm = this.modal.getContentParent().down('.emailForm');
        var sendEmailEl = emailForm.down('input[name=sendEmail]');
        var ccEl = emailForm.down('input[name=cc]');
        var messageEl = emailForm.down('textarea');
        var viewersList = this.modal.getContentParent().select('.viewersList > li');
        var parameters = {
            portfolioId: this.portfolioId,
            sendEmail: sendEmailEl.getValue() == 'true',
            cc: ccEl.getValue() == 'true',
            message: messageEl.hasClassName('defaulted') ? '' : messageEl.getValue(),
            username: viewersList.collect(function(e) {
                return e.down('.username').innerHTML
            })
        };
        
        new Ajax.Request('/portfolio/sharing/saveViewers', {
            parameters: parameters,
            onSuccess: function(transport) {
                var resp = transport.responseJSON;
                this.portfolioJson.viewers = resp.data.viewers;
                this.portfolioJson.numViewers = resp.data.viewers.size();

                var viewersEl = document.getElementById('viewers_' + this.portfolioId);
                
                if(viewersEl != null) {
                	var viewers_str = "";
                	var viewers_array = new Array();
                	
                	this.portfolioJson.viewers.each (
                		function(viewer) {
                			viewers_array.push(viewer.displayName);
                		}
                	);
                	
                	viewers_str = viewers_array.join(', ');
                	viewersEl.innerHTML = viewers_str;
                	
                }
                
                if (typeof this.options.onChange == 'function') {
                    this.options.onChange();
                }
                this.modal.close();
            }.bind(this)
        });
    }

});