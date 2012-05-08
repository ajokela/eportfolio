/* $Name:  $ */
/* $Id: modal.js,v 1.14 2011/02/16 04:26:45 shee0109 Exp $ */
EPF.namespace('widget.modal');

EPF.widget.modal.Modal = Class.create(/** @lends EPF.widget.modal.Modal.prototype */ {

    /**
     * Note that if the content has an element with the class 'fluid', that element will
     * resize along with the entire modal. That way toolbars and action buttons can stay
     * visible.
     *
     * @constructs
     * @param config
     * @param config.title the title of the modal window
     * @param {Number} [config.width] the width of the modal window, in px
     * @param [config.content] the html to use for the content
     * @param {Function} [config.onClose] onClose callback
     * @param [config.ajax] the ajax config, if using ajax to load content
     * @param [config.ajax.url] the url to request content from
     * @param [config.ajax.options] passed to the Prototype Ajax.Request
     * @param [config.showCover]
     * @param [config.contentCssClass]
     * @param [config.draggable]
     */
    initialize : function(config) {
        if (typeof config.showCover == 'undefined' || config.showCover) {
            EPF.widget.modal.Modal.showCover();
        }

        this.modalElement = Element.fromText(EPF.template.modal.modalWindow({title: config.title}));
        this.modalElement.down('.epf_modal_close').observe('click', this.close.bind(this));
        document.body.appendChild(this.modalElement);

        if (typeof config.onClose == 'function') {
            this.modalElement.observe('modal:closed', config.onClose);
        }

        if (config.width) {
            this.modalElement.style.width = config.width + 'px';
        }

        if (config.draggable) {
            this.modalElement.addClassName('draggable');
            new Draggable(this.modalElement, {handle:this.modalElement.down('.epf_modal_bar')});
        }

        this.contentElementWrapper = this.modalElement.down('.epf_modal_content_wrapper');
        this.contentElement = this.modalElement.down('.epf_modal_content');
        if (config.contentCssClass) {
            this.contentElement.addClassName(config.contentCssClass);
        }

        var ie6 = Prototype.Browser.IE && parseInt(navigator.userAgent.substring(navigator.userAgent.indexOf("MSIE") + 5)) == 6;
        if (ie6) {
            this.modalElement.setStyle({position:'absolute'});
        }

        if (config.ajax) {
            EPF.widget.modal.Modal.showLoading();

            var ajaxOptions = Object.extend({
                onSuccess : Prototype.emptyFunction
            }, config.ajax.options || {});

            ajaxOptions.onSuccess = ajaxOptions.onSuccess.wrap(function(proceed, transport) {
                this.updateContent(transport.responseText);
                proceed(transport);

                EPF.widget.modal.Modal.hideLoading();

                this.setMaxHeight();
                this.sizeFluidElement();
                this.modalElement.center();
                this.modalElement.setStyle({
                    visibility : 'visible'
                });
            }.bind(this));

            new Ajax.Request(config.ajax.url, ajaxOptions);
        } else if (config.content) {
            this.updateContent(config.content);

            this.setMaxHeight();
            this.sizeFluidElement();
            this.modalElement.center();
            this.modalElement.setStyle({
                visibility : 'visible'
            });
        }

        Event.observe(window, 'resize', function(event) {
            this.setMaxHeight();
            this.sizeFluidElement();
            this.modalElement.center();
        }.bind(this));
    },

    /**
     * Move the modal to the center of the viewing area.
     */
    center: function() {
        this.modalElement.center();
    },

    /**
     * Topmost element.
     */
    getModalElement: function() {
        return this.modalElement;
    },

    /**
     * First ancestor of the given content.
     */
    getContentParent: function() {
        return this.contentElement;
    },

    hide: function() {
        this.modalElement.hide();
    },

    show: function() {
        this.modalElement.show();
    },

    /**
     * Close the modal.
     */
    close: function() {
        this.modalElement.remove();
        if ($$('.epf_modal:not(#epf_modal_loading)').size() == 0) {
            EPF.widget.modal.Modal.hideCover();
        }
        this.modalElement.fire('modal:closed');
    },

    /** @private */
    updateContent: function(text) {
        this.contentElement.update(text);
    },

    /** @private */
    setMaxHeight: function() {
        var windowSize = document.viewport.getDimensions();
        var headerHeight = this.modalElement.down('.epf_modal_bar').getHeight();
        if (windowSize.height) {
            $(this.contentElementWrapper).setStyle({maxHeight: ((windowSize.height - headerHeight) * .9) + 'px'});
        }
    },

    /** @private */
    sizeFluidElement : function() {
        try {
            var fluidElement = this.modalElement.down('.fluid');
            if (fluidElement) {
                var headerHeight = this.modalElement.down('.epf_modal_bar').getHeight();
                var modalHeight = this.modalElement.clientHeight;
                var targetContentHeight = modalHeight - headerHeight;
                var actualContentHeight = this.contentElement.getHeight();
                var diff = targetContentHeight - actualContentHeight;
                var fluidElement = this.modalElement.down('.fluid');
                if (diff < 0) {
                    var fluidElementHeight = parseInt(fluidElement.getStyle('height'));
                    var newFluidElementHeight = fluidElementHeight + diff;
                    fluidElement.style.height = newFluidElementHeight + 'px';
                }
            }
        } catch(e) {
            EPF.log(e);
        }

    }
});

/**
 * Static helper methods for modals
 */
Object.extend(EPF.widget.modal.Modal, /** @lends EPF.widget.modal.Modal */ {
    showLoading : function() {
        var loadingEl = $('epf_modal_loading');
        if (!loadingEl) {
            loadingEl = new Element('div', {
                'class' : 'epf_modal loading',
                id : 'epf_modal_loading'
            });
            document.body.appendChild(loadingEl);
        }
        loadingEl.center();
        loadingEl.show();
    },
    hideLoading : function() {
        $('epf_modal_loading').hide();
    },
    showCover : function() {
        var coverEl = $('epf_modal_cover');
        if (!coverEl) {
            var windowSize = document.viewport.getDimensions();
            if (windowSize.height) {
                var height = Math.max(Math.max(document.body.offsetHeight, windowSize.height),
                        document.documentElement.scrollHeight) + 'px';
                var width = Math.max(document.body.offsetWidth, windowSize.width) + 'px';
                coverEl = new Element('div', {
                    'class' : 'epf_modal_cover',
                    id : 'epf_modal_cover'
                }).setStyle({
                                height : height,
                                width : width,
                                filter: 'alpha(opacity=50)'

                            });
                document.body.appendChild(coverEl);
            }
        }
        coverEl.show();
    },
    hideCover : function() {
        if ($('epf_modal_cover')) {
            $('epf_modal_cover').hide();
        }
    },
    closeAll : function() {
        document.fire('modal:closed');
        EPF.widget.modal.Modal.hideCover();
        $$('.epf_modal').invoke('remove');
    }
});
