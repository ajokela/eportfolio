/* $Name:  $ */
/* $Id: viewPortfolio.js,v 1.10 2011/03/08 05:03:31 shee0109 Exp $ */
EPF.namespace('controller.viewPortfolio');
EPF.controller.viewPortfolio.Main = Class.create(
    /** @lends EPF.controller.viewPortfolio.Main.prototype */
{
    /**
     * Main controller for View Portfolio page.
     *
     * @constructs
     */
    initialize : function(portfolioId, options) {
    	
        this.portfolioId = portfolioId;
        this.options = Object.extend({
            autOpenShare: false
        }, options);

        if ($('tagsButton')) {
            var tagsButton = new YAHOO.widget.Button('tagsButton');
            tagsButton.on("click", function(oEvent) {
                YAHOO.util.Event.stopEvent(oEvent);
                this.showTagSelector();
            }.bind(this));
        }
        if ($('themeButton')) {
            var themeButton = new YAHOO.widget.Button('themeButton');
            themeButton.on("click", function(oEvent) {
                YAHOO.util.Event.stopEvent(oEvent);
                this.showThemeSwitcher();
            }.bind(this));
        }
        if ($('colorsButton')) {
            var colorsButton = new YAHOO.widget.Button('colorsButton');
            colorsButton.on("click", function(oEvent) {
                YAHOO.util.Event.stopEvent(oEvent);
                this.showColorSchemeSwitcher();
            }.bind(this));
        }

        if ($('sharingButton')) {
            var sharingButton = new YAHOO.widget.Button('sharingButton');
            sharingButton.on("click", function(oEvent) {
                YAHOO.util.Event.stopEvent(oEvent);
                new EPF.widget.portfolio.Sharing(this.portfolioId, {onShareChange: this.onShareChange.bind(this)});
            }.bind(this));
        }

        if ($('editButton')) {
            var editButton = new YAHOO.widget.Button('editButton');
        }

        if ($('returnButton')) {
            var returnButton = new YAHOO.widget.Button('returnButton');
        }

        var closeButton = new YAHOO.widget.Button('closeButton');
        closeButton.on('click', function(oEvent) {
            YAHOO.util.Event.stopEvent(oEvent);
            window.close();
        });

        if (this.options.autOpenShare) {
            new EPF.widget.portfolio.Sharing(this.portfolioId, {onShareChange: this.onShareChange.bind(this)});
        }
    },

    showColorSchemeSwitcher: function() {
        var content = EPF.template.controller.viewPortfolio.colorSchemeSwitcher();

        var modal = new EPF.widget.modal.Modal({
            title: 'Change Color Scheme',
            content: content,
            showCover: false,
            width: 300,
            contentCssClass: 'colorSchemeDialogContent',
            draggable: true,
            onClose: function() {
                new Ajax.Request('/portfolio/' + this.portfolioId + '/updateColorScheme', {
                    parameters: {
                        value: this.options.colorScheme
                    }
                });
            }.bind(this)
        });

        modal.getContentParent().select('input[name=colorScheme]').invoke('observe', 'click', function(event) {
            this.updateColorScheme(event.element().getValue());
        }.bind(this));

        modal.getContentParent().down('button.done').observe('click', function(event) {
            modal.close();
        }.bind(this));

        $$('input[name=colorScheme][value=' + this.options.colorScheme + ']')[0].checked = 'checked';
    },

    showThemeSwitcher: function() {
        var content = EPF.template.controller.viewPortfolio.themeSwitcher();

        var modal = new EPF.widget.modal.Modal({
            title: 'Change Theme',
            content: content,
            showCover: false,
            width: 300,
            contentCssClass: 'themeDialogContent',
            draggable: true,
            onClose: function() {
                new Ajax.Request('/portfolio/' + this.portfolioId + '/updateTheme', {
                    parameters: {
                        value: this.options.theme
                    }
                });
            }.bind(this)
        });

        modal.getContentParent().select('input[name=theme]').invoke('observe', 'click', function(event) {
            this.updateTheme(event.element().getValue());
        }.bind(this));

        modal.getContentParent().down('button.done').observe('click', function(event) {
            modal.close();
        }.bind(this));

        $$('input[name=theme][value=' + this.options.theme + ']')[0].checked = 'checked';

    },

    showTagSelector: function() {
        var content = EPF.template.controller.viewPortfolio.tagSelector({tags:[]});

        var modal = new EPF.widget.modal.Modal({
            title: 'Tags',
            content: content,
            showCover: false,
            width: 300,
            contentCssClass: 'tagsDialogContent',
            draggable: true,
            onClose: function() {
//                new Ajax.Request('/portfolio/' + this.portfolioId + '/updateTheme', {
//                    parameters: {
//                        value: this.options.theme
//                    }
//                });
            }.bind(this)
        });

        var processTag = function(li) {
            li.down('a.delete').observe('click', function(event){
                event.stop();
                event.element().up('li').remove();
            });
        };

        modal.getContentParent().select('ul.currentTags li').each(function(li){
            processTag(li);
        });

        var list = modal.getContentParent().down('ul.currentTags');
        var input = modal.getContentParent().down('input[name=tagText]');

        modal.getContentParent().down('button.add').observe('click', function(event){
            var li = Element.fromText(EPF.template.controller.viewPortfolio.tagSelectorTag({tag:$F(input)}));
            list.insert(li);
            processTag(li);
        });
    },

    onShareChange : function(event) {
        var portfolio = event.memo.portfolio;
        if ($('privateEntriesWarning')) {
            if (portfolio.publicView) {
                $('privateEntriesWarning').show();
            } else {
                $('privateEntriesWarning').hide();
            }
        }
    },

    updateColorScheme : function(newScheme) {
        this.options.colorScheme = newScheme;
        $$('.colorSchemeCss').invoke('remove');
        var html = EPF.template.controller.viewPortfolio.colorSchemeCss({theme:this.options.theme, colorScheme:newScheme});
        $$('head')[0].insert(html);
    },

    updateTheme : function(newTheme) {
        this.options.theme = newTheme;
        $$('.themeCss').invoke('remove');
        $$('.colorSchemeCss').invoke('remove');
        var html = EPF.template.controller.viewPortfolio.themeCss({theme:newTheme});
        html += EPF.template.controller.viewPortfolio.colorSchemeCss({theme:newTheme, colorScheme:this.options.colorScheme});
        $$('head')[0].insert(html);
    }
});
