<?xml version="1.0" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<!-- Template for XHTML tags to FO tags Source:http://www.antennahouse.com/XSLsample/XSLsample.htm -->
	<!--<xsl:include href="./generic/xhtml.fo.xsl"/> -->
	<xsl:include href="./generic/xhtml2fo.xsl" />
	<xsl:include href="./generic/AcadPlan.fo.xsl" />
	<xsl:include href="./generic/AdditionalPhoto.fo.xsl" />
	<xsl:include href="./generic/AdditionalSkills.fo.xsl" />
	<xsl:include href="./generic/Address.fo.xsl" />
	<xsl:include href="./generic/AddressPS.fo.xsl" />
	<xsl:include href="./generic/Advisor.fo.xsl" />
	<xsl:include href="./generic/AssessScores.fo.xsl" />
	<xsl:include href="./generic/Awards.fo.xsl" />
	<xsl:include href="./generic/CarDocumentation.fo.xsl" />
	<xsl:include href="./generic/CarPlan.fo.xsl" />
	<xsl:include href="./generic/Certificates.fo.xsl" />
	<xsl:include href="./generic/CoCurricular.fo.xsl" />
	<xsl:include href="./generic/CollegeMajorPS.fo.xsl" />
	<xsl:include href="./generic/Committee.fo.xsl" />
	<xsl:include href="./generic/Communication.fo.xsl" />
	<xsl:include href="./generic/ComputerSkills.fo.xsl" />
	<xsl:include href="./generic/DegRequirement.fo.xsl" />
	<xsl:include href="./generic/DegreeApasPS.fo.xsl" />
	<xsl:include href="./generic/DemographicPS.fo.xsl" />
	<xsl:include href="./generic/Diversity.fo.xsl" />
	<xsl:include href="./generic/EduDocumentation.fo.xsl" />
	<xsl:include href="./generic/EducHistory.fo.xsl" />
	<xsl:include href="./generic/Email.fo.xsl" />
	<xsl:include href="./generic/EmailAddressPS.fo.xsl" />
	<xsl:include href="./generic/Exhibition.fo.xsl" />
	<xsl:include href="./generic/Expertise.fo.xsl" />
	<xsl:include href="./generic/ExploreInventory.fo.xsl" />
	<xsl:include href="./generic/GradCommMember.fo.xsl" />
	<xsl:include href="./generic/GradPlan.fo.xsl" />
	<xsl:include href="./generic/GradThesis.fo.xsl" />
	<xsl:include href="./generic/Graduation.fo.xsl" />
	<xsl:include href="./generic/Grants.fo.xsl" />
	<xsl:include href="./generic/Holds.fo.xsl" />
	<xsl:include href="./generic/Honors.fo.xsl" />
	<xsl:include href="./generic/IdNumber.fo.xsl" />
	<xsl:include href="./generic/IdNumberPS.fo.xsl" />
	<xsl:include href="./generic/InfoInterview.fo.xsl" />
	<xsl:include href="./generic/InformationSkills.fo.xsl" />
	<xsl:include href="./generic/Interests.fo.xsl" />
	<xsl:include href="./generic/KolbLearning.fo.xsl" />
	<xsl:include href="./generic/Language.fo.xsl" />
	<xsl:include href="./generic/Leadership.fo.xsl" />
	<xsl:include href="./generic/LearningDiff.fo.xsl" />
	<xsl:include href="./generic/material.fo.xsl" />
	<xsl:include href="./generic/MaterialStandalone.fo.xsl" />
	<xsl:include href="./generic/Mentors.fo.xsl" />
	<xsl:include href="./generic/MyersBriggs.fo.xsl" />
	<xsl:include href="./generic/Name.fo.xsl" />
	<xsl:include href="./generic/NamePS.fo.xsl" />
	<xsl:include href="./generic/NameUmphotoPS.fo.xsl" />
	<xsl:include href="./generic/PerDocumentation.fo.xsl" />
	<xsl:include href="./generic/Performance.fo.xsl" />
	<xsl:include href="./generic/Phone.fo.xsl" />
	<xsl:include href="./generic/PhonePS.fo.xsl" />
	<xsl:include href="./generic/PhotoPS.fo.xsl" />
	<xsl:include href="./generic/Presentation.fo.xsl" />
	<xsl:include href="./generic/ProDocumentation.fo.xsl" />
	<xsl:include href="./generic/ProfActivities.fo.xsl" />
	<xsl:include href="./generic/ProfDevelopment.fo.xsl" />
	<xsl:include href="./generic/ProfDevelopmentPS.fo.xsl" />
	<xsl:include href="./generic/ProfInterests.fo.xsl" />
	<xsl:include href="./generic/ProfMemberships.fo.xsl" />
	<xsl:include href="./generic/ProfSkills.fo.xsl" />
	<xsl:include href="./generic/Publications.fo.xsl" />
	<xsl:include href="./generic/Quant.fo.xsl" />
	<xsl:include href="./generic/RecDocumentation.fo.xsl" />
	<xsl:include href="./generic/recognitionDocumentation.fo.xsl" />
	<xsl:include href="./generic/RecognitionDocuments.fo.xsl" />
	<xsl:include href="./generic/References.fo.xsl" />
	<xsl:include href="./generic/RegQueuePS.fo.xsl" />
	<xsl:include href="./generic/Research.fo.xsl" />
	<xsl:include href="./generic/ResidencyPS.fo.xsl" />
	<xsl:include href="./generic/RoleModel.fo.xsl" />
	<xsl:include href="./generic/Scholarships.fo.xsl" />
	<xsl:include href="./generic/SelfDirSearch.fo.xsl" />
	<xsl:include href="./generic/SensoryModality.fo.xsl" />
	<xsl:include href="./generic/Service.fo.xsl" />
	<xsl:include href="./generic/SkiDocumentation.fo.xsl" />
	<xsl:include href="./generic/standard.fo.xsl" />
	<xsl:include href="./generic/StrongIntInventory.fo.xsl" />
	<xsl:include href="./generic/StudentTerm.fo.xsl" />
	<xsl:include href="./generic/Teaching.fo.xsl" />
	<xsl:include href="./generic/TrackingFlagsPS.fo.xsl" />
	<xsl:include href="./generic/TransSkills.fo.xsl" />
	<xsl:include href="./generic/Travel.fo.xsl" />
	<xsl:include href="./generic/UnofficialTranscriptPS.fo.xsl" />
	<xsl:include href="./generic/VisaStatusPS.fo.xsl" />
	<xsl:include href="./generic/WorkHistory.fo.xsl" />


	<xsl:template match="ospiTemplate">

		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="all-pages"
					page-width="auto" page-height="auto" margin-top="0.25in"
					margin-bottom="0.25in" margin-left="0.25in" margin-right="0.25in">
					<fo:region-body xsl:use-attribute-sets="region-body" />
					<fo:region-before display-align="before" extent="1in"
						region-name="page-header" />
					<fo:region-after display-align="after" extent="1in"
						region-name="page-footer" />
					<fo:region-start extent="0.5in" />
					<fo:region-end extent="0.5in" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="all-pages">
				<fo:static-content flow-name="page-footer"
					display-align="after">
					<fo:block xsl:use-attribute-sets="footer"><!--Page <fo:page-number/> -->
						University of Minnesota Portfolio
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body" xsl:use-attribute-sets="body">
					<fo:table xsl:use-attribute-sets="table.pTitle">
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell>
									<fo:block xsl:use-attribute-sets="pTitle.h1"
										text-align="left">
										<xsl:value-of select="@name" />
									</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block text-align="right">
										<fo:external-graphic
											xsl:use-attribute-sets="pTitle.headerImg" />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>

					<fo:table width="100%" table-layout="fixed"
						border-collapse="separate">
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell xsl:use-attribute-sets="td.pDescription">
									<fo:block text-align="left">
										<xsl:if test="string-length(@description) > 0">
											<fo:inline font-weight="bold">Description</fo:inline>
											<fo:block></fo:block>
											<xsl:value-of select="@description" />
										</xsl:if>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell xsl:use-attribute-sets="td.pMetaData">
									<fo:block text-align="right">
										<fo:inline font-weight="bold">Shared By:</fo:inline>
										<xsl:value-of select="sharer/@sharedBy" />
										<fo:block></fo:block>
										<fo:inline font-weight="bold">User Name:</fo:inline>
										<xsl:value-of select="sharer/@userName" />
										<fo:block></fo:block>
										<fo:inline font-weight="bold">Email:</fo:inline>
										<fo:basic-link>
											<xsl:attribute name="external-destination">url(mailto:<xsl:value-of
												select="sharer/@email" />)</xsl:attribute>
											<fo:inline text-decoration="underline">
												<xsl:value-of select="sharer/@email" />
											</fo:inline>
										</fo:basic-link>
										<fo:block></fo:block>
										<fo:inline font-weight="bold">Date Created:</fo:inline>
										<xsl:value-of select="@dateCreated" />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>

					<fo:block></fo:block>
					<fo:block></fo:block>

					<xsl:for-each select="category">

						<fo:block xsl:use-attribute-sets="pHeadingL1">
							<xsl:value-of select="@title" />
						</fo:block>
						<xsl:for-each select="subcategory">
							<fo:block xsl:use-attribute-sets="pHeadingL2">
								<xsl:value-of select="@title" />
							</fo:block>
							<xsl:for-each select="*">
								<fo:block xsl:use-attribute-sets="pElementBlock">
									<xsl:apply-templates select=".">
										<xsl:with-param name="entryName" select="@title" />
									</xsl:apply-templates>
									<xsl:apply-templates select="./elementStandards" />
								</fo:block>
							</xsl:for-each>
						</xsl:for-each>
					</xsl:for-each>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>


</xsl:stylesheet>