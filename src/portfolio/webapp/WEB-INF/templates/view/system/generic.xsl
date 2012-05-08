<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:include href="./generic/AcadPlan.xsl" />
	<xsl:include href="./generic/AdditionalPhoto.xsl" />
	<xsl:include href="./generic/AdditionalSkills.xsl" />
	<xsl:include href="./generic/Address.xsl" />
	<xsl:include href="./generic/AddressPS.xsl" />
	<xsl:include href="./generic/Advisor.xsl" />
	<xsl:include href="./generic/AssessScores.xsl" />
	<xsl:include href="./generic/Awards.xsl" />
	<xsl:include href="./generic/CarDocumentation.xsl" />
	<xsl:include href="./generic/CarPlan.xsl" />
	<xsl:include href="./generic/Certificates.xsl" />
	<xsl:include href="./generic/CoCurricular.xsl" />
	<xsl:include href="./generic/CollegeMajorPS.xsl" />
	<xsl:include href="./generic/Committee.xsl" />
	<xsl:include href="./generic/Communication.xsl" />
	<xsl:include href="./generic/ComputerSkills.xsl" />
	<xsl:include href="./generic/DegRequirement.xsl" />
	<xsl:include href="./generic/DegreeApasPS.xsl" />
	<xsl:include href="./generic/DemographicPS.xsl" />
	<xsl:include href="./generic/Diversity.xsl" />
	<xsl:include href="./generic/EduDocumentation.xsl" />
	<xsl:include href="./generic/EducHistory.xsl" />
	<xsl:include href="./generic/Email.xsl" />
	<xsl:include href="./generic/EmailAddressPS.xsl" />
	<xsl:include href="./generic/Exhibition.xsl" />
	<xsl:include href="./generic/Expertise.xsl" />
	<xsl:include href="./generic/ExploreInventory.xsl" />
	<xsl:include href="./generic/GradCommMember.xsl" />
	<xsl:include href="./generic/GradPlan.xsl" />
	<xsl:include href="./generic/GradThesis.xsl" />
	<xsl:include href="./generic/Graduation.xsl" />
	<xsl:include href="./generic/Grants.xsl" />
	<xsl:include href="./generic/Holds.xsl" />
	<xsl:include href="./generic/Honors.xsl" />
	<xsl:include href="./generic/IdNumber.xsl" />
	<xsl:include href="./generic/IdNumberPS.xsl" />
	<xsl:include href="./generic/InfoInterview.xsl" />
	<xsl:include href="./generic/InformationSkills.xsl" />
	<xsl:include href="./generic/Interests.xsl" />
	<xsl:include href="./generic/KolbLearning.xsl" />
	<xsl:include href="./generic/Language.xsl" />
	<xsl:include href="./generic/Leadership.xsl" />
	<xsl:include href="./generic/LearningDiff.xsl" />
	<xsl:include href="./generic/material.xsl" />
	<xsl:include href="./generic/MaterialStandalone.xsl" />
	<xsl:include href="./generic/Mentors.xsl" />
	<xsl:include href="./generic/MyersBriggs.xsl" />
	<xsl:include href="./generic/Name.xsl" />
	<xsl:include href="./generic/NamePS.xsl" />
	<xsl:include href="./generic/NameUmphotoPS.xsl" />
	<xsl:include href="./generic/PerDocumentation.xsl" />
	<xsl:include href="./generic/Performance.xsl" />
	<xsl:include href="./generic/Phone.xsl" />
	<xsl:include href="./generic/PhonePS.xsl" />
	<xsl:include href="./generic/PhotoPS.xsl" />
	<xsl:include href="./generic/Presentation.xsl" />
	<xsl:include href="./generic/ProDocumentation.xsl" />
	<xsl:include href="./generic/ProfActivities.xsl" />
	<xsl:include href="./generic/ProfDevelopment.xsl" />
	<xsl:include href="./generic/ProfDevelopmentPS.xsl" />
	<xsl:include href="./generic/ProfInterests.xsl" />
	<xsl:include href="./generic/ProfMemberships.xsl" />
	<xsl:include href="./generic/ProfSkills.xsl" />
	<xsl:include href="./generic/Publications.xsl" />
	<xsl:include href="./generic/Quant.xsl" />
	<xsl:include href="./generic/RecDocumentation.xsl" />
	<xsl:include href="./generic/recognitionDocumentation.xsl" />
	<xsl:include href="./generic/RecognitionDocuments.xsl" />
	<xsl:include href="./generic/References.xsl" />
	<xsl:include href="./generic/RegQueuePS.xsl" />
	<xsl:include href="./generic/Research.xsl" />
	<xsl:include href="./generic/ResidencyPS.xsl" />
	<xsl:include href="./generic/RoleModel.xsl" />
	<xsl:include href="./generic/Scholarships.xsl" />
	<xsl:include href="./generic/SelfDirSearch.xsl" />
	<xsl:include href="./generic/SensoryModality.xsl" />
	<xsl:include href="./generic/Service.xsl" />
	<xsl:include href="./generic/SkiDocumentation.xsl" />
	<xsl:include href="./generic/standard.xsl" />
	<xsl:include href="./generic/StrongIntInventory.xsl" />
	<xsl:include href="./generic/StudentTerm.xsl" />
	<xsl:include href="./generic/Teaching.xsl" />
	<xsl:include href="./generic/TrackingFlagsPS.xsl" />
	<xsl:include href="./generic/TransSkills.xsl" />
	<xsl:include href="./generic/Travel.xsl" />
	<xsl:include href="./generic/UnofficialTranscriptPS.xsl" />
	<xsl:include href="./generic/VisaStatusPS.xsl" />
	<xsl:include href="./generic/WorkHistory.xsl" />


	<xsl:template match="ospiTemplate">
		<table style="width:90%" class="pTitle">
			<tr>
				<td>
					<h1>
						<xsl:value-of select="@name" />
					</h1>
				</td>
				<td align="right" class="headerImg">
					<p align="right"></p>
				</td>
			</tr>
		</table>
		<table cellpadding="3" cellspacing="0" border="0" width="90%">
			<tr valign="middle">
				<td colspan="4">
					<table cellpadding="3" cellspacing="0" border="0" width="100%">
						<tr valign="top">
							<td class="pDescription">
								<p>
									<xsl:if test="string-length(@description) > 0">
										<strong>Description</strong>
										<br />
										<xsl:value-of select="@description" />
									</xsl:if>
								</p>
							</td>
							<td class="pMetaData">
								<p>
									<strong>
										Shared By:
										<xsl:value-of select="sharer/@sharedBy" />
									</strong>
									<br />
									<strong>User Name: </strong>
									<xsl:value-of select="sharer/@userName" />
									<br />
									<strong>Email: </strong>
									<a>
										<xsl:attribute name="href">mailto:<xsl:value-of
											select="sharer/@email"></xsl:value-of></xsl:attribute>
										<xsl:value-of select="sharer/@email" />
									</a>
									<br />
									<strong>Date Created: </strong>
									<xsl:value-of select="@dateCreated" />
								</p>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<xsl:for-each select="category">
			<div class="pHeadingL1">
				<div class="openImg">
					<xsl:attribute name="id">opencat<xsl:value-of
						select="position()" /></xsl:attribute>
					<xsl:attribute name="style">display:none;</xsl:attribute>
					<a>
						<xsl:attribute name="href">javascript:expandCollapse('cat<xsl:value-of
							select="position()" />');</xsl:attribute>
						<img src="/images/rtArWonG.gif" width="15px" height="15px"
							alt="Click to show category" />
					</a>
				</div>
				<div class="closeImg">
					<xsl:attribute name="id">closecat<xsl:value-of
						select="position()" /></xsl:attribute>
					<a>
						<xsl:attribute name="href">javascript:expandCollapse('cat<xsl:value-of
							select="position()" />');</xsl:attribute>
						<img src="/images/dwnArWonG.gif" width="15px" height="15px"
							alt="Click to hide category" />
					</a>
				</div>
				<h1>
					<xsl:value-of select="@title" />
				</h1>
			</div>
			<div>
				<xsl:attribute name="id">cat<xsl:value-of
					select="position()" /></xsl:attribute>
				<xsl:for-each select="subcategory">
					<div class="pHeadingL2">
						<xsl:value-of select="@title" />
					</div>
					<table width="90%" border="0" cellspacing="0" cellpadding="3"
						class="pelementTable">
						<xsl:for-each select="*">
							<tr valign="top">
								<td width="100%">
									<xsl:apply-templates select=".">
										<xsl:with-param name="entryName" select="@title" />
									</xsl:apply-templates>
									<xsl:apply-templates select="./elementStandards" />
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</xsl:for-each>
			</div>
			<br />
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>