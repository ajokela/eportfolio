EPF.namespace('controller.portfolioEdit.create');

EPF.controller.portfolioEdit.create.Main = Class.create(/** @lends EPF.controller.portfolioEdit.create.Main.prototype */ {

    /**
     * @constructs
     */
    initialize: function(options) {
        function updateCommunityTemplates() {
            $('communityTemplates').hide();
            $('indicator').show();
            new Ajax.Request('/communityTemplates.do', {
                parameters: {communityId: $F('communitySelect')},
                onComplete: function (transport) {
                    $('communityTemplates').update(transport.responseText);
                    $('indicator').hide();

                    var radios = $$('input[type="radio"][name="templateIdTemp"]');
                    if (!radios.pluck('checked').any()) {
                        radios.first().checked = true;
                    }

                    //new Effect.SlideDown('communityTemplates', {queue: 'end'});
                    $('communityTemplates').show();
                }
            });
        }

        $('communitySelect').observe('change', updateCommunityTemplates);

        $('templateTypeCustom').observe('click', function(e) {
            $('communitySelect').disable().selectedIndex = 0;
            $('communityTemplates').hide();
        });
        $('templateTypeCommunity').observe('click', function(e) {
            $('communitySelect').enable();
        });


        $$('.actions .create').first().observe('click', function(event) {
            var url = '/portfolio/createNew';
            if ($('templateTypeCommunity').checked) {
                var templateId = $$('input[type="radio"][name="templateIdTemp"]').find(function(e) { return e.checked; }).value;
                url += '?templateId=' + templateId;
            }
            window.location.href = url;
        });

        $('contentWrapper').scrollTop = 0;
        $('communitySelect').disable().selectedIndex = 0;
        $('templateTypeCustom').selectedIndex = 0;

        Event.observe(window, 'resize', this.resizePanes.bind(this));
        this.resizePanes();
    },


    /** @private */
    resizePanes: function() {
        var height = document.viewport.getHeight() - $('toolbar').getHeight();
        $('contentWrapper').setStyle({height: height + 'px'});
    }
});