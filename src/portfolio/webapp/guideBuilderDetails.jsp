<!-- $Name:  $ -->
<!-- $Id: guideBuilderDetails.jsp,v 1.8 2011/03/25 21:08:50 rkavajec Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>

<tags:communityPage community="${community}"
	pageTitle="Collection Guide Builder" id="guideBuilderDetails"
	cssClass="yui-skin-sam">
	<h2>Collection Guide Details</h2>
	<p>Below please enter the details and instructions that will appear
	for users of the collection guide.</p>
	<form action="guideBuilderDetails.do" method="post"
		class="newForm mediumForm"><input type="hidden" name="method"
		value="save" /> <input type="hidden" name="communityId"
		value="${community.id}" /> <input type="hidden" name="guideId"
		value="${guide.id}" />
	<fieldset>
	<ul>
		<li><label for="name"> Give the collection guide a name:
		</label> <input type="text" name="name" id="name" class="full"
			value="${guide.title}"></li>

		<li><label for="leftHeadingTitle"> Description: </label>
		<div id="descriptionWrapper"><textarea rows="" cols=""
			name="description" id="description"> 
						  <c:out value="${guide.description}" />
						</textarea></div>
		</li>
<div>
		<li><label for="textTip"> Enter tip: </label> <textarea rows="4"
			name="textTip" id="textTip" class="full">${guide.tip}</textarea></li>

		<li><label for="shareTip"> Share tip: </label> <textarea rows="4"
			name="shareTip" id="shareTip" class="full">${guide.shareTip}</textarea>
		</li>
</div>
	</ul>
	</fieldset>
	<fieldset class="submitSet"><input type="submit" name="next"
		value="Next: Sections and Elements" /></fieldset>
	</form>
	<div class="cleardiv"></div>
	<br />
	<br />
	<c:choose>
		<c:when test="${empty guideId}">
			<a href="/community/${community.id}">Cancel new collection guide
			creation</a>
		</c:when>
		<c:otherwise>
			<a
				href="/editCommunity.do?communityId=${community.id}&guideId=${guideId}">Cancel</a>
		</c:otherwise>
	</c:choose>
	<script>
	  new EPF.use(["editor"], "${applicationScope['org.portfolio.project.version']}", function(){
      var myEditor = new YAHOO.widget.Editor('description', {
        height: '250px',
        width: '100%',
        handleSubmit: true,
        toolbar: {
            buttons: [
                      { group: 'textstyle', 
                          buttons: [
                              { type: 'push', label: 'Bold CTRL + SHIFT + B', value: 'bold' },
                              { type: 'push', label: 'Italic CTRL + SHIFT + I', value: 'italic' },
                              { type: 'push', label: 'Underline CTRL + SHIFT + U', value: 'underline' }
                          ]
                      },
									    { type: 'separator' },
									    { group: 'parastyle', 
									        buttons: [
									        { type: 'select', label: 'Normal', value: 'heading', disabled: true,
									            menu: [
									                { text: 'Normal', value: 'none', checked: true },
									                { text: 'Header 1', value: 'h2' },
									                { text: 'Header 2', value: 'h3' },
									                { text: 'Header 3', value: 'h4' }
									            ]
									        }
									        ]
									    },
                      { type: 'separator' },
                      { group: 'indentlist', 
                          buttons: [
                              { type: 'push', label: 'Create an Unordered List', value: 'insertunorderedlist' },
                              { type: 'push', label: 'Create an Ordered List', value: 'insertorderedlist' }
                          ]
                      },
									    { type: 'separator' },
									    { group: 'insertitem', 
									        buttons: [
									            { type: 'push', label: 'HTML Link CTRL + SHIFT + L', value: 'createlink', disabled: true }
									        ]
									    }
									                      
                    ]
                }
    });
    myEditor.render();
  });
	</script>
</tags:communityPage>
